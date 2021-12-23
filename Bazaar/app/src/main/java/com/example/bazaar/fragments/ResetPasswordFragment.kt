package com.example.bazaar.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.bazaar.R
import com.example.bazaar.model.LoginRequest
import com.example.bazaar.model.ResetPasswordRequest
import com.example.bazaar.repository.Repository
import kotlinx.coroutines.launch


class ResetPasswordFragment : Fragment() {

    private lateinit var usernameET: EditText
    private lateinit var emailET: EditText
    private lateinit var resetButton: Button
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_reset_password, container, false)
        view?.apply{
            initializeView(view)
        }
        return view
    }

    private fun initializeView(view: View) {
        usernameET =view.findViewById(R.id.username_reset_pw_et)
        emailET =view.findViewById(R.id.email_reset_pw_et)
        resetButton = view.findViewById(R.id.reset_button_reset_fragment)


        resetButton.setOnClickListener {
            if (!usernameET.text.isEmpty() && !emailET.text.isEmpty()){
                resetPasswordProcess(usernameET.text.toString(), emailET.text.toString())
            }
        }
    }

    private fun resetPasswordProcess(username: String, email: String) {
        val repository = Repository()
        val request = ResetPasswordRequest(email=email ,username = username)

        lifecycleScope.launch {
            try {
                val result = repository.resetPassword(request)
                Log.d("xxx", "MyApplication - token:  ${result}")
                Toast.makeText(requireContext(),"${result.message}", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_resetPasswordFragment_to_loginFragment)
            } catch (e: Exception) {
                Log.d("xxx", "LoginViewModel - exception: ${e.toString()}")
            }
        }
    }

}