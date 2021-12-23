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
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.bazaar.R
import com.example.bazaar.api.RetrofitInstance
import com.example.bazaar.model.Product
import com.example.bazaar.repository.Repository
import com.example.bazaar.viewmodels.TimelineViewModel
import com.example.bazaar.viewmodels.TimelineViewModelFactory
import kotlinx.coroutines.launch
import java.lang.NullPointerException


class ItemDetailsFragment : Fragment() {
    private lateinit var title : TextView
    private lateinit var owner:  TextView
    private lateinit var price_per_unit:  TextView
    private lateinit var description :  TextView
    private lateinit var timelineViewModel : TimelineViewModel
    private lateinit var productList : List<Product>
    private lateinit var order_button : Button
    private lateinit var removeButton : Button
    private lateinit var token: String
    private val repository = Repository()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_item_details, container, false)
        // Inflate the layout for this fragment
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
        val factory = TimelineViewModelFactory(Repository(), sharedPref!!)


        try {
            timelineViewModel = ViewModelProvider(requireActivity(), factory).get(TimelineViewModel::class.java)
        }catch(e: NullPointerException){
            Log.d("xxx", " productList - ItemDetailsFragment null")
        }
        view?.apply{

            //token from sharedPref

            token = sharedPref?.getString("token", "").toString()
            initializeView(view)
        }
        return view
    }

    private fun initializeView(view: View) {
        title = view.findViewById(R.id.titleTV)
        owner = view.findViewById(R.id.ownerTV)
        price_per_unit = view.findViewById(R.id.pricePerUnitTV)
        description = view.findViewById(R.id.descriptionTV)
        removeButton = view.findViewById(R.id.remove_button_itemdetails_fragment)

        title.text = timelineViewModel.currentProduct.title
        owner.text = timelineViewModel.currentProduct.username
        price_per_unit.text = timelineViewModel.currentProduct.price_per_unit
        description.text = timelineViewModel.currentProduct.description

        //if the product is not the user's then hide the remove product button
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
        val username = sharedPref?.getString(getString(R.string.username_sharedpreferences_string_resource), "").toString()
        if (username != owner.text){
            removeButton.setVisibility(View.INVISIBLE)
        }else{
            removeButton.setVisibility(View.VISIBLE)
        }
        removeButton.setOnClickListener{
            val product_id = timelineViewModel.currentProduct.product_id

            lifecycleScope.launch {
                try{
                    val repo = Repository()
                    val result = repo.removeProduct(token, timelineViewModel.currentProduct.product_id)
                    timelineViewModel.refreshProducts()
                    findNavController().navigate(R.id.action_itemDetailsFragment_to_timelineFragment2)
                }catch(e: Exception){
                    Log.d("xxx", "Remove product error : $e")
                }
            }


        }

        //TODO properly implementing order process
        order_button = view.findViewById(R.id.order_button_itemdetails_fragment)
        order_button.setOnClickListener {
            withEditText(view)
        }




    }


    //TODO properly implementing order dialog
    fun withEditText(view: View) {

        val builder = AlertDialog.Builder(requireContext())
        val inflater = layoutInflater
        builder.setTitle("My Order")
        val dialogLayout = inflater.inflate(R.layout.order_dialog, null)
        val amountET = dialogLayout.findViewById<EditText>(R.id.orderDialogAmountET)

        //TODO comments not yet implemented
        val commentsET = dialogLayout.findViewById<EditText>(R.id.orderDialogCommentsET)
        var amount = "1"
        amountET.setText(amount)
        builder.setView(dialogLayout)
        /*builder.setPositiveButton("Order") {
                dialogInterface,
                i -> Toast.makeText(requireContext(), amountET.text.toString(), Toast.LENGTH_SHORT).show()

        }*/
        builder.setPositiveButton("Order") {
                dialogInterface,
                i -> if (!TextUtils.isEmpty(amountET.text)){
            Toast.makeText(requireContext(), "order dialog amount ET not empty", Toast.LENGTH_SHORT).show()
            addOrder(
                timelineViewModel.currentProduct.title,
                timelineViewModel.currentProduct.description,
                timelineViewModel.currentProduct.price_per_unit,
                amountET.text.toString(),
                timelineViewModel.currentProduct.username,
                "www.revolut.com"
            )
        }
        else{
            Toast.makeText(requireContext(), "order dialog amount ET empty", Toast.LENGTH_SHORT).show()
            addOrder(
                timelineViewModel.currentProduct.title,
                timelineViewModel.currentProduct.description,
                timelineViewModel.currentProduct.price_per_unit,
                amount,
                timelineViewModel.currentProduct.username,
                "www.revolut.com"
            )
        }

        }
        builder.setNegativeButton("Cancel") {dialogInterface, i -> Toast.makeText(requireContext(), "Order Canceled", Toast.LENGTH_SHORT).show()}
        builder.show()
    }

    private fun addOrder(title : String, description : String,price_per_unit : String,units : String, owner_username : String, revolut_link : String) {
        lifecycleScope.launch{
            try {
                val result = repository.addOrder(
                    timelineViewModel.token,
                    title,
                    description,
                    price_per_unit,
                    units,
                    owner_username,
                    revolut_link
                )
                Log.d("xxx", "addOrder: ${result}")
            }catch (e: java.lang.Exception) {
                Log.d("xxx", "addOrder exception: ${e.toString()}")

            }
        }
    }
}