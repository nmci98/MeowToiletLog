package com.meow.toilet.log.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.meow.toilet.log.R
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

        // ツールバーを設定する
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val navController =
            (supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment).navController
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            toolbar.visibility =
                when (destination.id) {
                    // スプラッシュとフッターがある画面ではツールバーを表示しない
                    R.id.splash_fragment, R.id.footer_tab_fragment -> View.GONE
                    else -> View.VISIBLE
                }
        }
    }

    override fun onSupportNavigateUp() =
        (supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment).navController.navigateUp()
}
