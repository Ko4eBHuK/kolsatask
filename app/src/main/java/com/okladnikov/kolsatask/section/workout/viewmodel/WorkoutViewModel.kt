package com.okladnikov.kolsatask.section.workout.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.okladnikov.kolsatask.section.workout.data.WorkoutRepository
import com.okladnikov.kolsatask.utils.Status.ERROR
import com.okladnikov.kolsatask.utils.Status.LOADING
import com.okladnikov.kolsatask.utils.Status.SUCCESS
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.core.net.toUri
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import com.okladnikov.kolsatask.BuildConfig
import kotlinx.coroutines.withContext

@HiltViewModel
class WorkoutViewModel @Inject constructor(
    private val workoutRepository: WorkoutRepository,
    private val exoPlayer: ExoPlayer
) : ViewModel() {
    private val _screenState = MutableStateFlow(WorkoutScreenState(exoPlayer = exoPlayer))
    val screenState: StateFlow<WorkoutScreenState>
        get() = _screenState

    private var playerPosition: Long = 0L

    init {
        exoPlayer.addListener(
            object : Player.Listener {
                override fun onPlayerError(error: PlaybackException) {
                    handleError(error)
                }
            }
        )
    }

    fun processIntent(intent: WorkoutScreenIntent) {
        when (intent) {
            WorkoutScreenIntent.LoadVideoInfo -> {
                if (screenState.value.workout != null) {
                    screenState.value.workout?.id?.let { workoutId ->
                        fetchWorkoutVideoInfo(workoutId)
                    }
                } else {
                    _screenState.value = _screenState.value.copy(
                        isLoading = false,
                        isError = true,
                        message = "Информация о тренировке не была получена."
                    )
                }
            }
            WorkoutScreenIntent.CloseError -> {
                _screenState.value = _screenState.value.copy(isError = false)
            }
            is WorkoutScreenIntent.SetWorkout -> {
                _screenState.value = _screenState.value.copy(workout = intent.workout)
                fetchWorkoutVideoInfo(intent.workout.id)
            }
            WorkoutScreenIntent.SavePlayerState -> {
                playerPosition = exoPlayer.currentPosition
            }
            WorkoutScreenIntent.StopPlayer -> {
                playerPosition = exoPlayer.currentPosition
                exoPlayer.stop()
            }
            WorkoutScreenIntent.PausePlayer -> {
                exoPlayer.pause()
            }
            WorkoutScreenIntent.StartPlayer -> {
                exoPlayer.play()
            }
            is WorkoutScreenIntent.SeekPlayer -> {
                exoPlayer.seekTo(playerPosition + intent.seekTime)
            }
        }
    }

    private fun fetchWorkoutVideoInfo(workoutId: Int) = viewModelScope.launch(Dispatchers.IO) {
        workoutRepository.getWorkoutVideo(workoutId).collect { videoInfoCallState ->
            when (videoInfoCallState.status) {
                LOADING -> {
                    _screenState.value = _screenState.value.copy(
                        isLoading = true,
                        message = videoInfoCallState.message ?: "Загрузка"
                    )
                }

                ERROR -> {
                    _screenState.value = _screenState.value.copy(
                        isLoading = false,
                        isError = true,
                        message = videoInfoCallState.message ?: "Ошибка"
                    )
                }

                SUCCESS -> {
                    if (videoInfoCallState.data != null) {
                        _screenState.value = _screenState.value.copy(
                            isLoading = false,
                            videoWorkout = videoInfoCallState.data
                        )

                        // configure exoPlayer
                        withContext(Dispatchers.Main) {
                            exoPlayer.apply {
                                val videoLink = BuildConfig.BASE_URL + videoInfoCallState.data.link
                                val mediaItem = MediaItem.fromUri(videoLink.toUri())
                                setMediaItem(mediaItem)
                                prepare()
                                playWhenReady = true
                                seekTo(playerPosition)
                            }
                        }
                    } else {
                        _screenState.value = _screenState.value.copy(
                            isLoading = false,
                            isError = true,
                            message = "Нет данных о видео"
                        )
                    }
                }
            }
        }
    }

    private fun handleError(error: PlaybackException) {
        when (error.errorCode) {
            PlaybackException.ERROR_CODE_IO_NETWORK_CONNECTION_FAILED -> {
                Log.d("WorkoutViewModel exoPlayer", "Network connection error")
            }
            PlaybackException.ERROR_CODE_IO_FILE_NOT_FOUND -> {
                Log.d("WorkoutViewModel exoPlayer", "File not found")
            }
            PlaybackException.ERROR_CODE_DECODER_INIT_FAILED -> {
                Log.d("WorkoutViewModel exoPlayer", "Decoder initialization error")
            }
            else -> {
                Log.d("WorkoutViewModel exoPlayer", "Other error: ${error.message}")
            }
        }
    }
}
