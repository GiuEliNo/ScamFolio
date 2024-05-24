package com.dosti.scamfolio.ui.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dosti.scamfolio.ui.theme.custom

@Composable
fun Homepage() {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray)
    ) {
        val username by rememberSaveable() { mutableStateOf("") }
        val search by rememberSaveable() { mutableStateOf("Search cryptos") }
        val personal by rememberSaveable() { mutableStateOf("Personal area") }
        val settings by rememberSaveable { mutableStateOf("Settings") }

        TopText(username = username)
        Spacer(modifier = Modifier.height(50.dp))
        
        GenericButton(buttonText = search)
        GenericButton(buttonText = personal)
        GenericButton(buttonText = settings)
    }
}

@Composable
fun TopText(
    username: String
) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = "Welcome back,",
            fontSize = 60.sp,
            fontFamily = custom,
            color = Color.White,
        )

        Text(
            text = username,
            fontSize = 60.sp,
            fontFamily = custom,
            color = Color.White,
        )
    }
}

@Composable
fun GenericButton(
    buttonText : String
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
                    text = buttonText,
                    fontSize = 40.sp,
                    fontFamily = custom,
                    color = Color.White
                )
            }
        }
    }
    
    Spacer(modifier = Modifier.height(20.dp))
}

