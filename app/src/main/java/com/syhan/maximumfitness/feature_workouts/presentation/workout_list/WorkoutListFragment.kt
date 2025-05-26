package com.syhan.maximumfitness.feature_workouts.presentation.workout_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.syhan.maximumfitness.R
import com.syhan.maximumfitness.common.data.ErrorType
import com.syhan.maximumfitness.common.data.NetworkUiState
import com.syhan.maximumfitness.common.data.setGone
import com.syhan.maximumfitness.common.data.setVisible
import com.syhan.maximumfitness.databinding.FragmentWorkoutListBinding
import com.syhan.maximumfitness.feature_workouts.presentation.workout_list.state.SortByType
import kotlinx.coroutines.flow.collectLatest
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

        observeNetworkState()
    }

    private fun setupUi() {
        recyclerView = binding.workoutRecyclerView
        setupRecyclerView()
        submitDataToRecyclerView()
        binding.apply {
            searchView.editText.doOnTextChanged { inputText, _, _, _ ->
                viewModel.onSearchTextChange(inputText.toString())
            }
            searchView.editText.setOnEditorActionListener { textView, _, _ ->
                viewModel.doSearch()
                searchBar.setText(textView.text.toString())
                searchView.hide()
                return@setOnEditorActionListener false
            }
            sortingMenu.setOnItemClickListener { _, _, _, id ->
                viewModel.changeSortingType(
                    when (id.toInt()) {
                        1 -> SortByType.SortByExercise
                        2 -> SortByType.SortByAether
                        3 -> SortByType.SortByComplex
                        else -> SortByType.SortByDefault
                    }
                )
            }
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.listState.collectLatest {
                    if (it.isSearching) {
                        sortingDropdown.setGone()
                    } else sortingDropdown.setVisible()
                }
            }
        }
    }

    private fun observeNetworkState() {
        binding.apply {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.networkUiState.collect { uiState ->
                    when (uiState) {
                        NetworkUiState.Loading -> {
                            progressIndicator.root.setVisible()
                            errorMessage.errorLayout.setGone()
                            workoutRecyclerView.setGone()
                        }

                        is NetworkUiState.Error -> {
                            progressIndicator.root.setGone()
                            errorMessage.errorLayout.setVisible()
                            workoutRecyclerView.setGone()
                            errorMessage.errorText.text =
                                if (uiState.type == ErrorType.ConnectionError) {
                                    getString(R.string.no_connection_error)
                                } else {
                                    getString(R.string.unexpected_error)
                                }
                            errorMessage.retryBtn.setOnClickListener {
                                viewModel.retryLoadingWorkoutList()
                            }
                        }

                        NetworkUiState.Success -> {
                            setupUi()
                            progressIndicator.root.setGone()
                            errorMessage.errorLayout.setGone()
                            workoutRecyclerView.setVisible()
                        }
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
                workoutListAdapter.submitList(
                    when {
                        state.isSearching && state.searchResults.isNotEmpty() -> state.searchResults
                        state.sortedList.isNotEmpty() -> state.sortedList
                        else -> state.list
                    }
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        val types = resources.getStringArray(R.array.dropdown_items)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, types)
        binding.sortingMenu.setAdapter(arrayAdapter)
    }
}