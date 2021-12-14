package com.example.bazaar.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.bazaar.R
import com.example.bazaar.recyclerview.dataadapters.TimelineDataAdapter
import com.example.bazaar.repository.Repository
import com.example.bazaar.viewmodels.TimelineViewModel
import com.example.bazaar.viewmodels.TimelineViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView


class MyFaresFragment : Fragment() {
    private lateinit var timelineViewModel: TimelineViewModel
    private lateinit var adapter: TimelineDataAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var bottomNavigation : BottomNavigationView

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
        val view = inflater.inflate(R.layout.fragment_my_fares, container, false)
        view?.apply {
            initializeView(view)
        }
        return view
    }

    private fun initializeView(view: View) {
        //topAppbar
        setHasOptionsMenu(true)

        //bottom navigation
        bottomNavigation = view.findViewById(R.id.bottom_navigation_myfares)
        bottomNavigation.setOnItemSelectedListener (NavigationBarView.OnItemSelectedListener { menuItem ->
            menuItem.isChecked = false
            when(menuItem.itemId){
                R.id.timelineMenuItem -> findNavController().navigate(R.id.timelineFragment)
                R.id.myMarketMenuItem -> findNavController().navigate(R.id.myMarketFragment)
            }
            true
        })



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