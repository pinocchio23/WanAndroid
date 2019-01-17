package wtaps.op.madness.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import kotlinx.android.synthetic.main.activity_splash.*
import org.jetbrains.anko.startActivity
import wtaps.op.madness.R
import wtaps.op.madness.base.BaseActivity

class SplashActivity : BaseActivity(){

    override fun start() {

    }

    private var alphaAnimation : AlphaAnimation? = null

    override fun attackLayoutRes(): Int = R.layout.activity_splash


    override fun initData() {
    }

    override fun useEventBus(): Boolean = false

    override fun initView() {
        alphaAnimation = AlphaAnimation(0.3F, 1.0F)
        alphaAnimation?.run {
            duration = 2000
            setAnimationListener(object : Animation.AnimationListener{
                override fun onAnimationRepeat(animation: Animation?) {
                }

                override fun onAnimationEnd(animation: Animation?) {
                     jumpToHome()
                }

                override fun onAnimationStart(animation: Animation?) {
                }

            })
        }
        layout_splash.startAnimation(alphaAnimation)
    }

    private fun jumpToHome() {
        val intent = Intent(this, MainActivity ::class.java)
        startActivity(intent)
        finish()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

}