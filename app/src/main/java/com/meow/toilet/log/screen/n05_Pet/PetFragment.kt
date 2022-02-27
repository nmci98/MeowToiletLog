package com.meow.toilet.log.screen.n05_Pet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.meow.toilet.log.base.BaseFragment
import com.meow.toilet.log.base.BaseViewModel
import com.meow.toilet.log.databinding.PetFragmentBinding
import org.koin.androidx.viewmodel.ext.android.stateViewModel

class PetFragment : BaseFragment() {

    // region 変数

    /** ビューモデル */
    private val viewModel by stateViewModel<PetViewModel>()

    // endregion 変数

    // region ライフサイクル

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    )= PetFragmentBinding.inflate(inflater).also {
        it.lifecycleOwner = this
        it.viewModel = viewModel
    }.root

    // endregion ライフサイクル

    // region 継承

    override fun getViewModel(): BaseViewModel = viewModel

    // endregion 継承

}