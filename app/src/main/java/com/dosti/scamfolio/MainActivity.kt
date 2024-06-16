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
import com.dosti.scamfolio.db.AppDatabase
import com.dosti.scamfolio.db.repositories.Repository
import com.dosti.scamfolio.ui.view.MainLoginScreen
import androidx.lifecycle.ViewModelProvider
import com.dosti.scamfolio.ui.theme.ScamFolioTheme
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
        val sharedPrefRepository = SharedPrefRepository(applicationContext)
        val factory = ViewModelFactory(repository, sharedCoinGeko, sharedPrefRepository)
        statusAPI = ViewModelProvider(this, factory)[SplashScreenViewModel::class.java]

        setContent {
            ScamFolioTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val isLoading by statusAPI.isLoading.collectAsState()
                    if (isLoading) {
                        SplashScreen(statusAPI)
                    } else {
                        MainLoginScreen(
                            viewModelStoreOwner = this,
                            viewModelFactory = factory,
                            sharedPrefRepository = sharedPrefRepository
                        )
                    }
                }
            }
        }

    }
}


