package sopt_jungnami_android.jungnami.contents

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import sopt_jungnami_android.jungnami.R
import sopt_jungnami_android.jungnami.data.ContentItemData

class ContentsRecyclerViewAdapter(val ctx :Context, val dataList : ArrayList<ContentItemData>) : RecyclerView.Adapter<ContentsRecyclerViewAdapter.Holder>() {
    lateinit var onItemClick : View.OnClickListener
    fun setOnItemClickListener(l : View.OnClickListener){
        onItemClick = l
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view =  LayoutInflater.from(ctx).inflate(R.layout.rv_item_contents, parent, false)
        view.setOnClickListener(onItemClick)
        return Holder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        //마진 동적 설정
        val dp = ctx.resources.displayMetrics.density
        val rootLayoutParams : RelativeLayout.LayoutParams = holder.root_layout.layoutParams as RelativeLayout.LayoutParams
        if ((position%2) == 0){
            rootLayoutParams.rightMargin = (6*dp).toInt()
        } else {
            rootLayoutParams.leftMargin = (6*dp).toInt()
        }
        holder.root_layout.layoutParams = rootLayoutParams
        //이미지 셋팅하기
        //holder.image.setBackgroundColor(Color.parseColor("#00B8D4"))
        holder.title.text = dataList[position].title
        holder.info.text = "${dataList[position].category} · 3분"

    }

    inner class Holder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val root_layout : RelativeLayout = itemView.findViewById(R.id.contents_frag_rv_item_root_layout_rl) as RelativeLayout
        val image : ImageView = itemView.findViewById(R.id.contents_frag_rv_item_sub_content_image_iv) as ImageView
        val title : TextView = itemView.findViewById(R.id.contents_frag_rv_item_sub_content_title_tv) as TextView
        val info : TextView = itemView.findViewById(R.id.contents_frag_rv_item_sub_content_info_tv) as TextView
    }
}