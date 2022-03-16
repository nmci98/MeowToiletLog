package com.meow.toilet.log.base

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.meow.toilet.log.activity.MainViewModel
import org.koin.androidx.viewmodel.ext.android.stateViewModel
import timber.log.Timber
import androidx.appcompat.app.AppCompatActivity


/**
 * 基底フラグメント.
 */

abstract class BaseFragment : Fragment(), BaseDialogFragment.DialogListener {

    // region 変数

    /** メインビューモデル */
    protected val mainViewModel by stateViewModel<MainViewModel>()

    // endregion 変数

    // region 公開

    /**
     * 監視処理.
     */
    @CallSuper
    open fun observe() = Unit

    /**
     * キーボードを閉じる.
     */
    fun hideKeyboard() {
        val focusView = activity?.currentFocus
        focusView?.let {
            val inputMethodManager =
                activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            // ソフトキーボードを閉じる
            inputMethodManager.hideSoftInputFromWindow(
                it.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }

    // endregion 公開

    // region ライフサイクル

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeInternal()

        // アクションバーを設定する
        (this.activity as AppCompatActivity).supportActionBar?.also { actionBar ->
            actionBar.title = title?.let { getString(it) }
            actionBar.setDisplayHomeAsUpEnabled(isUpEnabled)
        }
    }

    // endregion ライフサイクル

    // region 継承

    /**
     * ビューモデル取得処理.
     */
    abstract fun getViewModel(): BaseViewModel

    /**
     * タイトル.
     */
    open val title :Int? = null

    /**
     * 戻るボタン.
     */
    open val isUpEnabled :Boolean = true

    /**
     * ダイアログ ポジティブボタン押下時処理.
     */
    override fun onDialogPositive(id: String?) {
        Timber.d("dialog positive $id")
    }

    /**
     * ダイアログ ネガティブボタン押下時処理.
     */
    override fun onDialogNegative(id: String?) {
        Timber.d("dialog negative $id")
    }

    // endregion 継承

    // region 内部

    /**
     * 監視処理.
     */
    private fun observeInternal() {
        observe()

        getViewModel().loading.observe(this, {
            Timber.d("loading : $it")
            mainViewModel.loading.postValue(it)
        })

        getViewModel().transitionEvent.observe(this, { action ->
            Timber.d("transition event $action")
            findNavController().navigate(action)
        })

        getViewModel().transitionBackEvent.observe(this, {
            Timber.d("transition back event")
            findNavController().popBackStack()
        })

        getViewModel().showDialogEvent.observe(this, { dialog ->
            Timber.d("show dialog event $dialog")
            BaseDialogFragment.show(dialog, childFragmentManager)
        })
    }

    // endregion 内部
}