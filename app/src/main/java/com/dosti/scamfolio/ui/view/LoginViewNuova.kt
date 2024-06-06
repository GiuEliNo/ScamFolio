package com.dosti.scamfolio.ui.view

import android.content.Context
import android.content.res.Configuration
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
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dosti.scamfolio.R
import android.widget.Toast
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.dosti.scamfolio.db.entities.User
import com.dosti.scamfolio.ui.theme.custom
import com.dosti.scamfolio.viewModel.LoginViewModel
import com.dosti.scamfolio.viewModel.ViewModelFactory


@Composable
fun MainLoginScreen(
    viewModelStoreOwner: ViewModelStoreOwner,
    viewModelFactory: ViewModelFactory
){
    val viewModel=ViewModelProvider(viewModelStoreOwner, viewModelFactory)[LoginViewModel::class.java]
    val currentScreen by remember{ viewModel.currentScreen}
        when(currentScreen){
            LoginViewModel.loginScreens.LOGIN -> LoginView1( viewModelStoreOwner, viewModelFactory, onNavigateToRegister = {viewModel.navigateToRegister()}, onLoginSuccess = {viewModel.navigateToHome()})
            LoginViewModel.loginScreens.REGISTER -> SignInView(viewModelStoreOwner, viewModelFactory, onBackButton={viewModel.navigateToLogin()})
            LoginViewModel.loginScreens.HOME -> ComposeCryptoPages(viewModelFactory, viewModelStoreOwner)
        }
}



@Composable
fun LoginView1(
    viewModelStoreOwner: ViewModelStoreOwner,
    factory: ViewModelFactory,
    onNavigateToRegister:  () -> Unit,
    onLoginSuccess: () -> Unit,
) {
    val viewModel= ViewModelProvider(viewModelStoreOwner, factory)[LoginViewModel::class.java]
    var username by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    val loginResult by viewModel.loginResult.collectAsState()
    val configuration= LocalConfiguration.current

    when (configuration.orientation) {
        Configuration.ORIENTATION_PORTRAIT -> {
            LoginViewPortraitLayout(
                viewModel,
                username,
                onUsernameChange = { username = it },
                password,
                onPasswordChange = { password = it },
                loginResult,
                onNavigateToRegister,
                onLoginSuccess
            )
        }
        Configuration.ORIENTATION_LANDSCAPE -> {
            LoginViewLandscapeLayout(
                viewModel,
                username,
                onUsernameChange = { username = it },
                password,
                onPasswordChange = { password = it },
                loginResult,
                onNavigateToRegister,
                onLoginSuccess
            )

        }
        Configuration.ORIENTATION_UNDEFINED-> {
            LoginViewPortraitLayout(
                viewModel,
                username,
                onUsernameChange = { username = it },
                password,
                onPasswordChange = { password = it },
                loginResult,
                onNavigateToRegister,
                onLoginSuccess
            )
        }
    }
}


@Composable
fun LoginViewPortraitLayout(viewModel: LoginViewModel,
                            username: String,
                            onUsernameChange: (String) -> Unit,
                            password: String,
                            onPasswordChange: (String) -> Unit,
                            loginResult: User?,
                            onNavigateToRegister:  () -> Unit,
                            onLoginSuccess: () -> Unit,
                            )
{
        if (loginResult==null) {
            BackgroundGradient1()
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                LogoText1()
                Spacer(modifier = Modifier.height(100.dp))

                usernameField1(
                    value = username,
                    onValueChange = onUsernameChange,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(20.dp))
                passwordField1(
                    value = password,
                    onValueChange = onPasswordChange,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(100.dp))
                SubmitButtonPortrait(
                    onClick = {
                        onSubmitLogin(username, password, viewModel)
                    },
                    username = username,
                    password = password,
                    Modifier
                )
                Spacer(modifier = Modifier.height(30.dp))
                CreateAccountButtonPortrait(onNavigateToRegister,modifier = Modifier)
                Spacer(modifier = Modifier.height(40.dp))
            }
        } else {

            Toast.makeText(LocalContext.current, "Logged in correctly!", Toast.LENGTH_SHORT).show()
            onLoginSuccess()

        }

}





