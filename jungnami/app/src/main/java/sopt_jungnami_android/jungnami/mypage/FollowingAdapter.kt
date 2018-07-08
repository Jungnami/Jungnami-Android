package sopt_jungnami_android.jungnami.mypage

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import sopt_jungnami_android.jungnami.R
import sopt_jungnami_android.jungnami.data.FollowingData

class FollowingAdapter (private var followingItems : ArrayList<FollowingData>, private var context: Context)  : RecyclerView.Adapter<RecyclerView.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val mainView : View = LayoutInflater.from(parent!!.context).inflate(R.layout.rv_item_following, parent, false)
        return FollowingViewHolder(mainView)
    }

    override fun getItemCount(): Int {
        return followingItems.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val followingViewHolder : FollowingViewHolder = holder as FollowingViewHolder

        followingViewHolder.userImage.setImageResource(followingItems[position].userImg)
        followingViewHolder.nickName.setText(followingItems[position].userNickName)
        followingViewHolder.userName.setText(followingItems[position].userName)
    }


    class FollowingViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var userImage : ImageView = itemView!!.findViewById(R.id.act_following_rv_item_profile_btn)
        var userName : TextView = itemView!!.findViewById(R.id.act_following_rv_item_name_btn)
        var nickName : TextView = itemView!!.findViewById(R.id.act_following_rv_item_nickname_btn)
    }
}