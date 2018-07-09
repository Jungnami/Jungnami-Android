package sopt_jungnami_android.jungnami.mypage

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

class UserAndMyPageScrapRecyclerViewAdapter(val ctx : Context, val dataList : ArrayList<ContentItemData>) : RecyclerView.Adapter<UserAndMyPageScrapRecyclerViewAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(ctx).inflate(R.layout.rv_item_contents, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        //마진 동적 설정
        val rootLayoutParams : RelativeLayout.LayoutParams = holder.root_layout.layoutParams as RelativeLayout.LayoutParams
        if ((position%2) == 0){
            rootLayoutParams.rightMargin = 6
        } else {
            rootLayoutParams.leftMargin = 6
        }
        holder.root_layout.layoutParams = rootLayoutParams
        holder.title.text = dataList[position].title
        holder.category.text = "${dataList[position].category} · 3분"
        //holder.image.setBackgroundColor(Color.parseColor("#00B8D4"))
    }

    inner class Holder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val root_layout : RelativeLayout = itemView.findViewById(R.id.contents_frag_rv_item_root_layout_rl) as RelativeLayout
        val title : TextView = itemView.findViewById(R.id.contents_frag_rv_item_sub_content_title_tv) as TextView
        val category : TextView = itemView.findViewById(R.id.contents_frag_rv_item_sub_content_info_tv) as TextView
        val image : ImageView = itemView.findViewById(R.id.contents_frag_rv_item_sub_content_image_iv) as ImageView
    }
}