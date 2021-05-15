package com.cowin.vaccinateme.ui.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.cowin.vaccinateme.R
import com.cowin.vaccinateme.data.repositionries.UserDataRepositories
import com.cowin.vaccinateme.utils.templateAds.NativeTemplateStyle
import com.cowin.vaccinateme.utils.templateAds.TemplateView
import com.google.android.gms.ads.*
import com.google.android.gms.ads.nativead.NativeAd
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment() {

    private val profileViewModel: ProfileViewModel by activityViewModels()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        var root = inflater.inflate(R.layout.fragment_profile, container, false)

        showNativeTempAds(root)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        visionaryAd()

        getUserData()

        profile_btn_addPin.setOnClickListener {
            /*User Not Added PinCode Until Now*/
            findNavController().navigate(R.id.action_navigation_notifications_to_addLocationFragment)
        }

        about.setOnClickListener {
            findNavController().navigate(R.id.infoFragment)
        }
    }

    fun getUserData() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            val userRepository = UserDataRepositories(requireContext())
            userRepository.getUserData().also {
                /*User Not Added PinCode Until Now*/
                if (it == null){
                    profile_btn_addPin.text = " Add PinCode"
                    profile_tv_showPin.text = " Not Added "
                }else{
                    profile_btn_addPin.text = "Update pincode"
                    profile_tv_showPin.text = it.pincode
                }
            }
        }
    }
    fun visionaryAd(){
        visionary.setOnClickListener {
            Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://play.google.com/store/apps/details?id=com.reselling.visionary")
                requireContext().startActivity(this)
            }

        }
    }

    fun showNativeTempAds(root: View) {


        MobileAds.initialize(requireContext()) {

        }

        val adLoader = AdLoader.Builder(requireContext(), getString(R.string.ad_unit_id_native))
            .forNativeAd { ad: NativeAd ->
                val styles = NativeTemplateStyle.Builder().build()

                val template = root.findViewById<TemplateView>(R.id.my_template)
                template.setStyles(styles)
                template.visibility = View.VISIBLE
                template.setNativeAd(ad)
            }.withAdListener(object : AdListener() {
                override fun onAdFailedToLoad(p0: LoadAdError) {
                    super.onAdFailedToLoad(p0)
                    Log.e("Ads", p0.message)
                }

                override fun onAdLoaded() {
                    super.onAdLoaded()
                }
            }).build()

        adLoader.loadAd(AdRequest.Builder().build())


    }
}