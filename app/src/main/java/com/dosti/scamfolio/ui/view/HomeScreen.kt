package com.dosti.scamfolio.ui.view

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dosti.scamfolio.R
import com.dosti.scamfolio.SharedPrefRepository
import com.dosti.scamfolio.ui.theme.BackgroundGradient
import com.dosti.scamfolio.ui.theme.ScamFolioTheme
import com.dosti.scamfolio.ui.theme.custom
import com.dosti.scamfolio.ui.view.ScreenRouter.Companion.COIN_DETAIL
import com.dosti.scamfolio.viewModel.LoginViewModel
import com.dosti.scamfolio.viewModel.ViewModelFactory
@Composable
fun ComposeCryptoPages(factory : ViewModelFactory,
                        viewModel: LoginViewModel,
                       sharedPrefRepository: SharedPrefRepository) {
    val navController = rememberNavController()

    ScamFolioTheme {
        Scaffold(
            topBar = {
                ComposeCryptoTopBar(viewModel)
            },

            bottomBar = {
                ComposeCryptoBottomBar(navController)
            }

        ) {
            BackgroundGradient()
            ComposeCryptoNavHost(
                navController = navController,
                factory = factory,
                modifier = Modifier.padding(it),
                sharedPrefRepository = sharedPrefRepository
            )
        }
    }
}

@SuppressLint("ResourceAsColor")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComposeCryptoTopBar(viewModel: LoginViewModel) {
    TopAppBar(
        title = { Text(
            text = stringResource(id = R.string.app_name),
            fontFamily = custom,
            color = Color.White
            ) },
        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color(0xD40A0A0A)),
        actions = {
            Box{ IconButton(onClick = {viewModel.navigateToLoginFromLoggedIn()
            }) {
                Icon(Icons.Default.Logout,"Logout")
            }}
        }
    )
}

@Composable
fun ComposeCryptoBottomBar(navController: NavHostController) {
    BottomAppBar(
        containerColor = Color(0xD40A0A0A)
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        ScreenRouter.screens.forEach { screen ->
            NavigationBarItem(
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = {
                          navController.navigate(screen.route) {
                              launchSingleTop = true
                          }
                },
                label = {
                    Text(
                        text = stringResource(id = screen.label),
                        color = Color.White
                    )
                },
                icon = {
                    Icon(
                        imageVector = screen.icon,
                        contentDescription = stringResource(id = screen.label),
                        tint = Color.White
                    )
                },
                alwaysShowLabel = true,
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = MaterialTheme.colorScheme.secondary
                )
            )
        }
    }
}

@Composable
fun ComposeCryptoNavHost(
    navController: NavHostController,
    factory: ViewModelProvider.Factory?,
    modifier: Modifier,
    sharedPrefRepository: SharedPrefRepository
) {
    val actions = remember(navController) { AppActions(navController) }

    NavHost(
        navController = navController,
        startDestination = ScreenRouter.ROUTE_PERSONALAREA,
        modifier = modifier
    ) {
        composable(ScreenRouter.ROUTE_PERSONALAREA) {
            Welcome(viewModel = viewModel(factory = factory))
        }

        composable(ScreenRouter.ROUTE_SEARCHCOIN) {
            SearchedCryptos(viewModel = viewModel(factory = factory), selectedCoin = actions.selectedCoin)
        }
        
        composable(ScreenRouter.ROUTE_CALCULATOR) {
            ConverterScreen(viewModel = viewModel(factory = factory))
        }
        composable(
            "${ScreenRouter.ROUTE_SEARCHCOIN}/{$COIN_DETAIL}",
            arguments = listOf(
                navArgument(COIN_DETAIL) {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            arguments.getString(COIN_DETAIL)?.let {
                CryptoScreen(
                    viewModel = viewModel(factory = factory),
                    coinName = it,
                    navigateUp = actions.navigateUp,
                )
            }
        }
    }
}
class AppActions(
    navController: NavHostController
) {
    val selectedCoin: (String) -> Unit = { coinName: String ->
        navController.navigate("${ScreenRouter.ROUTE_SEARCHCOIN}/$coinName")
    }

    val navigateUp: () -> Unit = {
        navController.navigateUp()
    }
}
