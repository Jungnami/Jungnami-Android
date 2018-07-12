package sopt_jungnami_android.jungnami.community

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Window
import kotlinx.android.synthetic.main.dialog_scrap_agree_popup.*
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sopt_jungnami_android.jungnami.Network.ApplicationController
import sopt_jungnami_android.jungnami.Network.NetworkService
import sopt_jungnami_android.jungnami.Post.PostContentsScrapAgreeResponse
import sopt_jungnami_android.jungnami.R
import sopt_jungnami_android.jungnami.db.SharedPreferenceController

class CommunityScrapDialog(val ctx: Context, val contentsid: Int) : Dialog(ctx) {
    lateinit var networkService: NetworkService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_scrap_agree_popup)

        setClickListener()
    }

    private fun setClickListener() {
        scrap_popup_frag_yes_btn.setOnClickListener {
            requestScrapContentsToServer()
            ctx.toast("스크랩 완료")
        }
        scrap_popup_frag_no_btn.setOnClickListener {
            dismiss()
        }
    }

    private fun requestScrapContentsToServer() {
        networkService = ApplicationController.instance.networkService
        val postContentsScrapAgreeResponse = networkService.postContentsScrapAgreeResponse(SharedPreferenceController.getAuthorization(ctx), contentsid)
        postContentsScrapAgreeResponse.enqueue(object : Callback<PostContentsScrapAgreeResponse> {
            override fun onFailure(call: Call<PostContentsScrapAgreeResponse>?, t: Throwable?) {
                Log.e("컨텐츠 스크랩 실패", t.toString())
            }

            override fun onResponse(call: Call<PostContentsScrapAgreeResponse>?, response: Response<PostContentsScrapAgreeResponse>?) {
                if (response!!.isSuccessful) {
                    Log.e("컨텐츠 스크랩 성공", "성공")
                    dismiss()
                }
            }
        })
    }
}