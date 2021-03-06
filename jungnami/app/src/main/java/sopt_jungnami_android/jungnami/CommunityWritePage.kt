package sopt_jungnami_android.jungnami

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.DrawableImageViewTarget
import kotlinx.android.synthetic.main.activity_community_write_page.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Response
import sopt_jungnami_android.jungnami.Get.GetCommunityPostingResponse
import sopt_jungnami_android.jungnami.Network.ApplicationController
import sopt_jungnami_android.jungnami.Network.NetworkService
import sopt_jungnami_android.jungnami.Post.PostCommunityPostingResponse
import sopt_jungnami_android.jungnami.db.SharedPreferenceController
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileNotFoundException
import java.io.InputStream

// Written by SooYoung

class CommunityWritePage : AppCompatActivity(), View.OnClickListener {

    val My_READ_STORAGE_REQUEST_CODE = 7777
    var isStateChange: Boolean = false

    lateinit var networkService: NetworkService
    var context: Context = this

    private val REQ_CODE_SELECT_IMAGE = 100
    lateinit var data: Uri
    private var image: MultipartBody.Part? = null

    var isText: Boolean = false
    var isIMG: Boolean = false
    var isGIF: Boolean = false


    var isShared: Int = 0

    override fun onClick(v: View?) {
        when (v) {
            community_act_writepage_back_btn -> {
                finish()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_community_write_page)
        networkService = ApplicationController.instance.networkService
        getCommunityPostingResponse()

        isShared = intent.getIntExtra("isShared", 0)
        setStatusBarColor()

        val editTextContent = findViewById<EditText>(R.id.community_act_writepage_posting_et)

        editTextContent.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun afterTextChanged(s: Editable?) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (editTextContent.text.toString().isNotBlank()) {
                    isText = true
                    checkUploadedContent()
                    if (editTextContent.text.toString().length >= 150) {
                        Toast.makeText(applicationContext, "글자 수가 150자를 초과하였습니다.", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    isText = false
                    checkUploadedContent()
                }
            }
        })

        community_act_writepage_complete_btn.setOnClickListener {
            if (community_act_writepage_complete_btn.isSelected) {
                postCommunityPostingResponse()
            }
        }

        // 사진 버튼
        community_act_writepage_upload_pic_btn.setOnClickListener {
            requestReadExternalStoragePermission(0)
        }

        // <!--움짤 관련 항목 - 기능 삭제-->
//        // GIF 버튼
//        community_act_writepage_upload_gif_btn.setOnClickListener {
//            requestReadExternalStoragePermission(1)
//        }
        community_act_writepage_upload_pic_iv.setOnClickListener {
            community_act_writepage_upload_pic_iv.setImageBitmap(null)
            isGIF = false
            isIMG = false
            checkUploadedContent()
        }

        // <!--움짤 관련 항목 - 기능 삭제-->
//        val handler = Handler()
//        handler.postDelayed({
//            community_act_writepage_gif_bubble_iv.setImageBitmap(null)
//        }, 2000)
    }

    fun getCommunityPostingResponse() {
        val getCommunityPostingResponse = networkService.getCommunityPostingResponse(SharedPreferenceController.getAuthorization(context = applicationContext))
        getCommunityPostingResponse.enqueue(object : retrofit2.Callback<GetCommunityPostingResponse> {
            override fun onFailure(call: Call<GetCommunityPostingResponse>?, t: Throwable?) {
                Log.v("TAGG", t.toString())
                toast("error!")
            }

            override fun onResponse(call: Call<GetCommunityPostingResponse>?, response: Response<GetCommunityPostingResponse>?) {
                if (response!!.isSuccessful) {
                    var imgurl = response!!.body()!!.data
                    Glide.with(context).load(imgurl).into(rv_item_community_profile_img_iv)

                }
            }
        })
    }

//    fun stringTrim(editText: EditText): String {
//        val content = community_act_writepage_posting_et.text.toString()
//        val complete = content.myTrim()
//        Log.v("통신", "포스팅")
//        return complete
//    }
//
//    fun String.myTrim() : String {
//        var result: String = " "
//
//        var startIdx = 0
//        while (startIdx < this.length && this[startIdx] == '"') startIdx ++
//
//        var endIdx = this.length - 1
//        while (endIdx >= 0 && this[endIdx] == '"') endIdx --
//
//        result = this.substring(startIdx, endIdx+1)
//        return result
//    }

