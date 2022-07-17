package com.example.flo

import com.google.gson.annotations.SerializedName


data class AuthResponse(
    @SerializedName(value = "result") val isSuccess:Boolean,
    @SerializedName(value = "code") val code:Int,
    @SerializedName(value = "message") val message: String,
    @SerializedName(value = "result") val result : Result?
    //로그인 api와 회원가입 api가 같은 데이터 클래스 내에 있으므로 null처리 필요
)

data class Result(
    @SerializedName(value = "userIdx") var userIdx : Int,
    @SerializedName(value = "jwt") var jwt : String
)