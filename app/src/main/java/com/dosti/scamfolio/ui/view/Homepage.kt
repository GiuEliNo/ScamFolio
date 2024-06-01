package com.dosti.scamfolio.ui.view

import android.provider.Settings.Global.getString
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.dosti.scamfolio.R
import com.dosti.scamfolio.dbStuff.AppDatabase
import com.dosti.scamfolio.dbStuff.Repository
import com.dosti.scamfolio.ui.theme.custom
import com.dosti.scamfolio.viewModel.HomepageViewModel
import com.dosti.scamfolio.viewModel.LoginViewModel
import com.dosti.scamfolio.viewModel.ViewModelFactory

@Composable
fun Homepage(
    viewModelStoreOwner: ViewModelStoreOwner,
    homepageViewModel: HomepageViewModel
) {
    var state by rememberSaveable { mutableStateOf(0) }
    val context = LocalContext.current

    if (state == 0) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.DarkGray)
        ) {
            val username by rememberSaveable() { mutableStateOf("user") }
            val search by rememberSaveable() { mutableStateOf(R.string.search_cryptos) }
            val personal by rememberSaveable() { mutableStateOf(R.string.personal_area) }
            val settings by rememberSaveable { mutableStateOf(R.string.settings) }

            TopText(username = username)
            Spacer(modifier = Modifier.height(140.dp))

            GenericButton(buttonText = stringResource(id = search), { /* TODO */})
            GenericButton(buttonText = stringResource(id = personal), { /* TODO */})
            GenericButton(buttonText = stringResource(R.string.logout), { state += 1 })
        }
    } else {
        /*
        LoginView(viewModelStoreOwner = viewModelStoreOwner, factory = ViewModelFactory(Repository(
            AppDatabase.getInstance(LocalContext.current).userDao())))

         */
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
            text = stringResource(R.string.welcome_back),
            fontSize = 50.sp,
            fontFamily = custom,
            color = Color.White,
        )

        Text(
            text = username,
            fontSize = 50.sp,
            fontFamily = custom,
            color = Color.White,
        )
    }
}

@Composable
fun GenericButton(
    buttonText : String,
    onClick : () -> Unit
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
                    text = buttonText,
                    fontSize = 30.sp,
                    fontFamily = custom,
                    color = Color.White
                )
            }
        }
    }
    
    Spacer(modifier = Modifier.height(40.dp))
}

private fun onLogout() {

}

