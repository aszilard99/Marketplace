package com.example.bazaar.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bazaar.R
import com.example.bazaar.model.Order
import com.example.bazaar.recyclerview.dataadapters.OrdersDataAdapter
import com.example.bazaar.recyclerview.dataadapters.TimelineDataAdapter
import com.example.bazaar.repository.Repository
import com.example.bazaar.viewmodels.OrdersViewModel
import com.example.bazaar.viewmodels.OrdersViewModelFactory
import com.example.bazaar.viewmodels.TimelineViewModel
import com.example.bazaar.viewmodels.TimelineViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.tabs.TabLayout


class MyFaresFragment : Fragment() {
    private lateinit var timelineViewModel: TimelineViewModel
    private lateinit var adapter: OrdersDataAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var bottomNavigation : BottomNavigationView
    private lateinit var tabLayout : TabLayout
    private lateinit var ordersViewModel : OrdersViewModel
    private lateinit var repository: Repository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = OrdersViewModelFactory(Repository())
        ordersViewModel = ViewModelProvider(requireActivity(), factory).get(OrdersViewModel::class.java)
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
        tabLayout = view.findViewById(R.id.tab_layout)
        setupTabLayout()

        recyclerView = view.findViewById(R.id.recycler_view_myfares)
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        recyclerView.setHasFixedSize(true)


        ordersViewModel.orders.observe(viewLifecycleOwner){
            try {
                removeUselessCharactersOrder(ordersViewModel.orders)
                adapter = ordersViewModel.orders.value?.let { it1 -> OrdersDataAdapter(it1) }!!
            }catch(e : NullPointerException){
                Log.d("xxx-error", e.toString())
            }
            recyclerView.adapter = adapter
        }


    }


    private fun removeUselessCharactersOrder(orders: MutableLiveData<List<Order>>) {
        try {
            orders.value!!.forEach {
                it.title = it.title.replace("\"", "").replace("\\", "")
                it.description = it.description.replace("\"", "").replace("\\", "")
                it.units = it.units.replace("\"", "").replace("\\", "")
                it.price_per_unit = it.price_per_unit.replace("\"", "").replace("\\", "")
                it.order_id = it.order_id.replace("\"", "").replace("\\", "")
                it.owner_username = it.owner_username.replace("\"", "").replace("\\", "")
                it.username = it.username.replace("\"", "").replace("\\", "")
            }
        }catch(e: java.lang.NullPointerException){
            Log.d("xxx", "removeUselessCharactersOrder error")
        }
    }


    fun setupTabLayout(){
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab?.position == 0){
                    Toast.makeText(requireContext(),"0", Toast.LENGTH_SHORT).show()
                }
                if (tab?.position == 1){
                    Toast.makeText(requireContext(),"1", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // Handle tab reselect
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // Handle tab unselect
            }
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