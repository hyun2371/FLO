package com.example.flo

import androidx.room.*

@Dao
interface AlbumDao {
    @Insert
    fun insert(album:Album)

    @Update
    fun update(album: Album)

    @Delete
    fun delete(album: Album)

    @Query("SELECT * FROM AlbumTable")
    fun getAlbums():List<Album>

    @Query("SELECT * FROM AlbumTable WHERE id = :id")
    fun getAlbum(id: Int): Album

    //특정 유저 앨범 좋아요
    @Insert
    fun likeAlbum(like: Like)

    @Query("DELETE FROM LikeTable WHERE userId = :userId AND albumID = :albumId")
    fun disLikedAlbum(userId:Int, albumId:Int): Int?

    @Query("SELECT id FROM LikeTable WHERE userId = :userId AND albumId = :albumId")
    fun isLikedAlbum(userId: Int, albumId: Int): Int?

    //별칭 LT  왼쪽 테이블을 기준으로 병합
    @Query("SELECT AT.* FROM LikeTable as LT LEFT JOIN AlbumTable as AT ON LT.albumId = AT.id WHERE LT.userId = :userId")
    fun getLikedAlbums(userId: Int): List<Album>
}