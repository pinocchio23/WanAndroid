package wtaps.op.madness.base

/**
 *    author : pikai
 *    e-mail : pikai199023@sina.cn
 *    date   : 2018\11\21 0021
 */
 
interface IPresenter<in V: IView>{

    fun attachView(mView: V)

    fun detachView()
}