package com.example.lunchtray

import android.content.Context
import android.content.Intent
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.lunchtray.data.DataSource.accompanimentMenuItems
import com.example.lunchtray.data.DataSource.entreeMenuItems
import com.example.lunchtray.data.DataSource.sideDishMenuItems
import com.example.lunchtray.model.LunchTrayModel
import com.example.lunchtray.ui.OptionsScreen
import com.example.lunchtray.ui.OrderCheckoutScreen
import com.example.lunchtray.ui.StartOrderScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import javax.security.auth.Subject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LunchTrayApp(navController: NavHostController = rememberNavController(), viewModel: LunchTrayModel = viewModel()) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = Screens.valueOf(
        backStackEntry?.destination?.route ?: Screens.START_SCREEN.name
    )
    Scaffold (
        topBar = { 
            LunchTrayAppBar(
                canGoBack = navController.previousBackStackEntry != null,
                modifier = Modifier,
                navigateUp = {navController.navigateUp()},
                currentScreen = currentScreen
            )
        }
    ){ paddingValues ->  
        val startDestination = Screens.START_SCREEN.name
        val uiState = viewModel.uiState.collectAsState()
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(route = Screens.START_SCREEN.name) {
                StartOrderScreen(navController = navController)
            }
            composable(route = Screens.CHOOSE_ENTREE.name) {
                OptionsScreen(
                    navController = navController,
                    options = entreeMenuItems,
                    onCancelClick = {cancelOrderAndNavigateToStart(viewModel, navController)},
                    onNextClick = {
                        navController.navigate(route = Screens.CHOOSE_SIDE_DISH.name)
                        viewModel.setEntree(entreeMenuItems[it])
                    }
                )
            }
            composable(route = Screens.CHOOSE_SIDE_DISH.name) {
                OptionsScreen(
                    navController = navController,
                    options = sideDishMenuItems,
                    onCancelClick = {
                        cancelOrderAndNavigateToStart(viewModel, navController)
                    },
                    onNextClick = {
                        navController.navigate(route = Screens.CHOOSE_ACCOMPANIMENT.name)
                        viewModel.setSideDish(sideDishMenuItems[it])
                    }
                )
            }
            composable(route = Screens.CHOOSE_ACCOMPANIMENT.name) {
                OptionsScreen(
                    navController = navController,
                    options = accompanimentMenuItems,
                    onCancelClick = {cancelOrderAndNavigateToStart(viewModel, navController)},
                    onNextClick = {
                        navController.navigate(route = Screens.ORDER_CHECKOUT.name)
                        viewModel.setAccompaniment(accompanimentMenuItems[it])
                    }
                )
            }
            composable(route = Screens.ORDER_CHECKOUT.name) {
                val context = LocalContext.current
                OrderCheckoutScreen(
                    navController = navController,
                    onCancelClick = { cancelOrderAndNavigateToStart(viewModel, navController) },
                    onNextClick = {
                          shareOrder(
                              context = context,
                              subject = "NEW ORDER",
                              summary = buildString {
                                    append("Entree: ")
                                    append(uiState.value.entree.name)
                                    append("\nSide Dish: ")
                                    append(uiState.value.sideDish.name)
                                    append("\nAccompaniment: ")
                                    append(uiState.value.accompaniment.name)
                                    .append("\nTotal: ")
                                    .append("${uiState.value.total}")}
                          )
                    },
                    subTotal = uiState.value.subTotal,
                    tax = uiState.value.tax,
                    total = uiState.value.total,
                    list = listOf(
                        Pair(uiState.value.entree.name, uiState.value.entree.price),
                        Pair(uiState.value.sideDish.name, uiState.value.sideDish.price),
                        Pair(uiState.value.accompaniment.name, uiState.value.accompaniment.price),
                    )
                )
            }

        }
    }
}

private fun cancelOrderAndNavigateToStart(
    viewModel: LunchTrayModel,
    navController: NavHostController
) {
    viewModel.resetOrder()
    navController.popBackStack(Screens.START_SCREEN.name, inclusive = false)
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
private fun shareOrder(context: Context, subject: String, summary: String) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, summary)
    }
    context.startActivity(
        Intent.createChooser(
            intent,
            context.getString(R.string.new_Lunch_Tray)
        )
    )
}