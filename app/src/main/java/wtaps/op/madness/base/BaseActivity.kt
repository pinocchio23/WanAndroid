package wtaps.op.madness.base

import android.content.Context
import android.content.IntentFilter
import android.graphics.Color
import android.graphics.PixelFormat
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.afollestad.materialdialogs.color.CircleView
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import wtaps.op.madness.R
import wtaps.op.madness.event.NetworkChangedEvent
import wtaps.op.madness.receiver.NetworkChangeReceiver
import wtaps.op.madness.utils.SettingUtil
import wtaps.op.madness.utils.StatusBarUtil

abstract class BaseActivity : AppCompatActivity() {

    /**
     * 提示View
     */
    protected lateinit var mTipView: View
    protected lateinit var mWindowManager: WindowManager
    protected lateinit var mLayoutParams: WindowManager.LayoutParams

    /**
     * 布局id
     */
    protected abstract fun attackLayoutRes(): Int

    /**
     * 初始化数据
     */
    abstract fun initData()

    /**
     * 开始请求
     */
    abstract fun start()

    /**
     * 初始化 View
     */
    abstract fun initView()

    /**
     * 是否使用 EventBus
     */
    open fun useEventBus(): Boolean = true

    /**
     * 是否需要显示 TipView
     */
    open fun enableNetworkTip(): Boolean = true

    protected var mNetworkChangedReceiver :NetworkChangeReceiver? = null

    /**
     * theme color
     */
    protected var mThemeColor: Int = SettingUtil.getColor()

    /**
     * 无网状态—>有网状态 的自动重连操作，子类可重写该方法
     */
    open fun doReConnected() {
        start()
    }

    /**
     * Network Change
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onNetworkChangeEvent(event: NetworkChangedEvent) {
        checkNetwork(event.isConnected)
    }

    private fun checkNetwork (isConnected: Boolean){
        if(enableNetworkTip()){
            if(isConnected){
                if(mTipView != null && mTipView.parent != null){
                    mWindowManager.removeView(mTipView)
                }
            }else{
                if(mTipView != null){
                    mWindowManager.addView(mTipView, mLayoutParams)
                }
            }
        }
    }


    open fun initColor() {
        mThemeColor = if (!SettingUtil.getIsNightMode()) {
            SettingUtil.getColor()
        } else {
            resources.getColor(R.color.colorPrimary)
        }
        StatusBarUtil.setColor(this, mThemeColor, 0)
        if (this.supportActionBar != null) {
            this.supportActionBar?.setBackgroundDrawable(ColorDrawable(mThemeColor))
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            window.statusBarColor = CircleView.shiftColorDown(mThemeColor)
//            // 最近任务栏上色
//            val tDesc = ActivityManager.TaskDescription(
//                    getString(R.string.app_name),
//                    BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher),
//                    mThemeColor)
//            setTaskDescription(tDesc)
            if (SettingUtil.getNavBar()) {
                window.navigationBarColor = CircleView.shiftColorDown(mThemeColor)
            } else {
                window.navigationBarColor = Color.BLACK
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(attackLayoutRes())
        EventBus.getDefault().register(this)
        initData()
        initTipView()
        initView()
    }

    private fun initTipView() {
        mTipView = layoutInflater.inflate(R.layout.layout_network_tip, null)
        mWindowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        mLayoutParams = WindowManager.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                PixelFormat.TRANSLUCENT)
        mLayoutParams.gravity = Gravity.TOP
        mLayoutParams.x = 0
        mLayoutParams.y = 0
        mLayoutParams.windowAnimations = R.style.anim_float_view // add animations
    }

    override fun onResume() {
        var intent = IntentFilter()
        intent.addAction("android.net.conn.CONNECTIVITY_CHANGE")
        mNetworkChangedReceiver = NetworkChangeReceiver()
        registerReceiver(mNetworkChangedReceiver, intent)
        super.onResume()
        initColor()
    }

    override fun onPause() {
        if(mNetworkChangedReceiver != null){
            unregisterReceiver(mNetworkChangedReceiver)
            mNetworkChangedReceiver = null
        }
        super.onPause()
    }
}