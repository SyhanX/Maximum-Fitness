package com.syhan.maximumfitness.feature_workouts.presentation.workout_list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.syhan.maximumfitness.common.data.NetworkRequestUiState
import com.syhan.maximumfitness.databinding.FragmentWorkoutListBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val TAG = "WorkoutListFragment"
class WorkoutListFragment : Fragment() {

    private var _binding: FragmentWorkoutListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: WorkoutListViewModel by viewModel()

    private lateinit var recyclerView: RecyclerView
    private val workoutListAdapter = WorkoutListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWorkoutListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding.workoutRecyclerView
        setupRecyclerView()
        submitDataToRecyclerView()

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.networkUiState.collect { uiState ->
                when (uiState) {
                    NetworkRequestUiState.Loading -> {
                        Log.d(TAG, "onViewCreated: loading")
                    }
                    is NetworkRequestUiState.Error -> {
                        Log.d(TAG, "onViewCreated: error ${uiState.type}")
                    }
                    NetworkRequestUiState.Success -> {
                        Log.d(TAG, "onViewCreated: success")
                    }
                }
            }
        }
    }


    private fun setupRecyclerView() {
        recyclerView.apply {
            adapter = workoutListAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun submitDataToRecyclerView() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.listState.collect { state ->
                workoutListAdapter.submitList(state.list)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}