package com.meow.toilet.log.activity

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.meow.toilet.log.base.BaseViewModel
import com.meow.toilet.log.repository.DataRepository

class MainViewModel(
    val dataRepository: DataRepository,
    state: SavedStateHandle
) : BaseViewModel(state) {
}