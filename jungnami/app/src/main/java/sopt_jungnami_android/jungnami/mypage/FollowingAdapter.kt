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
import sopt_jungnami_android.jungnami.data.FollowingData

class FollowingAdapter (private var followingItems : ArrayList<FollowingData>, private var context: Context)  : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    lateinit var networkService: NetworkService
    var isLike : Int = 0



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val mainView : View = LayoutInflater.from(parent!!.context).inflate(R.layout.rv_item_following, parent, false)
        return FollowingViewHolder(mainView)
    }

    override fun getItemCount(): Int {
        return followingItems.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val followingViewHolder : FollowingViewHolder = holder as FollowingViewHolder

//        // 팔로잉하기, 팔로우 취소하기 때 사용해야한다.
//        var f_id = followingItems[position].following_id

        followingViewHolder.followBtn.setOnClickListener{
            postFollow(position)
            // checkLikeClicked함수랑 같은 역할

            if (isLike==0){
                followingViewHolder.followBtn.setImageResource(R.drawable.alarm_follow_blue)
            } else {
                followingViewHolder.followBtn.setImageResource(R.drawable.alarm_follow_gray)
            }

            followingViewHolder.followBtn.visibility = View.GONE
            followingViewHolder.followingBtn.visibility = View.VISIBLE


            Log.v("xxx", isLike.toString())

        }

        followingViewHolder.followingBtn.setOnClickListener {
            // 팔로잉을 취소하시겠습니까 ?

            //팔로우 취소 함수
            deleteFollow(position)

            if (isLike==0){
                followingViewHolder.followingBtn.setImageResource(R.drawable.alarm_follow_blue)
            } else {
                followingViewHolder.followingBtn.setImageResource(R.drawable.alarm_follow_gray)
            }

            followingViewHolder.followBtn.visibility = View.VISIBLE
            followingViewHolder.followingBtn.visibility = View.GONE


            Log.v("deletexxx", isLike.toString())

        }



        // rv 이미지 가져오기
        Glide.with(context)
                .load(followingItems[position].following_img_url)
                .into(holder.userImage)

        // rv nickName 하기
        followingViewHolder.nickName.setText(followingItems[position].following_nickname)

        // isMyFollowing에 따라 버튼 바꾸기.
        when (followingItems[position].isMyFollowing) {
            "나" -> {
                followingViewHolder.followBtn.visibility = View.GONE
                followingViewHolder.followingBtn.visibility = View.GONE
            }
            "팔로잉" -> {
                followingViewHolder.followBtn.visibility = View.GONE
                followingViewHolder.followingBtn.visibility = View.VISIBLE
            }
            "팔로우" -> {
                followingViewHolder.followBtn.visibility = View.VISIBLE
                followingViewHolder.followingBtn.visibility = View.GONE
            }
        }
        // 온클릭 리스너로 팔로우버튼 팔로잉 버튼 이벤트 추가해줘야한다.
    }
    fun postFollow(position : Int){
        networkService = ApplicationController.instance.networkService
        var following_id : String = followingItems!![position].following_id
        var postFollowRequest = PostFollowRequest(following_id)
        var postFollowResponse = networkService.postFollow(postFollowRequest)
        postFollowResponse.enqueue(object :Callback<PostFollwResponse>{
            override fun onFailure(call: Call<PostFollwResponse>?, t: Throwable?) {

            }

            override fun onResponse(call: Call<PostFollwResponse>?, response: Response<PostFollwResponse>?) {
                if(response!!.isSuccessful){
                    isLike = 1

                }
            }

        })
    }


//
//    // 색깔 바뀌는 곳
//    private fun checkLikeClicked(){
//        if (isLike==0){
//            followingViewHolder.followBtn.setImageResource(R.drawable.alarm_follow_gray)
//        } else {
//            followingViewHolder.followBtn.setImageResource(R.drawable.alarm_follow_blue)
//        }
//    }

    fun deleteFollow(position: Int){
        var following_id : String = followingItems!![position].following_id
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


    class FollowingViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        var userImage : ImageView = itemView!!.findViewById(R.id.act_following_rv_item_profile_btn)
        var nickName : TextView = itemView!!.findViewById(R.id.act_following_rv_item_nick_name_btn)
        var followingBtn : ImageView = itemView!!.findViewById(R.id.act_following_rv_item_following_btn)
        var followBtn : ImageView = itemView!!.findViewById(R.id.act_following_rv_item_follow_btn)
    }
}