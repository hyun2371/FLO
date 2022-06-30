package com.example.flo_

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.flo.AlbumVPAdapter
import com.example.flo.HomeFragment
import com.example.flo.MainActivity
import com.example.flo.R
import com.example.flo.databinding.FragmentAlbumBinding
import com.google.android.material.tabs.TabLayoutMediator


class AlbumFragment : Fragment() {

    lateinit var binding: FragmentAlbumBinding

    private val information = arrayListOf("수록곡", "상세정보", "영상")

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlbumBinding.inflate(inflater,container,false)

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

}