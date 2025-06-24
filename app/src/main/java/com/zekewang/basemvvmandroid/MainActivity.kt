package com.zekewang.basemvvmandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.navigation.fragment.NavHostFragment
import com.zekewang.basemvvmandroid.databinding.ActivityMainBinding
import com.zekewang.basemvvmandroid.router.NavigationRouter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // app导航图路由
        initRouter()
    }
    @Inject
    lateinit var router: NavigationRouter
    private fun initRouter() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_app_container) as NavHostFragment
        router.setNavController(navHostFragment.navController)
    }
}