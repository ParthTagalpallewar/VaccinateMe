package com.cowin.govaccine.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.cowin.govaccine.R
import com.cowin.govaccine.utils.buildProperties
import com.cowin.govaccine.utils.showNativeAds
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.my_template

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val root: View = inflater.inflate(R.layout.fragment_home, container, false)


        requireContext().showNativeAds(getString(R.string.ad_unit_id_native)){
            try {
                my_template.buildProperties(it)
            } catch (e: NullPointerException) {
            }
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        msgInfo.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_info)
        }
    }

}