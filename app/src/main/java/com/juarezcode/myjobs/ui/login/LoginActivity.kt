package com.juarezcode.myjobs.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.juarezcode.myjobs.databinding.ActivityLoginBinding
import com.juarezcode.myjobs.ui.createuser.CreateUserActivity
import com.juarezcode.myjobs.ui.home.HomeAdminActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.botonAbrirPantallaHome.setOnClickListener {
            val intent = Intent(this, HomeAdminActivity::class.java)
            startActivity(intent)
        }

        binding.botonAbrirPantallaCrearCuenta.setOnClickListener {
            val intent = Intent(this, CreateUserActivity::class.java)
            startActivity(intent)
        }
    }
}