package com.example.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class DetailFragment : Fragment() {

    lateinit var textView: TextView

    override fun onCreateView(

        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {

        val view = inflater.inflate(R.layout.fragment_detail, container, false)

        textView = view.findViewById(R.id.textDetails)

        val item = arguments?.getString("item")

        textView.text = "Selected Item:\n\n$item"

        return view

    }

}