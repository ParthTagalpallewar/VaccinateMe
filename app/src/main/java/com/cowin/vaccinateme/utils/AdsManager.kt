package com.cowin.vaccinateme.utils

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.android.gms.ads.*
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener
import com.google.android.gms.ads.interstitial.InterstitialAd
/*
val TAG:String = "AdsManager"

class MyInterstitialAd: InterstitialAd() {
    override fun getAdUnitId(): String {
        return "ca-app-pub-3940256099942544/1033173712"
    }

    override fun show(p0: Activity) {
        Log.e(TAG, "Show")
    }

    override fun setFullScreenContentCallback(p0: FullScreenContentCallback?) {
        Log.e(TAG, "setFullscreen CB")
    }

    override fun getFullScreenContentCallback(): FullScreenContentCallback? {
        Log.e(TAG, "setFullscreen CB")
        return this
    }

    override fun setImmersiveMode(p0: Boolean) {
        Log.e(TAG, "set immersive mode")
    }

    override fun getResponseInfo(): ResponseInfo {
        Log.e(TAG, "get Response")
    }

    override fun setOnPaidEventListener(p0: OnPaidEventListener?) {
        Log.e(TAG, "set paid event listener")
    }

    override fun getOnPaidEventListener(): OnPaidEventListener? {
        Log.e(TAG, "get paid event listener")
    }

}
*/
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

    open fun createInterstitialAds(): InterstitialAd? {
        val adRequest = AdRequest.Builder().build()
        interstitialAd = MyInterstitialAd()
        return interstitialAd
    }


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