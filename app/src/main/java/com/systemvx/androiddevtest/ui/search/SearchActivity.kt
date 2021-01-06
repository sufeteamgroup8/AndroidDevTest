package com.systemvx.androiddevtest.ui.search

import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.systemvx.androiddevtest.R
import com.systemvx.androiddevtest.databinding.ActivitySearchBinding
import com.systemvx.androiddevtest.ui.util.NothingFragment

class SearchActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivitySearchBinding

    private lateinit var optionViewModel: SOptionViewModel

    private val searchViewModel = null //TODO()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_search)
        //绑定自身引用,以执行返回操作
        mBinding.activity = this

        optionViewModel = ViewModelProvider(this).get(SOptionViewModel::class.java)

        mBinding.searchBar.setOnEditorActionListener(SearchListener(::showSearchResult))

        mBinding.txtCancel.setOnClickListener { finish() }
        optionViewModel.searchResult.observe(this, Observer {
            if (!it) {
                showFailFragment()
            }
        })


        //显示
        supportFragmentManager.beginTransaction()
                .add(mBinding.searchFragContainer.id, SOptionsFragment(this.optionViewModel), "option")
                .addToBackStack("option")
                .commit()

        mBinding.searchBar.requestFocus()

    }

    private fun showFailFragment() {
        //尚未进行搜索
        if (supportFragmentManager.findFragmentByTag("result") == null) {
            supportFragmentManager.beginTransaction()
                    .add(mBinding.searchFragContainer.id, NothingFragment(), "result")
                    .addToBackStack("result")
                    .commit()
        } else {
            supportFragmentManager.beginTransaction()
                    .replace(mBinding.searchFragContainer.id, NothingFragment(), "result")
                    .addToBackStack("result")
                    .commit()
        }
    }

    private fun showSearchResult() {
        val action = supportFragmentManager.beginTransaction()
        //尚未进行搜索
        if (supportFragmentManager.findFragmentByTag("search_result") == null) {
            action.addToBackStack("result")
        }
        action.replace(mBinding.searchFragContainer.id, SResultListFragment(optionViewModel), "search_result")
                .commit()
        optionViewModel.findOrder(mBinding.searchBar.text.toString())
    }

    companion object {
        class SearchListener(val method: () -> Unit) : TextView.OnEditorActionListener {

            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                when (actionId) {
                    EditorInfo.IME_ACTION_SEARCH -> {
                        method()
                    }
                    else -> {
                    }
                }
                return true
            }
            //class end
        }
    }
}