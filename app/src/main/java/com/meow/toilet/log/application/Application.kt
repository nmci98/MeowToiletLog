package com.meow.toilet.log.application

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import com.meow.toilet.log.BuildConfig
import com.meow.toilet.log.activity.MainViewModel
import com.meow.toilet.log.repository.DataRepository
import com.meow.toilet.log.repository.local.AppDatabase
import com.facebook.stetho.Stetho
import com.meow.toilet.log.screen.n00_Footer.FooterTabViewModel
import com.meow.toilet.log.screen.n01_Splash.SplashViewModel
import com.meow.toilet.log.screen.n02_home.HomeViewModel
import com.meow.toilet.log.screen.n03_Input.InputViewModel
import com.meow.toilet.log.screen.n04_Log.LogViewModel
import com.meow.toilet.log.screen.n05_Pet.PetViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import timber.log.Timber

class Application : Application(), Application.ActivityLifecycleCallbacks {

    // region 変数
//    private val dataRepository: DataRepository by inject()

    // endregion 変数

    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(this)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            Stetho.initializeWithDefaults(this)

        }

        startKoin {
            androidContext(this@Application)
            modules(module, productModule)
        }
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) = Unit

    override fun onActivityStarted(activity: Activity) = Unit

    override fun onActivityResumed(activity: Activity) = Unit

    override fun onActivityPaused(activity: Activity) = Unit

    override fun onActivityDestroyed(activity: Activity) = Unit

    override fun onActivityStopped(activity: Activity) = Unit

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) = Unit

    /**
     * アプリDIモジュール
     */
    private val module = module {
        // HTTP通信
//        single { HttpResponseParser() }
//        single { HttpClientDispatcher() }

        // ストレージ操作
//        single { Preference }
        single { AppDatabase.getInstance(get()) }

        // リポジトリ
        single { DataRepository(get()) }

        // ビューモデル
        viewModel { (savedState: SavedStateHandle) -> MainViewModel(get(), savedState) }
    }

    /**
     * プロダクトDIモジュール
     */
    private val productModule = module {
    }
}