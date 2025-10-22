package com.bookmark.app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.bookmark.app.utils.UserPreferences

class MoreOptionsActivity : AppCompatActivity() {

    private lateinit var cardAccount: CardView
    private lateinit var cardMyLists: CardView
    private lateinit var cardAbout: CardView
    private lateinit var btnLogout: Button
    private lateinit var userPrefs: UserPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_more_options)
        
        supportActionBar?.title = "More Options"
        
        userPrefs = UserPreferences(this)
        
        initViews()
        setupClickListeners()
    }

    private fun initViews() {
        cardAccount = findViewById(R.id.cardAccount)
        cardMyLists = findViewById(R.id.cardMyLists)
        cardAbout = findViewById(R.id.cardAbout)
        btnLogout = findViewById(R.id.btnLogout)
    }

    private fun setupClickListeners() {
        cardAccount.setOnClickListener {
            startActivity(Intent(this, AccountActivity::class.java))
        }

        cardMyLists.setOnClickListener {
            // TODO: Implement MyListsActivity
            // startActivity(Intent(this, MyListsActivity::class.java))
        }

        cardAbout.setOnClickListener {
            // TODO: Implement AboutActivity
            // startActivity(Intent(this, AboutActivity::class.java))
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
                logout()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
    
    private fun logout() {
        userPrefs.logout()
        
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}
