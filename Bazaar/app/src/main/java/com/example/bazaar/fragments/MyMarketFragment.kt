package com.example.bazaar.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
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


class MyMarketFragment : Fragment(), TimelineDataAdapter.OnItemClickListener, TimelineDataAdapter.OnOrderButtonClickListener {

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

        //topAppbar
        setHasOptionsMenu(true)

        recyclerView = view.findViewById(R.id.recycler_view_my_market)

        recyclerView.layoutManager = LinearLayoutManager(this.context)
        recyclerView.setHasFixedSize(true)
        try {
            val list = timelineViewModel.products.value?.filter {
                it.username == MyApplication.username
            }
            adapter = TimelineDataAdapter(list!!, this,this)
        }catch(e: NullPointerException){
            Log.d("xxx", "myMarketFragment - adapter input list null")
        }
        recyclerView.adapter = adapter

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

    //topAppbar

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.top_appbar_menu,menu)
    }
    //topAppbar
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId){
        R.id.profile_top_appbar_menu_item -> {
            findNavController().navigate(R.id.myProfileFragment)
            true
        }
        else -> {

            super.onOptionsItemSelected(item)
        }

    }

    override fun onOrderButtonClick(position: Int) {
        Log.d("MyMarketFragment", "order button task delegation success: $position")
    }
}