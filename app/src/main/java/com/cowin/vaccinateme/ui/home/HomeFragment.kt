package com.cowin.vaccinateme.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.cowin.vaccinateme.R
import com.cowin.vaccinateme.utils.AdsManager
import com.cowin.vaccinateme.utils.templateAds.NativeTemplateStyle
import com.cowin.vaccinateme.utils.templateAds.TemplateView
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.nativead.NativeAd
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var mInterstitialAd: InterstitialAd? = null
    private final var TAG = "MainActivity"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val root: View = inflater.inflate(R.layout.fragment_home, container, false)

        //showBanner(root)



        return root
    }

    private fun showBanner(root: View) {
        val adsManager = AdsManager(requireContext())
        // val mAdView = root.findViewById<AdView>(R.id.adView)

        // adsManager.createAds(mAdView)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        website.setOnClickListener {

            val cowinWebsite = "https://www.cowin.gov.in/home"

            Intent(Intent.ACTION_VIEW, Uri.parse(cowinWebsite)).apply {
                startActivity(this)
            }


        }
        showNativeTempAds(view)

    }

    fun showNativeTempAds(root: View) {

        Log.e(TAG, "showNativeTempAds: show Native temp", )
        
        MobileAds.initialize(requireContext()) {

        }

        val adLoader = AdLoader.Builder(requireContext(), "ca-app-pub-3940256099942544/2247696110")
            .forNativeAd { ad: NativeAd ->
                val styles = NativeTemplateStyle.Builder().build()

                val template = root.findViewById<TemplateView>(R.id.my_template)
                template.setStyles(styles)
                template.visibility = View.VISIBLE
                template.setNativeAd(ad)
                Log.e(TAG, "---------------------------onAdFailedToLoad: Showing")
            }.withAdListener(object : AdListener() {
                override fun onAdFailedToLoad(p0: LoadAdError) {
                    super.onAdFailedToLoad(p0)
                    Toast.makeText(requireContext(), p0.message, Toast.LENGTH_SHORT).show()
                    Log.e(TAG, "<<<<<<<<<<<<<<<<<<onAdFailedToLoad: ${p0}")

                }

                override fun onAdLoaded() {
                    super.onAdLoaded()
                   /// Toast.makeText(requireContext(),"adLodedSuccessfully", Toast.LENGTH_SHORT).show()
                }
            }).build()

        adLoader.loadAd(AdRequest.Builder().build())


    }
}

/*val adLoader = AdLoader.Builder(this, "ca-app-pub-3940256099942544/2247696110")
    .forNativeAd { ad : NativeAd ->
        // Show the ad.
    }
    .withAdListener(object : AdListener() {
        override fun onAdFailedToLoad(adError: LoadAdError) {
            // Handle the failure by logging, altering the UI, and so on.
        }
    })
    .withNativeAdOptions(NativeAdOptions.Builder()
            // Methods in the NativeAdOptions.Builder class can be
            // used here to specify individual options settings.
            .build())
    .build()*/