package com.example.bazaar.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.example.bazaar.R


class AddItemFragment : Fragment() {
    private lateinit var titleET : EditText
    private lateinit var descriptionET : EditText
    private lateinit var pricePerUnitET : EditText
    private lateinit var totalAmountET : EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_add_item, container, false)
        view?.apply { 
            initializeView(view)
        }
        return view
    }

    private fun initializeView(view: View) {

    }

}