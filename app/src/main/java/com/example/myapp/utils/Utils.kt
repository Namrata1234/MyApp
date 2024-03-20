package com.example.myapp.utils


import android.annotation.SuppressLint
import android.app.*
import android.content.*
import android.content.pm.*
import android.graphics.PorterDuff
import android.net.*
import android.os.*
import android.util.*
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.webkit.*
import android.widget.*
import androidx.activity.*
import androidx.appcompat.app.*
import androidx.appcompat.widget.*
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.lifecycle.*
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.*
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.myapp.App
import com.example.myapp.App.Companion.gson
import com.example.myapp.R
import com.example.myapp.api.ApiClient
import com.example.myapp.api.ApiResponse
import com.google.gson.reflect.TypeToken
import java.io.*
import java.util.*
import java.util.regex.*


//FOR SHARED PREFERENCES
fun Context?.prefManager(): Preference {
    return Preference(this)
}

//FOR TOAST
fun Context?.toast(message: String?) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

//START ACTIVITY
fun <T> Activity.navigateTo(mClass: Class<T>, bundle: (Bundle.() -> Unit) = {}) {
    val intent = Intent(this, mClass)
    intent.putExtras(Bundle().apply(bundle))
    startActivity(intent)
}

//START ACTIVITY
fun <T> Activity.finishAndNavigateTo(mClass: Class<T>, bundle: (Bundle.() -> Unit) = {}) {
    val intent = Intent(this, mClass)
    intent.putExtras(Bundle().apply(bundle))
    startActivity(intent)
    finish()
}

//TO MAKE VIEW VISIBILITY GONE
fun viewGone(view: View?) {
    view?.visibility = View.GONE
}

//TO MAKE VIEW VISIBILITY VISIBLE
fun viewVisible(view: View?) {
    view?.visibility = View.VISIBLE
}

//TO MAKE VIEW VISIBILITY INVISIBLE
fun viewInVisible(view: View?) {
    view?.visibility = View.INVISIBLE
}

//SET IMAGE USING GLIDE
fun setImage(imageView: ImageView?, url: String?) {
    if (imageView != null) {
        Glide.with(imageView.context).load(url).centerCrop().into(imageView)
    }
}

//SET IMAGE USING GLIDE
fun setImage(imageView: ImageView?, url: Int?) {
    if (imageView != null) {
       // Glide.with(imageView.context).load(url).centerCrop().placeholder(R.drawable.ic_placeholder).into(imageView)
    }
}

fun setCardImage(imageView: ImageView?, url: Int?) {
    if (imageView != null) {
       // Glide.with(imageView.context).load(url).placeholder(R.drawable.ic_credit_card).into(imageView)
    }
}

fun setImageWithoutScaleType(imageView: ImageView?, url: Int?) {
    if (imageView != null) {
       // Glide.with(imageView.context).load(url).placeholder(R.mipmap.ic_launcher).into(imageView)
    }
}

//SET IMAGE CIRCLE USING GLIDE
fun setImageCircle(imageView: ImageView?, url: String?) {
    if (imageView != null) {
       Glide.with(imageView.context).asBitmap().load(url).error(R.mipmap.ic_launcher).placeholder(R.mipmap.ic_launcher).diskCacheStrategy(
           DiskCacheStrategy.NONE).skipMemoryCache(true).circleCrop().into(imageView)
    }
}




//ON BACK PRESSED
fun AppCompatActivity.onBackPressedEvent() {
    onBackPressedDispatcher.onBackPressed()
}

fun AppCompatActivity.handleOnBackPressed(onBackPressed: () -> Unit = { finish() }) {
    onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            onBackPressed.invoke()
        }
    })
}

//SET TEXT
fun setText(textView: TextView, string: String?) {
    textView.setText(string)
}


//GET TEXT
fun getText(textView: TextView): String {
    return textView.text.trim().toString()
}

//GET TEXT
fun getText(editText: AppCompatEditText): String {
    return editText.text?.trim()?.toString() ?: ""
}

//EDIT TEXT TOUCH LISTENER
fun AppCompatEditText.setOnTouchListener() {
    this.setOnTouchListener { v, event ->
        this.onTouchEvent(event)
        this.setSelection(getText(this).length)
        true
    }
}

//IS VALID EMAIL
fun isValidEmail(editText: AppCompatEditText): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(getText(editText)).matches()
}

fun isValidWEBURL(editText: AppCompatEditText): Boolean {
    return Patterns.WEB_URL.matcher(getText(editText)).matches() && URLUtil.isValidUrl(getText(editText))
}

fun isValidWEBURL(url: String?): Boolean {
    return Patterns.WEB_URL.matcher(url).matches() && URLUtil.isValidUrl(url)
}

fun isValidWebUrl(editText: AppCompatEditText): Boolean {
    val regex = Pattern.compile("((?:http|https)://)?(?:www\\.)?[\\w\\d\\-_]+\\.\\w{2,3}(\\.\\w{2})?(/(?<=/)(?:[\\w\\d\\-./_]+)?)?")
    return regex.matcher(getText(editText)).matches()
}

