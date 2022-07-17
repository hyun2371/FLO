package com.example.flo

import com.google.gson.annotations.SerializedName

data class AuthResponse(val isSuccess:Boolean, val code:Int, val message: String)