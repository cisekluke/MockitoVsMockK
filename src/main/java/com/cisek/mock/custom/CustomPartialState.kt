package com.cisek.mock.custom

sealed class CustomPartialState {
    object Init : CustomPartialState()
    object Loading : CustomPartialState()
    data class Error(val message: String) : CustomPartialState()
    data class Data(val data: List<String>) : CustomPartialState()
}