package wtaps.op.madness.ui.activity

import android.support.design.widget.CoordinatorLayout
import com.just.agentweb.AgentWeb
import kotlinx.android.synthetic.main.layout_content.*
import wtaps.op.madness.R
import wtaps.op.madness.base.BaseActivity
import wtaps.op.madness.constant.Constant

/**
 *    author : pikai
 *    e-mail : pikai199023@sina.cn
 *    date   : 2018\11023
 */

class ContentActivity : BaseActivity() {

    private lateinit var url: String

    override fun attackLayoutRes(): Int = R.layout.layout_content

    override fun initData() {
    }

    override fun start() {

    }

    override fun initView() {
        url = intent.getStringExtra(Constant.CONTENT_URL_KEY)

        val layoutParams = CoordinatorLayout.LayoutParams(-1, -1)

        AgentWeb.with(this)
                .setAgentWebParent(constraintLayout, 1, layoutParams)//传入AgentWeb 的父控件 ，如果父控件为 RelativeLayout ， 那么第二参数需要传入 RelativeLayout.LayoutParams
                .useDefaultIndicator()// 使用默认进度条
//                .setReceivedTitleCallback(mCallback) //设置 Web 页面的 title 回调
                .createAgentWeb()//
                .ready()
                .go(url)

    }

}