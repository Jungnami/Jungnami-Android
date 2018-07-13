package sopt_jungnami_android.jungnami

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sopt_jungnami_android.jungnami.Delete.DeleteFollowResponse
import sopt_jungnami_android.jungnami.Network.ApplicationController
import sopt_jungnami_android.jungnami.Network.NetworkService
import sopt_jungnami_android.jungnami.Post.PostFollowRequest
import sopt_jungnami_android.jungnami.Post.PostFollwResponse
import sopt_jungnami_android.jungnami.data.AlarmData

class AlarmViewAdapter (private var context: Context, private var alarmItems : ArrayList<AlarmData>) : RecyclerView.Adapter<AlarmViewAdapter.AlarmViewHolder>() {

    lateinit var networkService: NetworkService
    var isLike : Int = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmViewHolder {
        val alarmView : View = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_alarm, parent, false)
        return AlarmViewHolder(alarmView)
    }

    override fun getItemCount(): Int = alarmItems.size

    override fun onBindViewHolder(holder: AlarmViewHolder, position: Int) {
        when (alarmItems[position].ischecked) {
            0 -> {
                holder.alarmlayout.setBackgroundColor(Color.parseColor("#F2FCFF"))
            }
            1 -> {
                holder.alarmlayout.setBackgroundColor(Color.parseColor("#ffffff"))
            }
        }

        holder.alarmFollowimg.setOnClickListener {
            postFollow(position)

            if (isLike==0){
                holder.alarmFollowimg.setImageResource(R.drawable.alarm_follow_blue)
            } else {
                holder.alarmFollowimg.setImageResource(R.drawable.alarm_follow_gray)
            }

            holder.alarmFollowimg.visibility = View.GONE
            holder.alarmFollowingimg.visibility = View.VISIBLE
        }

        holder.alarmFollowingimg.setOnClickListener {
            deleteFollow(position)

            if (isLike==0){
                holder.alarmFollowingimg.setImageResource(R.drawable.alarm_follow_blue)
            } else {
                holder.alarmFollowingimg.setImageResource(R.drawable.alarm_follow_gray)
            }

            holder.alarmFollowimg.visibility = View.VISIBLE
            holder.alarmFollowingimg.visibility = View.GONE
        }


        Glide.with(context).load(alarmItems[position].img_url).into(holder.alarmProfileimg)
        holder.alarmName.text = alarmItems[position].actionname
        holder.alarmState.text = alarmItems[position].actionmessage
        holder.alarmDate.text = alarmItems[position].time
        when (alarmItems[position].button) {
            "팔로우" -> {
                holder.alarmFollowimg.setImageResource(R.drawable.alarm_follow_blue)
                holder.alarmFollowingimg.visibility = View.GONE
            }
            "팔로잉" -> {
                holder.alarmFollowingimg.setImageResource(R.drawable.alarm_following_gray)
                holder.alarmFollowimg.visibility = View.GONE
            }
            "" -> {
                holder.alarmFollowimg.visibility = View.GONE
                holder.alarmFollowingimg.visibility = View.GONE
            }
        }
//        메뉴 이미지
//        holder.alarmMenuimg.setImageResource(alarmItems[position].menuimg)
//        when(alarmItems[position].menuimg) {
//            R.drawable.alarm_community -> {
//                holder.alarmFollowimg.visibility = View.INVISIBLE
//            }
//            R.drawable.alarm_contents -> {
//                holder.alarmFollowimg.visibility = View.INVISIBLE
//            }
//            R.drawable.alarm_follow -> {
//                when(alarmItems[position].followimg) {
//                    "팔로우" -> {
//                        holder.alarmFollowimg.setImageResource(R.drawable.alarm_follow_blue)
//                    }
//                    "팔로잉" -> {
//                        holder.alarmFollowimg.setImageResource(R.drawable.alarm_following_gray)
//                    }
//                }
//            }
//        }
    }

    fun postFollow(position : Int){
        networkService = ApplicationController.instance.networkService
//        var following_id : String = followingItems!![position].following_id
        var following_id : String = alarmItems[position].id
        var postFollowRequest = PostFollowRequest(following_id)
        var postFollowResponse = networkService.postFollow(postFollowRequest)
        postFollowResponse.enqueue(object : Callback<PostFollwResponse> {
            override fun onFailure(call: Call<PostFollwResponse>?, t: Throwable?) {

            }

            override fun onResponse(call: Call<PostFollwResponse>?, response: Response<PostFollwResponse>?) {
                if(response!!.isSuccessful){
                    isLike = 1

                }
            }

        })
    }


    fun deleteFollow(position: Int){
        var following_id : String = alarmItems!![position].id
        networkService = ApplicationController.instance.networkService
        val deleteFollowResponse = networkService.deleteFollow(following_id)
        deleteFollowResponse.enqueue(object :Callback<DeleteFollowResponse>{
            override fun onFailure(call: Call<DeleteFollowResponse>?, t: Throwable?) {
            }

            override fun onResponse(call: Call<DeleteFollowResponse>?, response: Response<DeleteFollowResponse>?) {
                isLike = 0
            }

        })
    }

    inner class AlarmViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        var alarmlayout : LinearLayout = itemView!!.findViewById(R.id.rv_alarm_item_click_btn) as LinearLayout
        var alarmProfileimg : CircleImageView = itemView!!.findViewById(R.id.rv_alarm_item_user_img_btn) as CircleImageView
        var alarmName : TextView = itemView!!.findViewById(R.id.rv_alarm_item_user_name_tv) as TextView
        var alarmState : TextView = itemView!!.findViewById(R.id.rv_alarm_item_user_state_tv) as TextView
        var alarmDate : TextView = itemView!!.findViewById(R.id.rv_alarm_item_user_time_tv) as TextView
        var alarmFollowimg : ImageView = itemView!!.findViewById(R.id.rv_alarm_item_follow_btn) as ImageView
        var alarmFollowingimg: ImageView = itemView!!.findViewById(R.id.rv_alarm_item_following_btn) as ImageView
    }
}