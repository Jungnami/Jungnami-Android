package sopt_jungnami_android.jungnami.kakao

import com.kakao.auth.ISessionConfig
import com.kakao.auth.KakaoAdapter

//class KakaoSDKAdapterTest : KakaoAdapter() {
//    /**
//     * Session Config에 대해서는 default값들이 존재한다.
//     * 필요한 상황에서만 override해서 사용하면 됨.
//     * @return Session의 설정값.
//     */
//    val sessionConfig: ISessionConfig
//        get() = object : ISessionConfig() {
//            val authTypes: Array<AuthType>
//                get() = arrayOf<AuthType>(AuthType.KAKAO_LOGIN_ALL)
//
//            val isUsingWebviewTimer: Boolean
//                get() = false
//
//            val isSecureMode: Boolean
//                get() = false
//
//            val approvalType: ApprovalType
//                get() = ApprovalType.INDIVIDUAL
//
//            val isSaveFormData: Boolean
//                get() = true
//        }
//
//    val applicationConfig: IApplicationConfig
//        get() = object : IApplicationConfig() {
//            val applicationContext: Context
//                get() = GlobalApplication.getGlobalApplicationContext()
//        }
//}