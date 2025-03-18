package com.fetch.fetch.utils

interface Logger {
    fun e(tag: String, message: String, throwable: Throwable? = null)
}