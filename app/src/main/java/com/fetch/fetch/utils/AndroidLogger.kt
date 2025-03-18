package com.fetch.fetch.utils

import android.util.Log

class AndroidLogger : Logger {
    override fun e(tag: String, message: String, throwable: Throwable?) {
        Log.e(tag, message, throwable)
    }
}