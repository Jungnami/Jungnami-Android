package sopt_jungnami_android.jungnami

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.kakao.auth.ISessionCallback
import com.kakao.auth.KakaoSDK
import com.kakao.auth.Session
import com.kakao.util.exception.KakaoException
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.toast
import sopt_jungnami_android.jungnami.kakao.KakaoSDKAdapter
import android.content.pm.PackageManager
import android.content.pm.PackageInfo
import android.util.Base64
import com.google.firebase.iid.FirebaseInstanceId
import org.jetbrains.anko.startActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sopt_jungnami_android.jungnami.Network.ApplicationController
import sopt_jungnami_android.jungnami.Network.NetworkService
import sopt_jungnami_android.jungnami.Post.PostLoginResponse
import sopt_jungnami_android.jungnami.db.SharedPreferenceController
import java.security.MessageDigest


// modify by YunHwan
class Login : AppCompatActivity() {
    private lateinit var callback: SessionCallback

    private lateinit var networkService: NetworkService



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if (KakaoSDK.getAdapter() == null) {
            KakaoSDK.init(KakaoSDKAdapter(ctx = this))
        }

        callback = SessionCallback()
        Session.getCurrentSession().addCallback(callback)
        Session.getCurrentSession().checkAndImplicitOpen()
        setStatusBarColor()


        setClickListener()

        //Log.e("해시키!!!", getHashKey(context = applicationContext))
    }

    private fun setClickListener() {
        kakao_custom_login_btn.setOnClickListener {
            kakao_login_btn.performClick()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return
        }
        super.onActivityResult(requestCode, resultCode, data)
    }


    override fun onDestroy() {
        super.onDestroy()
        Session.getCurrentSession().removeCallback(callback)
    }


    inner class SessionCallback : ISessionCallback {
        override fun onSessionOpenFailed(exception: KakaoException?) {
            if (exception != null) {
                Log.e("카톡 로긴 콜백 실패", exception.toString())
            }
        }

        override fun onSessionOpened() {
            val fcmToken : String = FirebaseInstanceId.getInstance().token.toString()
            val accessToken : String = Session.getCurrentSession().tokenInfo.accessToken
            requestLoginToServer(accessToken, fcmToken)
        }
    }
    private fun requestLoginToServer(accessToken : String, fcmToken : String){
        networkService = ApplicationController.instance.networkService
        val authorization : String = SharedPreferenceController.getAuthorization(context = applicationContext)
        var postLoginResponse : Call<PostLoginResponse>
        if (authorization.length != 0){
            postLoginResponse  = networkService.postLoginResponse(authorization, accessToken, fcmToken)
        } else {
            postLoginResponse  = networkService.postLoginResponse(null, accessToken, fcmToken)
        }
        postLoginResponse.enqueue(object : Callback<PostLoginResponse>{
            override fun onFailure(call: Call<PostLoginResponse>?, t: Throwable?) {
                Log.e("로긴 통신 실패", t.toString())
            }
            override fun onResponse(call: Call<PostLoginResponse>?, response: Response<PostLoginResponse>?) {
                if (response!!.isSuccessful){

                    val id : String = response.body()!!.data.id
                    val auth: String= response.body()!!.data.token
                    SharedPreferenceController.setAuthorization(context = applicationContext, authorization = auth)
                    SharedPreferenceController.setMyId(context = applicationContext,id = id)

                    startActivity<MainActivity>()
                    finish()
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

    // 프로젝트의 해시키를 반환
//    fun getHashKey(context: Context): String? {
//        val TAG = "KeyHash"
//        var keyHash: String? = null
//        try {
//            val info = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES)
//            for (signature in info.signatures) {
//                val md: MessageDigest
//                md = MessageDigest.getInstance("SHA")
//                md.update(signature.toByteArray())
//                keyHash = String(Base64.encode(md.digest(), 0))
//                Log.d(TAG, keyHash)
//            }
//        } catch (e: Exception) {
//            Log.e("name not found", e.toString())
//        }
//        return if (keyHash != null) {
//            keyHash
//        } else {
//            null
//        }
//    }
}
