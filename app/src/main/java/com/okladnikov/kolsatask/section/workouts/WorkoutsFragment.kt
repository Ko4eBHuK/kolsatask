package com.okladnikov.kolsatask.section.workouts

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.okladnikov.kolsatask.R
import com.okladnikov.kolsatask.domain.Workout
import com.okladnikov.kolsatask.section.workouts.viewmodel.WorkoutsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WorkoutsFragment : Fragment() {

    private val viewModel: WorkoutsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("WorkoutsFragment", "onCreate: viewModel.screenState.value=${viewModel.screenState.value}")

        lifecycleScope.launch {
            delay(2000)
            val testWorkout = Workout(
                id = 1,
                title = "testWorkout title",
                description = "testWorkout description",
                type = 3,
                duration = "15 min",
            )
            findNavController().navigate(testWorkout)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_workouts, container, false)
    }
}
