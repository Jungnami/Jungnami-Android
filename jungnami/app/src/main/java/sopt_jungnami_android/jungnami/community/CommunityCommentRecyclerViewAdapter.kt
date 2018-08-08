package sopt_jungnami_android.jungnami.community

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sopt_jungnami_android.jungnami.Delete.DeleteContentsLikeResponse
import sopt_jungnami_android.jungnami.Network.ApplicationController
import sopt_jungnami_android.jungnami.Network.NetworkService
import sopt_jungnami_android.jungnami.Post.PostCommunityCommentLikeRequset
import sopt_jungnami_android.jungnami.Post.postCommunityLikeResponse
import sopt_jungnami_android.jungnami.R
import sopt_jungnami_android.jungnami.data.CommunityCommentData
import sopt_jungnami_android.jungnami.db.SharedPreferenceController
import sopt_jungnami_android.jungnami.mypage.UserPageActivity

class CommunityCommentRecyclerViewAdapter(private var dataItems: ArrayList<CommunityCommentData>, private val context: Context, private val flag : Int) :RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    lateinit var networkService: NetworkService

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val mainView : View = LayoutInflater.from(parent!!.context).inflate(R.layout.rv_item_contents_comment, parent, false)

        return ViewHolder(mainView)
    }

    override fun getItemCount(): Int {
        return dataItems.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.e("포지션 ${position} : ", "like는 ${dataItems[position].islike}")
        val viewHolder : ViewHolder = holder as ViewHolder

        viewHolder.comment_profile_img_btn.setOnClickListener {
            // 프로필 상세보기로 넘어가기
            context.startActivity<UserPageActivity>("mypage_id" to dataItems[position].user_id)
        }

        //초기 좋아요 셋팅
        changeLikeBtnView(position, holder.comment_like_img_btn)

        when (position){
            0,1,2 -> viewHolder.comment_bestbox.visibility = View.VISIBLE
        }

        // 좋아요 눌렀을 때
        viewHolder.comment_like_img_btn.setOnClickListener {
            if (flag ==0){
                if (dataItems[position].islike == 0){
                    postCommunityCommentLike(position, holder.comment_like_img_btn)
                } else {
                    deleteCummunityCommendLike(position, holder.comment_like_img_btn)
                }
            } else if(flag==1){
                if(dataItems[position].islike == 0){
                    postContentsCommentLike(position, holder.comment_like_img_btn)
                } else {
                    deleteContentsCommendLike(position, holder.comment_like_img_btn)
                }
            }
        }

        // 이미지 버튼 담기
        Glide.with(context)
                .load(dataItems!![position].user_img)
                .into(viewHolder.comment_profile_img_btn)

        viewHolder.comment_profile_name_btn.setText(dataItems!![position].user_nick)
        viewHolder.comment_descrption.text = dataItems!![position].content
        viewHolder.comment_date.text = dataItems!![position].timeset
        viewHolder.comment_like_num.text = dataItems!![position].commentlikeCnt.toString()
    }

    private fun changeLikeBtnView(position : Int, view : ImageView){
        if (dataItems[position].islike == 0){
            view.setImageResource(R.drawable.community_heart_gray)
        } else {
            view.setImageResource(R.drawable.community_heart_blue)
        }

    }

    fun postCommunityCommentLike(position: Int, view : ImageView) {
        networkService = ApplicationController.instance.networkService
        var postCommunityLikePostResponse = networkService.postCommunityCommentLike(SharedPreferenceController.getAuthorization(context),
                dataItems[position].commentid)
        postCommunityLikePostResponse.enqueue(object : Callback<postCommunityLikeResponse>{
            override fun onFailure(call: Call<postCommunityLikeResponse>?, t: Throwable?) {
            }
            override fun onResponse(call: Call<postCommunityLikeResponse>?, response: Response<postCommunityLikeResponse>?) {
                if(response!!.isSuccessful){
                    context.toast("좋아요")
                    dataItems[position].islike = 1
                    Log.e("포지션 ${position}의 like가 ", "${ dataItems[position].islike}로 변경됨")
                    view.setImageResource(R.drawable.community_heart_blue)
                }
            }
        })

    }

    fun postContentsCommentLike(position : Int, view : ImageView){
        networkService = ApplicationController.instance.networkService
        var postContentsCommentLikeResponse = networkService.postContentsCommentLike(SharedPreferenceController.getAuthorization(context),
                dataItems[position].commentid)
        postContentsCommentLikeResponse.enqueue(object : Callback<postCommunityLikeResponse>{
            override fun onFailure(call: Call<postCommunityLikeResponse>?, t: Throwable?) {
            }

            override fun onResponse(call: Call<postCommunityLikeResponse>?, response: Response<postCommunityLikeResponse>?) {
                if(response!!.isSuccessful){
                    context.toast("좋아요")
                    dataItems[position].islike = 1
                    Log.e("포지션 ${position}의 like가 ", "${ dataItems[position].islike}로 변경됨")
                    view.setImageResource(R.drawable.community_heart_blue)
                }
            }

        })
    }

    fun deleteContentsCommendLike(position : Int, view : ImageView){
        networkService = ApplicationController.instance.networkService
        var deleteContentsCommendLikeResponse = networkService.deleteContentsCommendLikeResponse(SharedPreferenceController.getAuthorization(context),
                dataItems[position].commentid)
        deleteContentsCommendLikeResponse.enqueue(object : Callback<DeleteContentsLikeResponse>{
            override fun onFailure(call: Call<DeleteContentsLikeResponse>?, t: Throwable?) {
            }
            override fun onResponse(call: Call<DeleteContentsLikeResponse>?, response: Response<DeleteContentsLikeResponse>?) {
                if (response!!.isSuccessful){
                    context.toast("좋아요 취소")
                    dataItems[position].islike = 0
                    Log.e("포지션 ${position}의 메시지 ", "${response.body()!!.message}")
                    Log.e("포지션 ${position}의 like가 ", "${ dataItems[position].islike}로 변경됨")
                    view.setImageResource(R.drawable.community_heart_gray)

                }
            }
        })
    }
    fun deleteCummunityCommendLike(position : Int, view : ImageView){
        networkService = ApplicationController.instance.networkService
        var deleteCummunityCommendLikeResponse = networkService.deleteCummunityCommendLikeResponse(SharedPreferenceController.getAuthorization(context),
                dataItems[position].commentid)
        deleteCummunityCommendLikeResponse.enqueue(object : Callback<DeleteContentsLikeResponse>{
            override fun onFailure(call: Call<DeleteContentsLikeResponse>?, t: Throwable?) {
            }
            override fun onResponse(call: Call<DeleteContentsLikeResponse>?, response: Response<DeleteContentsLikeResponse>?) {
                if (response!!.isSuccessful){
                    context.toast("좋아요 취소")

                    dataItems[position].islike = 0
                    Log.e("포지션 ${position}의 메시지 ", "${response.body()!!.message}")
                    Log.e("포지션 ${position}의 like가 ", "${ dataItems[position].islike}로 변경됨")
                    view.setImageResource(R.drawable.community_heart_gray)
                }
            }
        })
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var comment_profile_img_btn : ImageView = itemView!!.findViewById(R.id.rv_item_community_contents_comment_profile_btn) as ImageView
        var comment_like_img_btn : ImageView = itemView!!.findViewById(R.id.rv_item_community_contents_comment_likes_btn) as ImageView
        var comment_profile_name_btn : TextView = itemView!!.findViewById(R.id.rv_item_community_contents_comment_name_btn) as TextView
        var comment_descrption : TextView = itemView!!.findViewById(R.id.rv_item_community_contents_comment_description) as TextView
        var comment_date : TextView = itemView!!.findViewById(R.id.rv_item_community_contents_comment_date_tv) as TextView
        var comment_like_num : TextView = itemView!!.findViewById(R.id.rv_item_community_contents_comment_likes_num_tv) as TextView
        var comment_bestbox : RelativeLayout = itemView!!.findViewById(R.id.rv_item_contents_comment_best_rl) as RelativeLayout
    }
}