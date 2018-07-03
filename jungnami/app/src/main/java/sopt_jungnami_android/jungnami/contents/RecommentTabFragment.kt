package sopt_jungnami_android.jungnami.contents

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import sopt_jungnami_android.jungnami.R

class RecommentTabFragment : Fragment() {
    //sub contents 중간 간격 12로 만드려면, 리사이클러뷰의 레이아웃을 그리드로 준다음 2개로 제한 후
    //홀수번째 content면 오른쪽마진 6, 짝수번째 content면 왼쪽 마진 6으로 주기!!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_recomment_tab, container, false)
    }


}
