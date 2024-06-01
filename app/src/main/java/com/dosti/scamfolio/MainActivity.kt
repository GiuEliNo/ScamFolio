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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.dosti.scamfolio.dbStuff.AppDatabase
import com.dosti.scamfolio.dbStuff.Repository
import com.dosti.scamfolio.dbStuff.User
import com.dosti.scamfolio.dbStuff.UserDao
import com.dosti.scamfolio.ui.theme.ScamFolioTheme
import com.dosti.scamfolio.ui.view.ComposeCryptoPages
import com.dosti.scamfolio.ui.view.CryptoScreen
import com.dosti.scamfolio.ui.view.Homepage
import com.dosti.scamfolio.ui.view.LoginView
import com.dosti.scamfolio.ui.view.LoginView
import com.dosti.scamfolio.ui.view.SearchedCryptos
import com.dosti.scamfolio.ui.view.SignInScreen
import com.dosti.scamfolio.viewModel.CryptoScreenViewModel
import com.dosti.scamfolio.viewModel.ViewModelFactory

class MainActivity : ComponentActivity() {
    private val CryptoScreenViewModel : CryptoScreenViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val db = AppDatabase.getInstance(LocalContext.current)
            val factory = ViewModelFactory(Repository(db.userDao()))
            // LoginView(viewModelStoreOwner = this, factory = factory)
            SignInScreen()
        }
    }
}



