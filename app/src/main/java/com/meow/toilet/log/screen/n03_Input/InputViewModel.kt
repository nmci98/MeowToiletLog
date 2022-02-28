package com.meow.toilet.log.screen.n03_Input

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.meow.toilet.log.base.BaseViewModel
import com.meow.toilet.log.base.DialogData
import com.meow.toilet.log.util.message.MessageId
import com.meow.toilet.log.repository.DataRepository
import com.meow.toilet.log.repository.local.entity.StoolLog
import com.meow.toilet.log.repository.local.entity.UrineLog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDate
import java.time.LocalDateTime

/**
 * 03_きろく画面 ビューモデル.
 */
class InputViewModel(
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

    /** 表示日 */
    val displayDate = state.getLiveData<LocalDate>("targetDate")

    /** 表示変更イベント */
    val updateTargetDateEvent = MutableLiveData<LocalDate>()

    /** おしっこ回数入力値 */
    val inputCountUrine = state.getLiveData("inputCountUrine", 0)

    /** うんち回数入力値 */
    val inputCountStool = state.getLiveData("inputCountStool", 0)

    // endregion 変数

    // region 公開

    /**
     * 登録ボタン押下処理.
     */
    fun onClickRegister() = viewModelScope.launch(Dispatchers.IO) {
        Timber.d("onClickRegister - urine : ${inputCountUrine.value} stool : ${inputCountStool.value}")
        try {
            val targetDate = displayDate.value
            val urineCount = inputCountUrine.value
            val stoolCount = inputCountStool.value

            if (targetDate != null && urineCount != null && stoolCount != null) {
                // おしっこデータ
                val urineData = UrineLog(
                    targetDate = targetDate.toString(),
                    count = urineCount,
                    createAt = LocalDateTime.now().toString(),
                    imageUrl = null
                )

                // うんちデータ
                val stoolData = StoolLog(
                    targetDate = targetDate.toString(),
                    count = stoolCount,
                    createAt = LocalDateTime.now().toString(),
                    imageUrl = null
                )

                // トイレログを更新する
                dataRepository.updateToiletLog(targetDate, urineData, stoolData)

                // きろく成功ダイアログを表示する
                showDialogEvent.postValue(
                    DialogData(
                        DIALOG_ID_INPUT_SUCCESSFUL,
                        MessageId.C00001
                    )
                )
            } else {
                Timber.e(" null is not allowed here")
                // エラーダイアログを表示する
                showDialogEvent.postValue(
                    DialogData(
                        DIALOG_ID_ERROR,
                        MessageId.E00001
                    )
                )
            }

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
     * 入力初期値設定処理.
     * DBに登録されたトイレログデータを取得して、初期値に設定する
     */
    fun setInitialInputCount() = viewModelScope.launch(Dispatchers.IO) {
        Timber.d("getSavedToiletCount - ${displayDate.value}")
        val toiletCount = displayDate.value?.let {
            // 対象日の登録データを取得する
            dataRepository.getToiletCount(it, it)
        }

        toiletCount?.let {
            // 入力初期値に設定する
            inputCountUrine.postValue(it.first)
            inputCountStool.postValue(it.second)
        }
    }

    /**
     * 前日ボタンタップ処理.
     */
    fun onClickBefore() {
        // 表示日を変更する
        updateTargetDateEvent.postValue(displayDate.value?.minusDays(1L))
    }

    /**
     * 翌日ボタンタップ処理.
     */
    fun onClickNext() {
        // 表示日を変更する
        updateTargetDateEvent.postValue(displayDate.value?.plusDays(1L))
    }

    // endregion 公開

}