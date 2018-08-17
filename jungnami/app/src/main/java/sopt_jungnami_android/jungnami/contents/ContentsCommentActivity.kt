package sopt_jungnami_android.jungnami.contents

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_contents_comment.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.toast
import org.jetbrains.anko.yesButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sopt_jungnami_android.jungnami.Delete.DeleteContentsCommendResponse
import sopt_jungnami_android.jungnami.Get.GetContentsCommentResponse
import sopt_jungnami_android.jungnami.Network.ApplicationController
import sopt_jungnami_android.jungnami.Network.NetworkService
import sopt_jungnami_android.jungnami.Post.PostContentsCommentRequest
import sopt_jungnami_android.jungnami.Post.postCommunityLikeResponse
import sopt_jungnami_android.jungnami.R
import sopt_jungnami_android.jungnami.community.CommunityCommentRecyclerViewAdapter
import sopt_jungnami_android.jungnami.data.CommunityCommentData
import sopt_jungnami_android.jungnami.db.SharedPreferenceController

class ContentsCommentActivity : AppCompatActivity(), View.OnLongClickListener {

    override fun onLongClick(v: View?): Boolean {
        val position : Int = contents_comment_act_rv.getChildAdapterPosition(v)
        alert ("댓글을 삭제하시겠습니까?"){
            yesButton {
                deleteContentsCommendRequest(position)
            }
            noButton {
                toast("취소")
            }
        }.show()
        return true
    }

    lateinit var networkService: NetworkService
    lateinit var contentsCommentItem : ArrayList<CommunityCommentData>
    var context : Context = this
    lateinit var contentsCommentRecyclerViewAdapter: CommunityCommentRecyclerViewAdapter
    var contents_id = 0

    private fun setClickedListener(){
        contents_comment_act_backarrow_btn.setOnClickListener {
            finish()
        }

        contents_comment_act_bottom_bar_write_btn.setOnClickListener {
            // 댓글 쓰기
            postContentsComment()
            // 값 다시 받아오기
            getContentsComment()
            // # Get 통신으로 다시 데이터 다시 받고 보여주기.
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contents_comment)
        setStatusBarColor()
        contents_id = intent.getIntExtra("contents_id", 0)
        getContentsComment()
        setClickedListener()
    }
    fun postContentsComment(){
        // # 콘텐츠 아이디 받아야한다.
        var content : String = contents_comment_act_bottom_bar_edit_text.text.toString()
        networkService = ApplicationController.instance.networkService
        var postContentsCommentResponse = networkService.postContentsComment(SharedPreferenceController.getAuthorization(context),
                contents_id, content)
        postContentsCommentResponse.enqueue(object : Callback<postCommunityLikeResponse>{
            override fun onFailure(call: Call<postCommunityLikeResponse>?, t: Throwable?) {
            }
            override fun onResponse(call: Call<postCommunityLikeResponse>?, response: Response<postCommunityLikeResponse>?) {
                if(response!!.isSuccessful){
                    contents_comment_act_bottom_bar_edit_text.setText("")
                }
            }

        })
    }

    fun deleteContentsCommendRequest(position : Int){
        networkService = ApplicationController.instance.networkService
        var deleteContentsCommend = networkService.deleteContentsCommendResponse(SharedPreferenceController.getAuthorization(context),
                contentsCommentItem[position].commentid)
        deleteContentsCommend.enqueue(object : Callback<DeleteContentsCommendResponse>{
            override fun onFailure(call: Call<DeleteContentsCommendResponse>?, t: Throwable?) {
            }
            override fun onResponse(call: Call<DeleteContentsCommendResponse>?, response: Response<DeleteContentsCommendResponse>?) {
                //댓글 갱신
                if (response!!.isSuccessful){
                    toast("댓글 삭제")
                    getContentsComment()
                }
            }
        })
    }


    fun getContentsComment(){
        //# 콘텐츠 아이디 받아와야한다.
        networkService = ApplicationController.instance.networkService
        val getContentsCommentResponse = networkService.getContentsComment(SharedPreferenceController.getAuthorization(context),contents_id)
        getContentsCommentResponse.enqueue(object : Callback<GetContentsCommentResponse>{
            override fun onFailure(call: Call<GetContentsCommentResponse>?, t: Throwable?) {
            }

            override fun onResponse(call: Call<GetContentsCommentResponse>?, response: Response<GetContentsCommentResponse>?) {
                if (response!!.isSuccessful){
                    contentsCommentItem = response!!.body()!!.data
                    contentsCommentRecyclerViewAdapter = CommunityCommentRecyclerViewAdapter(contentsCommentItem, context,1)
                    contentsCommentRecyclerViewAdapter.setOnItemLongClick(this@ContentsCommentActivity)
                    contents_comment_act_rv.layoutManager = LinearLayoutManager(context)
                    contents_comment_act_rv.adapter = contentsCommentRecyclerViewAdapter
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