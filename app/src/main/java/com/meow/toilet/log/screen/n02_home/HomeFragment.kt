package com.meow.toilet.log.screen.n02_home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.meow.toilet.log.base.BaseFragment
import com.meow.toilet.log.base.BaseViewModel
import com.meow.toilet.log.databinding.HomeFragmentBinding
import org.koin.androidx.viewmodel.ext.android.stateViewModel
import timber.log.Timber

/**
 * 02_ホーム画面 フラグメント.
 */
class HomeFragment : BaseFragment() {

    // region 変数

    /** ビューモデル */
    private val viewModel by stateViewModel<HomeViewModel>()

    // endregion 変数

    // region ライフサイクル

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = HomeFragmentBinding.inflate(inflater).also {
        it.lifecycleOwner = this
        it.viewModel = viewModel
    }.root

    override fun observe() {
        super.observe()

        viewModel.observePetProfile.observe(this, {
            Timber.d("observe PetProfile : $it")
            // ペットプロファイルを設定する
            viewModel.petProfile.postValue(PetProfileData(it))
        })
    }

    // endregion ライフサイクル

    // region 継承

    override fun getViewModel(): BaseViewModel = viewModel

    // endregion 継承
}