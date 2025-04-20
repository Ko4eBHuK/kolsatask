package com.okladnikov.kolsatask.section.workouts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.okladnikov.kolsatask.R
import com.okladnikov.kolsatask.domain.Workout
import com.okladnikov.kolsatask.domain.WorkoutType
import com.okladnikov.kolsatask.section.workouts.viewmodel.WorkoutsScreenIntent
import com.okladnikov.kolsatask.section.workouts.viewmodel.WorkoutsViewModel
import com.okladnikov.kolsatask.ui.theme.PurpleGrey40
import com.okladnikov.kolsatask.utils.ErrorAlertDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutsScreen(
    navController: NavController,
    viewModel: WorkoutsViewModel
) {
    val screenState by viewModel.screenState.collectAsState()

    if (screenState.isError) {
        ErrorAlertDialog(
            onDismissRequest = { viewModel.processIntent(WorkoutsScreenIntent.CloseError) },
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

        PullToRefreshBox(
            isRefreshing = screenState.isLoading,
            onRefresh = { viewModel.processIntent(WorkoutsScreenIntent.Refresh) },
            modifier = Modifier.padding(paddingValues)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (screenState.workouts.isNotEmpty()) {
                    screenState.workouts.forEach { workout ->
                        item {
                            WorkoutItem(workout = workout) { navController.navigate(workout) }
                        }
                    }
                } else {
                    item {
                        val columnHeight = LocalConfiguration.current.screenHeightDp.dp - paddingValues.calculateTopPadding()
                        Column(
                            modifier = Modifier.height(columnHeight),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Тренировок нет :(",
                                color = Color.Gray,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            )
                            IconButton(
                                onClick = {
                                    viewModel.processIntent(WorkoutsScreenIntent.Refresh)
                                }
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_refresh),
                                    contentDescription = "refresh workouts",
                                    tint = Color.Gray
                                )
                            }
                        }
                    }
                }
            }

        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun WorkoutItem(
    workout: Workout,
    onClick: () -> Unit
) {
    ElevatedCard(
        onClick = onClick,
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
            pressedElevation = 0.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(.85f)
            ) {
                Text(
                    text = workout.title,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Описание: ${workout.description}",
                    modifier = Modifier.padding(top = 10.dp)
                )
                FlowRow(
                    modifier = Modifier.fillMaxWidth()
                        .padding(top = 10.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Row {
                        val workoutType = WorkoutType from workout.type
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
                            contentDescription = "type",
                            tint = PurpleGrey40
                        )
                        Text(workout.duration)
                    }
                }
            }

            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_open),
                contentDescription = "Open ticket",
                tint = PurpleGrey40
            )
        }
    }
}
