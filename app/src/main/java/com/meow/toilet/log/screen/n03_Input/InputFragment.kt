package com.meow.toilet.log.screen.n03_Input

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.meow.toilet.log.base.BaseFragment
import com.meow.toilet.log.base.BaseViewModel
import com.meow.toilet.log.databinding.InputFragmentBinding
import com.meow.toilet.log.screen.n00_Footer.FooterTabViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.stateViewModel
import timber.log.Timber

/**
 * 03_きろく画面 フラグメント.
 */
class InputFragment : BaseFragment() {

    // region 変数

    /** ビューモデル */
    private val viewModel by stateViewModel<InputViewModel>()

    /** フッタービューモデル */
    private val footerTabViewModel: FooterTabViewModel by sharedViewModel()

    // endregion 変数

    // region ライフサイクル

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = InputFragmentBinding.inflate(inflater).also {
        it.lifecycleOwner = this
        it.viewModel = viewModel

        // ピッカーの最大値/最小値を設定する
        val maxValue = 10
        val minValue = 0
        maxValue.apply {
            it.urineNumberPicker.maxValue = this
            it.stoolNumberPicker.maxValue = this
        }
        minValue.apply {
            it.urineNumberPicker.minValue = this
            it.stoolNumberPicker.minValue = this
        }

        it.urineNumberPicker.setOnValueChangedListener { _, _, newValue ->
            viewModel.inputCountUrine.postValue(newValue)
        }

        it.stoolNumberPicker.setOnValueChangedListener { _, _, newValue ->
            viewModel.inputCountStool.postValue(newValue)
        }

    }.root

    override fun onResume() {
        super.onResume()
        // トイレログデータを取得して入力初期値に設定する
        viewModel.setInitialInputCount()
    }

    override fun observe() {
        super.observe()

        footerTabViewModel.targetDate.observe(this, {
            // 表示日を設定する
            viewModel.displayDate.value = it
            if (footerTabViewModel.currentItem.value == 1) {
                // 表示日が変更されたとき、トイレログデータを取得して入力初期値に設定する
                viewModel.setInitialInputCount()
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