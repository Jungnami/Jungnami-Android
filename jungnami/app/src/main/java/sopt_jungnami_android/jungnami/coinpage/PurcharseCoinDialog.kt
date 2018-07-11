package sopt_jungnami_android.jungnami.coinpage

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import kotlinx.android.synthetic.main.dialog_fragment_vote_agree.*
import kotlinx.android.synthetic.main.fragment_coinpage_popup_exchange.*
import kotlinx.android.synthetic.main.fragment_coinpage_popup_purchase.*
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sopt_jungnami_android.jungnami.Get.GetVotingResponse
import sopt_jungnami_android.jungnami.MainActivity
import sopt_jungnami_android.jungnami.Network.ApplicationController
import sopt_jungnami_android.jungnami.Network.NetworkService
import sopt_jungnami_android.jungnami.Post.PostCoinChargeCompletionResponse
import sopt_jungnami_android.jungnami.Post.PostCompletingVote
import sopt_jungnami_android.jungnami.R
import sopt_jungnami_android.jungnami.data.VotingCountData
import sopt_jungnami_android.jungnami.db.SharedPreferenceController

// made by YunHwan
class PurcharseCoinDialog(val ctx : Context, val won: String, val coin : String) : Dialog(ctx) {

    lateinit var networkService : NetworkService
    var isAgree : Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.fragment_coinpage_popup_purchase)
        setView()
        setClickListener()

    }
    private fun setClickListener(){
        coinpage_purchase_popup_cancel_btn.setOnClickListener {
            dismiss()
        }
        coinpage_purchase_popup_check_btn.setOnClickListener {
            if (isAgree) {
                requestCoinPurchaseToServer()
            } else {
                ctx.toast("약관에 동의해주세요.")
            }

        }
        coinpage_frag_popup_purchase_check_box_btn.setOnClickListener {
            if (isAgree){
                coinpage_frag_popup_purchase_check_btn.visibility = View.INVISIBLE
                isAgree = false
            } else {
                coinpage_frag_popup_purchase_check_btn.visibility = View.VISIBLE
                isAgree = true
            }

        }
    }

    private fun setView(){
        coinpage_frag_popup_purchase_money_tv.text = "${won}원"
        coinpage_frag_popup_purchase_coin_tv.text= "${coin}코인"
    }

    private fun requestCoinPurchaseToServer(){
        val total_coin : Int = coin.toInt()
        networkService = ApplicationController.instance.networkService
        val postCoinChargeCompletionResponse = networkService.postCoinChargeCompletionResponse(SharedPreferenceController.getAuthorization(context = context!!),total_coin)
        postCoinChargeCompletionResponse.enqueue(object : Callback<PostCoinChargeCompletionResponse>{
            override fun onFailure(call: Call<PostCoinChargeCompletionResponse>?, t: Throwable?) {
                Log.e("구매 통신 실패", t.toString())
            }
            override fun onResponse(call: Call<PostCoinChargeCompletionResponse>?, response: Response<PostCoinChargeCompletionResponse>?) {
                if (response!!.isSuccessful){
                    dismiss()
                    val completeDialog : Dialog = PurcharseCompleteCoinDialog(ctx!!)
                    completeDialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    completeDialog.show()


                }
            }
        })
    }


}