package com.cowin.vaccinateme.ui.sessions

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cowin.vaccinateme.R
import com.cowin.vaccinateme.data.repositionries.CentersRepositiory
import com.cowin.vaccinateme.ui.centers.SessionAdapter
import com.cowin.vaccinateme.utils.NUM_ROWS_FOR_AD
import com.cowin.vaccinateme.utils.templateAds.TemplateView
import kotlinx.android.synthetic.main.fragment_sessions.*

class Sessions : Fragment() {

    lateinit var recyclerView: RecyclerView
    lateinit var centerId:String


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_sessions, container, false)


        centerId = arguments?.getString("sessionId")!!

        recyclerView = root.findViewById<RecyclerView>(R.id.session_reycerview)
        recyclerView.layoutManager = (LinearLayoutManager(requireContext()))
        recyclerView.hasFixedSize()

        website2.setOnClickListener {

            val cowinWebsite = "https://www.cowin.gov.in/home"

            Intent(Intent.ACTION_VIEW, Uri.parse(cowinWebsite)).apply {
                startActivity(this)
            }
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        val repo = CentersRepositiory(requireContext())
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            val centersList = repo.getSessionsByCenterId(centerId)

            val anyArrayList = ArrayList<Any>()
            anyArrayList.clear()

            for (i in centersList.indices) {
                anyArrayList.add(i, centersList[i])
            }

            for (i in centersList.indices) {
                if (i % NUM_ROWS_FOR_AD == 0) {
                    TemplateView(requireContext()).apply {
                        anyArrayList.add(i,this)
                    }
                }
            }


            val adapter = SessionAdapter(anyArrayList)
            recyclerView.adapter = adapter




        }

    }


}