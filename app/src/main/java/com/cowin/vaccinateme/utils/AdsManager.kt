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
    private val TAG = "AdsManager"

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
                Log.e(TAG, "ad load failed" + loadAdError.code)
            }

            override fun onAdLoaded() {
                super.onAdLoaded()
                Log.e(TAG, "ads is loaded")
            }
        })
        adView.loadAd(adRequest)
    }


    /*

    //For Industrial Ads

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