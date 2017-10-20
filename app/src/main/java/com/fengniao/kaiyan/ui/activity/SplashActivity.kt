package com.fengniao.kaiyan.ui.activity

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.Typeface
import android.os.Bundle
import com.fengniao.kaiyan.R
import com.fengniao.kaiyan.base.BaseActivity
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        initView()
    }

    private fun initView() {
        setFont()
        setAnimation()
    }

    private fun setFont() {
        val font: Typeface = Typeface.createFromAsset(this.assets, "fonts/Lobster-1.4.otf")
        tv_name.typeface = font
        tv_intro_english.typeface = font
    }

    private fun setAnimation() {
        val alphaAnimation = ObjectAnimator.ofFloat(iv_ic_splash, "alpha", 0.1f, 1.0f)
        val scaleXAnimation = ObjectAnimator.ofFloat(iv_ic_splash, "scaleX", 0.1f, 1.0f)
        val scaleYAnimation = ObjectAnimator.ofFloat(iv_ic_splash, "scaleY", 0.1f, 1.0f)
        val set: AnimatorSet = AnimatorSet()
        set.play(alphaAnimation).with(scaleXAnimation).with(scaleYAnimation)
        set.duration = 1000
        set.start()
        set.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                jumpToActivity(MainActivity::class.java)
                finish()
            }
        })
    }
}
