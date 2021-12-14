package com.example.bazaar.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bazaar.R
import com.example.bazaar.recyclerview.dataadapters.TimelineDataAdapter
import com.example.bazaar.repository.Repository
import com.example.bazaar.viewmodels.TimelineViewModel
import com.example.bazaar.viewmodels.TimelineViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView


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
            findNavController().navigate(R.id.action_timelineFragment_to_itemDetailsFragment)
        }catch(e: java.lang.NullPointerException){

        }

        Log.d("xxx", "AdapterPosition: $position")
    }

    private fun initializeView(view: View){
        //topAppbar
        setHasOptionsMenu(true)


        recyclerView = view.findViewById(R.id.recycler_view_timeline)

        recyclerView.layoutManager = LinearLayoutManager(this.context)
        recyclerView.setHasFixedSize(true)


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



        /*bottomNavigation.setOnItemSelectedListener (NavigationBarView.OnItemSelectedListener {menuItem ->
            bottomNavigation.menu.getItem(0).isChecked = true

            when(menuItem.itemId){
                R.id.myMarketMenuItem -> findNavController().navigate(R.id.myMarketFragment)
                R.id.myFaresMenuItem -> findNavController().navigate(R.id.myFaresFragment)
            }

            true
        })*/



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