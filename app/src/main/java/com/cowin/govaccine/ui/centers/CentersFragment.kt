package com.cowin.govaccine.ui.centers

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.cowin.govaccine.R
import com.cowin.govaccine.data.models.roomModels.RoomCenters
import com.cowin.govaccine.data.repositionries.CentersRepositiory
import com.cowin.govaccine.data.repositionries.UserDataRepositories
import com.cowin.govaccine.ui.location.FindCentersWorker
import com.cowin.govaccine.utils.NUM_ROWS_FOR_AD
import com.cowin.govaccine.utils.templateAds.TemplateView
import kotlinx.android.synthetic.main.fragment_centers.*
import java.util.concurrent.TimeUnit

class CentersFragment : Fragment(), CentersAdapter.CenterClickListener {

    lateinit var recyclerView: RecyclerView
    lateinit var addPincodeRelativeLayout: RelativeLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_centers, container, false)

        addPincodeRelativeLayout = root.findViewById(R.id.centersAddPincodeLayout)

        recyclerView = root.findViewById<RecyclerView>(R.id.center_recycler_view)
        recyclerView.layoutManager = (LinearLayoutManager(requireContext()))
        recyclerView.hasFixedSize()

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repo = CentersRepositiory(requireContext())
        val settings = UserDataRepositories(requireContext())
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            var centersList = repo.getAllCenters()
            var pincode: String = settings.getUserData()?.pincode.toString()

            if (pincode == "null") {
                pincode = "None"
            }
            msgPincode.setText(pincode)

            val anyArrayList = ArrayList<Any>()
            anyArrayList.clear()

            centersList = centersList.sortedByDescending { it.totalAvailability }

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

            val adapter = CentersAdapter(anyArrayList, this@CentersFragment)
            recyclerView.adapter = adapter

            decideVisiblity(centersList)

            retryNow.setOnClickListener {
                val sendLogsWorkRequest = PeriodicWorkRequestBuilder<FindCentersWorker>(15, TimeUnit.MINUTES).build()
                WorkManager.getInstance(requireContext()).enqueueUniquePeriodicWork(
                    "sendLogs",
                    ExistingPeriodicWorkPolicy.REPLACE,
                    sendLogsWorkRequest
                )
            }
        }

    }

    override fun onCenterCLickedListener(centerId: String) {
        CentersFragmentDirections.actionNavigationDashboardToSessions2(centerId).apply {
            findNavController().navigate(this)
        }

    }

    private fun decideVisiblity(centerList: List<RoomCenters>) {

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            val userRepositiory = UserDataRepositories(requireContext())

            val userData = userRepositiory.getUserData()
            if ((userData == null) or (userData?.pincode == null)) {
                addPincodeRelativeLayout.visibility = View.VISIBLE
                center_add_btn.setOnClickListener {
                    findNavController().navigate(R.id.navigation_notifications)
                }

                Log.e("TAG", "decideVisiblity: 1", )
            } else if (centerList.isEmpty()) {

                emptySession.visibility = View.VISIBLE
                Log.e("TAG", "decideVisiblity: 2", )

            } else {
                addPincodeRelativeLayout.visibility = View.GONE
                emptySession.visibility = View.GONE
                Log.e("TAG", "decideVisiblity: 3", )
            }
        }
    }

}