package sopt_jungnami_android.jungnami.community

import android.support.v4.app.FragmentActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import sopt_jungnami_android.jungnami.R
import sopt_jungnami_android.jungnami.data.Content

//made by YunHwan
//modify by TakHyeongMin
class CommunityRecyclerViewAdapter(val dataList: ArrayList<Content>, val ctx: FragmentActivity?) :RecyclerView.Adapter<CommunityRecyclerViewAdapter.Holder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder{
        val view = LayoutInflater.from(ctx).inflate(R.layout.rv_item_feed, parent,false)
        return Holder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {

        Glide.with(this!!.ctx!!)
                .load(dataList[position].img)
                .into(holder.profile_img_btn)

        holder.profile_name_btn.text = dataList[position].nickname
        holder.feed_date.text = dataList[position].writingtime
        holder.feed_description.text = dataList[position].content
        holder.feed_likes_num_btn.text = dataList[position].likecnt.toString()
        holder.feed_chat_num_btn.text = dataList[position].commentcnt.toString()

        // 좋아요를 눌렀는지 안눌렀는지를 islike(Int)를 통해 판별한다.
        if (dataList[position].islike == 1){
            holder.feed_likes_btn.setImageResource(R.drawable.community_heart_blue)
        }

        holder.feed_likes_btn.setOnClickListener {

        }

        holder.feed_chats_btn.setOnClickListener {

        }

        holder.feed_share_btn.setOnClickListener {

        }

        holder.feed_scrap_btn.setOnClickListener {

        }




    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView){

        var profile_img_btn : ImageView = itemView.findViewById(R.id.userpage_feed_rv_item_shared_profile_image_btn) as ImageView
        var profile_name_btn : TextView = itemView.findViewById(R.id.contents_comment_rv_item_profile_name_tv) as TextView
        var feed_date : TextView = itemView.findViewById(R.id.contents_comment_rv_item_date) as TextView
        var feed_description : TextView = itemView.findViewById(R.id.contents_comment_rv_item_contents_tv) as TextView
        var feed_likes_num_btn : TextView = itemView.findViewById(R.id.contents_comment_rv_item_heart_num_btn) as TextView
        var feed_chat_num_btn : TextView = itemView.findViewById(R.id.contents_comment_rv_item_chat_num_des_btn) as TextView
        var feed_likes_btn : ImageView = itemView.findViewById(R.id.contents_comment_tv_item_bottom_bar_heart_btn) as ImageView
        var feed_chats_btn : ImageView = itemView.findViewById(R.id.contents_comment_tv_item_bottom_bar_chat_btn) as ImageView
        var feed_share_btn : ImageView = itemView.findViewById(R.id.contents_comment_tv_item_bottom_bar_share_btn) as ImageView
        var feed_scrap_btn : ImageView = itemView.findViewById(R.id.contents_comment_tv_item_bottom_bar_scrap_btn) as ImageView



    }
}