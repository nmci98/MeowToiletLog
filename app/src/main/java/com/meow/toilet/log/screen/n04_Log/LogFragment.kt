package com.meow.toilet.log.screen.n04_Log

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.meow.toilet.log.base.BaseFragment
import com.meow.toilet.log.base.BaseViewModel
import com.meow.toilet.log.databinding.LogFragmentBinding
import com.meow.toilet.log.screen.n00_Footer.FooterTabViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.stateViewModel
import timber.log.Timber

/**
 * 04_ログ画面 フラグメント.
 */
class LogFragment : BaseFragment() {

    // region 変数

    /** ビューモデル */
    private val viewModel by stateViewModel<LogViewModel>()

    /** フッタービューモデル */
    private val footerTabViewModel: FooterTabViewModel by sharedViewModel()

    // endregion 変数

    // region ライフサイクル

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = LogFragmentBinding.inflate(inflater).also {
        it.lifecycleOwner = this
        it.viewModel = viewModel
        // アダプターを設定する
        viewModel.adapter = LogAdapter()

    }.root

    override fun onResume() {
        super.onResume()
        // トイレログデータを取得する
        viewModel.getToiletLogData()
    }

    override fun observe() {
        super.observe()

        viewModel.updateListEvent.observe(this, {
            Timber.d("updateList event")
            // リストを更新する
            viewModel.updateList(it)
        })

        footerTabViewModel.targetDate.observe(this, {
            // 表示日を設定する
            viewModel.displayDate.value = it
            if (footerTabViewModel.currentItem.value == 2) {
                // 表示日が変更されたとき、トイレログデータを取得する
                viewModel.getToiletLogData()
            }
        })

        viewModel.updateTargetDateEvent.observe(this, {
            Timber.d("target date change event: $it")
            // 表示日を変更する
            footerTabViewModel.targetDate.postValue(it)
        })

    }

    // endregion ライフサイクル

    // region 継承

    override fun getViewModel(): BaseViewModel = viewModel

    // endregion 継承

}