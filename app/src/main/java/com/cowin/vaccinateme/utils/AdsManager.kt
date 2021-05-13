package com.cowin.vaccinateme.utils

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.android.gms.ads.*
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener
import com.google.android.gms.ads.interstitial.InterstitialAd

open class AdsManager {

    private lateinit var context:Context
    open lateinit var  interstitialAd: InterstitialAd

    constructor(context: Context) {
        this.context = context
        MobileAds.initialize(context, OnInitializationCompleteListener {

        })
    }

    //for banner ads
    fun createAds(adView: AdView) {
        val adRequest = AdRequest.Builder().build()
        adView.setAdListener(object : AdListener() {
            override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                super.onAdFailedToLoad(loadAdError)
                Toast.makeText(context, "ad load failed" + loadAdError.code, Toast.LENGTH_SHORT).show()
            }

            override fun onAdLoaded() {
                super.onAdLoaded()
                Toast.makeText(context, "ads is loaded", Toast.LENGTH_SHORT).show()
            }
        })
        adView.loadAd(adRequest)
    }


    /*

    //For Industrial Ads
    open fun createInterstitialAds(): InterstitialAd? {
        val adRequest = AdRequest.Builder().build()
        interstitialAd = InterstitialAd()
        interstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712")
        interstitialAd.loadAd(adRequest)
        return interstitialAd
    }

    open fun createUnifiedAds(unitid: Int, listening: AdUnifiedListening?) {
        val builder = AdLoader.Builder(context, context.getString(unitid))
        builder.forUnifiedNativeAd(listening)
        builder.withAdListener(listening)
        val adload = builder.build()
        adload.loadAd(AdRequest.Builder().build())
    }

    open fun createUnifiedAds(numads: Int, unitid: Int, listening: AdUnifiedListening) {
        val builder = AdLoader.Builder(context, context.getString(unitid))
        builder.forUnifiedNativeAd(listening)
        builder.withAdListener(listening)
        val adload = builder.build()
        adload.loadAds(AdRequest.Builder().build(), numads)
        listening.adLoader = adload
    }
    */
}