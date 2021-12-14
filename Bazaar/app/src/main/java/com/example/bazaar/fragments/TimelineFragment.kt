package com.example.bazaar.fragments

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bazaar.R
import com.example.bazaar.recyclerview.dataadapters.TimelineDataAdapter
import com.example.bazaar.repository.Repository
import com.example.bazaar.viewmodels.TimelineViewModel
import com.example.bazaar.viewmodels.TimelineViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.navigation.NavigationView


class TimelineFragment : Fragment(), TimelineDataAdapter.OnItemClickListener {
    private lateinit var  textView: TextView
    private lateinit var timelineViewModel: TimelineViewModel
    private lateinit var adapter: TimelineDataAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var bottomNavigation : BottomNavigationView
    private lateinit var searchButton : Button
    private lateinit var clearButton : Button
    private lateinit var searchET : EditText

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
        val view =  inflater.inflate(R.layout.fragment_timeline, container, false)
        initializeView(view)


        return view

    }

    override fun onItemClick(position: Int) {
        try {
            timelineViewModel.currentProduct = timelineViewModel.products.value!!.get(position)
            Log.d("xxx", timelineViewModel.currentProduct.toString())
        }catch(e: java.lang.NullPointerException){

        }
        findNavController().navigate(R.id.action_timelineFragment_to_itemDetailsFragment)
        Log.d("xxx", "AdapterPosition: $position")
    }

    private fun initializeView(view: View){
        recyclerView = view.findViewById(R.id.recycler_view_timeline)

        recyclerView.layoutManager = LinearLayoutManager(this.context)
        recyclerView.setHasFixedSize(true)

        //TODO bottomnavigation onItemReselected implementation needs to be done and maybe more
        bottomNavigation = view.findViewById(R.id.bottom_navigation_timeline)

        //when starting the app, or when making a new GET to the api to filter the products this gets executed
        timelineViewModel.products.observe(viewLifecycleOwner){
            try {
                adapter = timelineViewModel.products.value?.let { it1 -> TimelineDataAdapter(it1,this) }!!
            }catch(e : NullPointerException){
                Log.d("xxx-error", e.toString())
            }
            recyclerView.adapter = adapter
        }
        searchET = view.findViewById(R.id.timelineSearchET)
        searchButton = view.findViewById(R.id.timelineSearchButton)
        searchButton.setOnClickListener{
            val text = searchET.text
            if (text.toString() != ""){
                timelineViewModel.getFilteredProductsByTitle(text.toString())
            }
            else{
                //do nothing
            }
        }
        clearButton = view.findViewById(R.id.timelineClearButton)

        bottomNavigation.setOnItemSelectedListener (NavigationBarView.OnItemSelectedListener {menuItem ->
            menuItem.isChecked = true
            when(menuItem.itemId){
                R.id.myMarketMenuItem -> findNavController().navigate(R.id.myMarketFragment)
                R.id.myFaresMenuItem -> findNavController().navigate(R.id.myFaresFragment)
            }

            true
        })



        }

    }






/*
MenuItem ourSearchItem = menu.findItem(R.id.menu_item_search);

SearchView sv = (SearchView) ourSearchItem.getActionView();

sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
    @Override
    public boolean onQueryTextSubmit(String query) {
        Toast.makeText(getContext(), query, Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        adapter.getFilter().filter(newText);
    }
    return false;
}
});*/