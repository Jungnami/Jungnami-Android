package sopt_jungnami_android.jungnami.mypage

import android.app.Activity
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult
import retrofit2.Call
import retrofit2.Response
import sopt_jungnami_android.jungnami.Network.ApplicationController
import sopt_jungnami_android.jungnami.Network.NetworkService
import sopt_jungnami_android.jungnami.Post.PostCommunityLikeRequset
import sopt_jungnami_android.jungnami.Post.postCommunityLikeResponse
import sopt_jungnami_android.jungnami.R
import sopt_jungnami_android.jungnami.community.CommunityCommentActivity
import sopt_jungnami_android.jungnami.data.Board
import sopt_jungnami_android.jungnami.db.SharedPreferenceController
import javax.security.auth.callback.Callback

class UserAndMyPageFeedRecyclerViewAdapter(val ctx: Context, val dataList: ArrayList<Board>) : RecyclerView.Adapter<UserAndMyPageFeedRecyclerViewAdapter.Holder>() {
    lateinit var onItemClick: View.OnClickListener
    lateinit var networkService: NetworkService
    private val REQUEST_CODE_FEED =1002
    fun setOnItemClickListener(l: View.OnClickListener) {
        onItemClick = l
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(ctx).inflate(R.layout.rv_item_userpage_feed, parent, false)
        view.setOnClickListener(onItemClick)
        networkService = ApplicationController.instance.networkService
        return Holder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.u_nickname.text = dataList[position].u_nickname
        Glide.with(ctx).load(dataList[position].u_img).into(holder.u_img)
        holder.b_time.text = dataList[position].b_time
        if (dataList[position].source.size == 0){
            holder.source_ll.visibility = View.GONE
            holder.b_content.text = dataList[position].b_content
            Glide.with(ctx).load(dataList[position].b_img).into(holder.b_image)
        } else {
            holder.b_content.visibility = View.GONE
            holder.b_image.visibility = View.GONE
            holder.source_ll.visibility = View.VISIBLE
            Glide.with(ctx).load(dataList[position].source[0].u_img).into(holder.b_u_img)
            holder.b_u_nickname.text = dataList[position].source[0].u_nickname
            holder.b_b_time.text = dataList[position].source[0].b_time
            holder.b_b_content.text = dataList[position].source[0].b_content
            Glide.with(ctx).load(dataList[position].source[0].b_img).into(holder.b_b_image)
        }

        holder.b_likecnt.text = dataList[position].like_cnt.toString()+"명"
        holder.b_commentcnt.text = dataList[position].comment_cnt.toString()
        holder.b_like_img.setOnClickListener {
            Log.e("눌렸나?", "좋아요 눌리긴함")
            requestLikeBoardToServer(position)

        }
        holder.b_comment_img.setOnClickListener {
            (ctx as Activity).startActivityForResult<CommunityCommentActivity>(REQUEST_CODE_FEED,"board_id" to dataList[position].b_id)
        }
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val u_nickname: TextView = itemView.findViewById(R.id.userpage_feed_rv_item_profile_name_tv) as TextView
        val u_img: ImageView = itemView.findViewById(R.id.userpage_feed_rv_item_profile_image_btn) as ImageView
        val b_time : TextView = itemView.findViewById(R.id.userpage_comment_rv_item_date) as TextView
        val b_image : ImageView = itemView.findViewById(R.id.userpage_feed_rv_item_contents_iv) as ImageView
        val b_content: TextView = itemView.findViewById(R.id.userpage_feed_rv_item_contents_tv) as TextView
        val b_likecnt : TextView = itemView.findViewById(R.id.userpage_feed_rv_item_bottom_bar_heart_num_btn) as TextView
        val b_commentcnt : TextView = itemView.findViewById(R.id.userpage_feed_rv_item_bottom_bar_chat_num_btn) as TextView
        val b_like_img : ImageView = itemView.findViewById(R.id.userpage_feed_and_scrap_rv_item_bottom_bar_heart_btn) as ImageView
        val b_comment_img : ImageView = itemView.findViewById(R.id.userpage_feed_and_scrap_rv_item_bottom_bar_chat_btn) as ImageView

        //공유하기 있을때
        val source_ll : LinearLayout = itemView.findViewById(R.id.userpage_feed_rv_item_shared_rl) as LinearLayout
        val b_u_img : ImageView = itemView.findViewById(R.id.userpage_feed_rv_item_shared_profile_image_btn) as ImageView
        val b_u_nickname : TextView = itemView.findViewById(R.id.userpage_feed_rv_item_shared_profile_name_tv) as TextView
        val b_b_time : TextView = itemView.findViewById(R.id.userpage_feed_rv_item_shared_date_tv) as TextView
        val b_b_content : TextView = itemView.findViewById(R.id.userpage_feed_rv_item_shared_contents_tv) as TextView
        val b_b_image : ImageView = itemView.findViewById(R.id.userpage_feed_rv_item_shared_contents_iv) as ImageView
    }

    fun requestLikeBoardToServer(position : Int){
        val postCommunityLikeRequset = networkService.postCommunityLike(
                SharedPreferenceController.getAuthorization(ctx), PostCommunityLikeRequset(dataList[position].b_id))
        postCommunityLikeRequset.enqueue(object : Callback, retrofit2.Callback<postCommunityLikeResponse> {
            override fun onFailure(call: Call<postCommunityLikeResponse>?, t: Throwable?) {
                Log.v("유저,마이 페이지 좋아요 성공", t.toString())
            }
            override fun onResponse(call: Call<postCommunityLikeResponse>?, response: Response<postCommunityLikeResponse>?) {
                Log.v("유저,마이 페이지 좋아요!!! ", "성공 전")
                if(response!!.isSuccessful){
                    Log.v("유저,마이 페이지 좋아요 성공", response!!.body()!!.message)
                }
            }

        })
    }
}