package com.dosti.scamfolio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.ui.platform.LocalContext
import com.dosti.scamfolio.dbStuff.AppDatabase
import com.dosti.scamfolio.dbStuff.Repository
import com.dosti.scamfolio.ui.view.LoginView
import com.dosti.scamfolio.ui.view.MainLoginScreen
import com.dosti.scamfolio.ui.view.SignInScreen
import com.dosti.scamfolio.viewModel.CryptoScreenViewModel
import com.dosti.scamfolio.viewModel.ViewModelFactory

class MainActivity : ComponentActivity() {
    private val CryptoScreenViewModel : CryptoScreenViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val db = AppDatabase.getInstance(LocalContext.current)
            val factory = ViewModelFactory(Repository(db.ScamfolioDao()))
            //LoginView(viewModelStoreOwner = this, factory = factory)
            //SignInScreen(viewModelStoreOwner = this, factory = factory)
            MainLoginScreen(viewModelStoreOwner = this, viewModelFactory = factory)
        }
    }
}



