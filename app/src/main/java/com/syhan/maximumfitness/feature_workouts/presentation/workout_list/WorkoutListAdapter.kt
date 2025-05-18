package com.syhan.maximumfitness.feature_workouts.presentation.workout_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.syhan.maximumfitness.R
import com.syhan.maximumfitness.databinding.WorkoutCardBinding
import com.syhan.maximumfitness.feature_workouts.data.WorkoutType
import com.syhan.maximumfitness.feature_workouts.data.getMinutesDeclension
import com.syhan.maximumfitness.feature_workouts.presentation.workout_list.state.WorkoutCardState

class WorkoutListAdapter : ListAdapter<WorkoutCardState, WorkoutListAdapter.WorkoutStateViewHolder>(
    WorkoutListDiffCallback
) {

    inner class WorkoutStateViewHolder(val binding: WorkoutCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: WorkoutCardState) {
            binding.apply {
                title.text = item.title

                type.text = root.resources.getString(
                    WorkoutType.entries[item.type -1].typeName
                )

                description.text = item.description
                    ?: root.resources.getString(R.string.no_description)

                durationRow.visibility = if (item.duration == -1) View.GONE else View.VISIBLE

                duration.text = root.resources.getString(
                    getMinutesDeclension(item.duration),
                    item.duration
                )

                expandDescriptionButton.setOnClickListener {

                }
                root.setOnClickListener {

                }
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