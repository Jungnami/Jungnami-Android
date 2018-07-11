package sopt_jungnami_android.jungnami.kakao

import android.content.Context
import com.kakao.auth.IApplicationConfig
import com.kakao.auth.KakaoAdapter
import com.kakao.auth.ApprovalType
import com.kakao.auth.AuthType
import com.kakao.auth.ISessionConfig



class KakaoSDKAdapter(val ctx : Context) : KakaoAdapter(){
    override fun getApplicationConfig(): IApplicationConfig {
        return IApplicationConfig { ctx.applicationContext }
    }

    override fun getSessionConfig(): ISessionConfig {
        return object : ISessionConfig {
            override fun getAuthTypes(): Array<AuthType> {
                return arrayOf(AuthType.KAKAO_LOGIN_ALL)
            }

            override fun isUsingWebviewTimer(): Boolean {
                return false
            }

            override fun isSecureMode(): Boolean {
                return false
            }

            override fun getApprovalType(): ApprovalType {
                return ApprovalType.INDIVIDUAL
            }

            override fun isSaveFormData(): Boolean {
                return true
            }
        }
    }
}

//인스턴스 생성할때 사용하기.
//@Override
//public void onCreate() {
//    super.onCreate();
//
//    KakaoSDK.init(new KakaoSDKAdapter());
//
//    ...
//}