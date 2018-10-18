package sopt_jungnami_android.jungnami.community

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Response
import sopt_jungnami_android.jungnami.Delete.DeleteCommunityLikeResponse
import sopt_jungnami_android.jungnami.Network.ApplicationController
import sopt_jungnami_android.jungnami.Network.NetworkService
import sopt_jungnami_android.jungnami.Post.PostCommunityLikeRequset
import sopt_jungnami_android.jungnami.Post.PostFeedPostingResponse
import sopt_jungnami_android.jungnami.Post.postCommunityLikeResponse
import sopt_jungnami_android.jungnami.R
import sopt_jungnami_android.jungnami.data.Content
import sopt_jungnami_android.jungnami.db.SharedPreferenceController
import sopt_jungnami_android.jungnami.mypage.UserPageActivity
import javax.security.auth.callback.Callback

//made by YunHwan
//modify by TakHyeongMin
class CommunityRecyclerViewAdapter(val ctx: Context, val dataList: ArrayList<Content>) : RecyclerView.Adapter<CommunityRecyclerViewAdapter.Holder>() {
    private lateinit var onItemClick: View.OnClickListener
    private lateinit var onItemLongClick: View.OnLongClickListener
    lateinit var networkService: NetworkService
    lateinit var postCommunityLike: PostCommunityLikeRequset


    fun setOnItemClickListener(l: View.OnClickListener) {
        onItemClick = l
    }

    fun setOnItemLongClickListener(l: View.OnLongClickListener) {
        onItemLongClick = l
    }

    fun addItems(dataList: ArrayList<Content>) {
        this.dataList.addAll(dataList)
        notifyDataSetChanged()
    }
    fun addNew(dataList: ArrayList<Content>) {
        this.dataList.addAll(dataList)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(ctx).inflate(R.layout.rv_item_feed, parent, false)
        networkService = ApplicationController.instance.networkService
        view.setOnClickListener(onItemClick)
        view.setOnLongClickListener(onItemLongClick)
        return Holder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        Glide.with(this!!.ctx!!)
                .load(dataList[position].userimg)
                .into(holder.profile_img_btn)
        holder.profile_img_btn.setOnClickListener {
            ctx.startActivity<UserPageActivity>("mypage_id" to dataList[position].user_id)
        }

        // Text없으면 GONE 처리
        if (dataList[position].content.isEmpty()) {
            holder.feed_description.visibility = View.GONE
        } else {
            holder.feed_description.setText(dataList[position].content)
            Log.v("완료", dataList[position].content)
        }
        // img없으면 GONE 처리
        if (dataList[position].img == "0") {
            holder.feed_image.visibility = View.GONE
        } else {
            val requestOptions = RequestOptions()
            requestOptions.fitCenter()
            Glide.with(this!!.ctx!!)
                    .setDefaultRequestOptions(requestOptions)
                    .load(dataList[position].img)
                    .into(holder.feed_image)
        }

        //초기 좋아요 판별
        changeLikeBtnView(position, holder.feed_likes_btn)

        holder.profile_name_btn.text = dataList[position].nickname
        holder.feed_date.text = dataList[position].writingtime

        holder.feed_likes_num_btn.text = dataList[position].likecnt.toString()
        holder.feed_chat_num_btn.text = dataList[position].commentcnt.toString()


        // 좋아요 버튼 눌렀을 때
        holder.feed_likes_btn.setOnClickListener {
            if (dataList[position].islike == 0) {
                requestLikeBoardToServer(position, holder.feed_likes_btn, holder.feed_likes_num_btn)
            } else {
                requestDeleteLikeBoardToServer(position, holder.feed_likes_btn, holder.feed_likes_num_btn)
            }
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
            requestCommunityPostingResponse(position, holder.feed_scrap_btn)
        }
    }

