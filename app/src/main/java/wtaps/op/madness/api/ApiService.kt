package wtaps.op.madness.api

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import wtaps.op.madness.mvp.model.bean.Article
import wtaps.op.madness.mvp.model.bean.ArticleResponseBody
import wtaps.op.madness.mvp.model.bean.Banner
import wtaps.op.madness.mvp.model.bean.HttpResult

/**
 *    author : pikai
 *    e-mail : pikai199023@sina.cn
 *    date   : 2018\11\23 0023
 */
 
 interface ApiService {

    /**
     * 获取轮播图
     * http://www.wanandroid.com/banner/json
     */
    @GET("banner/json")
    fun getBanners(): Observable<HttpResult<List<Banner>>>

    /**
     * 获取首页置顶文章列表
     * http://www.wanandroid.com/article/top/json
     */
    @GET("article/top/json")
    fun getTopArticles(): Observable<HttpResult<MutableList<Article>>>

    /**
     * 获取文章列表
     * http://www.wanandroid.com/article/list/0/json
     * @param pageNum
     */
    @GET("article/list/{pageNum}/json")
    fun getArticles(@Path("pageNum") pageNum: Int): Observable<HttpResult<ArticleResponseBody>>
}