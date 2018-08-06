package sopt_jungnami_android.jungnami

import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService
import org.junit.Test

import org.junit.Assert.*
import android.widget.LinearLayout
import android.view.ViewGroup
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.view.LayoutInflater
import android.support.v4.view.PagerAdapter
import retrofit2.Call
import retrofit2.Response
import android.support.v4.widget.NestedScrollView




/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest : FirebaseInstanceIdService() {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)

        onTokenRefresh()

//        mNestedScrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
//            if (v.getChildAt(v.childCount - 1) != null) {
//                if (scrollY >= v.getChildAt(v.childCount - 1).measuredHeight - v.measuredHeight && scrollY > oldScrollY) {
//
//                    visibleItemCount = mLayoutManager.getChildCount()
//                    totalItemCount = mLayoutManager.getItemCount()
//                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition()
//
//                    if (isLoadData()) {
//
//                        if (visibleItemCount + pastVisiblesItems >= totalItemCount) {
//
//                            //                        Load Your Data
//                        }
//                    }
//                }
//            }
//        })
    }
    override fun onTokenRefresh() {
        val refreshedToken = FirebaseInstanceId.getInstance().token
        Log.e("refreshedToken토큰토큰", refreshedToken.toString())
        println("토큰 : " + refreshedToken)

    }

}



