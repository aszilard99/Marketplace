package com.example.bazaar.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import com.example.bazaar.MyApplication
import com.example.bazaar.R


class MyProfileFragment : Fragment() {
    private lateinit var usernameET : TextView
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

    }

}