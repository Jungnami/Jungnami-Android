package sopt_jungnami_android.jungnami.community

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_contents_comment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sopt_jungnami_android.jungnami.Get.GetCommunityCommentResponse
import sopt_jungnami_android.jungnami.Network.ApplicationController
import sopt_jungnami_android.jungnami.Network.NetworkService
import sopt_jungnami_android.jungnami.Post.postCommunityLikeResponse
import sopt_jungnami_android.jungnami.R
import sopt_jungnami_android.jungnami.data.CommunityCommentData
import sopt_jungnami_android.jungnami.db.SharedPreferenceController

class CommunityCommentActivity : AppCompatActivity() {
    var isStateChange: Boolean = false
    lateinit var networkService: NetworkService
    var communityCommentItem: ArrayList<CommunityCommentData>? = null
    var context: Context = this
    var communityCommentRecyclerViewAdapter: CommunityCommentRecyclerViewAdapter? = null
    var board_id: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contents_comment)
        setStatusBarColor()

        board_id = intent.getIntExtra("board_id", 0)

        networkService = ApplicationController.instance.networkService

//        communityCommentItem = ArrayList()
//        communityCommentRecyclerViewAdapter = CommunityCommentRecyclerViewAdapter(communityCommentItem!!,context,0)
        getCommunityComment(true)
        setClickedListener()
    }

    private fun setClickedListener() {
        // back 버튼 눌렀을 때
        contents_comment_act_backarrow_btn.setOnClickListener {
            finish()
        }
        // 댓글 쓰기 버튼 눌렀을 때
        contents_comment_act_bottom_bar_write_btn.setOnClickListener {
            // Post 통신코드
            postCommunityComment()
        }


    }

    fun postCommunityComment() {
        // # 보드아이디 받아와야한다.
        var content: String = contents_comment_act_bottom_bar_edit_text.text.toString()
        var postCommunityPostingResponse = networkService.postCommunityComment(SharedPreferenceController.getAuthorization(context),
                board_id, content)
        postCommunityPostingResponse.enqueue(object : Callback<postCommunityLikeResponse> {
            override fun onFailure(call: Call<postCommunityLikeResponse>?, t: Throwable?) {
            }

            override fun onResponse(call: Call<postCommunityLikeResponse>?, response: Response<postCommunityLikeResponse>?) {
                if (response!!.isSuccessful) {
                    isStateChange = true
                    contents_comment_act_bottom_bar_edit_text.setText("")

                    getCommunityComment(false)//엔터 후 바로 재요청..
                }
            }
        })
    }


    fun getCommunityComment(isinit : Boolean) {
        val getCommunityResponse = networkService.getCommunityComment(SharedPreferenceController.getAuthorization(context), board_id!!)
        getCommunityResponse.enqueue(object : Callback<GetCommunityCommentResponse> {
            override fun onFailure(call: Call<GetCommunityCommentResponse>?, t: Throwable?) {
            }

            override fun onResponse(call: Call<GetCommunityCommentResponse>?, response: Response<GetCommunityCommentResponse>?) {
                if (response!!.isSuccessful) {
                    communityCommentItem = ArrayList()
                    communityCommentItem = response!!.body()!!.data
                    if (communityCommentItem != null){
                        communityCommentRecyclerViewAdapter = CommunityCommentRecyclerViewAdapter(communityCommentItem!!,context,0)
                        Log.e("들어ㅏ갓니? " , communityCommentItem.toString())
                        contents_comment_act_rv.layoutManager = LinearLayoutManager(context)
                        contents_comment_act_rv.adapter = communityCommentRecyclerViewAdapter
                    }

                }
            }
        })
    }


    private fun setStatusBarColor() {
        val view: View? = window.decorView
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (view != null) {
                view.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                window.statusBarColor = Color.parseColor("#FFFFFF")
            }
        }
    }

    override fun onDestroy() {
        val intent = Intent()
        intent.putExtra("isStateChange", isStateChange)
        setResult(RESULT_OK, intent)
        super.onDestroy()
    }

    override fun onBackPressed() {
        val intent = Intent()
        intent.putExtra("isStateChange", isStateChange)
        setResult(RESULT_OK, intent)
        finish()
    }
}
