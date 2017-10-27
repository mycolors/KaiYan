package com.fengniao.kaiyan.wedegit

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.SuppressLint
import android.os.Build
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.DecelerateInterpolator

class CircularRevealAnim {
    companion object {
        val DURATION: Long = 200
    }

    interface AnimListener {
        fun onHideAnimationEnd()

        fun onShowAnimationEnd()
    }

    private var mListener: AnimListener? = null

    @SuppressLint("NewApi")
    private fun showOrVisible(isShow: Boolean, triggerView: View, animaView: View) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            if (isShow) {
                animaView.visibility = View.VISIBLE
                mListener?.onShowAnimationEnd()
            } else {
                animaView.visibility = View.GONE
                mListener?.onHideAnimationEnd()
            }
            return
        }

        val tvLocation = IntArray(2)
        triggerView.getLocationInWindow(tvLocation)
        val tvX = tvLocation[0] + triggerView.width / 2
        val tvY = tvLocation[1] + triggerView.height / 2
        triggerView.x

        val avLocation = IntArray(2)
        animaView.getLocationInWindow(avLocation)
        val avX = avLocation[0] + animaView.width / 2
        val avY = avLocation[1] + animaView.height / 2

        val rippleW = if (tvX < avX) animaView.width - tvX else tvX - avLocation[0]
        val rippleH = if (tvY < avY) animaView.height - tvY else tvY - avLocation[1]


        val maxRadius = Math.sqrt((rippleW * rippleW + rippleH * rippleH).toDouble()).toFloat()
        val startRadius: Float
        val endRadius: Float
        if (isShow) {
            startRadius = 0f
            endRadius = maxRadius
        } else {
            startRadius = maxRadius
            endRadius = 0f
        }

        val anim = ViewAnimationUtils.createCircularReveal(animaView, tvX, tvY, startRadius, endRadius)
        animaView.visibility = View.VISIBLE
        anim.duration = DURATION
        anim.interpolator = DecelerateInterpolator()

        anim.addListener(object : AnimatorListenerAdapter(){
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                if (isShow) {
                    animaView.visibility = View.VISIBLE
                    mListener?.onShowAnimationEnd()
                } else {
                    animaView.visibility = View.GONE
                    mListener?.onHideAnimationEnd()
                }
            }
        })
        anim.start()
    }


    fun show(triggerView: View,showView:View){
        showOrVisible(true,triggerView,showView)
    }

    fun hide(triggerView: View,showView:View){
        showOrVisible(false,triggerView,showView)
    }


    fun setAnimaListener(listener: AnimListener) {
        mListener = listener
    }


}