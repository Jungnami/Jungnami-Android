package sopt_jungnami_android.jungnami.mypage

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sopt_jungnami_android.jungnami.Delete.DeleteFollowResponse
import sopt_jungnami_android.jungnami.Network.ApplicationController
import sopt_jungnami_android.jungnami.Network.NetworkService
import sopt_jungnami_android.jungnami.Post.PostFollowRequest
import sopt_jungnami_android.jungnami.Post.PostFollwResponse
import sopt_jungnami_android.jungnami.R
import sopt_jungnami_android.jungnami.data.FollowerData
import sopt_jungnami_android.jungnami.db.SharedPreferenceController


class FollowerAdapter (private var followerItems : ArrayList<FollowerData>, private var context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    lateinit var networkService: NetworkService
    var isLike : Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val mainView : View = LayoutInflater.from(parent!!.context).inflate(R.layout.rv_item_follower, parent, false)
        return FollowerViewHolder(mainView)
    }

    override fun getItemCount(): Int {
        return followerItems.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {


        val followerViewHolder : FollowerViewHolder = holder as FollowerViewHolder

        followerViewHolder.followBtn.setOnClickListener{
            // 팔로잉을 취소하시겠습니까
            deleteFollow(position)

            if (isLike==0){
                followerViewHolder.followBtn.setImageResource(R.drawable.alarm_follow_blue)
            } else {
                followerViewHolder.followBtn.setImageResource(R.drawable.alarm_follow_gray)
            }

            followerViewHolder.followBtn.visibility = View.GONE
            followerViewHolder.followingBtn.visibility = View.VISIBLE


            Log.v("xxx", isLike.toString())
        }

        followerViewHolder.followingBtn.setOnClickListener {
            // 팔로우하시겠습니까 ?
            postFollow(position)

            if (isLike==0){
                followerViewHolder.followingBtn.setImageResource(R.drawable.alarm_follow_blue)
            } else {
                followerViewHolder.followingBtn.setImageResource(R.drawable.alarm_follow_gray)
            }

            followerViewHolder.followBtn.visibility = View.VISIBLE
            followerViewHolder.followingBtn.visibility = View.GONE


            Log.v("deletexxx", isLike.toString())
        }



        // rv 이미지 가져오기
        Glide.with(context)
                .load(followerItems[position].follower_img_url)
                .into(holder.userImage)

        // rv nickName 하기
        followerViewHolder.nickName.setText(followerItems[position].follower_nickname)

        // isMyFollowing에 따라 버튼 바꾸기.
        when (followerItems[position].isMyFollowing) {
            "나" -> {
                followerViewHolder.followBtn.visibility = View.GONE
                followerViewHolder.followingBtn.visibility = View.GONE
            }
            "팔로잉" -> {
                followerViewHolder.followBtn.visibility = View.GONE
                followerViewHolder.followingBtn.visibility = View.VISIBLE
            }
            "팔로우" -> {
                followerViewHolder.followBtn.visibility = View.VISIBLE
                followerViewHolder.followingBtn.visibility = View.GONE
            }




        }
        // 온클릭 리스너로 팔로우버튼 팔로잉 버튼 이벤트 추가해줘야한다.
    }

    fun postFollow(position : Int){
        networkService = ApplicationController.instance.networkService
        var following_id : String = followerItems!![position].follower_id
        var postFollowResponse = networkService.postFollow(SharedPreferenceController.getAuthorization(context),following_id)
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
        var following_id : String = followerItems!![position].follower_id
        networkService = ApplicationController.instance.networkService
        val deleteFollowResponse = networkService.deleteFollow(SharedPreferenceController.getAuthorization(context),following_id)
        deleteFollowResponse.enqueue(object :Callback<DeleteFollowResponse>{
            override fun onFailure(call: Call<DeleteFollowResponse>?, t: Throwable?) {
            }

            override fun onResponse(call: Call<DeleteFollowResponse>?, response: Response<DeleteFollowResponse>?) {
                isLike = 0
            }

        })
    }

    class FollowerViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        var userImage : ImageView = itemView!!.findViewById(R.id.rv_item_follower_profile_image_btn)
        var nickName : TextView = itemView!!.findViewById(R.id.rv_item_follower_nickname_btn)
        var followingBtn : ImageView = itemView!!.findViewById(R.id.act_follower_rv_item_following_btn)
        var followBtn : ImageView = itemView!!.findViewById(R.id.act_follower_rv_item_follow_btn)

    }
}
