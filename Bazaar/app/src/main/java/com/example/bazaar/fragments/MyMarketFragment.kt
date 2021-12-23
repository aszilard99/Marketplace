package com.example.bazaar.fragments

import android.content.Context
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
import com.example.bazaar.model.Product
import com.example.bazaar.recyclerview.dataadapters.TimelineDataAdapter
import com.example.bazaar.repository.Repository
import com.example.bazaar.viewmodels.TimelineViewModel
import com.example.bazaar.viewmodels.TimelineViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationBarView
import java.lang.Exception
import java.lang.NullPointerException


class MyMarketFragment : Fragment(), TimelineDataAdapter.OnItemClickListener, TimelineDataAdapter.OnOrderButtonClickListener {

    private lateinit var timelineViewModel: TimelineViewModel
    private lateinit var list : List<Product>
    private lateinit var recyclerView: RecyclerView
    private lateinit var bottomNavigation: BottomNavigationView
    private lateinit var adapter : TimelineDataAdapter
    private lateinit var addItemButton : FloatingActionButton
    private lateinit var username : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)

        val factory = TimelineViewModelFactory(Repository(), sharedPref!!)
        timelineViewModel = ViewModelProvider(requireActivity(), factory).get(TimelineViewModel::class.java)

        //username from sharedPreferences

        username = sharedPref?.getString(getString(R.string.username_sharedpreferences_string_resource), "").toString()
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

        //shared preferences for getting the username
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
        val username = sharedPref?.getString(getString(R.string.username_sharedpreferences_string_resource), "").toString()

        recyclerView = view.findViewById(R.id.recycler_view_my_market)

        recyclerView.layoutManager = LinearLayoutManager(this.context)
        recyclerView.setHasFixedSize(true)
        try {
            list = timelineViewModel.products.value?.filter {
                it.username == username
            }!!
            adapter = TimelineDataAdapter(username, list!!, this,this)
            recyclerView.adapter = adapter
        }catch(e: Exception){
            Log.d("xxx", "myMarketFragment - adapter input list null")
        }


        addItemButton = view.findViewById(R.id.add_button_my_market)
        addItemButton.setOnClickListener {
            findNavController().navigate(R.id.action_myMarketFragment_to_addItemFragment)
        }


    }


    override fun onItemClick(position: Int) {
        try {
            //timelineViewModel.currentProduct = timelineViewModel.products.value!!.get(position)
            timelineViewModel.currentProduct = list.get(position)
            Log.d("xxx", timelineViewModel.currentProduct.toString())
        }catch(e: java.lang.NullPointerException){

        }
        findNavController().navigate(R.id.action_myMarketFragment_to_itemDetailsFragment)
        Log.d("xxx", "AdapterPosition: $position")
    }

    //topAppbar
    //TODO (refresh gombot hozzaadni hogy frissljon a recyclerview)
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
        R.id.logout_top_appbar_menu_item -> {
            findNavController().navigate(R.id.action_myMarketFragment_to_loginFragment)
            true
        }
        R.id.refresh_top_appbar_menu_item -> {
            timelineViewModel.refreshProducts()
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