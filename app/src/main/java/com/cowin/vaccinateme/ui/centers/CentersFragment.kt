package com.cowin.vaccinateme.ui.centers

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
import com.cowin.vaccinateme.R
import com.cowin.vaccinateme.data.models.roomModels.RoomCenters
import com.cowin.vaccinateme.data.repositionries.CentersRepositiory
import com.cowin.vaccinateme.data.repositionries.UserDataRepositories
import com.cowin.vaccinateme.utils.AdsManager
import com.cowin.vaccinateme.utils.NUM_ROWS_FOR_AD
import com.cowin.vaccinateme.utils.templateAds.NativeTemplateStyle
import com.cowin.vaccinateme.utils.templateAds.TemplateView
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.fragment_centers.*

class CentersFragment : Fragment(), CentersAdapter.CenterClickListener {

    lateinit var recyclerView: RecyclerView
    lateinit var addPincodeRelativeLayout: RelativeLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_centers, container, false)

        val adsManager = AdsManager(requireContext())
        val mAdView = root.findViewById<AdView>(R.id.adViewCenters)
        adsManager.createAds(mAdView)

        addPincodeRelativeLayout = root.findViewById(R.id.centersAddPincodeLayout)

        MobileAds.initialize(requireContext())

        recyclerView = root.findViewById<RecyclerView>(R.id.center_recycler_view)
        recyclerView.layoutManager = (LinearLayoutManager(requireContext()))
        recyclerView.hasFixedSize()

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repo = CentersRepositiory(requireContext())
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            val centersList = repo.getAllCenters()

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

            val adapter = CentersAdapter(anyArrayList, this@CentersFragment)
            recyclerView.adapter = adapter

            decideVisiblity(centersList)

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
            } else if (centerList.isEmpty()) {

                emptySession.visibility = View.VISIBLE

            } else {
                addPincodeRelativeLayout.visibility = View.GONE
                emptySession.visibility = View.GONE
            }
        }
    }

}