package com.meow.toilet.log.screen.n06_Profile

import androidx.lifecycle.*
import com.meow.toilet.log.base.BaseViewModel
import com.meow.toilet.log.base.DialogData
import com.meow.toilet.log.repository.DataRepository
import com.meow.toilet.log.repository.local.entity.Gender
import com.meow.toilet.log.repository.local.entity.PetProfile
import com.meow.toilet.log.util.message.MessageId
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDate

/**
 * 06_プロファイル画面 フラグメント.
 */
class ProfileViewModel(
    val dataRepository: DataRepository,
    state: SavedStateHandle
) : BaseViewModel(state) {

    // region 定数

    companion object {
        /** ペットプロファイル登録確認ダイアログ */
        const val DIALOG_ID_CONFIRM_REGISTERING_PET_PROFILE = "DIALOG_ID_CONFIRM_REGISTERING_PET_PROFILE"

        /** エラーダイアログ */
        const val DIALOG_ID_ERROR = "DIALOG_ID_ERROR"
    }

    // endregion 定数

    // region 変数

    /** 名前(必須) */
    val name = MutableLiveData<String>()

    /** 種類(任意) */
    val breed = MutableLiveData<String?>()

    /** 性別(必須) */
    val gender = MutableLiveData<Gender>()

    /** 誕生日(必須) */
    val dateOfBirth = MutableLiveData<LocalDate>()

    /** 体重(任意) */
    val weight = MutableLiveData<String>()

    /** 登録ボタン活性状態 */
    val buttonEnabledState = MediatorLiveData<Boolean>()

    init {
        // 監視対象の変数を設定する
        buttonEnabledState.addSource(name) { validate() }
//        _buttonEnabledState.addSource(breed) { validate() }
        buttonEnabledState.addSource(gender) { validate() }
        buttonEnabledState.addSource(dateOfBirth) { validate() }
//        _buttonEnabledState.addSource(weight) { validate() }
    }

    // endregion 変数

    // region 内部

    /**
     * 入力チェック.
     */
    private fun validate() {
        buttonEnabledState.value =
                // 必須項目が入力されていない場合はボタンを非活性とする
            name.value.isNullOrBlank().not() && gender.value != null && dateOfBirth.value != null
    }

    // endregion 内部

    // region 公開

    /**
     * 入力初期値設定処理.
     * DBに登録されたペットプロファイルを取得して、初期値に設定する
     */
    fun setInitialTextPetProfile() = viewModelScope.launch(Dispatchers.IO) {
        dataRepository.getPetProfile()?.let {
            // EditTextに設定する
            name.postValue(it.name)
            breed.postValue(it.breed)
            dateOfBirth.postValue(it.dateOfBirth)
            // RadioButtonに設定する
            gender.postValue(Gender.from(it.gender))
            weight.postValue(it.weight?.toString())
        }
    }

    /**
     * ペットプロファイル保存処理.
     */
    fun savePetProfile() = viewModelScope.launch(Dispatchers.IO) {
        try {
            // ペットプロファイルを保存する
            dataRepository.savePetProfile(
                PetProfile(
                    name.value ?: "", // 名前は必須のためnullがくることはない
                    dateOfBirth.value ?: LocalDate.now(), // 誕生日は必須のためnullがくることはない
                    gender.value?.value ?: Gender.UNKNOWN.value, // 性別は必須のためnullがくることはない
                    if (!breed.value.isNullOrBlank()) breed.value else null,
                    if (!weight.value.isNullOrBlank()) weight.value?.toDoubleOrNull() else null
                )
            )

        } catch (t: Throwable) {
            Timber.e(t)
            // エラーダイアログを表示する
            showDialogEvent.postValue(
                DialogData(
                    DIALOG_ID_ERROR,
                    MessageId.E00001
                )
            )
        }
    }

    /**
     * 登録ボタン押下処理.
     */
    fun onClickRegister() = viewModelScope.launch(Dispatchers.IO) {
        // ペットプロファイル登録確認ダイアログを表示する
        showDialogEvent.postValue(
            DialogData(
                DIALOG_ID_CONFIRM_REGISTERING_PET_PROFILE,
                MessageId.C00002
            )
        )
    }

    // endregion 公開
}