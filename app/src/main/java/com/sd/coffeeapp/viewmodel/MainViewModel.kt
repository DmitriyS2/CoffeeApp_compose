package com.sd.coffeeapp.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sd.coffeeapp.dto.DataItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val prefs =
        application.applicationContext.getSharedPreferences("coffee", Context.MODE_PRIVATE)
    private val nameKey = "name"
    private val priceKey = "price"
    private val isFreeKey = "isFree"
    private val isDarkKey = "isDark"

    private val _temp = MutableLiveData<Double>()
    val temp: LiveData<Double>
        get() = _temp

    private val _itemOrder = MutableLiveData<DataItem>()
    val itemOrder: LiveData<DataItem>
        get() = _itemOrder

    private val _listItemOrder = MutableLiveData<List<DataItem>>()
    val listItemOrder: LiveData<List<DataItem>>
        get() = _listItemOrder

    init {
        getTemp()
        _itemOrder.value = getItem()
        _listItemOrder.value = getListItem()
    }

    private fun getItem(): DataItem {
        val name = prefs.getString(nameKey, "")
        val price = prefs.getInt(priceKey, 0)
        val isFree = prefs.getBoolean(isFreeKey, false)
        val isDark = prefs.getBoolean(isDarkKey, false)
        return DataItem(
            name = name ?: "",
            price = price,
            isFree = isFree,
            isDark = isDark
        )
    }

    fun setItem(name: String, price: Int, isFree: Boolean, isDark: Boolean) {
        with(prefs.edit()) {
            putString(nameKey, name)
            putInt(priceKey, price)
            putBoolean(isFreeKey, isFree)
            putBoolean(isDarkKey, isDark)
            apply()
        }
        _itemOrder.value = getItem()
        _listItemOrder.value = getListItem()
    }

    private fun getListItem() = List(15) { _itemOrder.value ?: DataItem() }

    private fun getTemp() {
        viewModelScope.launch {
            while (true) {
                _temp.value = Random.nextInt(850, 950) / 10.0
                delay(1000)
            }
        }
    }
}