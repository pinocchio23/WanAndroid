package wtaps.op.madness.mvp.presenter

import wtaps.op.madness.base.BasePresenter
import wtaps.op.madness.http.exception.ExceptionHandle
import wtaps.op.madness.mvp.contract.HomeContract
import wtaps.op.madness.mvp.model.bean.HomeModel

/**
 *    author : pikai
 *    e-mail : pikai199023@sina.cn
 *    date   : 2018\11\23 0023
 */

class HomePresenter : BasePresenter<HomeContract.View>(), HomeContract.Presenter {

    private val homeModel: HomeModel by lazy {
        HomeModel()
    }

    override fun requestHomeData() {
    }

    override fun requestBanner() {
        val disposable = homeModel.requestBanner()
                .subscribe({
                    mView?.apply {
                        if (it.errorCode != 0) {
                            showError(it.errorMsg)
                        } else {
                            setBanner(it.data)
                        }
                        hideLoading()
                    }
                }, {
                    mView?.apply {
                        hideLoading()
                        showError(ExceptionHandle.handleException(it))
                    }
                })
        addSubscription(disposable)
    }

    override fun requestArticles(num: Int) {
        val disposable = homeModel.requestArticles(num)
                .subscribe({
                    mView?.apply {
                        if (it.errorCode != 0) {
                            showError(it.errorMsg)
                        } else {
                            setArticles(it.data)
                        }
                        hideLoading()
                    }
                }, {
                    mView?.apply {
                        hideLoading()
                        showError(ExceptionHandle.handleException(it))
                    }
                })
        addSubscription(disposable)
    }
}