package com.example.bazaar.fragments

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
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
import com.example.bazaar.viewmodels.TimelineViewModel
import com.example.bazaar.viewmodels.TimelineViewModelFactory
import kotlinx.coroutines.launch
import java.lang.Exception


class AddItemFragment : Fragment() {
    private lateinit var titleET : EditText
    private lateinit var descriptionET : EditText
    private lateinit var pricePerUnitET : EditText
    private lateinit var totalAmountET : EditText
    private lateinit var launchProductB : Button
    private lateinit var repository : Repository
    private lateinit var title : String
    private lateinit var description : String
    private lateinit var pricePerUnit : String
    private lateinit var totalAmount:  String
    private lateinit var timelineViewModel: TimelineViewModel
    private lateinit var username: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //username from sharedPref
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
        username = sharedPref?.getString(getString(R.string.username_sharedpreferences_string_resource), "").toString()

        val factory = TimelineViewModelFactory(Repository(), sharedPref!!)
        timelineViewModel = ViewModelProvider(requireActivity(), factory).get(TimelineViewModel::class.java)
        timelineViewModel.token = sharedPref?.getString("token", "").toString()
    }

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
        titleET = view.findViewById(R.id.title_ET_addItemFragment)
        descriptionET = view.findViewById(R.id.description_ET_addItemFragment)
        pricePerUnitET  = view.findViewById(R.id.price_per_unit_ET_addItemFragment)
        totalAmountET = view.findViewById(R.id.total_amount_ET_addItemFragment)
        launchProductB = view.findViewById(R.id.launch_product_B_addItemFragment)

        //TODO maybe there should only be one Repository object in the application, now there are several
        repository = Repository()
        launchProductB.setOnClickListener {
            var isValid = true
            if (TextUtils.isEmpty(titleET.text)){
                titleET.setError("this field cannot be empty")
                isValid = false
            }
            if (TextUtils.isEmpty(descriptionET.text)){
                descriptionET.setError("this field cannot be empty")
                isValid = false
            }
            if (TextUtils.isEmpty(pricePerUnitET.text)){
                pricePerUnitET.setError("this field cannot be empty")
                isValid = false
            }
            if (TextUtils.isEmpty(totalAmountET.text)){
                totalAmountET.setError("this field cannot be empty")
                isValid = false
            }
            if (isValid == true){
                title = titleET.text.toString()
                description = descriptionET.text.toString()
                pricePerUnit = pricePerUnitET.text.toString()
                totalAmount = totalAmountET.text.toString()

                addProduct(title, description, pricePerUnit, totalAmount)

            }
        }

    }
    private fun addProduct(title : String, description : String, pricePerUnit : String, totalAmount : String) {
        lifecycleScope.launch{
            try {
                val result = repository.addProduct(
                    timelineViewModel.token,
                    title,
                    description,
                    pricePerUnit,
                    totalAmount,
                    true,
                    3.5,
                    "Kg",
                    "RON"
                )
                Log.d("xxx", "addItem: ${result}")
                timelineViewModel.refreshProducts()
                findNavController().navigate(R.id.action_addItemFragment_to_myMarketFragment)
            }catch (e: Exception) {
                Log.d("xxx", "addItemFragment exception: ${e.toString()}")

            }
        }
    }

}