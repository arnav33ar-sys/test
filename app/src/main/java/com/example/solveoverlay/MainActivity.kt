package com.example.solveoverlay

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(28, 32, 28, 32)
            setBackgroundColor(0xFFF8FAFC.toInt())
        }
        val title = TextView(this).apply {
            text = "Solve Overlay"
            textSize = 28f
            setTextColor(0xFF111827.toInt())
        }
        val info = TextView(this).apply {
            text = "A small floating button for getting AI help with questions on your screen.\\n\\nThis starter project shows the overlay. To solve screenshots automatically, connect a secure AI backend."
            textSize = 16f
            setTextColor(0xFF374151.toInt())
            setPadding(0, 18, 0, 22)
        }
        val permission = Button(this).apply {
            text = "1. Allow display over other apps"
            setOnClickListener {
                startActivity(Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:$packageName")))
            }
        }
        val start = Button(this).apply {
            text = "2. Start floating Solve button"
            setOnClickListener {
                if (Settings.canDrawOverlays(this@MainActivity)) {
                    startService(Intent(this@MainActivity, OverlayService::class.java))
                    finish()
                } else {
                    info.text = "Please grant overlay permission first, then return and tap Start."
                }
            }
        }
        val stop = Button(this).apply {
            text = "Stop floating button"
            setOnClickListener {
                stopService(Intent(this@MainActivity, OverlayService::class.java))
            }
        }
        layout.addView(title)
        layout.addView(info)
        layout.addView(permission)
        layout.addView(start)
        layout.addView(stop)
        setContentView(layout)
    }
}
