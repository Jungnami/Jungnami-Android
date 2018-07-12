package sopt_jungnami_android.jungnami.coinpage

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Window
import kotlinx.android.synthetic.main.fragment_coinpage_popup_exchange.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sopt_jungnami_android.jungnami.Network.ApplicationController
import sopt_jungnami_android.jungnami.Network.NetworkService
import sopt_jungnami_android.jungnami.Post.PostCoinExchangeResponse
import sopt_jungnami_android.jungnami.R
import sopt_jungnami_android.jungnami.db.SharedPreferenceController

// made by YunHwan
class ExchangeCoinDialog(val ctx : Context, val coin : String) : Dialog(ctx) {

    lateinit var networkService : NetworkService
    var isAgree : Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.fragment_coinpage_popup_exchange)
        setView()
        setClickListener()
    }
    private fun setClickListener(){
        coinpage_exchange_popup_cancel_btn.setOnClickListener {
            dismiss()
        }
        coinpage_exchange_popup_check_btn.setOnClickListener {
            requestExchangeCoinToServer()
        }
    }

    private fun setView(){
        coinpage_frag_popup_exchange_coin_tv.text = "-${coin}코인"
        coinpage_frag_popup_exchange_vote_tv.text= "${coin}개"
    }

    private fun requestExchangeCoinToServer(){
        val total_coin : Int = coin.toInt()
        networkService = ApplicationController.instance.networkService
        val postCoinExchangeResponse = networkService.postCoinExchangeResponse(SharedPreferenceController.getAuthorization(context = context!!),total_coin)
        postCoinExchangeResponse.enqueue(object : Callback<PostCoinExchangeResponse>{
            override fun onFailure(call: Call<PostCoinExchangeResponse>?, t: Throwable?) {
            }

            override fun onResponse(call: Call<PostCoinExchangeResponse>?, response: Response<PostCoinExchangeResponse>?) {
                if (response!!.isSuccessful){
                    dismiss()
                    val completeDialog: Dialog = ExchangeCompleteCoinDialog(ctx!!)
                    completeDialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    completeDialog.show()
                }
            }
        })
    }


}
//
//override fun onFailure(call: Call<PostExchangeCoinResponse>?, t: Throwable?) {
//    Log.e("교환 실패", t.toString())
//}
//override fun onResponse(call: Call<PostExchangeCoinResponse>?, response: Response<PostExchangeCoinResponse>?) {
//    Log.e("성공 중?", response!!.body()!!.message)
//    if (response!!.isSuccessful){
//        Log.e("성공 중222222?", "성공?!?!?!")
//        dismiss()
//        val completeDialog : Dialog = ExchangeCompleteCoinDialog(ctx!!)
//        completeDialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        completeDialog.show()
//    }
//}