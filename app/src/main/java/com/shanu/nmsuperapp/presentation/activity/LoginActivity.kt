package com.shanu.nmsuperapp.presentation.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.shanu.nmsuperapp.databinding.ActivityLoginBinding
import com.shanu.utilsmodule.errorSnack

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.tvLoginBtn.setOnClickListener {

            val mUserName = (binding.tilLoginUserName.editText?.text).toString().trim()
            val mPassword = (binding.tilLoginPwdData.editText?.text).toString().trim()

            if (mUserName.isNotEmpty() && mPassword.isNotEmpty()) {
                binding.circularProgressbar.visibility = View.VISIBLE

                proceedToLandingScreen()
            } else {
                binding.loginRoot.errorSnack("Please enter the user credentials")
            }
        }

    }

    private fun proceedToLandingScreen() {
        val intent = Intent(this@LoginActivity, LandingActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun showError(errorMessage: String) {
        binding.root.errorSnack(errorMessage)
    }

}