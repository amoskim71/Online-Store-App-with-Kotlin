package com.example.deniz.onlinestorekotlin

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_thank_you.*

class ThankYou : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thank_you)

        txtThankYou.setOnClickListener {

            val intent = Intent(this, HomeScreen::class.java)
            startActivity(intent)


        }
    }
}
