package sopt_jungnami_android.jungnami.community

import android.content.Context
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import okhttp3.MultipartBody
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Response
import sopt_jungnami_android.jungnami.Network.ApplicationController
import sopt_jungnami_android.jungnami.Network.NetworkService
import sopt_jungnami_android.jungnami.Post.PostCommunityLikeRequset
import sopt_jungnami_android.jungnami.Post.PostCommunityPostingResponse
import sopt_jungnami_android.jungnami.Post.postCommunityLikeResponse
import sopt_jungnami_android.jungnami.R
import sopt_jungnami_android.jungnami.data.Content
import sopt_jungnami_android.jungnami.db.SharedPreferenceController
import javax.security.auth.callback.Callback

//made by YunHwan
//modify by TakHyeongMin
class CommunityRecyclerViewAdapter(val ctx: Context ,val dataList: ArrayList<Content>) :RecyclerView.Adapter<CommunityRecyclerViewAdapter.Holder>() {
    private lateinit var onItemClick: View.OnClickListener
    lateinit var networkService: NetworkService
    lateinit var postCommunityLike : PostCommunityLikeRequset
    fun setOnItemClickListener(l : View.OnClickListener){
        onItemClick = l
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder{
        val view = LayoutInflater.from(ctx).inflate(R.layout.rv_item_feed, parent,false)
        view.setOnClickListener(onItemClick)
        return Holder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        Glide.with(this!!.ctx!!)
                .load(dataList[position].userimg)
                .into(holder.profile_img_btn)

        // Text없으면 GONE 처리
        if(dataList[position].content.isEmpty()) {
            holder.feed_description.visibility = View.GONE
        }else{
            holder.feed_description.setText(dataList[position].content)
            Log.v("완료", dataList[position].content)
        }
        // img없으면 GONE 처리
        if(dataList[position].img == "0") {
            holder.feed_image.visibility = View.GONE
        }else{
            val requestOptions = RequestOptions()
            requestOptions.fitCenter()
            Glide.with(this!!.ctx!!)
                    .setDefaultRequestOptions(requestOptions)
                    .load(dataList[position].img)
                    .into(holder.feed_image)
        }

        // isLike가 1이면 좋아요 버튼이 파란색
        if(dataList[position].islike == 1){
            holder.feed_likes_btn.setImageResource(R.drawable.community_heart_blue)
        }else{
            holder.feed_likes_btn.setImageResource(R.drawable.community_heart_gray)
        }

        holder.profile_name_btn.text = dataList[position].nickname
        holder.feed_date.text = dataList[position].writingtime

        holder.feed_likes_num_btn.text = dataList[position].likecnt.toString()
        holder.feed_chat_num_btn.text = dataList[position].commentcnt.toString()

        // 좋아요를 눌렀는지 안눌렀는지를 islike(Int)를 통해 판별한다.
        if (dataList[position].islike == 1){
            holder.feed_likes_btn.setImageResource(R.drawable.community_heart_blue)
        }

        // 좋아요가 눌렀을 때
        holder.feed_likes_btn.setOnClickListener {
            networkService = ApplicationController.instance.networkService
            postCommunityLike = PostCommunityLikeRequset(dataList[position].boardid)
            val postCommunityLikeRequset = networkService.postCommunityLike(SharedPreferenceController.getAuthorization(context = ctx!!),postCommunityLike)
            postCommunityLikeRequset.enqueue(object : Callback, retrofit2.Callback<postCommunityLikeResponse> {
                override fun onFailure(call: Call<postCommunityLikeResponse>?, t: Throwable?) {
                }
                override fun onResponse(call: Call<postCommunityLikeResponse>?, response: Response<postCommunityLikeResponse>?) {
                    if(response!!.isSuccessful){
                        Log.v("성공?", response!!.body()!!.message)
                    }
                }

            })


            // 보여주는 좋아요 수 늘려주기
            var likenum = dataList[position].likecnt + 1
            holder.feed_likes_num_btn.text = likenum.toString()

            // 좋아요 버튼 색 blue로 바꿔주기
            holder.feed_likes_btn.setImageResource(R.drawable.community_heart_blue)
        }

        holder.feed_chat_num_btn.setOnClickListener {
            ctx.startActivity<CommunityCommentActivity>("board_id" to dataList[position].boardid)
        }

        holder.feed_chats_btn.setOnClickListener {
            ctx.startActivity<CommunityCommentActivity>("board_id" to dataList[position].boardid)
        }

        holder.feed_share_btn.setOnClickListener {

        }

        holder.feed_scrap_btn.setOnClickListener {
            requestCommunityPostingResponse(dataList[position].boardid)
        }
    }


    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val profile_img_btn : ImageView = itemView.findViewById(R.id.contents_feed_rv_item_shared_profile_image_btn) as ImageView
        val profile_name_btn : TextView = itemView.findViewById(R.id.contents_comment_rv_item_profile_name_tv) as TextView
        val feed_date : TextView = itemView.findViewById(R.id.contents_comment_rv_item_date) as TextView
        val feed_description : TextView = itemView.findViewById(R.id.contents_comment_rv_item_contents_tv) as TextView
        val feed_image : ImageView = itemView.findViewById(R.id.contents_comment_rv_item_contents_iv) as ImageView
        val feed_likes_num_btn : TextView = itemView.findViewById(R.id.contents_comment_rv_item_heart_num_btn) as TextView
        val feed_chat_num_btn : TextView = itemView.findViewById(R.id.contents_comment_rv_item_chat_num_des_btn) as TextView

        val feed_likes_btn : ImageView = itemView.findViewById(R.id.contents_comment_tv_item_bottom_bar_heart_btn) as ImageView
        val feed_chats_btn : ImageView = itemView.findViewById(R.id.contents_comment_tv_item_bottom_bar_chat_btn) as ImageView
        val feed_share_btn : ImageView = itemView.findViewById(R.id.contents_comment_tv_item_bottom_bar_share_btn) as ImageView
        val feed_scrap_btn : ImageView = itemView.findViewById(R.id.contents_comment_tv_item_bottom_bar_scrap_btn) as ImageView
    }

    fun requestCommunityPostingResponse(board_id : Int){
        val networkService = ApplicationController.instance.networkService
        val postCommunityPostingResponse = networkService.postCommunityPostingResponse(SharedPreferenceController.getAuthorization(ctx),
                "", null, board_id)
        postCommunityPostingResponse.enqueue(object : retrofit2.Callback<PostCommunityPostingResponse>{
            override fun onFailure(call: Call<PostCommunityPostingResponse>?, t: Throwable?) {
            }

            override fun onResponse(call: Call<PostCommunityPostingResponse>?, response: Response<PostCommunityPostingResponse>?) {
                if(response!!.isSuccessful){
                    ctx.toast("스크랩 완료")
                }
            }
        })
    }

}