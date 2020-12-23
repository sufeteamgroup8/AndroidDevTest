package com.systemvx.androiddevtest.ui.main.personalcenter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.systemvx.androiddevtest.R
import com.systemvx.androiddevtest.databinding.FragmentPersonalCenterBinding

class PersonalCenterFragment : Fragment() {

    private lateinit var viewModel: PersonalCenterViewModel

    private lateinit var mBinding: FragmentPersonalCenterBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_personal_center, container, false)
        viewModel = PersonalCenterViewModel(requireContext())
        mBinding.viewModel = viewModel
        return mBinding.root
    }

}