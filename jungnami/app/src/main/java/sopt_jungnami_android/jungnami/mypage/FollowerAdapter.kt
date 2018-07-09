package sopt_jungnami_android.jungnami.mypage

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import sopt_jungnami_android.jungnami.R
import sopt_jungnami_android.jungnami.data.FollowerData


class FollowerAdapter (private var followerItems : ArrayList<FollowerData>, private var context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val mainView : View = LayoutInflater.from(parent!!.context).inflate(R.layout.rv_item_follower, parent, false)
        return FollowerViewHolder(mainView)
    }

    override fun getItemCount(): Int {
        return followerItems.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val followerViewHolder : FollowerViewHolder = holder as FollowerViewHolder
        followerViewHolder.userImage.setImageResource(followerItems[position].userImage)
        followerViewHolder.nickName.setText(followerItems[position].nickName)
    }

    class FollowerViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        var userImage : ImageView = itemView!!.findViewById(R.id.rv_item_follower_profile_image_btn)
        var nickName : TextView = itemView!!.findViewById(R.id.rv_item_follower_nickname_btn)

    }
}
