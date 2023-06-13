package com.example.rtepandroid.models

import android.content.Intent

data class HomeGrid(
    val title: String,
    val desc: String,
    val button: String,
    val activity: Class<*>? = null
)