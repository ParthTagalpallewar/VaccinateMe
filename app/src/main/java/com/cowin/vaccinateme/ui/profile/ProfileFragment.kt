package com.cowin.vaccinateme.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.cowin.vaccinateme.R
import com.cowin.vaccinateme.data.repositionries.UserDataRepositories
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.coroutines.flow.collect
import java.lang.Appendable

class ProfileFragment : Fragment() {

    private val profileViewModel: ProfileViewModel by activityViewModels()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_profile, container, false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getUserData()

        profile_btn_addPin.setOnClickListener {
            /*User Not Added PinCode Until Now*/
            findNavController().navigate(R.id.action_navigation_notifications_to_addLocationFragment)
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
                    profile_btn_addPin.text = "Update"
                    profile_tv_showPin.text = it.pincode
                }
            }
        }
    }
}