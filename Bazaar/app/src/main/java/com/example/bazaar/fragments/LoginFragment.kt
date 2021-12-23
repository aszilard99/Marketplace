package com.example.bazaar.fragments

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.bazaar.R
import com.example.bazaar.repository.Repository
import com.example.bazaar.viewmodels.LoginViewModel
import com.example.bazaar.viewmodels.LoginViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch


class LoginFragment : Fragment() {
    private lateinit var usernameET: EditText
    private lateinit var passwordET: EditText
    private lateinit var loginButton: Button
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var registerButton : Button
    private lateinit var forgotPwButton : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = LoginViewModelFactory(this.requireContext(), Repository())
        //looks for or if doesnt exist already creates a LoginViewModel with its lifecycle connected to the activity
        loginViewModel = ViewModelProvider(this, factory).get(LoginViewModel::class.java)




    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        view?.apply {

            initializeView(view)
        }


        //this observers the loginViewModel's token, and if it changes it navigates to timeLineFragment
        //!!only observes the token change if we are on the LoginFragment screen, if we are on another fragment this stops/gets destroyed
        loginViewModel.token.observe(viewLifecycleOwner){

            //token returned means successfull login, user data can be saved
            saveLoginDataToSharedpreferences(loginViewModel.token.value.toString())

            findNavController().navigate(R.id.timelineFragment)
        }

        return view
    }

    private fun initializeView(view : View) {



        //bottomNavigation = view.findViewById(R.id.bottom_navigation)
        //bottomNavigation.setVisibility(View.INVISIBLE)
        usernameET = view.findViewById(R.id.loginUsernameET)
        passwordET = view.findViewById(R.id.loginPasswordET)
        fillInLoginDataIfExist()

        forgotPwButton = view.findViewById(R.id.forgot_password_button)
        forgotPwButton.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_resetPasswordFragment)
        }
        registerButton = view.findViewById(R.id.register_B_login_fragment)
        registerButton.setOnClickListener {
            findNavController().navigate(R.id.registerFragment)
        }
        loginButton = view.findViewById(R.id.loginButton)
        loginButton.setOnClickListener{
            var isValid = true
            if (usernameET.text.toString().trim().isEmpty()){
                isValid = false
                usernameET.setError("Username required")
            }
            if (passwordET.text.toString().trim().isEmpty()){
                isValid = false
                passwordET.setError("Password required")
            }
            //only start the login process if the fields are not empty
            if (isValid) {
                loginProcess(usernameET.text.toString(),passwordET.text.toString())
            }
        }
    }


    private fun fillInLoginDataIfExist() {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
        val username = sharedPref?.getString(getString(R.string.username_sharedpreferences_string_resource), "").toString()
        val password = sharedPref?.getString(getString(R.string.password_sharedpreferences_string_resource), "").toString()
        Log.d("xxx", "1 from sharedPref username ${username}")
        Log.d("xxx", "1 from sharedPref password ${password}")
        if (!username.isEmpty() && !password.isEmpty()){
            usernameET.setText(username)
            passwordET.setText(password)
        }
    }

    private fun saveLoginDataToSharedpreferences(token : String) {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {
            putString("username", usernameET.text.toString())
            putString("password", passwordET.text.toString())
            putString("token", token)
            apply()
        }
        Log.d("xxx", "2 from sharedPref username ${sharedPref.getString("username", "").toString()}")
        Log.d("xxx", "2 from sharedPref password ${sharedPref.getString("password", "").toString()}")
    }


    private fun loginProcess(username : String, password : String){
        loginViewModel.user.value.let {
            if (it != null) {
                it.username = username
                it.password = password
            }
        }
        //launches Coroutine with its lifeCycle tied to LoginFragment
        lifecycleScope.launch {
            loginViewModel.login()
        }
    }

}