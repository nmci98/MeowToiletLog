package com.meow.toilet.log.screen.n01_Splash

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meow.toilet.log.base.BaseViewModel
import com.meow.toilet.log.repository.DataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

class SplashViewModel(
    val dataRepository: DataRepository,
    state: SavedStateHandle
) : BaseViewModel(state) {

    // region 公開

    /**
     * 初期処理.
     */
    fun init() = viewModelScope.launch(Dispatchers.IO) {
        Timber.d("init")
        // TODO:API通信など
        delay(6000L)

        dataRepository.getPetProfile()?.let {
            // プロファイルが登録されている場合は、ホーム画面に遷移する
            transitionEvent.postValue(SplashFragmentDirections.actionSplashFragmentToHomeFragment())
        } ?: also {
            // プロファイルが登録されていない場合は、プロファイル画面に遷移する
            transitionEvent.postValue(
                SplashFragmentDirections.actionSplashFragmentToProfileFragment(
                    fromSplash = true
                )
            )
        }
    }

    // end region 公開


}