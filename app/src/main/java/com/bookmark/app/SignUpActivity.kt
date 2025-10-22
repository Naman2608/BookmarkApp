package com.bookmark.app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bookmark.app.utils.UserPreferences
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.snackbar.Snackbar

class SignUpActivity : AppCompatActivity() {

    private lateinit var etName: TextInputEditText
    private lateinit var etEmail: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var etConfirmPassword: TextInputEditText
    private lateinit var cbTerms: CheckBox
    private lateinit var btnSignUp: Button
    private lateinit var tvLogin: TextView
    private lateinit var userPrefs: UserPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        
        userPrefs = UserPreferences(this)
        
        initViews()
        setupClickListeners()
    }

    private fun initViews() {
        etName = findViewById(R.id.etName)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        etConfirmPassword = findViewById(R.id.etConfirmPassword)
        cbTerms = findViewById(R.id.cbTerms)
        btnSignUp = findViewById(R.id.btnSignUp)
        tvLogin = findViewById(R.id.tvLogin)
    }

    private fun setupClickListeners() {
        findViewById<ImageButton>(R.id.btnBack).setOnClickListener {
            finish()
        }
        
        btnSignUp.setOnClickListener {
            val name = etName.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()
            val confirmPassword = etConfirmPassword.text.toString().trim()
            
            if (validateInput(name, email, password, confirmPassword)) {
                performSignUp(name, email, password)
            }
        }

        tvLogin.setOnClickListener {
            finish() // Go back to login
        }
    }

    private fun validateInput(name: String, email: String, password: String, confirmPassword: String): Boolean {
        if (name.isEmpty()) {
            etName.error = "Name is required"
            return false
        }
        
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
        
        if (password != confirmPassword) {
            etConfirmPassword.error = "Passwords do not match"
            return false
        }
        
        if (!cbTerms.isChecked) {
            Snackbar.make(findViewById(android.R.id.content),
                "Please accept the Terms & Conditions",
                Snackbar.LENGTH_SHORT).show()
            return false
        }
        
        return true
    }

    private fun performSignUp(name: String, email: String, password: String) {
        // TODO: Implement actual sign up with Firebase
        // For now, simulate successful sign up
        
        // Generate a simple user ID from email
        val userId = email.substringBefore("@")
        
        // Save user session
        userPrefs.login(userId, name, email)
        
        Snackbar.make(findViewById(android.R.id.content),
            "Welcome to Bookmark, $name!",
            Snackbar.LENGTH_SHORT).show()
        
        // Navigate to Now Reading dashboard
        val intent = Intent(this, NowReadingActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}
