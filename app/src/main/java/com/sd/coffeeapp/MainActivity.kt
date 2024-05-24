package com.sd.coffeeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sd.coffeeapp.ui.screen.CurrentItem
import com.sd.coffeeapp.ui.screen.MainScreen
import com.sd.coffeeapp.ui.theme.Black
import com.sd.coffeeapp.ui.theme.CoffeeAppTheme
import com.sd.coffeeapp.ui.theme.ColorTextAppBar
import com.sd.coffeeapp.ui.theme.ColorTextField
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
                    OutlinedCard(
                        shape = RoundedCornerShape(0.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Black,
                        ),

                        border = BorderStroke(1.dp, ColorTextField),
                        modifier = Modifier
                            .size(width = 1280.xp(), height = 54.xp())
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.xp()),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row() {
                                Image(
                                    painter = painterResource(id = R.drawable.runero1),
                                    contentDescription = getString(R.string.runero1),
                                    modifier = Modifier
                                        .padding(
                                            start = 26.xp(),
                                            top = 15.xp(),
                                            bottom = 15.xp()
                                        )
                                        .width(14.xp())
                                        .height(24.xp())
                                )
                                Image(
                                    painter = painterResource(id = R.drawable.runero2),
                                    contentDescription = getString(R.string.runero2),
                                    modifier = Modifier
                                        .padding(
                                            top = 15.xp(),
                                            end = 12.xp()
                                        )
                                        .width(11.xp())
                                        .height(21.xp())
                                )
                                Text(
                                    text = getString(R.string.runero_touch),
                                    modifier = Modifier
                                        .padding(top = 16.xp())
                                        .clickable {
                                            if (navController.currentDestination == navController.findDestination(
                                                    Routes.Main.route
                                                )
                                            ) {
                                                navController.navigate(Routes.Current.route)
                                            } else {
                                                navController.navigate(Routes.Main.route)
                                            }
                                        },
                                    color = ColorTextAppBar,
                                    fontSize = 16.sp()
                                )
                            }
                            Row(
                                modifier = Modifier
                            ) {
                                TextClock()
                                OutlinedCard(
                                    shape = RoundedCornerShape(0.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = Black,
                                    ),
                                    border = BorderStroke(1.dp, ColorTextField),
                                    modifier = Modifier
                                        .size(width = 83.xp(), height = 54.xp())
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(start = 16.xp(), top = 16.xp())
                                    ) {
                                        Text(
                                            text = "$temp°",
                                            color = ColorTextAppBar,
                                            fontSize = 16.sp(),
                                            modifier = Modifier
                                        )
                                        Image(
                                            modifier = Modifier
                                                .padding(
                                                    top = 6.xp(),
                                                )
                                                .width(9.xp())
                                                .height(11.xp()),
                                            painter = painterResource(id = R.drawable.gradus),
                                            contentDescription = "gradus",
                                        )
                                    }
                                }
                                Image(
                                    painter = painterResource(id = R.drawable.flag),
                                    contentDescription = "gradus",
                                    modifier = Modifier
                                        .padding(
                                            top = 18.xp(),
                                            start = 20.xp(),
                                            end = 2.xp()
                                        )
                                        .height(19.xp())
                                        .width(19.xp())
                                )
                                Text(
                                    text = "RU",
                                    modifier = Modifier.padding(
                                        top = 16.xp(),
                                        end = 50.xp()
                                    ),
                                    color = ColorTextAppBar,
                                    fontSize = 16.sp()
                                )
                            }
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
    format12Hour: String? = "hh:mm",
) {
    AndroidView(
        factory = { context ->
            android.widget.TextClock(context).apply {
                format12Hour?.let { this.format12Hour = it }
                this.setTextColor(resources.getColor(R.color.text_app_bar, null))
                this.textSize = 12f
            }
        },
        modifier = Modifier.padding(top = 16.xp(), end = 16.xp())
    )
}

sealed class Routes(val route: String) {
    object Main : Routes("mainScreen")
    object Current : Routes("currentItem")
}

@Composable
fun Int.xp() = with(LocalDensity.current) {
    this@xp.toDp()
}

@Composable
fun Int.sp() = with(LocalDensity.current) {
    this@sp.toSp()
}




