package com.example.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.flo.*
import com.example.flo.databinding.FragmentAlbumBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson


class AlbumFragment : Fragment() {

    lateinit var binding: FragmentAlbumBinding
    private var gson: Gson = Gson()

    private val information = arrayListOf("수록곡", "상세정보", "영상")

    private var isLiked : Boolean = false

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlbumBinding.inflate(inflater,container,false)

        //Home에서 넘어온 데이터 받아오기
        val albumJson = arguments?.getString("album") //argument에서 데이터를 꺼내서 json형태로 변환
        val album = gson.fromJson(albumJson, Album::class.java) //json->album객체

        //Home에서 넘어온 데이터 반영
        isLiked = isLikedAlbum(album.id)
        setInit(album)
        initViewPager()
        setClickListeners(album)

        binding.albumBackIv.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.main_frm, HomeFragment()) //메인 프래그먼트->홈 프래그먼트
                    .commitAllowingStateLoss()
        }


        return binding.root
    }

    private fun initViewPager() {
        //뷰페이저에 프래그먼트 연결
        val albumAdapter = AlbumVPAdapter(this)
        binding.albumContentVp.adapter = albumAdapter

        //탭 레이아웃을 뷰페이저와 연결 (선택된 탭과 뷰페이저 동기화)
        TabLayoutMediator(binding.albumContentTb, binding.albumContentVp) { tab, position ->
            tab.text = information[position]  //탭에 배열에 맞는 내용 표시
        }.attach()
    }

    private fun setInit(album: Album) {
        binding.albumAlbumIv.setImageResource(album.coverImg!!)
        binding.albumMusicTitleTv.text = album.title.toString()
        binding.albumSingerNameTv.text = album.singer.toString()

        if (isLiked){
            binding.albumLikeIv.setImageResource(R.drawable.ic_my_like_on)
        } else {
            binding.albumLikeIv.setImageResource(R.drawable.ic_my_like_off)
        }
    }

    private fun setClickListeners(album: Album){
        val userId = getJwt()
        binding.albumLikeIv.setOnClickListener {
            if (isLiked){
                binding.albumLikeIv.setImageResource(R.drawable.ic_my_like_off)
                disLikedAlbum(album.id)
            } else {
                binding.albumLikeIv.setImageResource(R.drawable.ic_my_like_on)
                likeAlbum(userId, album.id)
            }
        }
    }

    private fun likeAlbum(userId : Int, albumId : Int){
        val songDB = SongDatabase.getInstance(requireContext())!!
        val like = Like(userId, albumId)

        songDB.albumDao().likeAlbum(like)
    }

    // 하트를 해제하면, like table에서 userId와 albumId 가 같은 data를 삭제
    private fun disLikedAlbum(albumId : Int){
        val songDB = SongDatabase.getInstance(requireContext())!!
        val userId = getJwt()

        songDB.albumDao().disLikedAlbum(userId, albumId)
    }

    private fun isLikedAlbum(albumId : Int):Boolean{
        val songDB = SongDatabase.getInstance(requireContext())!!
        val userId = getJwt()

        val  likeId = songDB.albumDao().isLikedAlbum(userId, albumId)

        return likeId != null
    }

    private fun getJwt(): Int {
        //현재 sp에 jwt에 저장돼 있는지 확인
        //? 프래그먼트에서 사용하는 방법
        val spf = activity?.getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE)
        return spf!!.getInt("jwt",0) //저장된 값 없으면 0 반환
    }



}