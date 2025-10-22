package com.bookmark.app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class AddBookmarkActivity : AppCompatActivity() {

    private lateinit var cardConnectBook: CardView
    private lateinit var cardBookmarkCode: CardView
    private lateinit var cardManualAdd: CardView
    private lateinit var btnCancel: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_bookmark)
        
        initViews()
        setupClickListeners()
    }

    private fun initViews() {
        cardConnectBook = findViewById(R.id.cardConnectBook)
        cardBookmarkCode = findViewById(R.id.cardBookmarkCode)
        cardManualAdd = findViewById(R.id.cardManualAdd)
        btnCancel = findViewById(R.id.btnCancel)
    }

    private fun setupClickListeners() {
        cardConnectBook.setOnClickListener {
            // TODO: Connect Book feature coming soon
            // startActivity(Intent(this, ConnectBookActivity::class.java))
        }

        cardBookmarkCode.setOnClickListener {
            // TODO: QR Scanner feature coming soon
            // startActivity(Intent(this, QRScannerActivity::class.java))
        }

        cardManualAdd.setOnClickListener {
            // TODO: Manual Add feature coming soon
            // startActivity(Intent(this, ManualAddBookActivity::class.java))
        }

        btnCancel.setOnClickListener {
            finish()
        }
    }
}
