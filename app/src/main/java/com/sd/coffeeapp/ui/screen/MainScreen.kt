package com.sd.coffeeapp.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sd.coffeeapp.viewmodel.MainViewModel
import com.sd.coffeeapp.R
import com.sd.coffeeapp.ui.theme.ColorBottomItem
import com.sd.coffeeapp.ui.theme.ColorItem
import com.sd.coffeeapp.ui.theme.ColorTextMl
import com.sd.coffeeapp.ui.theme.ColorTextName
import com.sd.coffeeapp.ui.theme.ColorTextPrice

@Composable
fun MainScreen(vm: MainViewModel = viewModel()) {
    val listItem by vm.listItemOrder.observeAsState()

    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        columns = GridCells.Fixed(5)
    ) {
        items(listItem ?: emptyList()) { item ->
            Box(
                modifier = Modifier
                    .background(Color.Black)
                    .padding(24.dp)
            ) {

                Card(
                    modifier = Modifier
                        .background(Color.Black)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(ColorItem)
                    ) {
                        Image(
                            painter = painterResource(id = if (item.isDark) R.drawable.cocktail_dark_main else R.drawable.cocktail_light_main),
                            contentDescription = "avatar",
                            modifier = Modifier.padding(6.dp)
                        )
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(6.dp),
                            text = item.name,
                            color = ColorTextName,
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(ColorBottomItem)
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            horizontalArrangement = if (item.isFree) Arrangement.Center else Arrangement.SpaceBetween,
                        ) {
                            Text(text = "0.33", color = ColorTextMl)
                            if (!item.isFree) {
                                Text(text = "${item.price} ₽", color = ColorTextPrice)
                            }
                        }
                    }
                }
            }
        }
    }
}