//CURSOR TO END
fun AppCompatEditText.cursorToEnd() {
    this.requestFocus()
    this.setSelection(this.length())
}

//GET DATA FROM ARRAYLIST
fun <T> getData(data: Any?, aClass: TypeToken<ArrayList<T>>): ArrayList<T>? {
    return gson.fromJson(gson.toJson(data), aClass.type)
}

fun <T> getData(data: Any?, aClass: Class<T>): T? {
    data?.let {
        if (data is String) {
            return gson.fromJson(data, aClass)
            
        } else {
            return gson.fromJson(gson.toJson(data), aClass)
        }
    }
    
    return null
}

//RETROFIT-API REQUEST
fun <T> Context.request(classT: Class<T>): T {
    return ApiClient(this).retrofit.create(classT)
}


//IS INTERNET AVAILABLE
@SuppressLint("MissingPermission")
fun isNetworkAvailable(context: Context?): Boolean {
    val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    val nw = connectivityManager?.activeNetwork ?: return false
    val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
    
    return when {
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
        else -> false
    }
}




//SET EMPTY VIEW
fun setEmptyViewWithStaticMessage(isEmpty: Boolean, recyclerView: RecyclerView, llEmpty: LinearLayoutCompat, textView: AppCompatTextView, message: String) {
    if (isEmpty) {
        viewGone(recyclerView)
        viewVisible(llEmpty)
        setText(textView, message)
      //  hideLoader()
        
    } else {
        viewVisible(recyclerView)
        viewGone(llEmpty)
    }
}

//GET DATA
fun <T : Serializable?> getData(intent: Intent?, data: String, mClass: Class<T>): T? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getData(intent?.extras?.getSerializable(data, mClass), mClass)?.let { any ->
            any
        }
        
    } else {
        getData(intent?.extras?.getSerializable(data), mClass)?.let { any ->
            any
        }
    }
}

//GET DATA
fun getData(intent: Intent?, data: String): String? {
    return intent?.extras?.getString(data)
}


////SHOW LOADER
var dialog: Dialog? = null
fun Activity.showLoader(): Dialog? {
    dialog = Dialog(this, com.example.myapp.R.style.TransparentProgressDialog)
    dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog?.setContentView(com.example.myapp.R.layout.dialog_progressbar)
    val progressBar: ProgressBar? = dialog?.findViewById(com.example.myapp.R.id.progressBar)
    val rlDialogProgressbar: RelativeLayout? = dialog?.findViewById(com.example.myapp.R.id.rlDialogProgressbar)

    rlDialogProgressbar?.tag = 0
    rlDialogProgressbar?.postDelayed({ }, 100)

    progressBar?.indeterminateDrawable?.setTint(ContextCompat.getColor(this, R.color.colorPrimary))
    progressBar?.indeterminateDrawable?.setTintMode(PorterDuff.Mode.SRC_ATOP)
    progressBar?.isIndeterminate = true

    if (this.isFinishing.not() && dialog?.isShowing?.not() == true) {
        dialog?.show()

    } else {
        dialog?.hide()
    }

    dialog?.window?.setGravity(Gravity.CENTER)
    dialog?.setCancelable(false)
    dialog?.setCanceledOnTouchOutside(false)

    return dialog
}

//HIDE LOADER
fun hideLoader() {
    if (dialog?.isShowing == true) {
        dialog?.dismiss()
    }
}



//SET BACKGROUND EDGE TO EDGE & MAKE STATUS BAR TEXT COLOR LIGHT
fun AppCompatActivity.setStatusBar() {
    enableEdgeToEdge()
    
    WindowCompat.getInsetsController(window, window.decorView).apply {
        isAppearanceLightStatusBars = false
    }
}



//RECYCLERVIEW ITEM DECORATION EXCEPT LAST ITEM
fun RecyclerView.addItemDecorationWithoutLastItem() {
    if (layoutManager !is LinearLayoutManager) {
        return
    }
    
    addItemDecoration(DividerItemDecorator(context))
}

fun isShowApiMessage(apiResponse: ApiResponse?, activity: Context?) {
//    if (apiResponse?.type?.equals(activity?.getString(R.string.success), true) == false) {
//        apiResponse.message?.let { message ->
//            activity.toast(message)
//        }
//    }
}


fun AppCompatActivity.addOnBackPressedDispatcher(onBackPressed: () -> Unit = { finish() }) {
    onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            onBackPressed.invoke()
        }
    })
}



fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
    observe(lifecycleOwner, object : Observer<T> {
        override fun onChanged(value: T) {
            if (value != null) {
                observer.onChanged(value)
            }
            removeObserver(this)
        }
    })
}


fun Context?.hideSoftKeyboard(view: View) {
    val imm = this?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Activity.changeScreenOrientation(isLandScape: Boolean = false) {
    requestedOrientation = if (isLandScape) ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE else ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
}

