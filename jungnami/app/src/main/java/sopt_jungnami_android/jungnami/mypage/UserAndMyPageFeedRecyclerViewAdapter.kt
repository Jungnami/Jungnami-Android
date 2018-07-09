package sopt_jungnami_android.jungnami.mypage

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import sopt_jungnami_android.jungnami.R
import sopt_jungnami_android.jungnami.data.FeedItemData

class UserAndMyPageFeedRecyclerViewAdapter(val ctx : Context, val dataList : ArrayList<FeedItemData>) : RecyclerView.Adapter<UserAndMyPageFeedRecyclerViewAdapter.Holder>() {
    lateinit var onItemClick : View.OnClickListener
    fun setOnItemClickListener(l : View.OnClickListener){
        onItemClick = l
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(ctx).inflate(R.layout.rv_item_userpage_feed, parent, false)
        view.setOnClickListener(onItemClick)

        return Holder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {

        holder.profile_name.text = dataList[position].profile_name

    }

    inner class Holder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val profile_name : TextView = itemView.findViewById(R.id.userpage_feed_rv_item_profile_name_btn) as TextView
    }
}