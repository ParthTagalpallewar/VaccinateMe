package com.cowin.vaccinateme.ui.centers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cowin.vaccinateme.R
import com.cowin.vaccinateme.data.repositionries.CentersRepositiory

class CentersFragment : Fragment() , CentersAdapter.CenterClickListener{

  lateinit var  recyclerView :RecyclerView

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_centers, container, false)

        recyclerView = root.findViewById<RecyclerView>(R.id.center_recycler_view)
        recyclerView.layoutManager = (LinearLayoutManager(requireContext()))
        recyclerView.hasFixedSize()

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repo = CentersRepositiory(requireContext())
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            val centersList =repo.getAllCenters()
            val adapter = CentersAdapter(centersList,this@CentersFragment)
            recyclerView.adapter = adapter

        }

    }

    override fun onCenterCLickedListener(centerId: String) {
        CentersFragmentDirections.actionNavigationDashboardToSessions2(centerId).apply {
            findNavController().navigate(this)
        }

    }
}