@Composable
fun LoginViewLandscapeLayout(viewModel: LoginViewModel,
                            username: String,
                            onUsernameChange: (String) -> Unit,
                            password: String,
                            onPasswordChange: (String) -> Unit,
                            loginResult: User?,
                             onNavigateToRegister:  () -> Unit,
                             onLoginSuccess: () -> Unit)
{
    if (loginResult==null) {
        BackgroundGradient1()
        Row(
            modifier=Modifier.fillMaxSize(),
        ) {
            Column(modifier = Modifier
                .fillMaxHeight()
                .weight(1f))
            {
                LogoText1()
            }
            Column(modifier = Modifier
                .fillMaxHeight()
                .weight(1f))
            {
                usernameField1(value = username,
                    onValueChange = onUsernameChange,
                    modifier = Modifier.fillMaxWidth())

                passwordField1(
                    value = password,
                    onValueChange = onPasswordChange,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(30.dp))

                Row(modifier = Modifier) {

                    SubmitButtonLandScape(
                        onClick = {
                            onSubmitLogin(username, password, viewModel)
                        },
                        username = username,
                        password = password,
                        Modifier.weight(1f)
                    )
                    Spacer(modifier=Modifier.width(75.dp))

                    CreateAccountButtonLandscape(onNavigateToRegister,modifier = Modifier)
                }
            }
        }

    } else {

            Toast.makeText(LocalContext.current, "Logged in correctly!", Toast.LENGTH_SHORT).show()
            onLoginSuccess()
        }

}


@Composable
fun BackgroundGradient1() {
    val brush = Brush.verticalGradient(listOf(Color(0xFF1E1E1E), Color.DarkGray))
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush)
    )
}

@Composable
fun LogoText1() {
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




@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun usernameField1(
    value: String,
    onValueChange: (String) -> Unit,
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
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = "Username", color = Color.White) },
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedTextColor = Color.White,
            focusedTextColor = Color.White,
            focusedBorderColor = Color.White,
            unfocusedBorderColor = Color.White,
            unfocusedLabelColor = Color.White
        ),
        leadingIcon = icon,
        singleLine = true
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun passwordField1(
    value: String,
    onValueChange: (String) -> Unit,
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
    var textPassword by remember { mutableStateOf("") }
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
        leadingIcon = icon,
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        /*
        trailingIcon = {
            val image = if (passwordVisible)
                Icons.Filled.Visibility
            else Icons.Filled.VisibilityOff

            val description = if (passwordVisible) "Hide password" else "Show password"

            IconButton(onClick = {passwordVisible = !passwordVisible}){
                Icon(imageVector  = image, description)
            }
        }

         */
    )
}

@Composable
fun SubmitButtonLandScape(
    onClick: () -> Unit,
    username: String,
    password: String,
    modifier: Modifier
) {
    Column(
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
    ) {
        Button(
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4BC096)),
            border = BorderStroke(4.dp, Color.White),
            modifier = Modifier
                .size(100.dp, 50.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(R.string.login),
                    fontSize = 10.sp,
                    fontFamily = custom,
                    color = Color.White
                )
            }

        }
    }
}

@Composable
fun CreateAccountButtonLandscape(
    onClick: () -> Unit,
    modifier: Modifier) {
    Column(
        horizontalAlignment = Alignment.End,
        modifier = Modifier
    ) {
        Button(
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4BC096)),
            border = BorderStroke(4.dp, Color.White),
            modifier = Modifier
                .size(100.dp, 50.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(R.string.create_account),
                    fontSize = 10.sp,
                    fontFamily = custom,
                    color = Color.White
                )
            }

        }
    }
}


@Composable
fun SubmitButtonPortrait(
    onClick: () -> Unit,
    username: String,
    password: String,
    modifier: Modifier
) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Button(
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4BC096)),
            border = BorderStroke(4.dp, Color.White),
            modifier = Modifier
                .size(300.dp, 80.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(R.string.login),
                    fontSize = 40.sp,
                    fontFamily = custom,
                    color = Color.White
                )
            }

        }
    }
}

@Composable
fun CreateAccountButtonPortrait(
    onClick: () -> Unit,
    modifier: Modifier
) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Button(
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4BC096)),
            border = BorderStroke(4.dp, Color.White),
            modifier = Modifier
                .size(300.dp, 80.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(R.string.create_account),
                    fontSize = 25.sp,
                    fontFamily = custom,
                    color = Color.White
                )
            }

        }
    }
}
private fun onSubmitLogin(username : String, password : String, viewModel : LoginViewModel ) {
    viewModel.checkLogin(username, password)
}











@Composable
fun test1() {
    Text(text = "hi android!")
}



