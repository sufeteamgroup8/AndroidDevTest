package com.systemvx.androiddevtest

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.systemvx.androiddevtest.data.LoginRepository
import com.systemvx.androiddevtest.databinding.FragmentOrderPublishBinding
import org.json.JSONArray
import java.util.*

class OrderPublishFragment : Fragment() {
    //定义文本框
    private lateinit var mBinding: FragmentOrderPublishBinding
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_order_publish, container, false)
        //获取界面文本框
        val url = HttpUtil.BASE_URL + "kinds"
        var jsonArray: JSONArray? = null
        try {
            jsonArray = JSONArray(HttpUtil().getRequest(url))
        } catch (el: Exception) {
            el.printStackTrace()
        }
        mBinding.orderCancel.setOnClickListener(HomeListener(activity))
        mBinding.orderPublish.setOnClickListener { view: View? ->
            if (validate()) {
                val Tittle = mBinding.orderTittle.text.toString()
                val Context = mBinding.orderContext.text.toString()
                val Time = mBinding.orderDDL.text.toString()
                val Price = mBinding.orderPrice.text.toString()

                try {
                    val result = addOrder(Tittle, Context, Time, Price, LoginRepository.user!!.id)
                    DialogUtil.showDialog(activity, result, true)
                } catch (e: Exception) {
                    DialogUtil.showDialog(activity, "Error", false)
                    e.printStackTrace()
                }
            }
        }
        return mBinding.root
    }

    private fun validate(): Boolean {
        if (!LoginRepository.isLoggedIn) {
            return false
        }

        val Tittle = mBinding.orderTittle.text.toString().trim { it <= ' ' }
        if (Tittle == "") {
            DialogUtil.showDialog(activity, "请填写标题", false)
            return false
        }
        val Context = mBinding.orderContext.text.toString().trim { it <= ' ' }
        if (Context == "") {
            DialogUtil.showDialog(activity, "请填具体描述", false)
            return false
        }
        val Price = mBinding.orderPrice.text.toString().trim { it <= ' ' }
        if (Price == "") {
            DialogUtil.showDialog(activity, "请填写价格", false)
            return false
        }
        return true
    }

    @Throws(Exception::class)
    private fun addOrder(Tittle: String, Context: String, Time: String, Price: String, publisher: Int): String {
        //Map封装
        val map = HashMap<String, String>()
        map["orderTittle"] = Tittle
        map["orderContext"] = Context
        map["orderTime"] = Time
        map["orderPrice"] = Price
        val url = HttpUtil.BASE_URL + "order"
        return HttpUtil().postRequest(url, map)
    }

    internal object DialogUtil {
        fun showDialog(c: Context?, s: String?, b: Boolean?) {
            //TODO (do nothing yet)
        }
    }
}