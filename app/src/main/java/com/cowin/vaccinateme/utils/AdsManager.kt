package com.cowin.vaccinateme.utils

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.cowin.vaccinateme.R
import com.cowin.vaccinateme.utils.templateAds.NativeTemplateStyle
import com.cowin.vaccinateme.utils.templateAds.TemplateView
import com.google.android.gms.ads.*
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.nativead.NativeAd
import kotlinx.android.synthetic.main.fragment_home.*

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


}

fun Context.showIntestrialAds(adKey:String) :MutableLiveData<ResultIntestrialAdProvider>{
    var adRequest = AdRequest.Builder().build()

    val liveData = MutableLiveData<ResultIntestrialAdProvider>()

    InterstitialAd.load(this,adKey, adRequest, object : InterstitialAdLoadCallback() {
        override fun onAdFailedToLoad(adError: LoadAdError) {
            liveData.postValue(ResultIntestrialAdProvider.Error(adError))
        }

        override fun onAdLoaded(interstitialAd: InterstitialAd) {
            liveData.postValue(ResultIntestrialAdProvider.Success(interstitialAd))
        }
    })

    return  liveData
}

sealed class ResultIntestrialAdProvider(){
    data class Success(var interstitialAd: InterstitialAd):ResultIntestrialAdProvider()
    data class Error(var error : LoadAdError) : ResultIntestrialAdProvider()
}

fun Context.showNativeAds(key:String,method : ( nativeAds:NativeAd) -> Unit){
    MobileAds.initialize(this)
    val adLoader = AdLoader.Builder(this, key)
        .forNativeAd { ad: NativeAd ->


            method.invoke(ad)


        }.build()

    adLoader.loadAd(AdRequest.Builder().build())
}

fun TemplateView.buildProperties(ad:NativeAd){
    val styles = NativeTemplateStyle.Builder().build()
    this.apply {
        setStyles(styles)
        visibility = View.VISIBLE
        setNativeAd(ad)
    }
}