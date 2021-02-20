package com.example.wanandroid_copy2.ui.web.view

import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import androidx.appcompat.widget.Toolbar
import com.example.wanandroid_copy2.R
import com.example.wanandroid_copy2.common.BaseActivity
import com.just.agentweb.AgentWeb
import kotlinx.android.synthetic.main.activity_web.*

class WebActivity : BaseActivity() {

    private lateinit var agentWeb: AgentWeb
    lateinit var url: String
    lateinit var title: String

    override fun getLayoutId(): Int {
        return R.layout.activity_web
    }


    override fun initView() {
        super.initView()
        url = intent.getStringExtra("url").toString()
        title = intent.getStringExtra("title").toString()
        // 设置toolbar
        setToolBar(toolbar as Toolbar, title)
        agentWeb = AgentWeb.with(this)
            .setAgentWebParent(mContent, LinearLayout.LayoutParams(-1, -1))//传入AgentWeb 的父控件 ，如果父控件为 RelativeLayout ， 那么第二参数需要传入 RelativeLayout.LayoutParams
            .useDefaultIndicator()// 使用默认进度条
            .createAgentWeb()//
            .ready()
            .go(url);
        var setting = agentWeb.agentWebSettings.webSettings
        // 设置支持缩放，可以查看AgentWeb的文档
        setting.builtInZoomControls = true
    }

    // 设置头部的菜单
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.web_menu, menu)
        return true
    }

    // 如果在web里面又嵌套进去了，就不要直接finish退出web
    override fun onBackPressed() {
        if(!agentWeb.back()){
            finish()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.web_menu_share -> {
                Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, getString(
                        R.string.share_type_url,
                        getString(R.string.app_name),
                        title,url
                    ))
                    type = "text/plain"
                    startActivity(Intent.createChooser(this, getString(R.string.share_type_title)))
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}