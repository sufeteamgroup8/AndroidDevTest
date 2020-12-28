package com.systemvx.androiddevtest.utils


/**
 * 用来表示restful链接失败的Exception
 */
class CustomRestfulError(message: String? = null) : Exception(message ?: "Restful connection error")