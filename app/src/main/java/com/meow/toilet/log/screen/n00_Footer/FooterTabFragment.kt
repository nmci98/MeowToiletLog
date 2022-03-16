package com.meow.toilet.log.screen.n00_Footer


import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.meow.toilet.log.R
import com.meow.toilet.log.base.BaseFragment
import com.meow.toilet.log.base.BaseViewModel
import com.meow.toilet.log.databinding.FooterTabFragmentBinding
import com.meow.toilet.log.screen.n02_home.HomeFragment
import com.meow.toilet.log.screen.n03_Input.InputFragment
import com.meow.toilet.log.screen.n04_Log.LogFragment
import com.meow.toilet.log.screen.n05_Pet.PetFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber

class FooterTabFragment : BaseFragment() {

    // region 変数

    /** ビューモデル */
    private val viewModel: FooterTabViewModel by sharedViewModel()

    /** フラグメント（ホーム） */
    private val fragmentHome = HomeFragment()

    /** フラグメント（きろく） */
    private val fragmentInput = InputFragment()

    /** フラグメント（ログ） */
    private val fragmentLog = LogFragment()

    /** フラグメント（ペット） */
    private val fragmentPet = PetFragment()

    /** アダプター */
    private lateinit var adapter: FooterTabAdapter

    // endregion 変数


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FooterTabFragmentBinding.inflate(inflater).also {
        it.lifecycleOwner = this
        it.viewModel = viewModel
        adapter = FooterTabAdapter(
            childFragmentManager,
            viewLifecycleOwner.lifecycle,
            listOf<Fragment>(fragmentHome, fragmentInput, fragmentLog, fragmentPet)
        )
        it.adapter = this.adapter

        it.bottomNavigation.setOnItemSelectedListener { item ->
            Timber.d("onOptionsItemSelected $item")
            when (item.itemId) {
                R.id.page_home -> {
                    viewModel.transitionHomeTabEvent.postValue(Unit)
                    true
                }
                R.id.page_input -> {
                    viewModel.transitionInputTabEvent.postValue(Unit)
                    true
                }
                R.id.page_log -> {
                    viewModel.transitionLogTabEvent.postValue(Unit)
                    true
                }
                R.id.page_pet -> {
                    viewModel.transitionPetTabEvent.postValue(Unit)
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }
        }
    }.root

    override fun observe() {
        super.observe()

        viewModel.transitionHomeTabEvent.observe(this, {
            Timber.d("transition HOME tab event")
            viewModel.currentItem.postValue(0)
        })

        viewModel.transitionInputTabEvent.observe(this, {
            Timber.d("transition INPUT tab event")
            viewModel.currentItem.postValue(1)
        })

        viewModel.transitionLogTabEvent.observe(this, {
            Timber.d("transition LOG tab event")
            viewModel.currentItem.postValue(2)
        })

        viewModel.transitionPetTabEvent.observe(this, {
            Timber.d("transition PET tab event")
            viewModel.currentItem.postValue(3)
        })

        viewModel.targetDate.observe(this, {
            Timber.d("target date : $it")
        })
    }

    // endregion ライフサイクル

    // region 継承

    override fun getViewModel(): BaseViewModel = viewModel

    // endregion 継承

}