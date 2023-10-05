package com.example.di_hw

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(var repo:Repository):ViewModel() {
    private val _uiState = MutableLiveData<UIState>(UIState.Empty)
    val uiState: LiveData<UIState> = _uiState

    fun getData() {
        _uiState.value = UIState.Processing
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    var bitcoinResult = ""
                    var ounceResult = ""
                    var dashResult =""
                    var binanceResult = ""
                    var data: Data?

                    val bitcoin = repo.getCurrencyByName("bitcoin")
                    val ounce = repo.getCurrencyByName("silver-ounce")
                    val dash = repo.getCurrencyByName("dash")
                    val binance = repo.getCurrencyByName("binance-coin")
                    if (bitcoin.isSuccessful) {
                        data = bitcoin.body()?.data
                        bitcoinResult = "${data?.id?.uppercase()}\n${data?.symbol}\n${data?.rateUsd}"
                    }
                    if (ounce.isSuccessful) {
                        data = ounce.body()?.data
                        ounceResult = "${data?.id?.uppercase()}\n${data?.symbol}\n${data?.rateUsd}"
                    }
                    if (dash.isSuccessful) {
                        data = dash.body()?.data
                        dashResult ="${data?.id?.uppercase()}\n${data?.symbol}\n${data?.rateUsd}"
                    }
                    if (binance.isSuccessful) {
                        data = binance.body()?.data
                        binanceResult = "${data?.id?.uppercase()}\n${data?.symbol}\n${data?.rateUsd}"
                    }
                    else _uiState.postValue(UIState.Error("Error Code ${bitcoin.code()}"))

                    _uiState.postValue(UIState.Result(bitcoinResult, ounceResult, dashResult, binanceResult, ""))

                } catch (e: Exception) {
                    _uiState.postValue(UIState.Error(e.localizedMessage))
                }
            }
        }
    }
    sealed class UIState {
        object Empty : UIState()
        object Processing : UIState()
        class Result(val curBitcoin:String, val curOunce:String, val curDash:String, val curBinance:String, val text:String) : UIState()
        class Error(val description: String) : UIState()
    }

}