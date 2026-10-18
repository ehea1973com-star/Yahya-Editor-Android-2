
package com.yahya.editor

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dark = getPreferences(0).getBoolean("dark", false)
        val bg = if (dark) Color.rgb(25, 27, 32) else Color.WHITE
        val fg = if (dark) Color.WHITE else Color.rgb(30, 35, 45)

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER_HORIZONTAL
            setPadding(24, 32, 24, 24)
            setBackgroundColor(bg)
        }

        fun text(value: String, size: Float): TextView {
            return TextView(this).apply {
                text = value
                textSize = size
                setTextColor(fg)
                gravity = Gravity.CENTER
                setPadding(8, 12, 8, 12)
            }
        }

        root.addView(text("Yahya Editor Android 2", 25f))
        root.addView(text("محرر الملفات والتعريب | Code & Translation", 16f))

        val editor = android.widget.EditText(this).apply {
            hint = "اكتب أو الصق النص هنا...\nWrite or paste text here..."
            textSize = 16f
            gravity = Gravity.TOP or Gravity.START
            setTextColor(fg)
            setHintTextColor(Color.GRAY)
            minLines = 8
            setPadding(12, 12, 12, 12)
            setBackgroundColor(if (dark) Color.rgb(42, 45, 52) else Color.rgb(245, 245, 245))
        }

        root.addView(
            editor,
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 0, 1f
            )
        )

        fun button(label: String, action: () -> Unit) {
            root.addView(Button(this).apply {
                text = label
                setOnClickListener { action() }
            })
        }

        button("مسح النص / Clear") { editor.text.clear() }

        button("نسخ النص / Copy") {
            val clipboard = getSystemService(CLIPBOARD_SERVICE)
                    as android.content.ClipboardManager
            clipboard.setPrimaryClip(
                android.content.ClipData.newPlainText("Yahya Editor", editor.text)
            )
        }

        button(if (dark) "الوضع الفاتح / Light" else "الوضع الليلي / Dark") {
            getPreferences(0).edit().putBoolean("dark", !dark).apply()
            recreate()
        }

        button("تيليغرام / Telegram") {
            startActivity(android.content.Intent(
                android.content.Intent.ACTION_VIEW,
                android.net.Uri.parse("https://t.me/ehea1973hmza")
            ))
        }

        button("يوتيوب / YouTube") {
            startActivity(android.content.Intent(
                android.content.Intent.ACTION_VIEW,
                android.net.Uri.parse("https://youtube.com/channel/UCmChpVHGV74adiyqncSyocg")
            ))
        }

        button("فيسبوك / Facebook") {
            startActivity(android.content.Intent(
                android.content.Intent.ACTION_VIEW,
                android.net.Uri.parse("https://www.facebook.com/share/g/1HGSjVh3c5/")
            ))
        }

        setContentView(ScrollView(this).apply {
            addView(root)
        })
    }
}
