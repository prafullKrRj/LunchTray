package com.example.lunchtray

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.lunchtray.data.DataSource.accompanimentMenuItems
import com.example.lunchtray.data.DataSource.entreeMenuItems
import com.example.lunchtray.data.DataSource.sideDishMenuItems
import com.example.lunchtray.ui.OptionsScreen
import com.example.lunchtray.ui.StartOrderScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LunchTrayApp(navController: NavHostController = rememberNavController()) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = Screens.valueOf(
        backStackEntry?.destination?.route ?: Screens.START_SCREEN.name
    )
    Scaffold (
        topBar = { 
            LunchTrayAppBar(canGoBack = navController.previousBackStackEntry != null, modifier = Modifier, navigateUp = {}, currentScreen = currentScreen)
        }
    ){ paddingValues ->  
        val startDestination = Screens.START_SCREEN.name
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(route = Screens.START_SCREEN.name) {
                StartOrderScreen(navController = navController)
            }
            composable(route = Screens.CHOOSE_ENTREE.name) {
                OptionsScreen(navController = navController, options = entreeMenuItems, onCancelClick = {}, onNextClick = {
                    navController.navigate(route = Screens.CHOOSE_SIDE_DISH.name)
                })
            }
            composable(route = Screens.CHOOSE_SIDE_DISH.name) {
                OptionsScreen(navController = navController, options = sideDishMenuItems, onCancelClick = {}, onNextClick = {
                    navController.navigate(route = Screens.CHOOSE_ACCOMPANIMENT.name)
                })
            }
            composable(route = Screens.CHOOSE_ACCOMPANIMENT.name) {
                OptionsScreen(navController = navController, options = accompanimentMenuItems, onCancelClick = {}, onNextClick = {
                    navController.navigate(route = Screens.CHOOSE_SIDE_DISH.name)
                })
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LunchTrayAppBar(currentScreen:Screens, canGoBack: Boolean, modifier: Modifier = Modifier, navigateUp: () -> Unit) {
    TopAppBar(
        title = {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text(text = when (currentScreen) {
                    Screens.START_SCREEN -> stringResource(id = R.string.app_name)
                    Screens.CHOOSE_ENTREE -> stringResource(id = R.string.choose_entree)
                    Screens.CHOOSE_SIDE_DISH -> stringResource(id = R.string.choose_side_dish)
                    Screens.CHOOSE_ACCOMPANIMENT -> stringResource(id = R.string.choose_accompaniment)
                    else -> stringResource(id = R.string.order_checkout)
                })
            }
        },
        navigationIcon = {
            if (canGoBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Arrow Back"
                    )
                }
            }
        },
        modifier = modifier
    )
}