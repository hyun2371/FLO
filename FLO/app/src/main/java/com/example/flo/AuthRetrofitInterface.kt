package com.example.flo

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthRetrofitInterface {
    @POST("/users")
    fun signUp(@Body user:User): Call<AuthResponse> //응답받을 값을 미리 생성

    @POST("/users/login")
    fun login(@Body user:User): Call<AuthResponse>
}