package com.meow.toilet.log.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections

/**
 * 基底ビューモデル.
 */
abstract class BaseViewModel(state: SavedStateHandle) :ViewModel(){

    // region 変数

    /** ローディング */
    val loading = state.getLiveData<Boolean>("loading")

    /** 画面遷移 */
    val transitionEvent = MutableLiveData<NavDirections>()

    /** 画面遷移(戻る) */
    val transitionBackEvent = MutableLiveData<Unit>()

    /** ダイアログ表示イベント */
    val showDialogEvent = MutableLiveData<DialogData>()

    // endregion 変数
}