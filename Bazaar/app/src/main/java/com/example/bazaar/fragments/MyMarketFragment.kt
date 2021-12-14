package com.example.bazaar.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bazaar.MyApplication
import com.example.bazaar.R
import com.example.bazaar.recyclerview.dataadapters.TimelineDataAdapter
import com.example.bazaar.repository.Repository
import com.example.bazaar.viewmodels.TimelineViewModel
import com.example.bazaar.viewmodels.TimelineViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationBarView
import java.lang.NullPointerException


class MyMarketFragment : Fragment(), TimelineDataAdapter.OnItemClickListener {

    private lateinit var timelineViewModel: TimelineViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var bottomNavigation: BottomNavigationView
    private lateinit var adapter : TimelineDataAdapter
    private lateinit var addItemButton : FloatingActionButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = TimelineViewModelFactory(Repository())
        timelineViewModel = ViewModelProvider(requireActivity(), factory).get(TimelineViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_my_market, container, false)


        view?.apply {
            initializeView(view)
        }
        return view
    }

    private fun initializeView(view: View) {
        recyclerView = view.findViewById(R.id.recycler_view_my_market)

        recyclerView.layoutManager = LinearLayoutManager(this.context)
        recyclerView.setHasFixedSize(true)
        try {
            val list = timelineViewModel.products.value?.filter {
                //TODO username change to myusername when capable of uploading own products
                it.username == MyApplication.username
            }
            adapter = TimelineDataAdapter(list!!, this)
        }catch(e: NullPointerException){
            Log.d("xxx", "myMarketFragment - adapter input list null")
        }
        recyclerView.adapter = adapter
        bottomNavigation = view.findViewById(R.id.bottom_navigation_my_market)
        bottomNavigation.setOnItemSelectedListener (NavigationBarView.OnItemSelectedListener { menuItem ->
            menuItem.isChecked = true
            when(menuItem.itemId){
                R.id.timelineMenuItem -> findNavController().navigate(R.id.timelineFragment)
                R.id.myFaresMenuItem -> findNavController().navigate(R.id.myFaresFragment)
            }
            true
        })
        addItemButton = view.findViewById(R.id.add_button_my_market)
        addItemButton.setOnClickListener {
            findNavController().navigate(R.id.action_myMarketFragment_to_addItemFragment)
        }


    }


    override fun onItemClick(position: Int) {
        try {
            timelineViewModel.currentProduct = timelineViewModel.products.value!!.get(position)
            Log.d("xxx", timelineViewModel.currentProduct.toString())
        }catch(e: java.lang.NullPointerException){

        }
        findNavController().navigate(R.id.action_myMarketFragment_to_itemDetailsFragment)
        Log.d("xxx", "AdapterPosition: $position")
    }
}