package com.bassiouny.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bassiouny.myapplication.ui.home.HomeFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val homeFragment = HomeFragment()
        val fragmentTransitionSupport = supportFragmentManager.beginTransaction()
        fragmentTransitionSupport.add(fragmentContainer.id, homeFragment).commit()
    }
}