package com.dosti.scamfolio.ui.view

import android.graphics.drawable.Icon
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.constraintlayout.compose.*
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.*
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.*
import androidx.constraintlayout.widget.Placeholder
import com.dosti.scamfolio.ui.theme.DarkGreyScam
import com.dosti.scamfolio.ui.theme.custom


@Composable
fun HomeView() {
    var credentials by remember { mutableStateOf(Credentials()) }


    BackgroundGradient()
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
    ) {
        LogoText()
        Spacer(modifier = Modifier.height(100.dp))

        usernameField(
            value = "",
            onChange = { data -> credentials = credentials.copy(login = data)},
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(20.dp))
        passwordField(
            value = "",
            onChange = { data -> credentials = credentials.copy(pwd = data)},
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(100.dp))
        SubmitButton()
        Spacer(modifier = Modifier.height(30.dp))
        CreateAccountButton()
        Spacer(modifier = Modifier.height(40.dp))
/*
        ClickableText(
            text = AnnotatedString("Credits"),
            onClick = { Toast.makeText(LocalContext.current, "Made by [authors]", Toast.LENGTH_SHORT).show() }
        ) {
        }

 */
    }
}

@Composable
fun BackgroundGradient() {
    val brush = Brush.verticalGradient(listOf(Color(0xFF1E1E1E), Color.DarkGray))
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush)
    )
}

data class Credentials(
    var login: String = "",
    var pwd: String = "",
    var remember: Boolean = false
) {
    fun isNotEmpty(): Boolean {
        return login.isNotEmpty() && pwd.isNotEmpty()
    }
}

@Composable
fun LogoText() {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = "Scamfolio",
            fontSize = 60.sp,
            fontFamily = custom,
            color = Color.White,
            modifier = Modifier
        )

        Text(
            text = "login",
            fontSize = 50.sp,
            fontFamily = custom,
            color = Color.White,
        )
    }
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun usernameField(
    value: String,
    onChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Username"
) {
    val icon = @Composable {
        Icon(
            Icons.Default.Person,
            contentDescription = "",
            tint = Color.White
        )
    }

    TextField(
        value = "",
        onValueChange = onChange,
        label = { Text(text = "Username", color = Color.White) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.White,
            unfocusedBorderColor = Color.White
        ),
        leadingIcon = icon
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun passwordField(
    value: String,
    onChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Password"
) {
    val icon = @Composable {
        Icon(
            Icons.Default.Lock,
            contentDescription = "",
            tint = Color.White
        )
    }

    TextField(
        value = "",
        onValueChange = onChange,
        label = { Text(text = "Password", color = Color.White) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.White,
            unfocusedBorderColor = Color.White
        ),
        leadingIcon = icon
    )
}

@Composable
fun SubmitButton() {
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
                    text = "Login",
                    fontSize = 40.sp,
                    fontFamily = custom,
                    color = Color.White
                )
            }

        }
    }
}

@Composable
fun CreateAccountButton() {
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
                    text = "Create account",
                    fontSize = 25.sp,
                    fontFamily = custom,
                    color = Color.White
                )
            }

        }
    }
}