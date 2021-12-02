package com.example.bazaar.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bazaar.R
import com.example.bazaar.recyclerview.dataadapters.TimelineDataAdapter
import com.example.bazaar.repository.Repository
import com.example.bazaar.viewmodels.LoginViewModel
import com.example.bazaar.viewmodels.TimelineViewModel
import com.example.bazaar.viewmodels.TimelineViewModelFactory


class TimelineFragment : Fragment() {
    private lateinit var  textView: TextView
    private lateinit var timelineViewModel: TimelineViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = TimelineViewModelFactory(Repository())
        timelineViewModel = ViewModelProvider(this, factory).get(TimelineViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view =  inflater.inflate(R.layout.fragment_timeline, container, false)


        timelineViewModel.products.observe(viewLifecycleOwner){
            val adapter = timelineViewModel.products.value?.let { it1 -> TimelineDataAdapter(it1) }
            val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view)
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(this.context)
            recyclerView.setHasFixedSize(true)
        }

        return view

    }


}