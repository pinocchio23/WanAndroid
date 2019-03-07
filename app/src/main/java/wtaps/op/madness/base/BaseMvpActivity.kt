package wtaps.op.madness.base

import wtaps.op.madness.ext.showToast

/**
 *    author : pikai
 *    e-mail : pikai199023@sina.cn
 *    date   : 2019\3\7 0007
 */

abstract class BaseMvpActivity<V : IView, P : IPresenter<V>> : BaseActivity(), IView {


    protected var mPresenter: P? = null

    protected abstract fun createPresenter(): P

    override fun showError(msg: String) {
        showToast(msg)
    }
}