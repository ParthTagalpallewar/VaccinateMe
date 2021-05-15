package com.cowin.vaccinateme.ui.home

import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cowin.vaccinateme.R
import com.cowin.vaccinateme.utils.AdsManager
import com.cowin.vaccinateme.utils.buildProperties
import com.cowin.vaccinateme.utils.showNativeAds
import com.cowin.vaccinateme.utils.templateAds.NativeTemplateStyle
import com.cowin.vaccinateme.utils.templateAds.TemplateView
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.nativead.NativeAd
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val root: View = inflater.inflate(R.layout.fragment_home, container, false)


        requireContext().showNativeAds(getString(R.string.ad_unit_id_native)){
            my_template.buildProperties(it)
        }

        return root
    }

}