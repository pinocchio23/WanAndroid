package wtaps.op.madness.mvp.contract

import wtaps.op.madness.base.IPresenter
import wtaps.op.madness.base.IView

/**
 *    author : pikai
 *    e-mail : pikai199023@sina.cn
 *    date   : 2018\11\21 0021
 */

interface MainContract {

    interface View : IView {
        fun showLogoutSuc(isSuccess : Boolean)
    }

    interface Presenter : IPresenter<View> {

        fun logout()
    }
}