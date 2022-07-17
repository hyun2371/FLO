package com.example.flo

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {

    @Insert
    fun insert(user:User)

    @Query("SELECT * FROM UserTable")
    fun getUsers() : List<User>

    //한 명의 유저 정보만 가져옴
    @Query("SELECT * FROM UserTable WHERE email = :email AND password = :password")
    fun getUser(email:String, password:String) : User? //인자값 넣어야 오류 안 생김 (? null처리)
}