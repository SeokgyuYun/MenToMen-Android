package kr.hs.b1nd.intern.mentomen.view.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import kr.hs.b1nd.intern.mentomen.App
import kr.hs.b1nd.intern.mentomen.R
import kr.hs.b1nd.intern.mentomen.databinding.ActivityLoginBinding
import kr.hs.b1nd.intern.mentomen.viewmodel.LoginViewModel
import kr.hs.dgsw.smartschool.dauth.api.network.DAuth.loginForDodam
import kr.hs.dgsw.smartschool.dauth.api.network.DAuth.settingForDodam

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        performViewModel()

        loginViewModel.onClickLoginEvent.observe(this) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun performViewModel() {
        loginViewModel = LoginViewModel(application)
        binding.vm = loginViewModel
        binding.lifecycleOwner = this
        binding.executePendingBindings()
    }
}