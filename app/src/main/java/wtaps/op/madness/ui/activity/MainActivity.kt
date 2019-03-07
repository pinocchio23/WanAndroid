package wtaps.op.madness.ui.activity

import android.content.Intent
import android.support.design.bottomnavigation.LabelVisibilityMode.*
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*
import org.jetbrains.anko.startActivity
import wtaps.op.madness.R
import wtaps.op.madness.base.BaseActivity
import wtaps.op.madness.mvp.contract.MainContract
import wtaps.op.madness.ui.fragment.HomeFragment

/**
 *    author : pikai
 *    e-mail : pikai199023@sina.cn
 *    date   : 2018\11\19 0019
 */

class MainActivity : BaseActivity(), MainContract.View {

    private val BOTTOM_INDEX: String = "bottom_index"

    private val FRAGMENT_HOME = 0x01
    private val FRAGMENT_KNOWLEDGE = 0x02
    private val FRAGMENT_NAVIGATION = 0x03
    private val FRAGMENT_PROJECT = 0x04
    private val FRAGMENT_WECHAT = 0x05

    private var mIndex = FRAGMENT_HOME

    private var mHomeFragment: HomeFragment? = null


    override fun showError(msg: String) {
    }

    override fun showLogoutSuc(isSuccess: Boolean) {
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun attackLayoutRes(): Int = R.layout.layout_main

    override fun initData() {
    }

    override fun start() {
    }

    override fun initView() {
        bottom_navigation.run {
            // 以前使用 BottomNavigationViewHelper.disableShiftMode(this) 方法来设置底部图标和字体都显示并去掉点击动画
            // 升级到 28.0.0 之后，官方重构了 BottomNavigationView ，目前可以使用 labelVisibilityMode = 1 来替代
            // BottomNavigationViewHelper.disableShiftMode(this)
            labelVisibilityMode = LABEL_VISIBILITY_LABELED
//            setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        }

        showFragment(mIndex)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_layout_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.R_id_menu_search -> {
                Intent(this, SearchActivity::class.java).run {
                    startActivity(this)
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showFragment(index: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        when (index) {
            FRAGMENT_HOME
            -> {
                toolbar.title = "首页"
                if (mHomeFragment == null) {
                    mHomeFragment = HomeFragment.getInstance()
                    transaction.add(R.id.container, mHomeFragment!!, "home")
                } else {
                    mHomeFragment?.let { transaction.show(it) }
                }
            }
        }
        transaction.commit()

    }

}