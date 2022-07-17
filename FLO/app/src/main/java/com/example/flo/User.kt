package com.example.flo

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "UserTable")
data class User(
    //Json으로 리퀘스트값 보낼때 명시해줘야함 (서버에서 사용하는 변수명과 동일해야 하기 때문)
    @SerializedName(value = "email") var email: String,
    @SerializedName(value = "password") var password: String,
    @SerializedName(value = "name") var name: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}