@Composable
fun SignInView(
    viewModelStoreOwner: ViewModelStoreOwner,
    factory: ViewModelFactory,
    onBackButton: () -> Unit
) {
    val context= LocalContext.current
    val viewModel=ViewModelProvider(viewModelStoreOwner, factory)[LoginViewModel::class.java]
    var username by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    val configuration= LocalConfiguration.current
    when(configuration.orientation) {
        Configuration.ORIENTATION_PORTRAIT-> {
            SignInViewPortraitLayout(
                viewModel,
                username,
                onUsernameChange ={username = it},
                password,
                onPasswordChange = {password=it},
                onBackButton
            )
        }
        Configuration.ORIENTATION_LANDSCAPE -> {
            SignInViewLandscapeLayout(
                viewModel,
                username,
                onUsernameChange ={username = it},
                password,
                onPasswordChange = {password=it},
                onBackButton
            )
        }
        Configuration.ORIENTATION_UNDEFINED -> {
            SignInViewPortraitLayout(
                viewModel,
                username,
                onUsernameChange ={username = it},
                password,
                onPasswordChange = {password=it},
                onBackButton
            )
        }
    }
}

@Composable
fun SignInLogoText1() {
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
fun SignInPasswordField1(
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
fun SignInButtonPortrait(
    username: String,
    password: String,
    viewModel: LoginViewModel,
    context: Context,
    onClick: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Button(
            onClick = { onSubmitRegister(username, password, viewModel, context) },
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

private fun onSubmitRegister(
    username: String,
    password: String,
    viewModel: LoginViewModel,
    context: Context
) {
    viewModel.createNewUser(username, password)
    Toast.makeText(context, "Sign in completed", Toast.LENGTH_SHORT).show()
}


@Composable
fun SignInViewPortraitLayout(
    viewModel: LoginViewModel,
    username: String,
    onUsernameChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    onBackButton: () -> Unit
) {
    var context = LocalContext.current
    BackgroundGradient()
    Column (
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
    ) {

        SignInLogoText1()
        Spacer(modifier = Modifier.height(100.dp))

        usernameField(
            value = username,
            onValueChange = onUsernameChange,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(20.dp))

        SignInPasswordField1(
            value = password,
            onValueChange = onPasswordChange,
        )

        Spacer(modifier = Modifier.height(100.dp))

        SignInButtonPortrait(username, password, viewModel, LocalContext.current) {
            onSubmitRegister(
                username,
                password,
                viewModel,
                context
            )
        }
        Spacer(modifier = Modifier.height(30.dp))
        BackButtonPortrait(onBackButton)
    }
}


@Composable
fun BackButtonPortrait(
    onClick: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Button(
            onClick = onClick ,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4BC096)),
            border = BorderStroke(4.dp, Color.White),
            modifier = Modifier
                .size(300.dp, 80.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(R.string.back),
                    fontSize = 40.sp,
                    fontFamily = custom,
                    color = Color.White
                )
            }

        }
    }
}

@Composable
fun SignInViewLandscapeLayout(
    viewModel: LoginViewModel,
    username: String,
    onUsernameChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    onBackButton: () -> Unit
    ){
    var context= LocalContext.current
    BackgroundGradient1()
    Row(
        modifier=Modifier.fillMaxSize(),
    ) {
        Column(modifier = Modifier
            .fillMaxHeight()
            .weight(1f))
        {
            SignInLogoText1()
        }
        Column(modifier = Modifier
            .fillMaxHeight()
            .weight(1f))
        {
            usernameField1(value = username,
                onValueChange = onUsernameChange,
                modifier = Modifier.fillMaxWidth())

            passwordField1(
                value = password,
                onValueChange = onPasswordChange,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(30.dp))

            Row(modifier = Modifier) {

                SignInButtonLandscape(username, password, viewModel, LocalContext.current) {
                    onSubmitRegister(
                        username,
                        password,
                        viewModel,
                        context
                    )
                }
                Spacer(modifier=Modifier.width(75.dp))

                BackButtonLandscape(onBackButton)
            }
        }
    }
}

@Composable
fun SignInButtonLandscape(
    username: String,
    password: String,
    viewModel: LoginViewModel,
    context: Context,
    onClick: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
    ) {
        Button(
            onClick = { onSubmitRegister(username, password, viewModel, context) },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4BC096)),
            border = BorderStroke(4.dp, Color.White),
            modifier = Modifier
                .size(100.dp, 50.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(R.string.submit),
                    fontSize = 10.sp,
                    fontFamily = custom,
                    color = Color.White
                )
            }

        }
    }
}

@Composable
fun BackButtonLandscape(
    onClick: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
    ) {
        Button(
            onClick = onClick ,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4BC096)),
            border = BorderStroke(4.dp, Color.White),
            modifier = Modifier
                .size(100.dp, 50.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(R.string.back),
                    fontSize = 10.sp,
                    fontFamily = custom,
                    color = Color.White
                )
            }

        }
    }
}

