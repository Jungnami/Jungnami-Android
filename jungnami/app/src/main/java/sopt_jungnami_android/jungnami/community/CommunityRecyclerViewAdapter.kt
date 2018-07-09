package sopt_jungnami_android.jungnami.community

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import sopt_jungnami_android.jungnami.R
import sopt_jungnami_android.jungnami.data.FeedItemData

//made by YunHwan
class CommunityRecyclerViewAdapter(val ctx :Context, val dataList : ArrayList<FeedItemData>) :RecyclerView.Adapter<CommunityRecyclerViewAdapter.Holder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder{
        val view = LayoutInflater.from(ctx).inflate(R.layout.rv_item_feed, parent,false)
        return Holder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.profile_name.text = dataList[position].profile_name
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val profile_name : TextView = itemView.findViewById(R.id.contents_comment_rv_item_profile_name_tv) as TextView

    }
}