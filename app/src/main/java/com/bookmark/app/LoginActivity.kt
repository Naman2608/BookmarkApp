package com.bookmark.app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bookmark.app.utils.UserPreferences
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.snackbar.Snackbar

class LoginActivity : AppCompatActivity() {

    private lateinit var etEmail: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var btnLogin: Button
    private lateinit var tvSignUp: TextView
    private lateinit var tvForgotPassword: TextView
    private lateinit var userPrefs: UserPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Check if already logged in
        userPrefs = UserPreferences(this)
        if (userPrefs.isLoggedIn) {
            navigateToHome()
            return
        }
        
        setContentView(R.layout.activity_login)
        
        initViews()
        setupClickListeners()
    }

    private fun initViews() {
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        tvSignUp = findViewById(R.id.tvSignUp)
        tvForgotPassword = findViewById(R.id.tvForgotPassword)
    }

    private fun setupClickListeners() {
        btnLogin.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()
            
            if (validateInput(email, password)) {
                performLogin(email, password)
            }
        }

        tvSignUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
        
        tvForgotPassword.setOnClickListener {
            Snackbar.make(it, "Password reset not implemented yet", Snackbar.LENGTH_SHORT).show()
        }
        
        findViewById<Button>(R.id.btnGoogleSignIn).setOnClickListener {
            Snackbar.make(it, "Google Sign-In not implemented yet", Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun validateInput(email: String, password: String): Boolean {
        if (email.isEmpty()) {
            etEmail.error = "Email is required"
            return false
        }
        
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.error = "Invalid email format"
            return false
        }
        
        if (password.isEmpty()) {
            etPassword.error = "Password is required"
            return false
        }
        
        if (password.length < 6) {
            etPassword.error = "Password must be at least 6 characters"
            return false
        }
        
        return true
    }

    private fun performLogin(email: String, password: String) {
        // TODO: Implement actual authentication with Firebase
        // For now, simulate successful login
        
        // Generate a simple user ID from email
        val userId = email.substringBefore("@")
        val userName = email.substringBefore("@").capitalize()
        
        // Save user session
        userPrefs.login(userId, userName, email)
        
        Snackbar.make(findViewById(android.R.id.content), 
            "Welcome back, $userName!", 
            Snackbar.LENGTH_SHORT).show()
        
        navigateToHome()
    }
    
    private fun navigateToHome() {
        val intent = Intent(this, NowReadingActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}
