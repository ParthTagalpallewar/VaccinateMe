package com.cowin.vaccinateme.ui.sessions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cowin.vaccinateme.R
import com.cowin.vaccinateme.data.models.roomModels.RoomSessions
import com.cowin.vaccinateme.data.repositionries.CentersRepositiory
import com.cowin.vaccinateme.data.repositionries.UserDataRepositories
import com.cowin.vaccinateme.ui.centers.SessionAdapter
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



        val repo = CentersRepositiory(requireContext())
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            val centersList = repo.getSessionsByCenterId(centerId)
            val adapter = SessionAdapter(centersList)
            recyclerView.adapter = adapter




        }

    }


}