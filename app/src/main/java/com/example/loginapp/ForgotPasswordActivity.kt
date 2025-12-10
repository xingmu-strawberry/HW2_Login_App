package com.example.loginapp

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var etEmail: EditText
    private lateinit var btnSubmit: Button
    private lateinit var btnCancel: Button
    private lateinit var handler: Handler  // 声明Handler变量

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        // 初始化Handler
        handler = Handler(Looper.getMainLooper())

        etEmail = findViewById(R.id.et_email)
        btnSubmit = findViewById(R.id.btn_submit)
        btnCancel = findViewById(R.id.btn_cancel)

        // 获取从登录页面传递过来的邮箱（如果有）
        val intentEmail = intent.getStringExtra("email")
        if (!intentEmail.isNullOrEmpty()) {
            etEmail.setText(intentEmail)
        }

        // 提交按钮点击事件
        btnSubmit.setOnClickListener {
            val email = etEmail.text.toString().trim()

            if (email.isEmpty()) {
                Toast.makeText(this, "请输入邮箱地址", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!isValidEmail(email)) {
                Toast.makeText(this, "请输入有效的邮箱地址", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 模拟发送重置密码邮件
            Toast.makeText(this, "重置密码链接已发送到 $email", Toast.LENGTH_LONG).show()

            // 延迟返回登录页面
            handler.postDelayed({
                finish()
            }, 1500)
        }

        // 取消按钮点击事件
        btnCancel.setOnClickListener {
            finish()
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}