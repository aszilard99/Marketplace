package com.example.bazaar.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.bazaar.R
import com.example.bazaar.repository.Repository
import com.example.bazaar.viewmodels.TimelineViewModel
import com.example.bazaar.viewmodels.TimelineViewModelFactory
import kotlinx.coroutines.launch


class MyProfileFragment : Fragment() {
    private lateinit var usernameET : EditText
    private lateinit var usernameTextView : TextView
    private lateinit var phoneET : EditText
    private lateinit var emailTextView: TextView
    private lateinit var publishButton : Button
    private lateinit var repo : Repository
    private lateinit var timelineViewModel : TimelineViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        repo = Repository()
        //username from sharedPref
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)

        val factory = TimelineViewModelFactory(repo, sharedPref!!)
        timelineViewModel = ViewModelProvider(requireActivity(), factory).get(TimelineViewModel::class.java)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_profile, container, false)
        view?.apply {
            initializeView(view)
        }
        return view
    }

    private fun initializeView(view: View) {
        usernameET = view.findViewById(R.id.username_ET_myprofile_fragment)
        usernameTextView = view.findViewById(R.id.username_TV_my_profile_fragment)
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
        usernameTextView.text = sharedPref?.getString("username", "").toString()
        phoneET = view.findViewById(R.id.phonenumber_ET_myprofile_fragment)
        emailTextView = view.findViewById(R.id.email_TV_my_profile_fragment)
        publishButton = view.findViewById(R.id.publish_profile_B_myprofile_fragment)
        publishButton.setOnClickListener {
            var isValid = true
            if (usernameET.text.toString().trim().isEmpty()){
                isValid = false
                usernameET.setError("Username required")
            }
            if (phoneET.text.toString().trim().isEmpty()){
                isValid = false
                phoneET.setError("Password required")
            }
            if (isValid) {
                lifecycleScope.launch {
                    try {
                        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
                        val token = sharedPref?.getString("token", "").toString()

                        val username = usernameET.text.toString()
                        val phone = phoneET.text.toString()

                        val response = repo.updateUser(token, username, phone)

                        Log.d("xxx", "myProfileFragment, userUpdate done, new token : ${response.updatedData.token}")
                        sharedPref?.edit()?.putString("token", response.updatedData.token)
                        sharedPref?.edit()?.putString("username", response.updatedData.username)
                        usernameTextView.text = response.updatedData.username
                    }catch(e: Exception){

                    }
                }
            }
        }


    }

}
