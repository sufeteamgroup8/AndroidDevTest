package com.systemvx.androiddevtest

import okhttp3.*
import java.util.*
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.FutureTask

class HttpUtil {
    companion object {
        const val BASE_URL = ""
    }


    private val cookieStore: MutableMap<String, List<Cookie>> = HashMap()
    private val threadPool = Executors.newFixedThreadPool(30)
    private val okHttpClient: OkHttpClient = OkHttpClient.Builder().cookieJar(object : CookieJar {
        override fun saveFromResponse(httpUrl: HttpUrl,
                                      list: List<Cookie>) {
            cookieStore[httpUrl.host] = list
        }

        override fun loadForRequest(httpUrl: HttpUrl): List<Cookie> {
            val cookies = cookieStore[httpUrl.host]
            return cookies ?: ArrayList()
        }
    }).build()

    fun getRequest(url: String): String? {
        val task = FutureTask(Callable<String> {
            val request: Request = Request.Builder().url(url).build()
            val call = okHttpClient.newCall(request)
            val response = call.execute()
            if (response.isSuccessful && response.body != null) {
                return@Callable response.body!!.string().trim { it <= ' ' }
            } else {
                return@Callable null
            }
        })
        threadPool.submit(task)
        return task.get()
    }

    fun postRequest(url: String,
                    rawParams: Map<String?, String?>): String {
        val task = FutureTask(Callable<String> {
            val builder = FormBody.Builder()
            rawParams.forEach { (name: String?, value: String?) -> builder.add(name!!, value!!) }
            val body: FormBody = builder.build()
            val request: Request = Request.Builder()
                    .url(url)
                    .post(body).build()
            val call = okHttpClient.newCall(request)
            val response = call.execute()
            if (response.isSuccessful && response.body != null) {
                return@Callable response.body!!.string().trim { it <= ' ' }
            } else {
                return@Callable null
            }
        })
        threadPool.submit(task)
        return task.get()
    }
}