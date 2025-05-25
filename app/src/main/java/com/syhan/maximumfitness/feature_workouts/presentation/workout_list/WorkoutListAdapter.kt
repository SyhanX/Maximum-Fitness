package com.syhan.maximumfitness.feature_workouts.presentation.workout_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.syhan.maximumfitness.R
import com.syhan.maximumfitness.databinding.WorkoutCardBinding
import com.syhan.maximumfitness.feature_workouts.data.WorkoutType
import com.syhan.maximumfitness.feature_workouts.data.getMinutesDeclension
import com.syhan.maximumfitness.feature_workouts.presentation.workout_list.state.WorkoutCardState
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

private const val TAG = "WorkoutListAdapter"

class WorkoutListAdapter : ListAdapter<WorkoutCardState, WorkoutListAdapter.WorkoutStateViewHolder>(
    WorkoutListDiffCallback
) {

    inner class WorkoutStateViewHolder(val binding: WorkoutCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: WorkoutCardState) {
            val currentTypeIndex = WorkoutType.entries[item.type -1]
            binding.apply {
                val resources = root.resources

                title.text = item.title
                type.text = resources.getString(currentTypeIndex.typeName)
                description.text = item.description ?: resources.getString(R.string.no_description)

                if (item.duration == -1) {
                    durationIcon.visibility = View.GONE
                    durationText.visibility = View.GONE
                } else {
                    durationText.text = resources.getString(
                        getMinutesDeclension(item.duration),
                        item.duration
                    )
                }
                type.setChipBackgroundColorResource(currentTypeIndex.typeColor)
                type.setTextColor(root.context.getColor(currentTypeIndex.textColor))
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WorkoutStateViewHolder {
        val view = WorkoutCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WorkoutStateViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: WorkoutStateViewHolder,
        position: Int
    ) {
        val item = currentList[position]
        holder.bind(item)

        holder.binding.root.setOnClickListener {
            val action = WorkoutListFragmentDirections
                .actionWorkoutListFragmentToWorkoutDetailsFragment(
                    Json.encodeToString(
                        WorkoutCardState(
                            item.id,
                            item.title,
                            item.description,
                            item.type,
                            item.duration
                        )
                    )
                )
            holder.itemView.findNavController().navigate(action)
        }
    }
}

private object WorkoutListDiffCallback : DiffUtil.ItemCallback<WorkoutCardState>() {
    override fun areItemsTheSame(
        oldItem: WorkoutCardState,
        newItem: WorkoutCardState
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: WorkoutCardState,
        newItem: WorkoutCardState
    ): Boolean = oldItem.hashCode() == newItem.hashCode() && oldItem == newItem
}