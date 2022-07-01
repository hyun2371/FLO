package com.example.flo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flo.databinding.ItemAlbumBinding

// 어댑터가 아이템 뷰 객체들에게 데이터를 바인딩 해줌
// 어댑터 클래스 상속 받음 <>안에 뷰 홀더 넣어줌
class AlbumRVAdapter(private val albumList: ArrayList<Album>) : RecyclerView.Adapter<AlbumRVAdapter.ViewHolder>(){

    //recycler 뷰에는 click listener가 없으므로 추가
    interface MyItemClickListener{
        fun onItemClick(album: Album)
        fun onRemoveAlbum(position: Int)
    }

    private lateinit var mItemClickListener: MyItemClickListener
    //홈 프래그먼트에서 리스너 객체 전달 받음
    fun setMyItemClickListener(itemClickListener : MyItemClickListener){
        mItemClickListener = itemClickListener
    }

    fun addItem(album: Album){
        albumList.add(album)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int){
        albumList.removeAt(position)
        notifyDataSetChanged()
    }

    // 뷰홀더를 생성해줘야 할 때 호출되는 함수 => 아이템 뷰 객체를 만들어서 뷰홀더에 던져줌
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): AlbumRVAdapter.ViewHolder {
        //아이템뷰 객체 생성
        val binding: ItemAlbumBinding = ItemAlbumBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding) //아이템뷰 객체를 재활용하도록 뷰 홀더에게 던져줌
    }


    // 뷰홀더에 데이터를 바인딩해줘야 할 때마다 호출되는 함수 => 스크롤할 때마다 호출됨
    override fun onBindViewHolder(holder: AlbumRVAdapter.ViewHolder, position: Int) {
        holder.bind(albumList[position]) //position=indexid 받아온 뷰홀더에 바인딩을 해주기 위해 해당 포지션의 데이터를 던져줌
        holder.itemView.setOnClickListener { mItemClickListener.onItemClick(albumList[position]) }
//        holder.binding.itemAlbumTitleTv.setOnClickListener { mItemClickListener.onRemoveAlbum(position) } //삭제됐을 때
    }

    // 데이터 세트 크기를 알려주는 함수 => 리사이클러뷰가 마지막이 언제인지를 알게 된다.
    override fun getItemCount(): Int = albumList.size

    // 뷰홀더 (아이템 뷰 객체가 날아가지 않도록 담는 그릇)
    //아이템 레이아웃 객체를 매개변수로 받음
    //ViewHolder 클래스 상속 받음
    inner class ViewHolder(val binding: ItemAlbumBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(album: Album){
            binding.itemAlbumTitleTv.text = album.title
            binding.itemAlbumSingerTv.text = album.singer
            binding.itemAlbumCoverImgIv.setImageResource(album.coverImg!!)
        }
    }
}