    fun postCommunityPostingResponse() {
        var content: String? = null
        var contentBody : RequestBody
//        var content = RequestBody.create(MediaType.parse("text/plain"), input_title)
        if (isShared == 0) {
            Log.v("test1", community_act_writepage_posting_et.text.toString())
            if (community_act_writepage_posting_et.text.toString().isEmpty()) {
                Log.v("E", "null")
                content = null
                contentBody = RequestBody.create(MediaType.parse("text/plain"), " ")
            } else {
                content = community_act_writepage_posting_et.text.toString()
                contentBody = RequestBody.create(MediaType.parse("text/plain"), content)
                Log.v("test", content)
            }
            val postCommunityPostingResponse = networkService.postCommunityPostingResponse(SharedPreferenceController.getAuthorization(context = applicationContext), contentBody, image, isShared)
            postCommunityPostingResponse.enqueue(object : retrofit2.Callback<PostCommunityPostingResponse> {
                override fun onResponse(call: Call<PostCommunityPostingResponse>?, response: Response<PostCommunityPostingResponse>?) {
                    if (response!!.isSuccessful) {
                        isStateChange = true
                        val intent = Intent()
                        intent.putExtra("isComplete", true)
                        setResult(Activity.RESULT_OK, intent)
                        finish()
                    }
                }

                override fun onFailure(call: Call<PostCommunityPostingResponse>?, t: Throwable?) {
                    toast("Error!")
                }
            })
        } else {
            contentBody = RequestBody.create(MediaType.parse("text/plain"), " ")
            val postCommunityPostingResponse = networkService.postCommunityPostingResponse(SharedPreferenceController.getAuthorization(context = applicationContext), contentBody, null, isShared)
            postCommunityPostingResponse.enqueue(object : retrofit2.Callback<PostCommunityPostingResponse> {
                override fun onResponse(call: Call<PostCommunityPostingResponse>?, response: Response<PostCommunityPostingResponse>?) {
                    if (response!!.isSuccessful) {
                        finish()
                        Log.v("success", "타인이 쓴 글")
                    }
                }

                override fun onFailure(call: Call<PostCommunityPostingResponse>?, t: Throwable?) {
                    toast("Error!")
                }
            })
        }
    }

//    fun postCommunityPostingResponse() {
//        var content : String? = null
//        if (community_act_writepage_posting_et.text.toString().isEmpty()){
//            content = null
//        } else {
//            content = community_act_writepage_posting_et.text.toString()
//        }
//        val postCommunityPostingResponse = networkService.postCommunityPostingResponse(SharedPreferenceController.getAuthorization(context = applicationContext), content, image, isShared)
//        postCommunityPostingResponse.enqueue(object : retrofit2.Callback<PostCommunityPostingResponse>{
//            override fun onResponse(call: Call<PostCommunityPostingResponse>?, response: Response<PostCommunityPostingResponse>?) {
//                if(response!!.isSuccessful){
//                    if (isShared == 0){
//                        // refresh 기능 구현. intent 기능 윤환오빠와.
//                        finish()
//                        Log.v("success", "내가 쓴 글")
//                    }
//                    else {
//
//                        Log.v("success", "타인이 쓴 글")
//                    }
//                }
//            }
//            override fun onFailure(call: Call<PostCommunityPostingResponse>?, t: Throwable?) {
//                toast("Error!")
//            }
//        })
//    }

    private fun checkUploadedContent() {
        community_act_writepage_complete_btn.isSelected = isText || isIMG || isGIF
    }

    fun Invisible(v: View?) {
        v!!.visibility = View.INVISIBLE
    }

    fun Visible(v: View?) {
        v!!.visibility = View.VISIBLE
    }

    fun changeImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = android.provider.MediaStore.Images.Media.CONTENT_TYPE
        intent.data = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        startActivityForResult(intent, REQ_CODE_SELECT_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQ_CODE_SELECT_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    //if(ApplicationController.getInstance().is)
                    this.data = data!!.data
                    Log.v("이미지", this.data.toString())
                    val options = BitmapFactory.Options()

                    var input: InputStream? = null

                    try {
                        input = contentResolver.openInputStream(this.data)
                    } catch (e: FileNotFoundException) {
                        e.printStackTrace()
                    }

                    val bitmap = BitmapFactory.decodeStream(input, null, options) // InputStream 으로부터 Bitmap 생성
                    val baos = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos)
                    val photoBody = RequestBody.create(MediaType.parse("image/jpg"), baos.toByteArray())
                    val photo = File(this.data.toString()) // 가져온 파일의 이름

