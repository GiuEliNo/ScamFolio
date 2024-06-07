package com.dosti.scamfolio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import com.dosti.scamfolio.dbStuff.AppDatabase
import com.dosti.scamfolio.dbStuff.Repository
import com.dosti.scamfolio.ui.view.MainLoginScreen
import com.dosti.scamfolio.viewModel.ViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val db = AppDatabase.getInstance(LocalContext.current)
            val factory = ViewModelFactory(Repository(db.ScamfolioDao()))
            MainLoginScreen(viewModelStoreOwner = this, viewModelFactory = factory)
        }
    }
}



