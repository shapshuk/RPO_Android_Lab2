package com.example.lab2

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.lab2.databinding.ActivityAuthBinding
import com.example.lab2.services.AuthService
import com.example.lab2.services.FirebaseAuthService

class AuthActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityAuthBinding

    private var authService: AuthService = FirebaseAuthService()
    private var authCompletion: (Boolean) -> Unit = { success ->
        if (success) {
            startActivity(Intent(applicationContext, ItemDetailHostActivity::class.java))
        } else {
            Toast
                .makeText(this, "Error occurred", Toast.LENGTH_LONG)
                .show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.modeSwitch.setOnCheckedChangeListener() { _, isChecked ->
            binding.button.text = if (isChecked) "Sign in" else "Sign up"
        }

        binding.button.setOnClickListener() {
            if (binding.modeSwitch.isChecked)
                authService.signIn(
                    binding.emaiInput.text.toString(),
                    binding.passwordInput.text.toString(),
                    authCompletion
                )
            else
                authService.signUp(
                    binding.emaiInput.text.toString(),
                    binding.passwordInput.text.toString(),
                    authCompletion
                )
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_auth)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}