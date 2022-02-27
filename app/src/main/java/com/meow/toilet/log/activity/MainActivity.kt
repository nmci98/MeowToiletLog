package com.meow.toilet.log.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.meow.toilet.log.databinding.ActivityMainBinding
import com.meow.toilet.log.repository.DataRepository
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.stateViewModel

class MainActivity : AppCompatActivity() {

    /** データレポジトリ */
    private val dataRepository: DataRepository by inject()

    /** ビューモデル */
    private val viewModel by stateViewModel<MainViewModel>()

    /** バインディング */
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater).also {
            it.lifecycleOwner = this
            it.viewModel = this.viewModel
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // ナビゲーションを設定する
//        val navController = (supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment).navController
//        val appBarConfiguration = AppBarConfiguration(navController.graph)
//        setupActionBarWithNavController(navController, appBarConfiguration)
    }
}
