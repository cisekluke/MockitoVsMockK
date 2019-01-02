package com.cisek.mock.custom

class CustomPartialStateReducer {
    companion object {
        fun reduceState(previousState: CustomViewState, partialState: CustomPartialState): CustomViewState {
            return when(partialState) {
                CustomPartialState.Init -> previousState.copy(loading = false, data = emptyList(), error = "")
                CustomPartialState.Loading -> previousState.copy(loading = true, data = emptyList(), error = "")
                is CustomPartialState.Data -> previousState.copy(loading = false, data = partialState.data, error = "")
                is CustomPartialState.Error -> previousState.copy(loading = false, data = emptyList(), error = partialState.message)
            }
        }
    }
}