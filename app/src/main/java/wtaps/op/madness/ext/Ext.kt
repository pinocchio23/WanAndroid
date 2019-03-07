package wtaps.op.madness.ext

import android.content.Context
import wtaps.op.madness.widget.CustomToast

/**
 *    author : pikai
 *    e-mail : pikai199023@sina.cn
 *    date   : 2019\3\7 0007
 */

fun Context.showToast(msg: String) {
    CustomToast(this, msg).show()
}