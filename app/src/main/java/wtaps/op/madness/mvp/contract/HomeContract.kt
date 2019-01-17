package wtaps.op.madness.mvp.contract

import wtaps.op.madness.base.IPresenter
import wtaps.op.madness.base.IView
import wtaps.op.madness.mvp.model.bean.ArticleResponseBody
import wtaps.op.madness.mvp.model.bean.Banner

/**
 *    author : pikai
 *    e-mail : pikai199023@sina.cn
 *    date   : 2018\11\21 0021
 */
 
interface HomeContract {

    interface View : IView {

        fun scrollToTop()

        fun setBanner(banners: List<Banner>)

        fun setArticles(articles: ArticleResponseBody)
    }

    interface  Presenter : IPresenter<View> {
        fun requestHomeData()

        fun requestBanner()

        fun requestArticles(num :Int)
    }
}