package sopt_jungnami_android.jungnami

import android.os.Bundle
import android.os.Handler
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import com.bumptech.glide.Glide
import org.jetbrains.anko.startActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        val splashimg: ImageView? = findViewById(R.id.gif_img)
        if (splashimg != null) {
            Glide.with(this).load(R.drawable.asplash).into(splashimg)
        }

        val handler = Handler()
        handler.postDelayed(Runnable {
            startActivity<Login>()
        }, 4000)


    }

}