package sopt_jungnami_android.jungnami.contents

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Window
import kotlinx.android.synthetic.main.dialog_scrap_agree_popup.*
import kotlinx.android.synthetic.main.dialog_scrap_delete_popup.*
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sopt_jungnami_android.jungnami.Delete.DeleteContentsScrapResponse
import sopt_jungnami_android.jungnami.Network.ApplicationController
import sopt_jungnami_android.jungnami.Network.NetworkService
import sopt_jungnami_android.jungnami.Post.PostContentsScrapAgreeResponse
import sopt_jungnami_android.jungnami.R
import sopt_jungnami_android.jungnami.db.SharedPreferenceController

class ContentsDeleteScrapDialog(val ctx: Context, val contentsid: Int) : Dialog(ctx) {
    lateinit var networkService: NetworkService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_scrap_delete_popup)

        setClickListener()
    }

    private fun setClickListener() {
        scrap_delete_popup_frag_yes_btn.setOnClickListener {
            requestDeleteContentsToServer()
            (ctx as ContentsDetail).isScrapInPage = 0
            (ctx as ContentsDetail).changeIsScrapBtnView()
            (ctx).isChangeScapState = true
        }
        scrap_delete_popup_frag_no_btn.setOnClickListener {
            dismiss()
        }
    }

    private fun requestDeleteContentsToServer() {
        networkService = ApplicationController.instance.networkService
        val deleteContentsScrapResponse = networkService.deleteContentsScrapResponse(SharedPreferenceController.getAuthorization(ctx), contentsid)
        deleteContentsScrapResponse.enqueue(object : Callback<DeleteContentsScrapResponse> {
            override fun onFailure(call: Call<DeleteContentsScrapResponse>?, t: Throwable?) {
                Log.e("컨텐츠 스크랩 삭제 실패", t.toString())
            }

            override fun onResponse(call: Call<DeleteContentsScrapResponse>?, response: Response<DeleteContentsScrapResponse>?) {
                if (response!!.isSuccessful) {
                    ctx.toast("스크랩 삭제")
                    Log.e("컨텐츠 스크랩 삭제 성공", "성공")
                    dismiss()
                }
            }
        })
    }
}