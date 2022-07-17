package com.example.flo

import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//서버가 제공한 URL (맨끝에 /없어야 함)
const val BASE_URL = "https://edu-api-test.softsquared.com"

//retrofit객체 생성
fun getRetrofit(): Retrofit {
    val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create()).build() //gson넣어줌

    return retrofit
}