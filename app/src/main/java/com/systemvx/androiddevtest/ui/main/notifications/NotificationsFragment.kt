package com.systemvx.androiddevtest.ui.main.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.systemvx.androiddevtest.R
import com.systemvx.androiddevtest.data.LoginRepository
import com.systemvx.androiddevtest.databinding.FragmentNotificationsBinding

class NotificationsFragment : Fragment() {
    private lateinit var notificationsViewModel: NotificationsViewModel

    private lateinit var mBinding: FragmentNotificationsBinding
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?, savedInstanceState: Bundle?,
    ): View {
        notificationsViewModel = ViewModelProvider(this).get(NotificationsViewModel::class.java)
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_notifications, container, false)

        if (LoginRepository.isLoggedIn) {
            mBinding.hintRequireLogin.visibility = View.GONE
            mBinding.chatBriefing.visibility = View.VISIBLE

            mBinding.chatBriefing.layoutManager = LinearLayoutManager(this.context)
            val mAdapter = ChatBriefingAdapter(this.requireContext())
            mBinding.chatBriefing.adapter = mAdapter

            mAdapter.updateWithNewData(notificationsViewModel.findChatter())
        } else {
            mBinding.hintRequireLogin.visibility = View.VISIBLE
            mBinding.chatBriefing.visibility = View.GONE
        }
        return mBinding.root
    }
}