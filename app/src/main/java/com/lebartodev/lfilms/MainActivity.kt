package com.lebartodev.lfilms

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lebartodev.lfilms.ui.home.MainNavFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fragment = supportFragmentManager.findFragmentByTag(MainNavFragment.TAG)
        if (fragment == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_container, MainNavFragment(), MainNavFragment.TAG)
                .commit()
        }
    }
}