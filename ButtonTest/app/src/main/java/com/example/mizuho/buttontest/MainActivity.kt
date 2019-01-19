package com.example.mizuho.buttontest

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // 定義したリスナーを
        val button1: Button = findViewById(R.id.button1) as Button
        button1.setOnClickListener(listener1)


        // メソッド内でインスタンス化
        val button2: Button = findViewById<Button>(R.id.button2) as Button
        button2.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                Toast.makeText(this@MainActivity, "BUTTON 2", Toast.LENGTH_SHORT).show()
            }
        })


        // lambda式で記述
        val button3: Button = findViewById(R.id.button3)
        button3.setOnClickListener{ view ->
            Toast.makeText(this@MainActivity, "BUTTON3", Toast.LENGTH_SHORT).show()
        }

    }

    private val listener1 = object: View.OnClickListener {
        override fun onClick(v: View?) {
            Toast.makeText(this@MainActivity, "BUTTON 1", Toast.LENGTH_SHORT).show()
        }
    }
}
