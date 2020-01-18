package com.octacore.instagramapp.handlers

interface ApiCallHandler {
    fun success(data: Any)
    fun failed(title: String, reason: String)
}