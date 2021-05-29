package com.cowin.govaccine.ui.location

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.cowin.govaccine.R
import com.cowin.govaccine.data.models.SettingsModel
import com.cowin.govaccine.data.repositionries.CentersRepositiory
import com.cowin.govaccine.data.repositionries.UserDataRepositories
import com.cowin.govaccine.utils.ResultIntestrialAdProvider
import com.cowin.govaccine.utils.getCurrentDate
import com.cowin.govaccine.utils.showIntestrialAds
import kotlinx.android.synthetic.main.fragment_add_location.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

class AddLocationFragment : DialogFragment() {



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_add_location, container, false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        add_addapp.setOnClickListener {
            val pincode = add_et_package.text.trim().toString()


            if (checkPinCode(pincode)) {
                viewLifecycleOwner.lifecycleScope.launch {
                    val userRepository = UserDataRepositories(requireContext())
                    userRepository.getUserData().also {
                        /*User Not Added PinCode Until Now*/


                        if (it == null) {

                            userRepository.addData(SettingsModel(0, pincode, getCurrentDate()))

                            withContext(Dispatchers.Main) {
                                showToast("Pincode Set Successfully")
                            }

                        } else {
                            userRepository.UpdateData(
                                SettingsModel(
                                    0,
                                    pincode,
                                    getCurrentDate()
                                )
                            )

                            withContext(Dispatchers.Main) {
                                showToast("Pincode Updated Successfully")
                            }
                        }

                        val centersRepositories = CentersRepositiory(requireContext())
                        centersRepositories.deleteAllData()

                        val sendLogsWorkRequest = PeriodicWorkRequestBuilder<FindCentersWorker>(15, TimeUnit.MINUTES).build()
                        WorkManager.getInstance(requireContext()).enqueueUniquePeriodicWork(
                            "sendLogs",
                            ExistingPeriodicWorkPolicy.REPLACE,
                            sendLogsWorkRequest
                        )

                        findNavController().popBackStack()


                    }


                }
            }
        }

        requireContext().showIntestrialAds(getString(R.string.ad_unit_id_interstitial)).observe(viewLifecycleOwner){
            when(it){
                is ResultIntestrialAdProvider.Success -> {
                    it.interstitialAd.show(requireActivity())
                }is ResultIntestrialAdProvider.Error -> {
                    //showToast(it.error.toString())
                }
            }
        }

    }

    private fun checkPinCode(pincode: String): Boolean {
        return when {
            pincode.isNullOrBlank() -> {
                showToast("Please Enter Pincode here")
                false
            }
            pincode.length != 6 -> {
                showToast("Please Enter Valid Code")
                false
            }
            else -> {
                true
            }
        }
    }

    fun showToast(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }
}