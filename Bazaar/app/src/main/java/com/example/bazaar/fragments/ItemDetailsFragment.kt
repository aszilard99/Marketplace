package com.example.bazaar.fragments

import android.os.Bundle
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
import com.example.bazaar.R
import com.example.bazaar.model.Product
import com.example.bazaar.repository.Repository
import com.example.bazaar.viewmodels.TimelineViewModel
import com.example.bazaar.viewmodels.TimelineViewModelFactory
import java.lang.NullPointerException


class ItemDetailsFragment : Fragment() {
    private lateinit var title : TextView
    private lateinit var owner:  TextView
    private lateinit var price_per_unit:  TextView
    private lateinit var description :  TextView
    private lateinit var timelineViewModel : TimelineViewModel
    private lateinit var productList : List<Product>
    private lateinit var order_button : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_item_details, container, false)
        // Inflate the layout for this fragment
        val factory = TimelineViewModelFactory(Repository())


        try {
            timelineViewModel = ViewModelProvider(requireActivity(), factory).get(TimelineViewModel::class.java)
        }catch(e: NullPointerException){
            Log.d("xxx", " productList - ItemDetailsFragment null")
        }
        view?.apply{

            initializeView(view)
        }
        return view
    }

    private fun initializeView(view: View) {
        title = view.findViewById(R.id.titleTV)
        owner = view.findViewById(R.id.ownerTV)
        price_per_unit = view.findViewById(R.id.pricePerUnitTV)
        description = view.findViewById(R.id.descriptionTV)

        //TODO properly implementing order process
        order_button = view.findViewById(R.id.order_button_itemdetails_fragment)
        order_button.setOnClickListener {
            withEditText(view)
        }


        title.text = timelineViewModel.currentProduct.title
        owner.text = timelineViewModel.currentProduct.username
        price_per_unit.text = timelineViewModel.currentProduct.price_per_unit
        description.text = timelineViewModel.currentProduct.description

    }

    //TODO properly implementing order dialog
    fun withEditText(view: View) {
        val builder = AlertDialog.Builder(requireContext())
        val inflater = layoutInflater
        builder.setTitle("With EditText")
        val dialogLayout = inflater.inflate(R.layout.order_dialog, null)
        val editText  = dialogLayout.findViewById<EditText>(R.id.editText)
        builder.setView(dialogLayout)
        builder.setPositiveButton("OK") { dialogInterface, i -> Toast.makeText(requireContext(), "EditText is " + editText.text.toString(), Toast.LENGTH_SHORT).show() }
        builder.show()
    }
}