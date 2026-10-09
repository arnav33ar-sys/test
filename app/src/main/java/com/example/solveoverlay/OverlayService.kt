package com.example.solveoverlay

import android.app.Service
import android.content.Intent
import android.graphics.PixelFormat
import android.graphics.Color
import android.os.IBinder
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import kotlin.math.abs

class OverlayService : Service() {
    private lateinit var wm: WindowManager
    private var bubble: View? = null
    private var card: View? = null

    override fun onCreate() {
        super.onCreate()
        wm = getSystemService(WINDOW_SERVICE) as WindowManager
        showBubble()
    }

    private fun params(width: Int, height: Int): WindowManager.LayoutParams =
        WindowManager.LayoutParams(
            width, height,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        ).apply {
            gravity = Gravity.TOP or Gravity.END
            x = 24
            y = 220
        }

    private fun showBubble() {
        val b = TextView(this).apply {
            text = "Solve"
            textSize = 15f
            setTextColor(Color.WHITE)
            setPadding(24, 16, 24, 16)
            setBackgroundColor(0xFF2563EB.toInt())
            elevation = 12f
            setOnClickListener {
                showInfoCard()
                Toast.makeText(this@OverlayService,
                    "Overlay is working. AI screenshot solving needs backend setup.",
                    Toast.LENGTH_LONG).show()
            }
            setOnTouchListener(object : View.OnTouchListener {
                var startX = 0
                var startY = 0
                var downX = 0f
                var downY = 0f
                var moved = false
                override fun onTouch(v: View, event: MotionEvent): Boolean {
                    val lp = v.layoutParams as WindowManager.LayoutParams
                    when (event.action) {
                        MotionEvent.ACTION_DOWN -> {
                            startX = lp.x; startY = lp.y
                            downX = event.rawX; downY = event.rawY
                            moved = false
                            return false
                        }
                        MotionEvent.ACTION_MOVE -> {
                            val dx = (event.rawX - downX).toInt()
                            val dy = (event.rawY - downY).toInt()
                            if (abs(dx) > 8 || abs(dy) > 8) moved = true
                            if (moved) {
                                lp.x = (startX + dx).coerceAtLeast(0)
                                lp.y = (startY + dy).coerceAtLeast(0)
                                wm.updateViewLayout(v, lp)
                                return true
                            }
                        }
                    }
                    return false
                }
            })
        }
        bubble = b
        wm.addView(b, params(WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT))
    }

    private fun showInfoCard() {
        card?.let { try { wm.removeView(it) } catch (_: Exception) {} }
        val box = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(24, 20, 24, 20)
            setBackgroundColor(0xFF111827.toInt())
            elevation = 16f
        }
        val heading = TextView(this).apply {
            text = "Solve Overlay"
            textSize = 17f
            setTextColor(Color.WHITE)
        }
        val body = TextView(this).apply {
            text = "Floating overlay is active.\\n\\nNext step: connect screenshot capture and a secure AI backend to get real answers here."
            textSize = 14f
            setTextColor(0xFFE5E7EB.toInt())
            setPadding(0, 12, 0, 14)
        }
        val close = TextView(this).apply {
            text = "CLOSE"
            textSize = 14f
            setTextColor(0xFF93C5FD.toInt())
            setOnClickListener {
                card?.let { try { wm.removeView(it) } catch (_: Exception) {} }
                card = null
            }
        }
        box.addView(heading); box.addView(body); box.addView(close)
        card = box
        val p = params((resources.displayMetrics.widthPixels * 0.78).toInt(),
            WindowManager.LayoutParams.WRAP_CONTENT)
        p.gravity = Gravity.TOP or Gravity.CENTER_HORIZONTAL
        p.x = 0; p.y = 180
        wm.addView(box, p)
    }

    override fun onDestroy() {
        bubble?.let { try { wm.removeView(it) } catch (_: Exception) {} }
        card?.let { try { wm.removeView(it) } catch (_: Exception) {} }
        bubble = null; card = null
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
