package com.example.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment

class ListFragment : Fragment() {

    lateinit var listView: ListView

    val items = arrayOf(

        "Android",
        "Java",
        "Python",
        "Kotlin",
        "Flutter"

    )

    override fun onCreateView(

        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {

        val view = inflater.inflate(R.layout.fragment_list, container, false)

        listView = view.findViewById(R.id.listView)

        val adapter = ArrayAdapter(

            requireContext(),
            android.R.layout.simple_list_item_1,
            items

        )

        listView.adapter = adapter

        listView.setOnItemClickListener { _, _, position, _ ->

            val fragment = DetailFragment()

            val bundle = Bundle()

            bundle.putString("item", items[position])

            fragment.arguments = bundle

            
            parentFragmentManager.beginTransaction()

                .replace(R.id.container, fragment)

                .addToBackStack(null)

                .commit()

        }

        return view

    }

}