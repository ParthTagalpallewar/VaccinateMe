package com.cowin.govaccine.ui.sessions

import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cowin.govaccine.R
import com.cowin.govaccine.data.repositionries.CentersRepositiory
import com.cowin.govaccine.ui.centers.SessionAdapter
import com.cowin.govaccine.utils.NUM_ROWS_FOR_AD
import com.cowin.govaccine.utils.templateAds.TemplateView
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

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        website2.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        website2.setOnClickListener {

            val cowinWebsite = "https://www.cowin.gov.in/home"

            Intent(Intent.ACTION_VIEW, Uri.parse(cowinWebsite)).apply {
                startActivity(this)
            }
        }

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