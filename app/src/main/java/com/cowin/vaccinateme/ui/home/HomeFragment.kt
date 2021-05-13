package com.cowin.vaccinateme.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cowin.vaccinateme.R
import com.cowin.vaccinateme.utils.AdsManager
import com.google.android.gms.ads.AdView
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_home, container, false)

        val adsManager = AdsManager(requireContext())
        val mAdView = root.findViewById<AdView>(R.id.adView)
        adsManager.createAds(mAdView)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        website.setOnClickListener {

            val cowinWebsite = "https://www.cowin.gov.in/home"

            Intent(Intent.ACTION_VIEW, Uri.parse(cowinWebsite)).apply {
                startActivity(this)
            }
        }
    }
}