package com.systemvx.androiddevtest.ui.main.addorder

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.systemvx.androiddevtest.OrderPublishActivity
import com.systemvx.androiddevtest.R

class NewJump : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment tv_start
        val inflate = inflater.inflate(R.layout.fragment_new_jump, null, false)
        initView(inflate!!)
        return inflate
    }
    fun initView(view:View?){
        val tvStart = view?.findViewById<TextView>(R.id.tv_start)

        tvStart?.setOnClickListener { startActivity(Intent(activity,OrderPublishActivity::class.java)) }
    }
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        this.context?.startActivity(Intent(context, OrderPublishActivity::class.java))
//    }
}