package com.cowin.vaccinateme.ui.centers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cowin.vaccinateme.R

class CentersFragment : Fragment() {

    private lateinit var centersViewModel: CentersViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        centersViewModel =
                ViewModelProvider(this).get(CentersViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_centers, container, false)
        val textView: TextView = root.findViewById(R.id.text_dashboard)
        centersViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}