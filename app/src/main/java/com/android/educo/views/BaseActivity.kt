package com.android.educo.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.educo.R

open class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
    }

    fun showShortToast(m : String){
        Toast.makeText(this,m,Toast.LENGTH_SHORT).show()
    }

    fun showLongToast(m : String){
        Toast.makeText(this,m,Toast.LENGTH_LONG).show()
    }

}