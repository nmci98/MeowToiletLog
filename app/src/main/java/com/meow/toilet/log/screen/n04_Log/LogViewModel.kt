package com.meow.toilet.log.screen.n04_Log

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.meow.toilet.log.base.BaseViewModel
import com.meow.toilet.log.base.DialogData
import com.meow.toilet.log.repository.DataRepository
import com.meow.toilet.log.util.message.MessageId
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * 04_ログ画面 ビューモデル.
 */
class LogViewModel(
    val dataRepository: DataRepository,
    state: SavedStateHandle
) : BaseViewModel(state) {

    // region 定数

    companion object {
        /** エラーダイアログ */
        const val DIALOG_ID_ERROR = "DIALOG_ID_ERROR"
    }

    // endregion 定数

    // region 変数

    /** アダプター */
    lateinit var adapter: LogAdapter

    /** 表示日 */
    val displayDate = state.getLiveData<LocalDate>("targetDate")

    /** 表示月 */
    val displayMonthTitle = Transformations.map(displayDate) {
        it.format(DateTimeFormatter.ofPattern("yyyy年M月"))
    }

    /** 表示変更イベント */
    val updateTargetDateEvent = MutableLiveData<LocalDate>()

    /** ログデータが空かどうか */
    val isLogEmpty = state.getLiveData<Boolean>("isLogEmpty")

    /** リスト更新イベント */
    val updateListEvent = state.getLiveData<List<LogItemData>>("updateListEvent")

    // endregion 変数

    // region 公開

    /**
     * トイレログデータ取得処理.
     */
    fun getToiletLogData() = viewModelScope.launch(Dispatchers.IO) {
        try {
            val data = displayDate.value?.let {
                // 月初日から月末日までの登録データを取得する
                dataRepository.getToiletLogData(LocalDate.of(it.year, it.month, 1), LocalDate.of(it.year, it.month, it.lengthOfMonth()))
            }
            val urineDataList = data?.first
            Timber.d("urineDataList : $urineDataList")
            val stoolDataList = data?.second
            Timber.d("stoolDataList : $stoolDataList")

            val displayData = ArrayList<LogItemData>()

            urineDataList?.filterNot { urineLog ->
                stoolDataList?.map { it.targetDate }?.contains(urineLog.targetDate) == true
            }?.forEach {
                // うんちログの日付と重複しないおしっこログデータのみ追加する
                displayData.add(LogItemData(LocalDate.parse(it.targetDate), it.count, 0))
            }

            stoolDataList?.filterNot { stoolLog ->
                urineDataList?.map { it.targetDate }?.contains(stoolLog.targetDate) == true
            }?.forEach {
                // おしっこログの日付と重複しないうんちログデータのみ追加する
                displayData.add(LogItemData(LocalDate.parse(it.targetDate), 0, it.count))
            }

            urineDataList?.forEach { urineLog ->
                stoolDataList?.forEach { stoolLog ->
                    if (urineLog.targetDate == stoolLog.targetDate) {
                        // おしっことうんちが両方登録された日のデータを追加する
                        displayData.add(LogItemData(LocalDate.parse(urineLog.targetDate), urineLog.count, stoolLog.count))
                    }
                }
            }
            // 表示日が古い順にソートする
            displayData.sortBy { it.date }
            Timber.d("displayData : $displayData")

            // ログデータが空かどうかのフラグを更新する
            isLogEmpty.postValue(displayData.isEmpty())
            // リストを更新する
            updateListEvent.postValue(displayData)

        } catch (e: Exception) {
            Timber.e(e)
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
     * リスト更新処理.
     */
    fun updateList(dataList: List<LogItemData>) {
        if (adapter.itemList != dataList) {
            adapter.update(dataList)
        }
    }

    /**
     * 前月ボタンタップ処理.
     */
    fun onClickBefore() {
        // 表示月を変更する
        displayDate.value?.let {
            // 前月の1日を表示日とする
            updateTargetDateEvent.postValue(LocalDate.of(it.year, it.month.minus(1L), 1))
        }
    }

    /**
     * 翌月ボタンタップ処理.
     */
    fun onClickNext() {
        // 表示月を変更する
        displayDate.value?.let {
            // 翌月の1日を表示日とする
            updateTargetDateEvent.postValue(LocalDate.of(it.year, it.month.plus(1L), 1))
        }
    }

    // endregion 公開
}