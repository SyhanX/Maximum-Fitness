package com.syhan.maximumfitness.feature_workouts.presentation.workout_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.OptIn
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.navigation.fragment.findNavController
import com.syhan.maximumfitness.R
import com.syhan.maximumfitness.common.data.ErrorType
import com.syhan.maximumfitness.common.data.NetworkUiState
import com.syhan.maximumfitness.common.data.setGone
import com.syhan.maximumfitness.common.data.setVisible
import com.syhan.maximumfitness.common.di.RetrofitConstants.BASE_URL
import com.syhan.maximumfitness.databinding.FragmentWorkoutDetailsBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val TAG = "WorkoutDetailsFragment"

class WorkoutDetailsFragment : Fragment() {

    private var _binding: FragmentWorkoutDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: WorkoutDetailsViewModel by viewModel()

    private var player: ExoPlayer? = null
    private var playWhenReady = true
    private var mediaItemIndex = 0
    private var playbackPosition = 0L

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWorkoutDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeNetworkState()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeNetworkState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.networkState.collectLatest { networkState ->
                when (networkState) {
                    is NetworkUiState.Error -> {
                        binding.errorLayout.root.setVisible()
                        binding.errorLayout.errorText.text =
                            if (networkState.type == ErrorType.ConnectionError) {
                                getString(R.string.no_connection_error)
                            } else {
                                getString(R.string.unexpected_error)
                            }
                        binding.loadingLayout.root.setGone()
                        binding.detailsLayout.setGone()
                    }

                    NetworkUiState.Loading -> {
                        binding.loadingLayout.root.setVisible()
                        binding.detailsLayout.setGone()
                        binding.errorLayout.root.setGone()
                    }

                    NetworkUiState.Success -> {
                        binding.errorLayout.root.setGone()
                        binding.loadingLayout.root.setGone()
                        binding.detailsLayout.setVisible()
                        setupUi()
                    }
                }
            }
        }
    }

    private fun setupUi() {
        binding.apply {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.detailsState.collectLatest {
                    detailsAppbar.title = it.title
                    detailsAppbar.setNavigationOnClickListener {
                        findNavController().navigateUp()
                    }
                    txtDescription.text = it.description
                    if (it.description == null) descriptionCard.setGone()
                }
            }
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.videoState.collectLatest {
                    durationTxt.text = resources.getString(R.string.duration_min, it.duration)
                }
            }
        }
    }

    @OptIn(UnstableApi::class)
    private fun initializePlayer() {
        player = ExoPlayer.Builder(requireContext())
            .build()
            .also { exoPlayer ->
                exoPlayer.trackSelectionParameters = exoPlayer.trackSelectionParameters
                    .buildUpon()
                    .setMaxVideoSizeSd()
                    .build()
                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.videoState.collectLatest {
                        val videoUrl = BASE_URL + it.link
                        val mediaItem = MediaItem.fromUri(videoUrl)
                        exoPlayer.setMediaItems(
                            listOf(mediaItem),
                            mediaItemIndex,
                            playbackPosition
                        )
                        exoPlayer.playWhenReady = playWhenReady
                    }
                }
            }
        binding.playerView.apply {
            player = this@WorkoutDetailsFragment.player
            setShowFastForwardButton(false)
            setShowRewindButton(false)
            setShowNextButton(false)
            setShowPreviousButton(false)
        }
        player?.prepare()
    }

    private fun releasePlayer() {
        player?.let { exoPlayer ->
            playWhenReady = exoPlayer.playWhenReady
            mediaItemIndex = exoPlayer.currentMediaItemIndex
            playbackPosition = exoPlayer.currentPosition
            exoPlayer.release()
        }
        player = null
    }

    override fun onPause() {
        super.onPause()
        releasePlayer()
    }

    override fun onStop() {
        super.onStop()
        releasePlayer()
    }

    override fun onStart() {
        super.onStart()
        initializePlayer()
    }

    override fun onResume() {
        super.onResume()
        if (player == null) initializePlayer()
    }
}