package com.example.flo_

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.flo.*
import com.example.flo.databinding.FragmentAlbumBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson


class AlbumFragment : Fragment() {

    lateinit var binding: FragmentAlbumBinding
    private var gson: Gson = Gson()

    private val information = arrayListOf("수록곡", "상세정보", "영상")

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlbumBinding.inflate(inflater,container,false)

        val albumJson = arguments?.getString("album") //argument에서 데이터를 꺼내서 json형태로 변환
        val album = gson.fromJson(albumJson, Album::class.java) //json->album객체
        setInit(album)

        binding.albumBackIv.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.main_frm, HomeFragment()) //메인 프래그먼트->홈 프래그먼트
                    .commitAllowingStateLoss()
        }

        //뷰페이저에 프래그먼트 연결
        val albumAdapter = AlbumVPAdapter(this)
        binding.albumContentVp.adapter = albumAdapter

        //탭 레이아웃을 뷰페이저와 연결 (선택된 탭과 뷰페이저 동기화)
        TabLayoutMediator(binding.albumContentTb, binding.albumContentVp){
            tab, position ->
            tab.text = information[position] //탭에 배열에 맞는 내용 표시
        }.attach()

        return binding.root
    }

    private fun setInit(album: Album) {
        binding.albumAlbumIv.setImageResource(album.coverImg!!)
        binding.albumMusicTitleTv.text = album.title.toString()
        binding.albumSingerNameTv.text = album.singer.toString()
    }


}