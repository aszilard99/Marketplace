package com.example.bazaar.fragments

import android.graphics.Color
import android.os.Bundle
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
    private lateinit var bottomNavigation : BottomNavigationView
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
            findNavController().navigate(R.id.timelineFragment)
        }

        return view
    }

    private fun initializeView(view : View) {



        //bottomNavigation = view.findViewById(R.id.bottom_navigation)
        //bottomNavigation.setVisibility(View.INVISIBLE)
        usernameET = view.findViewById(R.id.loginUsernameET)
        passwordET = view.findViewById(R.id.loginPasswordET)
        registerButton = view.findViewById(R.id.register_B_login_fragment)
        registerButton.setOnClickListener {
            findNavController().navigate(R.id.registerFragment)
        }
        loginButton = view.findViewById(R.id.loginButton)
        loginButton.setOnClickListener{
            loginViewModel.user.value.let{
                if (it != null) {
                    it.username = usernameET.text.toString()
                    it.password = passwordET.text.toString()
                }
            }
            //launches Coroutine with its lifeCycle tied to LoginFragment
            lifecycleScope.launch {
                loginViewModel.login()
            }
        }
    }


}