package com.sd.coffeeapp.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sd.coffeeapp.viewmodel.MainViewModel
import com.sd.coffeeapp.R
import com.sd.coffeeapp.sp
import com.sd.coffeeapp.ui.theme.ColorBottomItem
import com.sd.coffeeapp.ui.theme.ColorItem
import com.sd.coffeeapp.ui.theme.ColorTextMl
import com.sd.coffeeapp.ui.theme.ColorTextName
import com.sd.coffeeapp.ui.theme.ColorTextPrice
import com.sd.coffeeapp.xp

@Composable
fun MainScreen(vm: MainViewModel = viewModel()) {
    val listItem by vm.listItemOrder.observeAsState()

    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(horizontal = 12.xp()),
        columns = GridCells.Fixed(5)
    ) {
        items(listItem ?: emptyList()) { item ->
            Box(
                modifier = Modifier
                    .background(Color.Black)
                    .padding(top=24.xp(), start=12.xp(), end=12.xp())
            ) {

                Card(
                    modifier = Modifier
                        .background(Color.Black)
                        .width(227.xp())
                        .height(313.xp())
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(ColorItem)
                    ) {
                        Image(
                            painter = painterResource(id = if (item.isDark) R.drawable.cocktail_dark_main else R.drawable.cocktail_light_main),
                            contentDescription = "avatar",
                            modifier = Modifier.padding(start = 9.xp(), end = 8.xp())
                                .height(210.xp())
                                .width(210.xp())
                        )
                        Text(
                            modifier = Modifier
                                .fillMaxWidth().height(57.xp()),
                            text = item.name,
                            color = ColorTextName,
                            fontSize = 17.sp(),

                            textAlign = TextAlign.Center
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(ColorBottomItem)
                                .padding(horizontal = 16.xp(), vertical = 8.xp())
                                .height(42.xp()),
                            horizontalArrangement = if (item.isFree) Arrangement.Center else Arrangement.SpaceBetween,
                        ) {
                            Text(text = "0.33", color = ColorTextMl, fontSize = 16.sp())
                            if (!item.isFree) {
                                Text(text = "${item.price} ₽", color = ColorTextPrice, fontSize = 18.sp())
                            }
                        }
                    }
                }
            }
        }
    }
}