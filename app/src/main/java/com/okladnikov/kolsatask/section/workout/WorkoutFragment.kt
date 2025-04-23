package com.okladnikov.kolsatask.section.workout

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.toRoute
import com.okladnikov.kolsatask.R
import com.okladnikov.kolsatask.domain.Workout
import com.okladnikov.kolsatask.section.workout.viewmodel.WorkoutViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WorkoutFragment : Fragment() {

    private val viewModel: WorkoutViewModel by viewModels()

    private lateinit var workout: Workout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("WorkoutFragment", "onCreate: viewModel.screenState.value=${viewModel.screenState.value}")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
         workout= findNavController().getBackStackEntry<Workout>().toRoute<Workout>()
        return inflater.inflate(R.layout.fragment_workout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val textView = view.findViewById<TextView>(R.id.fragment_workout_text)
        textView.text = "$workout"
    }
}
