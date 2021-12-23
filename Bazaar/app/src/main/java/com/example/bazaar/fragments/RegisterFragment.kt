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
import com.example.bazaar.model.RegisterRequest
import com.example.bazaar.repository.Repository
import kotlinx.coroutines.launch


class RegisterFragment : Fragment() {
    private lateinit var usernameET : EditText
    private lateinit var passwordET : EditText
    private lateinit var phoneET : EditText
    private lateinit var emailET : EditText
    private lateinit var registerB : Button

    //to the register request the backend returns an HTML file not the specified JSON with result_code so no implementation for now
    //var resultCode = MutableLiveData<Int>(0)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_register, container, false)
        view?.apply {
            initializeView(view)

        }
        return view
    }

    private fun initializeView(view: View) {
        usernameET = view.findViewById(R.id.username_ET_register_fragment)
        passwordET = view.findViewById(R.id.password_ET_register_fragment)
        phoneET = view.findViewById(R.id.phone_ET_register_fragment)
        emailET = view.findViewById(R.id.email_ET_register_fragment)
        registerB = view.findViewById(R.id.register_button_register_fragment)


        //to the register request the backend returns an HTML file not the specified JSON with result_code so no implementation for now
        /*resultCode.observe(viewLifecycleOwner){
            resultCode.value?.let { it1 -> checkResultCode(it1) }
        }*/

        registerB.setOnClickListener {
            var isValid = true
            if (usernameET.text.toString().trim().isEmpty()){
                isValid = false
                usernameET.setError("Username required")
            }
            if (passwordET.text.toString().trim().isEmpty()){
                isValid = false
                passwordET.setError("Password required")
            }
            if (phoneET.text.toString().trim().isEmpty()){
                isValid = false
                phoneET.setError("phone required")
            }
            if (emailET.text.toString().trim().isEmpty()){
                isValid = false
                emailET.setError("email required")
            }
            //only start the login process if the fields are not empty
            if (isValid) {

                lifecycleScope.launch() {
                    register()
                }
                //Toast.makeText(requireContext(), "Register probably Successfull (backend doesnt return code)", Toast.LENGTH_LONG).show()
                //findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            }
        }
    }

    //to the register request the backend returns an HTML file not the specified JSON with result_code so no implementation for now
    private fun checkResultCode(resultCode: Int) {
        Log.d("xxx", "Register Fragment result:  ${resultCode} ")
        if (resultCode.compareTo(200) == 0){
            Toast.makeText(requireContext(), "Creation Successfull", Toast.LENGTH_SHORT)

        }
        if (resultCode.compareTo(300) == 0){
            Toast.makeText(requireContext(), "some details are missing", Toast.LENGTH_SHORT)

        }
        if (resultCode.compareTo(301) == 0){
            Toast.makeText(requireContext(), "wrong file format", Toast.LENGTH_SHORT)

        }
        if (resultCode.compareTo(302) == 0){
            Toast.makeText(requireContext(), "email incorrect", Toast.LENGTH_SHORT)

        }
        if (resultCode.compareTo(303) == 0){
            Toast.makeText(requireContext(), "username, email or phone number already in use", Toast.LENGTH_SHORT)

        }

    }


    //to the register request the backend returns an HTML file not the specified JSON with result_code so no implementation for now
    suspend fun register() {
        val repository = Repository()
        val request = RegisterRequest(username = usernameET.text.toString(), password = passwordET.text.toString(), email = emailET.text.toString(), phone_number = phoneET.text.toString())
        try {
            //to the register request the backend returns an HTML file not the specified JSON with result_code so no implementation for now
            val result = repository.register(request)
            Toast.makeText(requireContext(), "Register probably Successfull (backend doesnt return code)", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)

        } catch (e: Exception) {
            Log.d("xxx", "RegisterFragment - exception: ${e.toString()}")
        }
    }
}