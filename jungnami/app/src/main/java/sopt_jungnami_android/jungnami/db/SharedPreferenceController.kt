package sopt_jungnami_android.jungnami.db

import android.content.Context

object SharedPreferenceController {

    private val USER_NAME = "MYKEY"
    //private val authorization = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6ODA3NDY1MjM5LCJpYXQiOjE1MzA3NzU1MDQsImV4cCI6MTUzMzM2NzUwNH0.DAXcgbHm4gOaJMTFyQW0KCvs64lUZai6Cc_pi5pKu4Q"
    private val kakaoKey = "authorization"


    fun setAuthorization(context: Context, authorization : String){
        val pref = context.getSharedPreferences(USER_NAME, Context.MODE_PRIVATE) //현재 내 기기에서만 볼수 있는 데이터
        val editor = pref.edit()
        editor.putString(kakaoKey, authorization)
        editor.commit()
    }

    fun getAuthorization(context: Context) : String {
        val pref = context.getSharedPreferences(USER_NAME, Context.MODE_PRIVATE) //현재 내 기기에서만 볼수 있는 데이터
        return pref.getString(kakaoKey, "")
    }


    fun clearSPC(context: Context){
        val pref = context.getSharedPreferences(USER_NAME, Context.MODE_PRIVATE) //현재 내 기기에서만 볼수 있는 데이터
        val editor = pref.edit()
        editor.clear()
        editor.commit()
    }
}