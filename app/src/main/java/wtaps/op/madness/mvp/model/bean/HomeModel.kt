package wtaps.op.madness.mvp.model.bean

import io.reactivex.Observable
import wtaps.op.madness.base.BaseModel
import wtaps.op.madness.http.RetrofitHelper
import wtaps.op.madness.rx.SchedulerUtils

/**
 *    author : pikai
 *    e-mail : pikai199023@sina.cn
 *    date   : 2018\11\23 0023
 */
 
open class HomeModel: BaseModel(){

    fun requestBanner(): Observable<HttpResult<List<Banner>>> {
        return RetrofitHelper.service.getBanners()
                .compose(SchedulerUtils.ioToMain())
    }

    fun requestArticles(pageNum: Int): Observable<HttpResult<ArticleResponseBody>> {
        return RetrofitHelper.service.getArticles(pageNum)
                .compose(SchedulerUtils.ioToMain())
    }

    fun requestTopArticles(): Observable<HttpResult<MutableList<Article>>>{
        return RetrofitHelper.service.getTopArticles()
                .compose(SchedulerUtils.ioToMain())
    }

}