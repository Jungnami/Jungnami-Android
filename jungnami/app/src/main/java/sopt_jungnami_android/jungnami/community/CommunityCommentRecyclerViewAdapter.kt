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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sopt_jungnami_android.jungnami.Network.ApplicationController
import sopt_jungnami_android.jungnami.Network.NetworkService
import sopt_jungnami_android.jungnami.Post.PostCommunityCommentLikeRequset
import sopt_jungnami_android.jungnami.Post.postCommunityLikeResponse
import sopt_jungnami_android.jungnami.R
import sopt_jungnami_android.jungnami.data.CommunityCommentData
import sopt_jungnami_android.jungnami.mypage.UserPageActivity

class CommunityCommentRecyclerViewAdapter(private val dataItems: ArrayList<CommunityCommentData>, private val context: Context) :RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    lateinit var networkService: NetworkService

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val mainView : View = LayoutInflater.from(parent!!.context).inflate(R.layout.rv_item_contents_comment, parent, false)
        return ViewHolder(mainView)
    }

    override fun getItemCount(): Int {
        return dataItems.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {



        val viewHolder : ViewHolder = holder as ViewHolder

        viewHolder.comment_profile_img_btn.setOnClickListener {

            // 프로필 상세보기로 넘어가기
            context.startActivity<UserPageActivity>("mypage_id" to dataItems[position].user_id)



        }

        // 좋아요 눌렀을 때
        viewHolder.comment_like_img_btn.setOnClickListener {
            // 좋아요 수 늘리기 통신
            postCommunityCommentLike(position)
            // 좋아요 수 + 1 해서 값 뷰에 적용
            viewHolder.comment_like_img_btn.setImageResource(R.drawable.community_heart_blue)
            var temp = dataItems!![position].commentlikeCnt + 1
            viewHolder.comment_like_num.text = temp.toString()
            // #좋아요 버튼 색 푸른색 == > 회색, (취소) 회색 ==> 푸른색
            viewHolder.comment_like_img_btn.setImageResource(R.drawable.community_heart_blue)

        }

        // 만약 제일 첫 아이템에만 BEST 박스 적용
        if(position == 0){
            viewHolder.comment_bestbox.visibility = View.VISIBLE
        }else{
            viewHolder.comment_bestbox.visibility = View.GONE
        }

        // 이미지 버튼 담기
        Glide.with(context)
                .load(dataItems!![position].user_img)
                .into(viewHolder.comment_profile_img_btn)

        // isLike 변수에 따라 Like 버튼 푸른색으로 적용
        if (dataItems[position].islike == 0){
            viewHolder.comment_like_img_btn.setImageResource(R.drawable.community_heart_blue)
        }else{
            viewHolder.comment_like_img_btn.setImageResource(R.drawable.community_heart_gray)
        }
        Log.v("ㅌㅅ", dataItems!![position].user_nick)
        viewHolder.comment_profile_name_btn.setText(dataItems!![position].user_nick)
        viewHolder.comment_descrption.text = dataItems!![position].content
        viewHolder.comment_date.text = dataItems!![position].timeset
        viewHolder.comment_like_num.text = dataItems!![position].commentlikeCnt.toString()

    }

    fun postCommunityCommentLike(position: Int) {
        networkService = ApplicationController.instance.networkService
        var comment_id : Int = dataItems!![position].commentid
        var postCommunityCommentLikeRequset = PostCommunityCommentLikeRequset(comment_id)
        var postCommunityLikePostResponse = networkService.postCommunityCommentLike(postCommunityCommentLikeRequset)
        postCommunityLikePostResponse.enqueue(object : Callback<postCommunityLikeResponse>{
            override fun onFailure(call: Call<postCommunityLikeResponse>?, t: Throwable?) {

            }

            override fun onResponse(call: Call<postCommunityLikeResponse>?, response: Response<postCommunityLikeResponse>?) {
                if(response!!.isSuccessful){
                    Log.v("success", "좋아요성공")
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