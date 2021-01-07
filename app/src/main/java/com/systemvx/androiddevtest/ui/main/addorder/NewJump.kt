package com.systemvx.androiddevtest.ui.main.addorder

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.systemvx.androiddevtest.R
import com.systemvx.androiddevtest.data.LoginRepository
import com.systemvx.androiddevtest.ui.edit.OrderPublishActivity
import com.systemvx.androiddevtest.ui.login.LoginActivity

class NewJump : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment tv_start
        val inflate: View = inflater.inflate(R.layout.fragment_new_jump, container, false)
        initView(inflate)
        return inflate
    }

    private fun initView(view: View?) {
        val tvStart = view?.findViewById<TextView>(R.id.tv_start)
        tvStart?.setOnClickListener {
            if (LoginRepository.isLoggedIn)
                startActivity(Intent(requireContext(), OrderPublishActivity::class.java))
            else
                startActivity(Intent(requireContext(), LoginActivity::class.java))
        }
    }
}