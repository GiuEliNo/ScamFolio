package com.dosti.scamfolio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.dosti.scamfolio.dbStuff.AppDatabase
import com.dosti.scamfolio.dbStuff.Repository
import com.dosti.scamfolio.ui.view.MainLoginScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import com.dosti.scamfolio.db.entities.Purchasing
import com.dosti.scamfolio.viewModel.SharedCoinGekoViewModel
import com.dosti.scamfolio.viewModel.SplashScreenViewModel
import com.dosti.scamfolio.ui.view.SplashScreen
import com.dosti.scamfolio.viewModel.ViewModelFactory

class MainActivity : ComponentActivity() {

    private lateinit var statusAPI: SplashScreenViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = AppDatabase.getInstance(this)
        val repository = Repository(db.ScamfolioDao())
        val sharedCoinGeko: SharedCoinGekoViewModel by viewModels()
        val factory = ViewModelFactory(repository, sharedCoinGeko)
        statusAPI = ViewModelProvider(this, factory)[SplashScreenViewModel::class.java]
        val sharedPrefRepository = SharedPrefRepository(applicationContext)

        setContent {
            Surface(modifier = Modifier.fillMaxSize(), color = Color.Black) {
                val isLoading by statusAPI.isLoading.collectAsState()
                if (isLoading) {
                    SplashScreen()
                } else {
                    MainLoginScreen(viewModelStoreOwner = this, viewModelFactory = factory, sharedPrefRepository = sharedPrefRepository)
                }
            }
        }

    }
}


