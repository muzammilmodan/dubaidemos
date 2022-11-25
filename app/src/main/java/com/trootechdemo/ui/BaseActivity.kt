package com.trootechdemo.ui

import android.animation.Animator
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleObserver

import com.trootechdemo.utils.Progress
import dagger.hilt.android.AndroidEntryPoint

import java.util.*


@AndroidEntryPoint
open class BaseActivity : AppCompatActivity(), LifecycleObserver, Animator.AnimatorListener {

    lateinit var progress: Progress

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setProgressbar()
    }

    fun setProgressbar() {
        progress = Progress(this, lifecycle)
        progress.setCancelable(false)
    }

    override fun attachBaseContext(base: Context?) {

    }

    private var mCurrentLocale: Locale? = null

    override fun onStart() {
        super.onStart()

    }

    override fun onRestart() {
        super.onRestart()

    }

    fun warn(msg: String?) {
       // CommonMethods.ShowToastWarn(this@BaseActivity, msg)
    }

    fun successMessage(msg: String?) {
      //  CommonMethods.ShowToastSuccess(this@BaseActivity, msg)
    }

    fun checkAccessAndDisplayMessage(code: Int, message: String?) {
        Log.e("code : ", "$code nn ")
        if (code == 403) {
            warn(message)
            //GeneralUtils.gotoLogin(this);
        } else {
            if (message != null && !message.isEmpty()) {
                if (message.equals("Session expired", ignoreCase = true)) {
                    warn(message)
                    //GeneralUtils.gotoLogin(this);
                } else {
                    warn(message)
                }
            } else {
                warn(message)
            }
        }
    }


    fun startAct(cls: Class<*>?, isFinishThis: Boolean) {
        startActivity(Intent(this, cls))
        if (isFinishThis) finish()
    }

    fun hideSoftKeyboard(view: View) {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun hideKeyboard() {
        var view = currentFocus
        if (view == null) {
            view = View(this)
        }
        hideSoftKeyboard(view)
    }

    // ========================================== Internet Connection ===================================================================
    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    public override fun onDestroy() {
        super.onDestroy()

    }

    override fun onAnimationStart(p0: Animator?) {
    }

    override fun onAnimationEnd(p0: Animator?) {
    }

    override fun onAnimationCancel(p0: Animator?) {
    }

    override fun onAnimationRepeat(p0: Animator?) {
    }





}