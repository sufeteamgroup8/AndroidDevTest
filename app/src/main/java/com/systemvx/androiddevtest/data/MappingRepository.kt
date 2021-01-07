package com.systemvx.androiddevtest.data

import android.content.Context
import com.alibaba.fastjson.JSON
import com.systemvx.androiddevtest.data.model.helperdata.AddressMap
import com.systemvx.androiddevtest.data.model.helperdata.OrderStateMap
import com.systemvx.androiddevtest.data.model.helperdata.TaskStateMap
import com.systemvx.androiddevtest.data.model.helperdata.TaskTypeMap
import com.systemvx.androiddevtest.utils.CustomRestfulError
import com.systemvx.androiddevtest.utils.HttpUtil
import com.systemvx.androiddevtest.utils.UtilStaticFunc

class MappingRepository {
    companion object {
        var addressMapping: ArrayList<AddressMap>? = null

        var orderTypeMapping: HashMap<Int, String>? = null

        var orderStateMapping: HashMap<Int, String>? = null

        var taskStateMapping: HashMap<Int, HashMap<Int, String>>? = null
    }

    fun initAll(context: Context) {
        try {
            initAddressMap()
            initOrderStateMap()
            initTaskStateMap()
            initTaskTypeMap()
        } catch (e: CustomRestfulError) {
            UtilStaticFunc.netWorkErrorToast(context)
        }
    }

    private fun initAddressMap() {
        if (addressMapping == null) {
            try {
                val result = JSON.parseObject(HttpUtil().postRequest("/mapping/address", HashMap()))
                addressMapping = ArrayList()
                if (result.getBoolean("success")) {
                    val list = result.getJSONArray("payload")
                    for (i in list) {
                        val temp = JSON.parseObject(i as String, AddressMap::class.java)
                        addressMapping!![temp.id] = temp
                    }
                } else {
                    throw CustomRestfulError()
                }
            } catch (e: Exception) {
                throw CustomRestfulError()
            }
        }
    }

    private fun initTaskStateMap() {
        if (taskStateMapping == null) {
            try {
                val result = JSON.parseObject(HttpUtil().postRequest("/mapping/taskstate", HashMap()))
                taskStateMapping = HashMap()
                if (result.getBoolean("success")) {
                    val list = result.getJSONArray("payload")
                    for (i in list) {
                        val temp = JSON.parseObject(i.toString(), TaskStateMap::class.java)
                        if (taskStateMapping!![temp.type.id] == null) {
                            taskStateMapping!![temp.type.id] = HashMap()
                        }
                        taskStateMapping!![temp.type.id]!!.put(temp.serial, temp.text)
                    }
                } else {
                    throw CustomRestfulError()
                }
            } catch (e: Exception) {
                throw CustomRestfulError()
            }
        }
    }

    private fun initOrderStateMap() {
        if (orderStateMapping == null) {
            try {
                val result = JSON.parseObject(HttpUtil().postRequest("/mapping/orderstate", HashMap()))
                orderStateMapping = HashMap<Int, String>()
                if (result.getBoolean("success")) {
                    val list = result.getJSONArray("payload")
                    for (i in list) {
                        val temp = JSON.parseObject(i.toString(), OrderStateMap::class.java)
                        orderStateMapping!![temp.id] = temp.text
                    }
                } else {
                    throw CustomRestfulError()
                }
            } catch (e: Exception) {
                throw CustomRestfulError()
            }
        }
    }

    private fun initTaskTypeMap() {
        if (orderTypeMapping == null) {
            try {
                val result = JSON.parseObject(HttpUtil().postRequest("/mapping/tasktype", HashMap()))
                orderTypeMapping = HashMap<Int, String>()
                if (result.getBoolean("success")) {
                    val list = result.getJSONArray("payload")
                    for (i in list) {
                        val temp = JSON.parseObject(i.toString(), TaskTypeMap::class.java)
                        orderTypeMapping!![temp.id] = temp.text
                    }
                } else {
                    throw CustomRestfulError()
                }
            } catch (e: Exception) {
                throw CustomRestfulError()
            }
        }
    }

    fun getAddressStr(addressID: Int): String {
        if (addressMapping == null) {
            initAddressMap()
        }
        return addressMapping!![addressID].text
    }

    fun getAddressChain(addressID: Int): String {
        if (addressMapping == null) {
            initAddressMap()
        }

        var temp: AddressMap? = addressMapping?.get(addressID)
        var output = temp?.text
        do {
            temp = temp?.rootAddress
            output = "${temp?.text}|" + output
        } while (temp != null)
        return output ?: ""
    }

    fun fetchAddressMap(): ArrayList<AddressMap> {
        if (addressMapping == null) initAddressMap();return addressMapping!!
    }

    fun fetchTypeMap(): HashMap<Int, String> {
        if (orderTypeMapping == null) initTaskTypeMap();return orderTypeMapping!!
    }

    fun getAddressID(addressDescription: String): Int? {
        if (addressMapping == null) {
            initAddressMap()
        }
        for (i in addressMapping!!) {
            if (i.text == addressDescription)
                return i.id
        }
        return null
    }

    fun getOrderStateStr(stateID: Int): String? {
        if (orderStateMapping == null) {
            initOrderStateMap()
        }
        return orderStateMapping!![stateID]
    }

    fun getOrderStateID(stateStr: String): Int? {
        if (orderStateMapping == null) {
            initOrderStateMap()
        }
        for (i in orderStateMapping!!) {
            if (i.value == stateStr) {
                return i.key
            }
        }
        return null
    }

    fun getOrderTypeStr(stateID: Int): String? {
        if (orderTypeMapping == null) {
            initTaskTypeMap()
        }
        return orderTypeMapping!![stateID]
    }

    fun getOrderTypeID(stateStr: String): Int? {
        if (orderTypeMapping == null) {
            initTaskTypeMap()
        }
        for (i in orderTypeMapping!!) {
            if (i.value == stateStr) {
                return i.key
            }
        }
        return null
    }


    fun getTaskStateStr(typeID: Int, stateID: Int): String? {
        if (orderTypeMapping == null) {
            initTaskTypeMap()
        }
        return taskStateMapping?.get(typeID)?.get(stateID)
    }

    fun getTaskStateBatch(typeID: Int): HashMap<Int, String>? {
        if (orderTypeMapping == null) {
            initTaskTypeMap()
        }
        return taskStateMapping?.get(typeID)
    }

}