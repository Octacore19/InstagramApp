package com.octacore.instagramapp.network

interface ApiCallHandler {
    fun done()
    fun success(data: Any){ done() }
    fun failed(title: String, reason: String){ done() }
    fun logout(){ done() }
}