                    // RequestBody photoBody = RequestBody.create(MediaType.parse("image/jpg"), baos.toByteArray());

                    image = MultipartBody.Part.createFormData("image", photo.name, photoBody) // 실제 파일의 이름 전송

                    //body = MultipartBody.Part.createFormData("image", photo.getName(), profile_pic);

                    Glide.with(this).load(data.data).into(community_act_writepage_upload_pic_iv)
                    isIMG = true
                    checkUploadedContent()
                } catch (e: Exception) {
                    toast("잘못된 파일 형식입니다!")
                    isIMG = false
                    checkUploadedContent()
                    e.printStackTrace()
                }
            }
        }
    }

    override fun onBackPressed() {
        val intent = Intent()
        intent.putExtra("isStateChange", true)
        finish()
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

    /*
    Written by Tak
    저장소 권한 요청
    */
    private fun requestReadExternalStoragePermission(gifFlag: Int) {
        //첫번째 if문을 통해 과거에 이미 권한 메시지에 대한 OK를 했는지 아닌지에 대해 물어봅니다! if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)) { //사용자에게 권한을 왜 허용해야되는지에 메시지를 주기 위한 대한 로직을 추가하려면 이 블락에서 하면됩니다!! //하지만 우리는 그냥 비워놓습니다!! 딱히 줄말 없으면 비워놔도 무관해요!!! 굳이 뭐 안넣어도됩니다!
        } else {
            //아래 코드는 권한을 요청하는 메시지를 띄우는 기능을 합니다! 요청에 대한 결과는 callback으로 onRequestPermissionsResult 메소드에서 받습니다!!!
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), My_READ_STORAGE_REQUEST_CODE)
        }

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)) { //사용자에게 권한을 왜 허용해야되는지에 메시지를 주기 위한 대한 로직을 추가하려면 이 블락에서 하면됩니다!! //하지만 우리는 그냥 비워놓습니다!! 딱히 줄말 없으면 비워놔도 무관해요!!! 굳이 뭐 안넣어도됩니다!
            } else {
                //아래 코드는 권한을 요청하는 메시지를 띄우는 기능을 합니다! 요청에 대한 결과는 callback으로 onRequestPermissionsResult 메소드에서 받습니다!!!
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), My_READ_STORAGE_REQUEST_CODE)
            }
        } else {
            //첫번째 if 문의 else 로써, 기존에 이미 권한 메시지를 통해 권한을 허용했다면 아래와 같은 곧바로 앨범을 여는 메소드를 호출 해주면됩니다!!
            if (gifFlag == 1) {
                // <!--움짤 관련 항목 - 기능 삭제-->
//                changeGifFile()
            } else {
                changeImage()
            }
        }
    }

    // 움짤 관련 기능 - 기능 삭제
//    private fun changeGifFile() {
//        val imageviewGIF: ImageView = findViewById(R.id.community_act_writepage_upload_gif_iv)
//        val imageviewdummyGIF1: ImageView = findViewById(R.id.community_act_writepage_dummy_gif1)
//        val imageviewdummyGIF2: ImageView = findViewById(R.id.community_act_writepage_dummy_gif2)
//        val gifImage = DrawableImageViewTarget(imageviewGIF)
//        Visible(imageviewGIF)
//        Visible(imageviewdummyGIF1)
//        Visible(imageviewdummyGIF2)
//        Glide.with(this).load(R.drawable.dancing_citizen).into(gifImage)
//        community_act_writepage_upload_gif_iv.setOnClickListener {
//            Invisible(imageviewGIF)
//            Invisible(imageviewdummyGIF1)
//            Invisible(imageviewdummyGIF2)
//            Glide.with(this).load(R.drawable.dancing_citizen).into(community_act_writepage_upload_pic_iv)
//            isGIF = true
//            checkUploadedContent()
//        }
//        val options = BitmapFactory.Options()
//        var input: InputStream? = null
//        try {
//            input = contentResolver.openInputStream(this.data)
//        } catch (e: FileNotFoundException) {
//            e.printStackTrace()
//        }
//        val bitmap = BitmapFactory.decodeStream(input, null, options) // InputStream 으로부터 Bitmap 생성
//        val baos = ByteArrayOutputStream()
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos)
//        val photoBody = RequestBody.create(MediaType.parse("image/jpg"), baos.toByteArray())
//        val photo = File(this.data.toString()) // 가져온 파일의 이름
//        image = MultipartBody.Part.createFormData("image", photo.name, photoBody)
//    }

}