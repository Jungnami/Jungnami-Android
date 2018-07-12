package sopt_jungnami_android.jungnami.contents

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import sopt_jungnami_android.jungnami.R
import sopt_jungnami_android.jungnami.data.ContentsCardViewData

class ContentsDetailViewPagerAdapter(val ctx : Context, val cardContentsData : ContentsCardViewData) : PagerAdapter(){

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as View
    }

    override fun getCount(): Int = cardContentsData.imagearray.size

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val itemView : View = LayoutInflater.from(ctx).inflate(R.layout.view_pager_item_contents_detail, container, false)
        val imageView : ImageView = itemView.findViewById(R.id.contents_detail_viewpager_item_image_iv) as ImageView
        if (position == 0) {
            val firstPageInfo : LinearLayout = itemView.findViewById(R.id.contents_detail_viewpager_first_main_info_ll) as LinearLayout
            firstPageInfo.visibility = View.VISIBLE
            val firstPageTitle : TextView = itemView.findViewById(R.id.contents_detail_viewpager_first_main_title_tv) as TextView
            firstPageTitle.text = cardContentsData.subtitle
            val firstPageText : TextView = itemView.findViewById(R.id.contents_detail_viewpager_first_main_text_tv) as TextView
            firstPageText.text = cardContentsData.text
            val requestOptions = RequestOptions()
            requestOptions.fitCenter()
            Glide.with(ctx)
                    .setDefaultRequestOptions(requestOptions)
                    .load(cardContentsData.thumbnail)
                    .thumbnail(0.5f)
                    .into(imageView)
            container.addView(itemView)

//            Glide.with(ctx)
//                    .setDefaultRequestOptions(requestOptions)
//                    .load(cardContentsData.imagearray[position].imgURL)
//                    .thumbnail(0.5f)
//                    .into(imageView)

        } else {
            val requestOptions = RequestOptions()
            requestOptions.fitCenter()
            Glide.with(ctx)
                    .setDefaultRequestOptions(requestOptions)
                    .load(cardContentsData.imagearray[position].imgURL)
                    .thumbnail(0.5f)
                    .into(imageView)
        }

        container.addView(itemView)
        return itemView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.invalidate()
    }
}