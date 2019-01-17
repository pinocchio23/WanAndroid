package wtaps.op.madness.base

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import org.greenrobot.eventbus.EventBus

/**
 *    author : pikai
 *    e-mail : pikai199023@sina.cn
 *    date   : 2018\11\23 0023
 */
 
abstract class BasePresenter<V: IView>: IPresenter<V>, LifecycleObserver {

    var mView: V? = null

    var mCompositeDisposable: CompositeDisposable? = null

    private var isViewAttached: Boolean = false
        get() = mView != null

    var useEventBus: Boolean = false

    override fun attachView(mView: V) {
        mCompositeDisposable = CompositeDisposable()
        this.mView = mView
        if(mView is LifecycleOwner){
            mView.lifecycle.addObserver(this)
        }
        if(useEventBus){
            EventBus.getDefault().register(this)
        }
    }

    open fun checkViewAttached() {
        if (!isViewAttached) throw MvpViewNotAttachedException()
    }

    open fun addSubscription(disposable: Disposable) {
        mCompositeDisposable?.add(disposable)
    }

    private class MvpViewNotAttachedException internal constructor() : RuntimeException("Please call IPresenter.attachView(IBaseView) before" + " requesting data to the IPresenter")


    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy(owner: LifecycleOwner) {
        detachView()
        owner.lifecycle.removeObserver(this)
    }

    override fun detachView() {
        if(useEventBus){
            EventBus.getDefault().unregister(this)
        }
        unDispose()
        mView = null
    }

    /**
     *  保证activity结束时取消所有正在执行的订阅
     */
    private fun unDispose() {
        if(mCompositeDisposable != null){
            mCompositeDisposable?.clear()
        }
        mCompositeDisposable = null
    }
}