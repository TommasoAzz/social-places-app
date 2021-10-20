package com.example.maptry.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.maptry.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

open class ListActivity: AppCompatActivity(R.layout.activity_list) {
    companion object {
        private val TAG: String = ListActivity::class.qualifiedName!!
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.v(TAG, "onCreate")
        super.onCreate(savedInstanceState)
    }

    protected fun pushFragment(fragment: Fragment) {
        CoroutineScope(Dispatchers.Main).launch {
            if(!supportFragmentManager.isDestroyed) {
                supportFragmentManager.beginTransaction().apply {
                    replace(R.id.list_fragment, fragment)
                    setReorderingAllowed(true)
                    commit()
                }
            }
        }
    }
}