    fun changeLikeBtnView(position: Int, view: ImageView) {
        if (dataList[position].islike == 0) {
            view.setImageResource(R.drawable.community_heart_gray)
        } else {
            view.setImageResource(R.drawable.community_heart_blue)
        }
    }


    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val profile_img_btn: ImageView = itemView.findViewById(R.id.contents_feed_rv_item_shared_profile_image_btn) as ImageView
        val profile_name_btn: TextView = itemView.findViewById(R.id.contents_comment_rv_item_profile_name_tv) as TextView
        val feed_date: TextView = itemView.findViewById(R.id.contents_comment_rv_item_date) as TextView
        val feed_description: TextView = itemView.findViewById(R.id.contents_comment_rv_item_contents_tv) as TextView
        val feed_image: ImageView = itemView.findViewById(R.id.contents_comment_rv_item_contents_iv) as ImageView
        val feed_likes_num_btn: TextView = itemView.findViewById(R.id.contents_comment_rv_item_heart_num_btn) as TextView
        val feed_chat_num_btn: TextView = itemView.findViewById(R.id.contents_comment_rv_item_chat_num_des_btn) as TextView

        val feed_likes_btn: ImageView = itemView.findViewById(R.id.contents_comment_tv_item_bottom_bar_heart_btn) as ImageView
        val feed_chats_btn: ImageView = itemView.findViewById(R.id.contents_comment_tv_item_bottom_bar_chat_btn) as ImageView
        val feed_share_btn: ImageView = itemView.findViewById(R.id.contents_comment_tv_item_bottom_bar_share_btn) as ImageView
        val feed_scrap_btn: ImageView = itemView.findViewById(R.id.contents_comment_tv_item_bottom_bar_scrap_btn) as ImageView
    }

    fun requestCommunityPostingResponse(position: Int, view: ImageView) {
        val networkService = ApplicationController.instance.networkService
        val postFeedPostingResponse = networkService.postFeedPostingResponse(SharedPreferenceController.getAuthorization(ctx),
                "", null, dataList[position].boardid)
        postFeedPostingResponse.enqueue(object : retrofit2.Callback<PostFeedPostingResponse> {
            override fun onFailure(call: Call<PostFeedPostingResponse>?, t: Throwable?) {
            }

            override fun onResponse(call: Call<PostFeedPostingResponse>?, response: Response<PostFeedPostingResponse>?) {
                if (response!!.isSuccessful) {
                    ctx.toast("스크랩 완료")
                }
            }
        })
    }

    fun requestDeleteLikeBoardToServer(position: Int, view: ImageView, textView: TextView) {
        val deleteCommunityLikeResponse = networkService.deleteCommunityLikeResponse(
                SharedPreferenceController.getAuthorization(ctx), dataList[position].boardid)
        deleteCommunityLikeResponse.enqueue(object : retrofit2.Callback<DeleteCommunityLikeResponse> {
            override fun onFailure(call: Call<DeleteCommunityLikeResponse>?, t: Throwable?) {
            }

            override fun onResponse(call: Call<DeleteCommunityLikeResponse>?, response: Response<DeleteCommunityLikeResponse>?) {
                if (response!!.isSuccessful) {
                    ctx.toast("좋아요 취소")
                    dataList[position].islike = 0
                    dataList[position].likecnt -= 1

                    textView.text = dataList[position].likecnt.toString()
                    view.setImageResource(R.drawable.community_heart_gray)
                }
            }
        })
    }

    fun requestLikeBoardToServer(position: Int, view: ImageView, textView: TextView) {
        val postCommunityLikeRequset = networkService.postCommunityLike(
                SharedPreferenceController.getAuthorization(ctx), PostCommunityLikeRequset(dataList[position].boardid))
        postCommunityLikeRequset.enqueue(object : Callback, retrofit2.Callback<postCommunityLikeResponse> {
            override fun onFailure(call: Call<postCommunityLikeResponse>?, t: Throwable?) {
            }

            override fun onResponse(call: Call<postCommunityLikeResponse>?, response: Response<postCommunityLikeResponse>?) {
                if (response!!.isSuccessful) {
                    ctx.toast("좋아요")
                    dataList[position].islike = 1
                    dataList[position].likecnt += 1

                    textView.text = dataList[position].likecnt.toString()
                    view.setImageResource(R.drawable.community_heart_blue)
                }
            }
        })
    }


}