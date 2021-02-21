package com.example.wanandroid_copy2

import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.Fragment
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.ashokvarma.bottomnavigation.BottomNavigationItem
import com.example.wanandroid_copy2.common.BaseActivity
import com.example.wanandroid_copy2.common.constant.Constant
import com.example.wanandroid_copy2.common.constant.Constant.HOME
import com.example.wanandroid_copy2.common.utils.Preference
import com.example.wanandroid_copy2.ui.home.view.HomeFragmentV2
import com.example.wanandroid_copy2.ui.navigation.view.NavigationFragment
import com.example.wanandroid_copy2.ui.system.view.SystemFragment
import com.example.wanandroid_copy2.ui.wechat.view.WeChatFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_drawer_header.view.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class MainActivity : BaseActivity() {

    private lateinit var currentFragment : Fragment
    private val homeFragment : Fragment by lazy {
        HomeFragmentV2()
    }
    private val weChatFragment : Fragment by lazy {
        WeChatFragment()
    }
    private val navigationFragment : NavigationFragment by lazy {
        NavigationFragment()
    }
    private val systemFragment: SystemFragment by lazy {
        SystemFragment()
    }
    private lateinit var headerView : View
    private var mUsername : String by Preference(Constant.USERNAME_KEY,"未登录")

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        super.initView()
        initToolbar()
        initDrawerLayout()
        initFabButton()
        initBottomNavigationBar()
        defaultFragment()
    }

    private fun initToolbar() {
        setToolBar(toolbar,getString(R.string.app_name))
        val actionBarDrawerToggle =
            ActionBarDrawerToggle(this, drawerMain, toolbar, R.string.app_name, R.string.app_name)
        drawerMain.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
    }

    private fun initDrawerLayout(){
        headerView = navigation.getHeaderView(0)

        headerView.tv_name.text = mUsername
        headerView.iv_logo.setOnClickListener {
            // UserContext.instance.login(this)
        }

        navigation.setNavigationItemSelectedListener {

            when(it.itemId) {
                R.id.nav_menu_collect -> {
                    // goCollectActivity()
                }
                R.id.nav_menu_logout -> {
                    // logout()
                }
                R.id.nav_about -> {
                    // goAbout()
                }
            }
            drawerMain.closeDrawers()
            true
        }
    }

    private fun initFabButton() {
        fab.setOnClickListener{}
        mNavigationBar.setFab(fab)
    }

    /**
     * 初始化底部导航栏
     */
    private fun initBottomNavigationBar() {
        mNavigationBar.setMode(BottomNavigationBar.MODE_DEFAULT)
            .setActiveColor(R.color.colorPrimaryDark)
            .addItem(
                BottomNavigationItem(
                    R.mipmap.navigation_home,
                    getString(R.string.navigation_home)
                )
            )
            .addItem(
                BottomNavigationItem(
                    R.mipmap.navigation_wechat,
                    getString(R.string.navigation_wechat)
                )
            )
            .addItem(
                BottomNavigationItem(
                    R.mipmap.navigation_system,
                    getString(R.string.navigation_system)
                )
            )
            .addItem(
                BottomNavigationItem(
                    R.mipmap.navigation_navigation,
                    getString(R.string.navigation_navigation)
                )
            )
            .addItem(
                BottomNavigationItem(
                    R.mipmap.nagivation_project,
                    getString(R.string.navigation_project)
                )
            )
            .setBarBackgroundColor(R.color.white)
            .setFirstSelectedPosition(HOME)
            .initialise()
        mNavigationBar.setTabSelectedListener(object : BottomNavigationBar.OnTabSelectedListener {

            override fun onTabUnselected(position: Int) {}

            override fun onTabReselected(position: Int) {}

            override fun onTabSelected(position: Int) {
                switchFragment(position)
            }

        })
    }

    private fun switchFragment(position: Int) {
        when (position) {
            Constant.HOME -> {
                setToolBar(toolbar, getString(R.string.navigation_home))
                changeFragment(homeFragment)
            }

            Constant.WE_CHAT -> {
                setToolBar(toolbar,getString(R.string.navigation_wechat))
                changeFragment(weChatFragment)
            }

            Constant.NAGIVATION -> {
                setToolBar(toolbar,getString(R.string.navigation_navigation))
                changeFragment(navigationFragment)
            }

            Constant.SYSTEM -> {
                setToolBar(toolbar,getString(R.string.navigation_system))
                changeFragment(systemFragment)
            }

            Constant.PROJECT -> {
//                setToolBar(toolbar,getString(R.string.navigation_project))
//                changeFragment(projectFragment)
            }

            else -> {

            }
        }
    }


    private fun changeFragment(to: Fragment) {
        if (currentFragment != to) {
            val transaction = supportFragmentManager.beginTransaction()
            if (to.isAdded)
                transaction.hide(currentFragment).show(to)
            else
                transaction.hide(currentFragment).add(R.id.content, to)
            transaction.commit()
            currentFragment = to
        }
    }

    // 初始化fragment
    private fun defaultFragment() {
        currentFragment = homeFragment
        supportFragmentManager.beginTransaction().add(R.id.content, homeFragment).commit()
    }

    // 首页显示feb和底部菜单栏
    fun onShow() {
        fab.animate().translationX(0f).interpolator = AccelerateDecelerateInterpolator()
        mNavigationBar.animate().translationY(0f).interpolator = AccelerateDecelerateInterpolator()
    }

    // 首页隐藏feb和底部菜单栏
    fun onHide() {
        val layoutParams = fab.layoutParams
        val leftMargin = (layoutParams as ViewGroup.MarginLayoutParams).marginEnd
        fab.animate().translationX(fab.width.toFloat() + leftMargin).interpolator = AccelerateDecelerateInterpolator()

        mNavigationBar.animate().translationY(mNavigationBar.height.toFloat()).interpolator = AccelerateDecelerateInterpolator()
    }
}