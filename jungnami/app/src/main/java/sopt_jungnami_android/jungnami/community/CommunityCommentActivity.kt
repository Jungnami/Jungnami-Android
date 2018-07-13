package sopt_jungnami_android.jungnami.community

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_contents_comment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sopt_jungnami_android.jungnami.Get.GetCommunityCommentResponse
import sopt_jungnami_android.jungnami.Network.ApplicationController
import sopt_jungnami_android.jungnami.Network.NetworkService
import sopt_jungnami_android.jungnami.Post.PostCommunityCommentRequest
import sopt_jungnami_android.jungnami.Post.postCommunityLikeResponse
import sopt_jungnami_android.jungnami.R
import sopt_jungnami_android.jungnami.data.CommunityCommentData
import sopt_jungnami_android.jungnami.db.SharedPreferenceController

class CommunityCommentActivity : AppCompatActivity() {

    lateinit var networkService: NetworkService
    lateinit var communityCommentItem : ArrayList<CommunityCommentData>
    var context : Context = this
    lateinit var communityCommentRecyclerViewAdapter: CommunityCommentRecyclerViewAdapter
    var board_id :Int =0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contents_comment)
        setStatusBarColor()

        board_id = intent.getIntExtra("board_id", 0)
        networkService = ApplicationController.instance.networkService
        getCommunityComment()
        setClickedListener()
    }

    private fun setClickedListener(){
        // back 버튼 눌렀을 때
        contents_comment_act_backarrow_btn.setOnClickListener {
            finish()
        }
        // 댓글 쓰기 버튼 눌렀을 때
        contents_comment_act_bottom_bar_write_btn.setOnClickListener {
            // Post 통신코드
            postCommunityComment()
            // Get 통신
            getCommunityComment()
        }
//        //마이페이지
//        community_frag_top_bar_my_page_btn.setOnClickListener {
//            startActivity<MyPageActivity>()
//        }
//        //내 피드 작성
//        community_frag_write_feed_btn.setOnClickListener {
//            startActivity<CommunityWritePage>("isShared" to 0)
//        }

    }

    fun postCommunityComment(){
        // # 보드아이디 받아와야한다.
        var content : String = contents_comment_act_bottom_bar_edit_text.text.toString()
        var postCommunityCommentRequest = PostCommunityCommentRequest(board_id, content)
        var postCommunityPostingResponse = networkService.postCommunityComment(SharedPreferenceController.getAuthorization(context),
                board_id,content)
        postCommunityPostingResponse.enqueue(object : Callback <postCommunityLikeResponse> {
            override fun onFailure(call: Call<postCommunityLikeResponse>?, t: Throwable?) {
            }
            override fun onResponse(call: Call<postCommunityLikeResponse>?, response: Response<postCommunityLikeResponse>?) {
                if(response!!.isSuccessful){
                    Log.v("success", "내가 쓴 글")
                    contents_comment_act_bottom_bar_edit_text.setText("")
                }
            }

        })

    }





    fun getCommunityComment(){
        var board_id = 103
        val getCommunityResponse = networkService.getCommunityComment(SharedPreferenceController.getAuthorization(context),board_id!!)
        getCommunityResponse.enqueue(object : Callback<GetCommunityCommentResponse>{
            override fun onFailure(call: Call<GetCommunityCommentResponse>?, t: Throwable?) {
                Log.v("ㅌㅅ","이거들어오면getCommunityComment실패")
            }

            override fun onResponse(call: Call<GetCommunityCommentResponse>?, response: Response<GetCommunityCommentResponse>?) {
                if (response!!.isSuccessful){
                    Log.v("ㅌㅅ","이거들어오면getCommunityComment성공")
                    communityCommentItem = response!!.body()!!.data as ArrayList<CommunityCommentData>
                    communityCommentRecyclerViewAdapter = CommunityCommentRecyclerViewAdapter(communityCommentItem, context,0)
                    contents_comment_act_rv.layoutManager = LinearLayoutManager(context)
                    contents_comment_act_rv.adapter = communityCommentRecyclerViewAdapter
                }
            }
        })
    }



    private fun setStatusBarColor(){
        val view : View? = window.decorView
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (view != null){
                view.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                window.statusBarColor = Color.parseColor("#FFFFFF")
            }
        }
    }
}
