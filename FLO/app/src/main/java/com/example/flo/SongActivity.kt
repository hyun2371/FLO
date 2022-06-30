package com.example.flo

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivitySongBinding
import com.google.gson.Gson


class SongActivity : AppCompatActivity() {

    //소괄호 : 클래스를 다른 클래스로 상속을 진행할 때는 소괄호를 넣어줘야 한다.

    //나중에 초기화
    lateinit var binding : ActivitySongBinding
    lateinit var song : Song
    lateinit var timer: Timer
    //변수 선언 방법 var test1 : String = "DD"

    //미디어 파일 쉽게 재생 시키는 클래스
    private var mediaPlayer : MediaPlayer? = null
    private var gson: Gson= Gson()


    //액티비티가 실행될 때 최초로 실행되는 함수
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater) //xml 레이아웃 메모리에 객체화
        setContentView(binding.root) //activity_song.xml 레이아웃 요소들을 마음대로 씀

        initSong()
        setPlayer(song)

        binding.songDownIb.setOnClickListener {
            finish()
        }

        binding.songMiniplayerIv.setOnClickListener {
            setPlayerStatus(true)
        }

        binding.songPauseIv.setOnClickListener {
            setPlayerStatus(false)
        }

    }

    override fun onPause(){
        super.onPause()
        setPlayerStatus(false)

        //음악이 몇초까지 재생되었는지 반영
        song.second = ((binding.songProgressSb.progress * song.playTime)/100)/1000

        //내부 저장소에 데이터 저장(앱 재실행해도 저장됨)
        var sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)
        val editor = sharedPreferences.edit() // 에디터
        val songJson = gson.toJson(song) //song객체->Json
        editor.putString("songData", songJson) //sharedP 이름 song & sharedP에 저장된 데이터 이름 songData

        editor.apply()
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.interrupt()
        mediaPlayer?.release() //미디어 플레이어가 가지고 있던 리소스 해제
        mediaPlayer = null //미디어 플레이어 해제
    }

    private fun initSong(){
        if(intent.hasExtra("title") && intent.hasExtra("singer")){ //intent에 해당 키값 존재하는지
            song = Song( //putExtra
                intent.getStringExtra("title")!!,
                intent.getStringExtra("singer")!!,
                intent.getIntExtra("second",0),
                intent.getIntExtra("playTime",0),
                intent.getBooleanExtra("isPlaying",false),
                intent.getStringExtra("music",)!!
            )
        }
        startTimer()
    }

    private fun setPlayer(song: Song){
        binding.songMusicTitleTv.text = intent.getStringExtra("title")!!
        binding.songSingerNameTv.text = intent.getStringExtra("singer")!!
        binding.songStartTimeTv.text = String.format("%02d:%02d",song.second / 60, song.second % 60)
        binding.songEndTimeTv.text = String.format("%02d:%02d",song.playTime / 60, song.playTime % 60)
        binding.songProgressSb.progress = (song.second * 1000 / song.playTime)
        val music = resources.getIdentifier(song.music, "raw", this.packageName) //리소스 반환 받음
        mediaPlayer = MediaPlayer.create(this, music) //재생할 음악 알려줌
        setPlayerStatus(song.isPlaying)

    }

    fun setPlayerStatus (isPlaying : Boolean){ //정지, 재생 버튼 이미지 설정

        //초기화
        song.isPlaying = isPlaying
        timer.isPlaying = isPlaying

        if(isPlaying){
            binding.songMiniplayerIv.visibility = View.GONE
            binding.songPauseIv.visibility = View.VISIBLE
            mediaPlayer?.start()
        } else {
            binding.songMiniplayerIv.visibility = View.VISIBLE//아이디값
            binding.songPauseIv.visibility = View.GONE
            if (mediaPlayer?.isPlaying == true){
                mediaPlayer?.pause()
            }
        }
    }



    private fun startTimer(){
        timer = Timer(song.playTime,song.isPlaying)
        timer.start()
    }

    //내부 클래스
    inner class Timer(private val playTime: Int,var isPlaying: Boolean = true):Thread(){

        private var second : Int = 0
        private var mills: Float = 0f

        override fun run() {
            super.run()
            try {
                while (true){

                    if (second >= playTime){
                        break
                    }

                    if (isPlaying){
                        sleep(50)
                        mills += 50

                        runOnUiThread {
                            binding.songProgressSb.progress = ((mills / playTime)*100).toInt()
                        }

                        if (mills % 1000 == 0f){ //ms 1000당 s 1증가
                            runOnUiThread {
                                binding.songStartTimeTv.text = String.format("%02d:%02d",second / 60, second % 60)
                            }
                            second++
                        }

                    }

                }

            }catch (e: InterruptedException){
                Log.d("Song","쓰레드가 죽었습니다. ${e.message}")
            }


        }
    }
}