package sopt_jungnami_android.jungnami.rank

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import kotlinx.android.synthetic.main.dialog_fragment_vote_agree.*
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sopt_jungnami_android.jungnami.Get.GetVotingResponse
import sopt_jungnami_android.jungnami.MainActivity
import sopt_jungnami_android.jungnami.Network.ApplicationController
import sopt_jungnami_android.jungnami.Network.NetworkService
import sopt_jungnami_android.jungnami.Post.PostCompletingVote
import sopt_jungnami_android.jungnami.R
import sopt_jungnami_android.jungnami.data.VotingCountData

class VoteAgreeDialog(val ctx : Context, val isLikeable: Int, val l_id : Int) : Dialog(ctx) {

    lateinit var networkService : NetworkService
    var myVotingCount : Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_fragment_vote_agree)
        requestDataToServer()

        vote_agree_dialog_yes_btn.setOnClickListener {
            requestCompletingVoteToServer()
        }
        vote_agree_dialog_no_btn.setOnClickListener {
            dismiss()
        }
    }
    private fun requestCompletingVoteToServer(){
        networkService = ApplicationController.instance.networkService
        val postCompletingVote = networkService.postCompletingVote(l_id, isLikeable)
        postCompletingVote.enqueue(object : Callback<PostCompletingVote>{
            override fun onFailure(call: Call<PostCompletingVote>?, t: Throwable?) {
                Log.e("failed", t.toString())
            }

            override fun onResponse(call: Call<PostCompletingVote>?, response: Response<PostCompletingVote>?) {
                if (response!!.isSuccessful){
                    if (isLikeable == 1){
                        (ctx as MainActivity).setAnimRankTabIcon(true)
                    } else {
                        (ctx as MainActivity).setAnimRankTabIcon(false)
                    }
                    Log.e("성공","성공!")
                    dismiss()
                } else {
                    ctx.toast("투표권을 충전해주세요!")
                }
            }
        })


    }

    private fun requestDataToServer(){
        networkService = ApplicationController.instance.networkService

        val getMyVotingResponse = networkService.getMyVotingCount()
        getMyVotingResponse.enqueue(object :Callback<GetVotingResponse>{
            override fun onFailure(call: Call<GetVotingResponse>?, t: Throwable?) {
                Log.e("실패 로그", t.toString())
            }

            override fun onResponse(call: Call<GetVotingResponse>?, response: Response<GetVotingResponse>?) {
                if (response!!.isSuccessful){
                    myVotingCount = response.body()!!.data.voting_cnt
                    vote_agree_dialog_myvote_count_tv.text = "나의 보유 투표권 : ${myVotingCount}개"
                }
            }
        })
    }
}