package com.dosti.scamfolio.ui.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dosti.scamfolio.R
import com.dosti.scamfolio.ui.theme.custom

@Composable
fun SignInScreen() {
    BackgroundGradient()
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
    ) {
        var username by rememberSaveable { mutableStateOf("") }
        var password by rememberSaveable { mutableStateOf("") }

        SignInLogoText()
        Spacer(modifier = Modifier.height(100.dp))

        usernameField(
            value = username,
            onValueChange = { username = it },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(20.dp))
        
        SignInPasswordField(
            value = password,
            onValueChange = { password = it },
        )

        Spacer(modifier = Modifier.height(100.dp))
        
        SignInButton() { onSubmit() }
        Spacer(modifier = Modifier.height(30.dp))
    }
}

@Composable
fun SignInLogoText() {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.app_name),
            fontSize = 60.sp,
            fontFamily = custom,
            color = Color.White,
            modifier = Modifier
        )

        Text(
            text = stringResource(R.string.create_account),
            fontSize = 50.sp,
            fontFamily = custom,
            color = Color.White,
        )
    }
}


@Composable
fun SignInPasswordField(
    value: String,
    onValueChange: (String) -> Unit,
) {
    val icon = @Composable {
        Icon(
            Icons.Default.Lock,
            contentDescription = "",
            tint = Color.White
        )
    }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = "Password", color = Color.White) },
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedTextColor = Color.White,
            focusedTextColor = Color.White,
            focusedBorderColor = Color.White,
            unfocusedBorderColor = Color.White,
            unfocusedLabelColor = Color.White
        ),
        singleLine = true,
        leadingIcon = icon
    )
}

@Composable
fun SignInButton(
    onClick: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Button(
            onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4BC096)),
            border = BorderStroke(4.dp, Color.White),
            modifier = Modifier
                .size(300.dp, 80.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(R.string.submit),
                    fontSize = 40.sp,
                    fontFamily = custom,
                    color = Color.White
                )
            }

        }
    }
}

private fun onSubmit() {}