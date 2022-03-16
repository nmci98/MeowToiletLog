package com.meow.toilet.log.screen.n00_Footer

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.viewpager2.widget.ViewPager2
import com.meow.toilet.log.base.BaseViewModel
import com.meow.toilet.log.repository.DataRepository
import java.time.LocalDate

class FooterTabViewModel(
    dataRepository: DataRepository,
    state: SavedStateHandle
) : BaseViewModel(state) {

    // region 変数

    /** 現在のページ */
    val currentItem = state.getLiveData("currentItem", 0)

    /** ページコントロールリスナー */
//    val pageControlListener = object : ViewPager2.OnPageChangeCallback() {}

    /** ホームタブ遷移イベント */
    val transitionHomeTabEvent = MutableLiveData<Unit>()

    /** きろくタブ遷移イベント */
    val transitionInputTabEvent = MutableLiveData<Unit>()

    /** ログタブ遷移イベント */
    val transitionLogTabEvent = MutableLiveData<Unit>()

    /** ペットタブ遷移イベント */
    val transitionPetTabEvent = MutableLiveData<Unit>()

    /** 表示日 */
    val targetDate = state.getLiveData("targetDate", LocalDate.now())

    // endregion 変数

}