package com.dosti.scamfolio.ui.view

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.preference.PreferenceManager
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
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material3.IconButton
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.dosti.scamfolio.SharedPrefRepository
import com.dosti.scamfolio.db.entities.User
import com.dosti.scamfolio.ui.theme.BackgroundGradient
import com.dosti.scamfolio.ui.theme.custom
import com.dosti.scamfolio.viewModel.LoginViewModel
import com.dosti.scamfolio.viewModel.ViewModelFactory


@Composable
fun MainLoginScreen(
    viewModelStoreOwner: ViewModelStoreOwner,
    viewModelFactory: ViewModelFactory,
    sharedPrefRepository: SharedPrefRepository
){
    val viewModel=ViewModelProvider(viewModelStoreOwner, viewModelFactory)[LoginViewModel::class.java]
    val currentScreen by remember{ viewModel.currentScreen}

        when(currentScreen){
            LoginViewModel.loginScreens.LOGIN -> LoginView( viewModel, onNavigateToRegister = {viewModel.navigateToRegister()}, sharedPrefRepository = sharedPrefRepository)
            LoginViewModel.loginScreens.REGISTER -> SignInView(viewModel, onBackButton={viewModel.navigateToLogin()})
            LoginViewModel.loginScreens.HOME -> ComposeCryptoPages(viewModelFactory, viewModel, sharedPrefRepository)
        }
}



@Composable
fun LoginView(
    viewModel: LoginViewModel,
    onNavigateToRegister:  () -> Unit,
    sharedPrefRepository: SharedPrefRepository
) {
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
                { onLoginSuccess(viewModel, sharedPrefRepository, username) }
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
                { onLoginSuccess(viewModel, sharedPrefRepository, username) }
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
                { onLoginSuccess(viewModel, sharedPrefRepository, username) }
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
                    value = username,
                    onValueChange = onUsernameChange,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(20.dp))
                passwordField(
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

            Toast.makeText(LocalContext.current,
                stringResource(R.string.logged_in_correctly), Toast.LENGTH_SHORT).show()
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
        BackgroundGradient()
        Row(
            modifier=Modifier.fillMaxSize(),
        ) {
            Column(modifier = Modifier
                .fillMaxHeight()
                .weight(1f))
            {
                LogoText()
            }
            Column(modifier = Modifier
                .fillMaxHeight()
                .weight(1f))
            {
                usernameField(value = username,
                    onValueChange = onUsernameChange,
                    modifier = Modifier.fillMaxWidth())

                passwordField(
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

            Toast.makeText(LocalContext.current, stringResource(R.string.logged_in_correctly), Toast.LENGTH_SHORT).show()
            onLoginSuccess()
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
            text = stringResource(id = R.string.app_name),
            fontSize = 60.sp,
            fontFamily = custom,
            color = Color.White,
            modifier = Modifier
        )

        Text(
            text = stringResource(id = R.string.login),
            fontSize = 50.sp,
            fontFamily = custom,
            color = Color.White,
        )
    }
}




@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun usernameField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = stringResource(id = R.string.username)
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
        label = { Text(text = stringResource(R.string.username), color = Color.White) },
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
fun passwordField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = stringResource(id = R.string.password)
) {
    val icon = @Composable {
        Icon(
            Icons.Default.Lock,
            contentDescription = "",
            tint = Color.White
        )
    }

    var showPass by remember { mutableStateOf(false) }

    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(stringResource( R.string.password), color = Color.White) },
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedTextColor = Color.White,
            focusedTextColor = Color.White,
            focusedBorderColor = Color.White,
            unfocusedBorderColor = Color.White,
            unfocusedLabelColor = Color.White
        ),
        singleLine = true,
        leadingIcon = icon,
        visualTransformation = if (showPass) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            IconButton(onClick = { showPass=!showPass}){
                Icon(
                    Icons.Default.RemoveRedEye, contentDescription = "", tint=Color.White)
            }
        },
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
fun SignInView(
    viewModel: LoginViewModel,
    onBackButton: () -> Unit
) {
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
fun SignInLogoText() {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.app_name),
            fontSize = 50.sp,
            fontFamily = custom,
            color = Color.White,
            modifier = Modifier
        )

        Text(
            text = stringResource(R.string.create_account),
            fontSize = 40.sp,
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

    var showPass by remember { mutableStateOf(false) }

    val visualTransformation = if (showPass) {
        VisualTransformation.None
    } else {
        PasswordVisualTransformation()
    }

    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = stringResource(R.string.password), color = Color.White) },
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedTextColor = Color.White,
            focusedTextColor = Color.White,
            focusedBorderColor = Color.White,
            unfocusedBorderColor = Color.White,
            unfocusedLabelColor = Color.White
        ),
        singleLine = true,
        leadingIcon = icon,
        visualTransformation = visualTransformation,
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        trailingIcon = {
            IconButton(onClick = { showPass=!showPass}){
                Icon(
                    Icons.Default.RemoveRedEye, contentDescription = "", tint=Color.White)
            }
        },

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
    if(!viewModel.eventToast) {
        Toast.makeText(context, context.getString(R.string.sign_in_completed), Toast.LENGTH_SHORT).show()
        viewModel.navigateToLogin()
    }
    else{
        Toast.makeText(context, context.getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show()
        viewModel.resetEventToast()
    }
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

        SignInLogoText()
        Spacer(modifier = Modifier.height(100.dp))

        usernameField(
            value = username,
            onValueChange = onUsernameChange,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(20.dp))

        SignInPasswordField(
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
    BackgroundGradient()
    Row(
        modifier=Modifier.fillMaxSize(),
    ) {
        Column(modifier = Modifier
            .fillMaxHeight()
            .weight(1f))
        {
            SignInLogoText()
        }
        Column(modifier = Modifier
            .fillMaxHeight()
            .weight(1f))
        {
            usernameField(value = username,
                onValueChange = onUsernameChange,
                modifier = Modifier.fillMaxWidth())

            passwordField(
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

private fun onLoginSuccess(viewModel: LoginViewModel, sharedPrefRepository: SharedPrefRepository, username: String) {
    viewModel.navigateToHome()
    sharedPrefRepository.putUsr("username", username)
}