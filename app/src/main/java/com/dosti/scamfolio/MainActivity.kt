package com.dosti.scamfolio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.dosti.scamfolio.ui.theme.ScamFolioTheme
import com.dosti.scamfolio.ui.view.CryptoScreen
import com.dosti.scamfolio.ui.view.Homepage
import com.dosti.scamfolio.ui.view.LoginView
import com.dosti.scamfolio.ui.view.LoginView
import com.dosti.scamfolio.ui.view.SearchedCryptos
import com.dosti.scamfolio.ui.view.routing.NavGraph
import com.dosti.scamfolio.viewModel.CryptoScreenViewModel

class MainActivity : ComponentActivity() {
    private val CryptoScreenViewModel : CryptoScreenViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CryptoScreen(CryptoScreenViewModel)
        }
    }
}

@Composable
private fun MainScreen() {
    ScamFolioTheme {
        val navController = rememberNavController()
        NavGraph(navController)
    }
}

