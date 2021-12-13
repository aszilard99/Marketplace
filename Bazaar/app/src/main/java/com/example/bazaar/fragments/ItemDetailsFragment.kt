package com.example.bazaar.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = TimelineViewModelFactory(Repository())
        timelineViewModel = ViewModelProvider(this, factory).get(TimelineViewModel::class.java)

        try {

        }catch(e: NullPointerException){
            Log.d("xxx", " productList - ItemDetailsFragment null")
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_item_details, container, false)
        // Inflate the layout for this fragment
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



        title.text = timelineViewModel.currentProduct.title
    }
}