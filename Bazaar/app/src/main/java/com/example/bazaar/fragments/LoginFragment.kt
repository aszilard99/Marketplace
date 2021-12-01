package com.example.bazaar.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.bazaar.R
import com.example.bazaar.repository.Repository
import com.example.bazaar.viewmodels.LoginViewModel
import com.example.bazaar.viewmodels.LoginViewModelFactory
import kotlinx.coroutines.launch


class LoginFragment : Fragment() {
    private lateinit var usernameET: EditText
    private lateinit var passwordET: EditText
    private lateinit var loginButton: Button
    private lateinit var loginViewModel: LoginViewModel

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

        return view
    }

    private fun initializeView(view : View) {
        usernameET = view.findViewById(R.id.loginUsernameET)
        passwordET = view.findViewById(R.id.loginPasswordET)

        loginButton = view.findViewById(R.id.loginButton)
        loginButton.setOnClickListener{
            loginViewModel.user.value.let{
                if (it != null) {
                    it.username = usernameET.text.toString()
                    it.password = passwordET.text.toString()
                }
            }
            lifecycleScope.launch {
                loginViewModel.login()
            }
        }
    }


}