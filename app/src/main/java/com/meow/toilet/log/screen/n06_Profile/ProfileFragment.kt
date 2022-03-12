package com.meow.toilet.log.screen.n06_Profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.google.android.material.textfield.TextInputLayout
import com.meow.toilet.log.R
import com.meow.toilet.log.base.BaseFragment
import com.meow.toilet.log.base.BaseViewModel
import com.meow.toilet.log.base.DatePickerFragment
import com.meow.toilet.log.databinding.ProfileFragmentBinding
import org.koin.androidx.viewmodel.ext.android.stateViewModel
import timber.log.Timber
import java.time.LocalDate
import java.time.Month

/**
 * 06_プロファイル画面 フラグメント.
 */
class ProfileFragment : BaseFragment(), DatePickerFragment.OnDateSelectedListener {

    // region 変数

    /** ビューモデル */
    private val viewModel by stateViewModel<ProfileViewModel>()

    /** 名前入力 */
    private lateinit var nameTextInput: TextInputLayout

    // endregion 変数

    // region ライフサイクル

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ProfileFragmentBinding.inflate(inflater).also {
        it.lifecycleOwner = this
        it.viewModel = viewModel
        nameTextInput = it.textInputName
        // 登録済みのプロファイル情報を取得して、初期値に設定する
        viewModel.setInitialTextPetProfile()

        it.pickerTextDateOfBirth.setOnClickListener {
            // ピッカーを設定する
            DatePickerFragment(viewModel.dateOfBirth.value).show(childFragmentManager, "datePicker")
        }
    }.root

    override fun observe() {
        super.observe()

        viewModel.name.observe(this, {
            Timber.d("name : $it")
            // 未入力エラーを表示する
            nameTextInput.error = if (it.isNullOrBlank()) {
                getString(R.string.input_error_name)
            } else {
                null
            }
        })
    }

    // endregion ライフサイクル

    // region 継承

    override fun onDateSelected(year: Int, month: Month, dayOfMonth: Int) {
        try {
            Timber.d("onDateSelected : $year-$month-$dayOfMonth")
            viewModel.dateOfBirth.postValue(LocalDate.of(year, month, dayOfMonth))
        } catch (e: Exception) {
            Timber.e(e)
        }
    }

    override fun onDialogPositive(id: String?) {
        super.onDialogPositive(id)
        when (id) {
            ProfileViewModel.DIALOG_ID_CONFIRM_REGISTERING_PET_PROFILE -> {
                // キーボードを閉じる
                hideKeyboard()
                // ペットプロファイルを保存する
                viewModel.savePetProfile()
            }
        }
    }

    override fun getViewModel(): BaseViewModel = viewModel

    // endregion 継承
}