package com.sd.coffeeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sd.coffeeapp.ui.screen.CurrentItem
import com.sd.coffeeapp.ui.screen.MainScreen
import com.sd.coffeeapp.ui.theme.CoffeeAppTheme
import com.sd.coffeeapp.ui.theme.ColorTextAppBar
import com.sd.coffeeapp.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalComposeUiApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CoffeeAppTheme {
                val vm: MainViewModel = viewModel()
                val temp by vm.temp.observeAsState()
                val navController = rememberNavController()
                val keyboardController = LocalSoftwareKeyboardController.current
                val focusManager = LocalFocusManager.current

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row() {
                            Icon(
                                painter = painterResource(id = R.drawable.runero),
                                contentDescription = getString(R.string.runero),
                            )
                            Text(text = getString(R.string.runero_touch), modifier = Modifier.clickable {
                                if (navController.currentDestination == navController.findDestination(
                                        Routes.Main.route
                                    )
                                ) {
                                    navController.navigate(Routes.Current.route)
                                } else {
                                    navController.navigate(Routes.Main.route)
                                }
                            }, color = ColorTextAppBar, fontSize = 18.sp)
                        }
                        Row {
                            TextClock()
                            Text(
                                text = temp.toString(),
                                modifier = Modifier.padding(horizontal = 24.dp),
                                color = ColorTextAppBar,
                                fontSize = 18.sp
                            )
                        }
                    }
                    NavHost(
                        navController = navController,
                        startDestination = Routes.Main.route
                    ) {
                        composable(Routes.Main.route) {
                            MainScreen(vm)
                        }
                        composable(Routes.Current.route) {
                            CurrentItem(vm, keyboardController, focusManager)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TextClock(
    modifier: Modifier = Modifier,
    format12Hour: String? = "hh:mm",
    format24Hour: String? = null,
    timeZone: String? = null,
) {
    AndroidView(
        factory = { context ->
            android.widget.TextClock(context).apply {
                format12Hour?.let { this.format12Hour = it }
                format24Hour?.let { this.format24Hour = it }
                timeZone?.let { this.timeZone = it }
                this.setTextColor(resources.getColor(R.color.text_app_bar, null))
                this.textSize = 18f
            }
        },
        modifier = modifier
    )
}

sealed class Routes(val route: String) {
    object Main : Routes("mainScreen")
    object Current : Routes("currentItem")
}



