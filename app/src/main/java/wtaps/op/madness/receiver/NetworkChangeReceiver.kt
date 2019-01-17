package wtaps.op.madness.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import org.greenrobot.eventbus.EventBus
import wtaps.op.madness.constant.Constant
import wtaps.op.madness.event.NetworkChangedEvent
import wtaps.op.madness.utils.NetWorkUtil
import wtaps.op.madness.utils.Preference

class NetworkChangeReceiver : BroadcastReceiver() {

    /**
     * 缓存上次网络状态
     */
    private var hasNetwork: Boolean by Preference(Constant.HAS_NETWORK_KEY, true)

    override fun onReceive(context: Context, intent: Intent) {
        val isConnected = NetWorkUtil.isNetworkConnected(context)
        if (isConnected) {
            if (isConnected != hasNetwork) {
                EventBus.getDefault().post(NetworkChangedEvent(isConnected))
            }
        } else {
            EventBus.getDefault().post(NetworkChangedEvent(isConnected))
        }
    }
}