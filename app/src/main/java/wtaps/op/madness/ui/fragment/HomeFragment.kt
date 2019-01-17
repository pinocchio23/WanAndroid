package wtaps.op.madness.ui.fragment

import android.content.Intent
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.ImageView
import cn.bingoogolapple.bgabanner.BGABanner
import com.chad.library.adapter.base.BaseQuickAdapter
import io.reactivex.Observable
import kotlinx.android.synthetic.main.item_home_banner.view.*
import kotlinx.android.synthetic.main.layout_fragment_home.*
import wtaps.op.madness.R
import wtaps.op.madness.adapter.HomeAdapter
import wtaps.op.madness.base.BaseFragment
import wtaps.op.madness.constant.Constant
import wtaps.op.madness.mvp.contract.HomeContract
import wtaps.op.madness.mvp.model.bean.Article
import wtaps.op.madness.mvp.model.bean.ArticleResponseBody
import wtaps.op.madness.mvp.model.bean.Banner
import wtaps.op.madness.mvp.presenter.HomePresenter
import wtaps.op.madness.rx.SchedulerUtils
import wtaps.op.madness.ui.activity.ContentActivity
import wtaps.op.madness.utils.ImageLoader
import java.util.concurrent.TimeUnit

/**
 *    author : pikai
 *    e-mail : pikai199023@sina.cn
 *    date   : 2018\11\21 0021
 */

class HomeFragment : BaseFragment(), HomeContract.View {

    companion object {
        fun getInstance(): HomeFragment = HomeFragment()
    }

    private val mPresenter: HomePresenter by lazy {
        HomePresenter()
    }

    /**
     * LinearLayoutManager
     */
    private val linearLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(activity)
    }

    private var index = 1

    private var datas = mutableListOf<Article>()

    private val mRvAdapter: HomeAdapter by lazy {
        HomeAdapter(context, datas)
    }

    /**
     * banner datas
     */
    private lateinit var bannerDatas: ArrayList<Banner>

    /**
     * banner view
     */
    private var bannerView: View? = null


    private val bannerDelegate = BGABanner.Delegate<ImageView, String>{
        _, _, _, position ->
        if(bannerDatas.size > 0){
            val data = bannerDatas[position]
//            Intent(activity, ContentActivity::class.java).run{
//                putExtra(Constant.CONTENT_URL_KEY, data.url)
//                putExtra(Constant.CONTENT_TITLE_KEY, data.title)
//                putExtra(Constant.CONTENT_ID_KEY, data.id)
//                startActivity(this)
//            }
        }

    }



    private val bannerAdapter: BGABanner.Adapter<ImageView, String> by lazy {
        BGABanner.Adapter<ImageView, String> { _, imageView, feedImageUrl, _ ->
            ImageLoader.load(activity, feedImageUrl, imageView)
        }
    }

    override fun attachLayoutRes(): Int = R.layout.layout_fragment_home

    override fun initView() {
        mPresenter.attachView(this)

        homeRefreshLayout.apply {
            setOnRefreshListener(onSwipeRefreshListener)
        }

        bannerView = layoutInflater.inflate(R.layout.item_home_banner, null)

        bannerView?.banner?.run {
            setDelegate(bannerDelegate)
        }

        homeRecyclerView?.run {
            layoutManager = linearLayoutManager
            mRvAdapter.run {
                addHeaderView(bannerView)
                onItemClickListener = this@HomeFragment.onItemClickListener
            }
            adapter = mRvAdapter
        }

    }

    private val onSwipeRefreshListener = SwipeRefreshLayout.OnRefreshListener {
        Observable.timer(3, TimeUnit.SECONDS)
                .compose(SchedulerUtils.ioToMain())
                .subscribe{
                    homeRefreshLayout?.isRefreshing = false
                }
    }

    /**
     * ItemClickListener
     */
    private val onItemClickListener = BaseQuickAdapter.OnItemClickListener { _, _, position ->
        if (datas.size != 0) {
            val data = datas[position]
            Intent(activity, ContentActivity::class.java).run {
                putExtra(Constant.CONTENT_URL_KEY, data.link)
                putExtra(Constant.CONTENT_TITLE_KEY, data.title)
                putExtra(Constant.CONTENT_ID_KEY, data.id)
                startActivity(this)
            }
        }
    }

    override fun lazyLoad() {
        mPresenter.requestBanner()
        mPresenter.requestArticles(index)
    }

    override fun scrollToTop() {
    }

    override fun setBanner(banners: List<Banner>) {
        bannerDatas = banners as ArrayList<Banner>
        val bannerFeedList = ArrayList<String>()
        val bannerTitleList = ArrayList<String>()
        Observable.fromIterable(banners)
                .subscribe { list ->
                    bannerFeedList.add(list.imagePath)
                    bannerTitleList.add(list.title)
                }
        bannerView?.banner?.run {
            setAutoPlayAble(bannerFeedList.size > 1)
            setData(bannerFeedList, bannerTitleList)
            setAdapter(bannerAdapter)
        }

    }

    override fun setArticles(articles: ArticleResponseBody) {
        articles.datas?.let {
            mRvAdapter.run {
                replaceData(it)
            }
        }
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun showError(msg: String) {
    }

}