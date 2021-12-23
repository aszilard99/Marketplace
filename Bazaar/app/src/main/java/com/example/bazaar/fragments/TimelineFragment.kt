package com.example.bazaar.fragments

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bazaar.R
import com.example.bazaar.model.Product
import com.example.bazaar.recyclerview.dataadapters.TimelineDataAdapter
import com.example.bazaar.repository.Repository
import com.example.bazaar.viewmodels.TimelineViewModel
import com.example.bazaar.viewmodels.TimelineViewModelFactory
import kotlinx.coroutines.launch
import java.lang.Exception


class TimelineFragment : Fragment(), TimelineDataAdapter.OnItemClickListener, TimelineDataAdapter.OnOrderButtonClickListener {
    private lateinit var timelineViewModel: TimelineViewModel
    private lateinit var adapter: TimelineDataAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchButton : Button
    private lateinit var searchET : EditText
    private lateinit var repository : Repository
    private lateinit var username: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //username from sharedPref
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
        username = sharedPref?.getString(getString(R.string.username_sharedpreferences_string_resource), "").toString()

        val factory = TimelineViewModelFactory(Repository(), sharedPref!!)
        timelineViewModel = ViewModelProvider(requireActivity(), factory).get(TimelineViewModel::class.java)
        timelineViewModel.token = sharedPref?.getString("token", "").toString()


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

        repository = Repository()


        recyclerView = view.findViewById(R.id.recycler_view_timeline)
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        recyclerView.setHasFixedSize(true)


        //when starting the app, or when making a new GET to the api to filter the products this gets executed
        timelineViewModel.products.observe(viewLifecycleOwner){
            try {
                removeUselessCharactersProduct(timelineViewModel.products)
                adapter = timelineViewModel.products.value?.let { it1 -> TimelineDataAdapter(username, it1,this, this) }!!
            }catch(e : NullPointerException){
                Log.d("xxx-error", e.toString())
            }
            recyclerView.adapter = adapter
        }
        searchET = view.findViewById(R.id.timelineSearchET)
        searchET.setVisibility(View.GONE)
        searchButton = view.findViewById(R.id.timelineSearchButton)
        searchButton.setVisibility(View.GONE)
        searchButton.setOnClickListener{
            val text = searchET.text
            if (text.toString() != ""){
                try {
                    var a = timelineViewModel.products.value
                    var b = a!!.map { it }?.filter { it.title == text.toString() }
                    //-> working // timelineViewModel.products.value = b
                    recyclerView.setAdapter(TimelineDataAdapter(username,b,this,this));
                    recyclerView.invalidate();

                }catch(e : NullPointerException){
                    Log.d("xxx-error", e.toString())
                }
            }

        }







        }
    //topAppbar
    //TODO (refresh gombot hozzaadni hogy frissljon a recyclerview)
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.top_appbar_menu_timeline,menu)
    }
    //topAppbar
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId){
        R.id.profile_top_appbar_menu_item -> {
            findNavController().navigate(R.id.action_timelineFragment_to_myProfileFragment)
            true
        }
        R.id.logout_top_appbar_menu_item -> {
            findNavController().navigate(R.id.action_timelineFragment_to_loginFragment)
            true
        }
        R.id.refresh_top_appbar_menu_item -> {
            timelineViewModel.refreshProducts()
            true
        }
        R.id.filter_top_appbar_menu_item -> {
            if (searchET.visibility == View.GONE){
                searchET.visibility = View.VISIBLE
                searchButton.visibility = View.VISIBLE
            }else{
                searchET.visibility = View.GONE
                searchButton.visibility = View.GONE
            }
            true
        }
        else -> {

            super.onOptionsItemSelected(item)
        }

    }

    override fun onOrderButtonClick(position: Int) {

        try {
            timelineViewModel.currentProduct = timelineViewModel.products.value!!.get(position)
            Log.d("xxx", timelineViewModel.currentProduct.toString())
            Log.d("xxx", "order button task delegation success: $position")
            withEditText(requireView())
        }catch(e: java.lang.NullPointerException){
            Log.d("xxx", "on Order Button click")
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
            }catch (e: Exception) {
                Log.d("xxx", "addOrder exception: ${e.toString()}")

            }
        }
    }
    fun removeUselessCharactersProduct(list: MutableLiveData<List<Product>>){
        try {
            list.value!!.forEach {
                it.title = it.title.replace("\"", "").replace("\\", "")
                it.amount_type = it.amount_type.replace("\"", "").replace("\\", "")
                it.description = it.description.replace("\"", "").replace("\\", "")
                it.units = it.units.replace("\"", "").replace("\\", "")
                it.price_type = it.price_type.replace("\"", "").replace("\\", "")
                it.price_per_unit = it.price_per_unit.replace("\"", "").replace("\\", "")
            }
        }
        catch(e: java.lang.NullPointerException){
            Log.d("xxx", "TimelineFRagment removeUselessCharacters: ${e.toString()}")
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