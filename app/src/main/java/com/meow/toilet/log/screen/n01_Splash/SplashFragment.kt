package com.meow.toilet.log.screen.n01_Splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.meow.toilet.log.base.BaseFragment
import com.meow.toilet.log.base.BaseViewModel
import com.meow.toilet.log.databinding.SplashFragmentBinding
import org.koin.androidx.viewmodel.ext.android.stateViewModel
import timber.log.Timber

class SplashFragment : BaseFragment() {

    // region 変数

    /** ビューモデル */
    private val viewModel by stateViewModel<SplashViewModel>()

    // endregion 変数

    // region ライフサイクル

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = SplashFragmentBinding.inflate(inflater).also {
        it.lifecycleOwner = this
        it.viewModel = viewModel
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.init()
    }

    // endregion ライフサイクル

    // region 継承

    override fun getViewModel(): BaseViewModel = viewModel

    // endregion 継承
}