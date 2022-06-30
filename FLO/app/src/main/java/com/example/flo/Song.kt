package com.example.flo

//데이터 클래스 생성
//제목, 가수, 사진,재생시간,현재 재생시간, isplaying(재생 되고 있는지)

data class Song(
        val title : String = "",
        val singer : String = ""
//    val startTime : String = "",
//    val endTime : String = "",
//    val isplaying : Boolean = false
)
