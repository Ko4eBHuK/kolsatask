@file:OptIn(ExperimentalLayoutApi::class)

package com.okladnikov.kolsatask.section.workout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.ui.PlayerView
import androidx.navigation.NavController
import com.okladnikov.kolsatask.R
import com.okladnikov.kolsatask.domain.WorkoutType
import com.okladnikov.kolsatask.section.workout.viewmodel.WorkoutScreenIntent
import com.okladnikov.kolsatask.section.workout.viewmodel.WorkoutScreenState
import com.okladnikov.kolsatask.section.workout.viewmodel.WorkoutViewModel
import com.okladnikov.kolsatask.ui.theme.PurpleGrey40
import com.okladnikov.kolsatask.utils.ErrorAlertDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutScreen(
    navController: NavController,
    viewModel: WorkoutViewModel
) {
    val screenState by viewModel.screenState.collectAsState()

    if (screenState.isError) {
        ErrorAlertDialog(
            onDismissRequest = { viewModel.processIntent(WorkoutScreenIntent.CloseError) },
            dialogText = screenState.message
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(R.string.workouts_screen_title))
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Black,
                    titleContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues),
        ) {
            item {
                Media3Player(
                    screenState = screenState,
                    viewModel = viewModel
                )
            }

            item {
                WorkoutInfo(screenState = screenState)
            }
        }
    }
}

@Composable
private fun WorkoutInfo(
    screenState: WorkoutScreenState
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            contentColor = Color.Black,
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 5.dp,
            pressedElevation = 5.dp
        )
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(.85f)
        ) {
            if (screenState.workout != null) {
                Text(
                    text = screenState.workout.title,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Описание: ${screenState.workout.description}",
                    modifier = Modifier.padding(top = 10.dp)
                )
                FlowRow(
                    modifier = Modifier.fillMaxWidth()
                        .padding(top = 10.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Row {
                        val workoutType = WorkoutType from screenState.workout.type
                        Icon(
                            painter = painterResource(id = R.drawable.ic_workout_type),
                            contentDescription = "type",
                            tint = when (workoutType) {
                                WorkoutType.TRAINING -> Color.Red
                                WorkoutType.STREAM -> Color.Blue
                                WorkoutType.COMPLEX -> Color.Green
                            }
                        )
                        Text(workoutType.title)
                    }
                    Row {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_workout_time),
                            contentDescription = "duration",
                            tint = PurpleGrey40
                        )
                        Text(screenState.workout.duration)
                    }
                }
            }
        }
    }
}

@Composable
private fun Media3Player(
    screenState: WorkoutScreenState,
    viewModel: WorkoutViewModel
) {
    DisposableEffect(Unit) {
        onDispose {
            viewModel.processIntent(WorkoutScreenIntent.StopPlayer)
        }
    }

    Column {
        AndroidView(
            modifier = Modifier.fillMaxWidth(),
            factory = { context ->
                PlayerView(context).apply {
                    player = screenState.exoPlayer
                }
            },
            update = { playerView ->
                playerView.player = screenState.exoPlayer
            }
        )

        /*Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {
                    viewModel.processIntent(WorkoutScreenIntent.StartPlayer)
                }
            ) {
                Text("Play")
            }
            Button(
                onClick = {
                    viewModel.processIntent(WorkoutScreenIntent.PausePlayer)
                }
            ) {
                Text("Pause")
            }

            Button(
                onClick = {
                    viewModel.processIntent(WorkoutScreenIntent.SeekPlayer(-10_000))
                }
            ) {
                Text("Seek -10s")
            }
            Button(
                onClick = {
                    viewModel.processIntent(WorkoutScreenIntent.SeekPlayer(10_000))
                }
            ) {
                Text("Seek +10s")
            }
        }*/
    }
}
