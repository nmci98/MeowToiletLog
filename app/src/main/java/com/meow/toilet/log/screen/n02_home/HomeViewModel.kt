package com.meow.toilet.log.screen.n02_home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.meow.toilet.log.base.BaseViewModel
import com.meow.toilet.log.base.DialogData
import com.meow.toilet.log.repository.DataRepository
import com.meow.toilet.log.repository.local.entity.StoolLog
import com.meow.toilet.log.repository.local.entity.UrineLog
import com.meow.toilet.log.screen.n00_Footer.FooterTabFragmentDirections
import com.meow.toilet.log.util.message.MessageId
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDate
import java.time.LocalDateTime

/**
 * 02_ホーム画面 ビューモデル.
 */
class HomeViewModel(
    val dataRepository: DataRepository,
    state: SavedStateHandle
) : BaseViewModel(state) {

    // region 定数

    companion object {
        /** きろく成功ダイアログ */
        const val DIALOG_ID_INPUT_SUCCESSFUL = "DIALOG_ID_INPUT_SUCCESSFUL"

        /** エラーダイアログ */
        const val DIALOG_ID_ERROR = "DIALOG_ID_ERROR"
    }

    // endregion 定数

    // region 変数

    /** ペットプロファイルテーブル */
    val observePetProfile = dataRepository.observePetProfile()

    /** ペットプロファイル */
    val petProfile = state.getLiveData<PetProfileData>("petProfile")

    /** 表示日 */
    val displayDate = state.getLiveData("targetDate", LocalDate.now())

    /** おしっこボタンアクティブ状態 */
    val isUrineButtonActive = state.getLiveData("isUrineButtonActive", false)

    /** うんちボタンアクティブ状態 */
    val isStoolButtonActive = state.getLiveData("isStoolButtonActive", false)

    // endregion 変数

    // region 公開

    /**
     * 編集ボタンタップ処理.
     */
    fun onClickEdit() {
        transitionEvent.postValue(FooterTabFragmentDirections.actionHomeFragmentToProfileFragment(fromSplash = false))
    }

    /**
     * おしっこボタンタップ処理.
     * @param isActive true -> アクティブ化 false -> 非アクティブ化
     */
    fun onClickUrineButton(isActive: Boolean) = isUrineButtonActive.postValue(isActive)

    /**
     * うんちボタンタップ処理.
     * @param isActive true -> アクティブ化 false -> 非アクティブ化
     */
    fun onClickStoolButton(isActive: Boolean) = isStoolButtonActive.postValue(isActive)

    /**
     * 登録ボタンタップ処理.
     */
    fun onClickRegister() = viewModelScope.launch(Dispatchers.IO) {
        Timber.d("onClickRegister - urine : ${isUrineButtonActive.value} stool : ${isStoolButtonActive.value}")
        try {
            displayDate.value?.let { targetDate ->
                // 登録済みのトイレログの回数を取得する
                val toiletCount = dataRepository.getToiletCount(targetDate, targetDate)

                // おしっこデータ
                val urineData = isUrineButtonActive.value?.let { isActive ->
                    when (isActive) {
                        true -> UrineLog(
                            targetDate = targetDate.toString(),
                            count = toiletCount.first + 1,
                            createAt = LocalDateTime.now().toString(),
                            imageUrl = null
                        )
                        else -> null
                    }
                }

                // うんちデータ
                val stoolData = isStoolButtonActive.value?.let { isActive ->
                    when (isActive) {
                        true -> StoolLog(
                            targetDate = targetDate.toString(),
                            count = toiletCount.second + 1,
                            createAt = LocalDateTime.now().toString(),
                            imageUrl = null
                        )
                        false -> null
                    }
                }

                // トイレログを更新する
                dataRepository.updateToiletLog(targetDate, urineData, stoolData)
            }

            // 登録成功ダイアログを表示する
            showDialogEvent.postValue(
                DialogData(
                    DIALOG_ID_INPUT_SUCCESSFUL,
                    MessageId.C00001
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

    // endregion 公開

}