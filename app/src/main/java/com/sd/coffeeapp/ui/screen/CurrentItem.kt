package com.sd.coffeeapp.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sd.coffeeapp.R
import com.sd.coffeeapp.sp
import com.sd.coffeeapp.ui.theme.Black
import com.sd.coffeeapp.ui.theme.ColorItem
import com.sd.coffeeapp.ui.theme.ColorTextAppBar
import com.sd.coffeeapp.ui.theme.ColorTextField
import com.sd.coffeeapp.ui.theme.ColorTextName
import com.sd.coffeeapp.viewmodel.MainViewModel
import com.sd.coffeeapp.xp

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun CurrentItem(
    vm: MainViewModel = viewModel(),
    keyboardController: SoftwareKeyboardController?,
    focusManager: FocusManager
) {

    val checkedState = remember {
        mutableStateOf(vm.itemOrder.value?.isFree ?: false)
    }

    val buttonEnabled = remember {
        mutableStateOf(false)
    }

    val textName = remember {
        mutableStateOf(vm.itemOrder.value?.name ?: "")
    }
    val isErrorName = remember {
        mutableStateOf(false)
    }

    val textPrice = remember {
        mutableStateOf((vm.itemOrder.value?.price ?: 0).toString())
    }
    val isErrorPrice = remember {
        mutableStateOf(false)
    }

    val chooseDarkColor = remember {
        mutableStateOf(vm.itemOrder.value?.isDark ?: false)
    }

    Row(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .padding(start = 263.xp(), top = 91.xp())
                .width(418.xp())
        )
        {
            Text(text = stringResource(R.string.name), color = ColorTextName, fontSize = 16.sp())

            TextField(
                value = textName.value,
                onValueChange = { newText ->
                    textName.value = newText
                    buttonEnabled.value = checkEnabledButton(
                        vm = vm,
                        textN = textName.value,
                        textP = textPrice.value,
                        free = checkedState.value,
                        dark = chooseDarkColor.value
                    )
                },
                modifier = Modifier
                    .width(418.xp())
                    .padding(top = 12.xp()),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                keyboardActions = KeyboardActions(onDone = {
                    if (textName.value.isNotEmpty()) {
                        isErrorName.value = false
                        keyboardController?.hide()
                        focusManager.clearFocus()
                    } else {
                        isErrorName.value = true
                    }
                }),
                placeholder = {
                    Text(text = stringResource(R.string.enter_name))
                },
                textStyle = TextStyle(fontSize = 20.sp()),
                isError = isErrorName.value,
                supportingText = {
                    if (isErrorName.value) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = stringResource(R.string.field_mustnt_empty),
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.White,
                    containerColor = ColorTextField
                )
            )

            Text(
                text = stringResource(R.string.price),
                color = ColorTextName,
                modifier = Modifier.padding(top = 32.xp()),
                fontSize = 16.sp()
            )

            TextField(
                value = textPrice.value,
                enabled = !checkedState.value,
                onValueChange = { newText ->
                    textPrice.value = newText
                    buttonEnabled.value = checkEnabledButton(
                        vm = vm,
                        textN = textName.value,
                        textP = textPrice.value,
                        free = checkedState.value,
                        dark = chooseDarkColor.value
                    )
                },
                modifier = Modifier
                    .width(418.xp())
                    .padding(top = 12.xp()),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                keyboardActions = KeyboardActions(onDone = {
                    if (checkInputText(textPrice.value)) {
                        isErrorPrice.value = false
                        keyboardController?.hide()
                        focusManager.clearFocus()
                    } else {
                        isErrorPrice.value = true
                    }
                }),
                textStyle = TextStyle(fontSize = 20.sp()),
                placeholder = {
                    Text(text = stringResource(R.string.enter_price))
                },
                isError = isErrorPrice.value,
                supportingText = {
                    if (isErrorPrice.value) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = stringResource(R.string.enter_number),
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                singleLine = true,
                trailingIcon = {
                    Image(
                        painter = painterResource(id = R.drawable.rub),
                        contentDescription = stringResource(R.string.rub),
                        modifier = Modifier
                            .width(16.xp())
                            .height(20.xp())
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.White,
                    containerColor = ColorTextField
                )
            )

            OutlinedCard(
                colors = CardDefaults.cardColors(
                    containerColor = Black,
                ),
                border = BorderStroke(1.xp(), ColorTextField),
                modifier = Modifier
                    .padding(top = 12.xp())
                    .size(width = 418.xp(), height = 72.xp())
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.xp(), vertical = 12.xp()),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        stringResource(R.string.free), fontSize = 14.sp(),
                        color = ColorTextAppBar
                    )
                    Switch(
                        checked = checkedState.value,
                        onCheckedChange = {
                            checkedState.value = it
                            buttonEnabled.value = checkEnabledButton(
                                vm = vm,
                                textN = textName.value,
                                textP = textPrice.value,
                                free = checkedState.value,
                                dark = chooseDarkColor.value
                            )
                        },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.White
                        ),
                        modifier = Modifier
                            .padding(vertical = 12.xp())
                            .size(width = 40.xp(), height = 24.xp())
                    )
                }
            }

            Button(
                modifier = Modifier
                    .padding(top = 64.xp())
                    .width(170.xp())
                    .height(52.xp()),
                onClick = {
                    vm.setItem(
                        textName.value,
                        textPrice.value.toInt(),
                        checkedState.value,
                        chooseDarkColor.value
                    )
                    buttonEnabled.value = checkEnabledButton(
                        vm = vm,
                        textN = textName.value,
                        textP = textPrice.value,
                        free = checkedState.value,
                        dark = chooseDarkColor.value
                    )
                },
                enabled = buttonEnabled.value,
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White,
                    disabledContainerColor = ColorItem,
                    disabledContentColor = ColorTextAppBar
                )
            ) {
                Text(text = stringResource(R.string.save), fontSize = 20.sp())
            }
        }

        Row(
            modifier = Modifier.padding(start = 30.xp(), top = 92.xp())
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(287.xp())
            ) {
                Image(
                    painter = painterResource(id = R.drawable.cocktail_light),
                    contentDescription = stringResource(R.string.light),
                    modifier = Modifier
                        .width(287.xp())
                        .height(287.xp())
                        .clickable {
                            chooseDarkColor.value = false
                            buttonEnabled.value = checkEnabledButton(
                                vm = vm,
                                textN = textName.value,
                                textP = textPrice.value,
                                free = checkedState.value,
                                dark = chooseDarkColor.value
                            )
                        },
                    alpha = if (!chooseDarkColor.value) 1f else 0.3f
                )
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Image(
                        painter = painterResource(id = R.drawable.circle),
                        contentDescription = stringResource(R.string.circlelight),
                        modifier = Modifier
                            .offset(y = (-56).xp())
                            .height(32.xp())
                            .width(32.xp())
                            .clip(shape = RoundedCornerShape(15.dp)),
                        alpha = if (!chooseDarkColor.value) 1f else 0f,
                        alignment = Alignment.Center
                    )
                    Image(
                        imageVector = Icons.Default.Check,
                        contentDescription = stringResource(R.string.oklight),
                        modifier = Modifier
                            .offset(y = (-56).xp()),
                        colorFilter = ColorFilter.tint(Color.White),
                        alignment = Alignment.Center,
                        alpha = if (!chooseDarkColor.value) 1f else 0f
                    )
                }
            }

            Column(
                modifier = Modifier.width(287.xp())
            ) {
                Image(
                    painter = painterResource(id = R.drawable.cocktail_dark),
                    contentDescription = stringResource(R.string.dark),
                    modifier = Modifier
                        .offset(x = (-30).xp(), y = 25.xp())
                        .width(287.xp())
                        .height(287.xp())
                        .clickable {
                            chooseDarkColor.value = true
                            buttonEnabled.value = checkEnabledButton(
                                vm = vm,
                                textN = textName.value,
                                textP = textPrice.value,
                                free = checkedState.value,
                                dark = chooseDarkColor.value
                            )
                        },
                    alpha = if (chooseDarkColor.value) 1f else 0.3f
                )
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Image(
                        painter = painterResource(id = R.drawable.circle),
                        contentDescription = stringResource(R.string.circledark),
                        modifier = Modifier
                            .offset(x = (-30).xp(), y = (-56).xp())
                            .height(32.xp())
                            .width(32.xp())
                            .height(24.xp())
                            .width(24.xp())
                            .clip(shape = RoundedCornerShape(15.dp)),
                        alpha = if (chooseDarkColor.value) 1f else 0f
                    )
                    Image(
                        imageVector = Icons.Default.Check,
                        contentDescription = stringResource(R.string.okdark),
                        modifier = Modifier
                            .offset(x = (-30).xp(), y = (-56).xp()),
                        colorFilter = ColorFilter.tint(Color.White),
                        alpha = if (chooseDarkColor.value) 1f else 0f
                    )
                }
            }
        }
    }
}

fun checkInputText(text: String): Boolean {
    if (text.isEmpty()) return false
    return try {
        val number: Int = text.toInt()
        number > 0
    } catch (e: Exception) {
        false
    }
}

fun checkEnabledButton(
    vm: MainViewModel,
    textN: String,
    textP: String,
    free: Boolean,
    dark: Boolean
): Boolean {

    when {
        (vm.itemOrder.value?.name != textN) -> return true
        (vm.itemOrder.value?.price.toString() != textP) -> return true
        (vm.itemOrder.value?.isFree != free) -> return true
        (vm.itemOrder.value?.isDark != dark) -> return true
    }
    return false
}