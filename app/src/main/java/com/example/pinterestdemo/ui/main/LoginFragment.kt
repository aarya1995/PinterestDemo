package com.example.pinterestdemo.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.airbnb.mvrx.BaseMvRxFragment
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.example.pinterestdemo.MainFeedActivity
import com.example.pinterestdemo.R
import com.google.android.material.snackbar.Snackbar
import com.pinterest.android.pdk.PDKClient
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : BaseMvRxFragment() {

    private val authViewModel: AuthViewModel by fragmentViewModel()
    private lateinit var pdkClient: PDKClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configurePinterestClient()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureViews()
    }

    private fun configureViews() {
        loginButton.setOnClickListener {
            authViewModel.loginWithPinterest(pdkClient, requireContext())
        }
    }

    private fun configurePinterestClient() {
        pdkClient = PDKClient.configureInstance(requireContext(), resources.getString(R.string.pinterest_app_id))
        pdkClient.onConnect(requireContext())
        PDKClient.setDebugMode(true)
    }

    fun handlePinterestOAuthResponse(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d(TAG, "received callback")
        pdkClient.onOauthResponse(requestCode, resultCode, data)
    }

    private fun redirectToFeed() {
        val intent = Intent(requireContext(), MainFeedActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        requireActivity().finish()
    }

    /**
     * Override this to handle any state changes from MvRxViewModels created through MvRx Fragment delegates.
     */
    override fun invalidate() = withState(authViewModel) {
        if (it.loginState == AuthViewModel.LoginState.LOGGED_IN) {
            Log.d(TAG, "logged in successfully!")
            redirectToFeed()
        }

        if (it.loginState == AuthViewModel.LoginState.LOG_IN_ERROR) {
            Log.e(TAG, "login error.")
            Snackbar.make(view!!, "Failed to login user :-(", Snackbar.LENGTH_SHORT).show()
        }
    }

    companion object {
        fun newInstance() = LoginFragment()
        const val TAG = "LoginFragment"
    }
}
