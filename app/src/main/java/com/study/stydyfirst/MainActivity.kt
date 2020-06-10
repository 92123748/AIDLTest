package com.study.stydyfirst

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.qmuiteam.qmui.alpha.QMUIAlphaButton
import com.qmuiteam.qmui.util.QMUIStatusBarHelper
import com.study.stydyfirst.Bean.PersonBean

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        QMUIStatusBarHelper.isFullScreen(this)
        var bean = PersonBean()
        bean.name = "hell0"
    }
}
