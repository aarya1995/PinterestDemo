package com.example.pinterestdemo.ui.main

import android.content.Context
import android.util.Log
import com.airbnb.mvrx.BaseMvRxViewModel
import com.airbnb.mvrx.MvRxState
import com.pinterest.android.pdk.PDKCallback
import com.pinterest.android.pdk.PDKClient
import com.pinterest.android.pdk.PDKException
import com.pinterest.android.pdk.PDKResponse

data class MainState(
    val loginState: AuthViewModel.LoginState = AuthViewModel.LoginState.LOGGED_OUT
): MvRxState

class AuthViewModel(
    initialState: MainState
): BaseMvRxViewModel<MainState>(initialState, debugMode = true) {

    fun loginWithPinterest(pdkClient: PDKClient, context: Context) {
        val loginPermissions = listOf(
            PDKClient.PDKCLIENT_PERMISSION_READ_PUBLIC,
            PDKClient.PDKCLIENT_PERMISSION_WRITE_PUBLIC,
            PDKClient.PDKCLIENT_PERMISSION_READ_RELATIONSHIPS,
            PDKClient.PDKCLIENT_PERMISSION_WRITE_RELATIONSHIPS
        )

        pdkClient.login(context, loginPermissions, object: PDKCallback() {
            override fun onSuccess(response: PDKResponse?) {
                response?.let {
                    Log.d(TAG, it.data.toString())
                    setState { copy(loginState = LoginState.LOGGED_IN) }
                }
            }

            override fun onFailure(exception: PDKException?) {
                exception?.let {
                    Log.e(TAG, exception.detailMessage)
                    setState { copy(loginState = LoginState.LOG_IN_ERROR) }
                }
            }
        })
    }

    companion object {
        private const val TAG = "MainViewModel"
    }

    enum class LoginState {
        LOGGED_OUT, LOGGED_IN, LOG_IN_ERROR
    }

}
