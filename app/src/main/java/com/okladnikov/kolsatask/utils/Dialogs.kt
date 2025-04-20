package com.okladnikov.kolsatask.utils

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.okladnikov.kolsatask.R

@Composable
fun LoadingDialog(
    dialogText: String,
) {
    Dialog(
        onDismissRequest = {
            // No logic
        }
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Text(
                text = dialogText,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.Center)
                    .padding(16.dp),
                textAlign = TextAlign.Center,
            )
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 16.dp,
                        bottom = 16.dp
                    ),
            )
        }
    }
}

@Composable
fun ErrorAlertDialog(
    onDismissRequest: () -> Unit,
    dialogTitle: String = stringResource(id = R.string.error_title),
    dialogText: String,
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            Button(
                onClick = onDismissRequest,
                modifier = Modifier
                    .padding(5.dp)
                    .height(48.dp),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 2.dp,
                    pressedElevation = 0.dp
                ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFE0000)
                )
            ) {
                Text(
                    text = "OK",
                    fontSize = 24.sp
                )
            }
        },
        title = { Text(text = dialogTitle) },
        text = { Text(text = dialogText) },
    )
}
