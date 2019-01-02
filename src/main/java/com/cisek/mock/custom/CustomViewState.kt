package com.cisek.mock.custom

data class CustomViewState(val loading: Boolean = false, val data: List<String> = emptyList(), val error: String = "")