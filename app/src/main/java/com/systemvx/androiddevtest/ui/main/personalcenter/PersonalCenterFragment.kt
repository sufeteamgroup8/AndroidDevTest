package com.systemvx.androiddevtest.ui.main.personalcenter

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.systemvx.androiddevtest.R
import com.systemvx.androiddevtest.databinding.FragmentPersonalCenterBinding
import com.systemvx.androiddevtest.ui.credit.ConfidentialActivity
import com.systemvx.androiddevtest.ui.payment.CoinManageActivity
import com.systemvx.androiddevtest.ui.payment.PayAndWithdrawActivity
import com.systemvx.androiddevtest.ui.setting.SettingActivity

class PersonalCenterFragment : Fragment() {

    private lateinit var viewModel: PersonalCenterViewModel

    private lateinit var mBinding: FragmentPersonalCenterBinding

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_personal_center, container, false)
        viewModel = PersonalCenterViewModel(requireContext())
        mBinding.viewModel = viewModel
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.coinCard.setOnClickListener {
            startActivity(Intent(context, CoinManageActivity::class.java))
        }
        mBinding.txtBtnMyCredit.setOnClickListener {
            startActivity(Intent(context, ConfidentialActivity::class.java))
        }
        mBinding.txtBtnCharge.setOnClickListener {
            startActivity(Intent(context, PayAndWithdrawActivity::class.java))
        }
        mBinding.txtBtnDischarge.setOnClickListener {
            startActivity(Intent(context, PayAndWithdrawActivity::class.java))
        }
        mBinding.txtBtnSettings.setOnClickListener {
            startActivity(Intent(context, SettingActivity::class.java))
        }
    }
}