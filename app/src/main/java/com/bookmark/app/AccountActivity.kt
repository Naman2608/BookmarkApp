package com.bookmark.app

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.bookmark.app.utils.UserPreferences
import com.google.android.material.button.MaterialButton

class AccountActivity : AppCompatActivity() {

    private lateinit var tvUserName: TextView
    private lateinit var tvUserEmail: TextView
    private lateinit var cardAccountInfo: CardView
    private lateinit var cardSecurity: CardView
    private lateinit var btnLogout: MaterialButton
    private lateinit var userPrefs: UserPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)
        
        supportActionBar?.title = "Account"
        
        userPrefs = UserPreferences(this)
        
        initViews()
        loadUserInfo()
        setupClickListeners()
    }

    private fun initViews() {
        tvUserName = findViewById(R.id.tvUserName)
        tvUserEmail = findViewById(R.id.tvUserEmail)
        cardAccountInfo = findViewById(R.id.cardAccountInfo)
        cardSecurity = findViewById(R.id.cardSecurity)
        btnLogout = findViewById(R.id.btnLogout)
    }

    private fun loadUserInfo() {
        tvUserName.text = userPrefs.userName
        tvUserEmail.text = userPrefs.userEmail
    }

    private fun setupClickListeners() {
        cardAccountInfo.setOnClickListener {
            // TODO: Implement AccountInfoActivity
            // startActivity(Intent(this, AccountInfoActivity::class.java))
        }

        cardSecurity.setOnClickListener {
            // TODO: Implement SecurityActivity
            // startActivity(Intent(this, SecurityActivity::class.java))
        }

        btnLogout.setOnClickListener {
            showLogoutConfirmation()
        }
    }

    private fun showLogoutConfirmation() {
        AlertDialog.Builder(this)
            .setTitle("Logout")
            .setMessage("Are you sure you want to logout?")
            .setPositiveButton("Logout") { _, _ ->
                performLogout()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun performLogout() {
        userPrefs.logout()
        
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}
