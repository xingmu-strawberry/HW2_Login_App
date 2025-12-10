package com.example.loginapp

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.InputType
import android.text.SpannableString
import android.text.Spannable
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    private var etEmail: EditText? = null  // 邮箱输入框
    private var etPassword: EditText? = null  // 密码输入框
    private var btnTogglePassword: ImageButton? = null  // 密码显示/隐藏按钮
    private var btnLogin: Button? = null
    private var btnWechat: LinearLayout? = null  // 改为 LinearLayout
    private var btnApple: LinearLayout? = null    // 改为 LinearLayout
    private var dbHelper: UserDatabaseHelper? = null
    private var tvForgotPassword: TextView? = null
    private var tvRegister: TextView? = null

    private var isPasswordVisible = false  // 密码可见性状态

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // 1. 获取邮箱输入组件（来自email_input_card）
        val emailInputCard = findViewById<View>(R.id.email_input_card)
        etEmail = emailInputCard.findViewById(R.id.et_username)  // 邮箱输入框

        // 2. 获取密码输入组件（来自password_input_card）
        val passwordInputCard = findViewById<View>(R.id.password_input_card)
        etPassword = passwordInputCard.findViewById(R.id.et_username)  // 密码输入框
        btnTogglePassword = passwordInputCard.findViewById(R.id.btn_toggle_password)  // 眼睛按钮

        // 3. 获取其他视图组件
        btnLogin = findViewById(R.id.btn_login)
        btnWechat = findViewById(R.id.btn_wechat)
        btnApple = findViewById(R.id.btn_apple)
        // 获取忘记密码TextView
        tvForgotPassword = findViewById(R.id.tv_forgot_password)
        // 获取注册TextView
        tvRegister = findViewById(R.id.tv_register)

        dbHelper = UserDatabaseHelper(this)

        // 设置注册文字高亮和点击事件
        tvRegister?.let {
            val spannable = SpannableString("还没有账户？立即注册")
            spannable.setSpan(
                ForegroundColorSpan(Color.BLUE),
                6,  // "立即注册"开始位置
                10, // "立即注册"结束位置
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            it.text = spannable

            it.setOnClickListener {
                Toast.makeText(this, "跳转到注册", Toast.LENGTH_SHORT).show()
                // 跳转到注册页面
                // val intent = Intent(this, RegisterActivity::class.java)
                // startActivity(intent)
            }
        }

        // 设置密码显示/隐藏的点击事件
        btnTogglePassword?.setOnClickListener {
            togglePasswordVisibility()
        }

        btnLogin?.setOnClickListener {
            val email = etEmail?.text.toString().trim()  // 添加trim()去除空格
            val password = etPassword?.text.toString().trim()  // 添加trim()去除空格

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "请填写完整信息", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 可以添加邮箱格式验证
            if (!isValidEmail(email)) {
                Toast.makeText(this, "请输入有效的邮箱地址", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 测试数据库查询
            val isExist = dbHelper?.checkUser(email, password) ?: false
            Log.d("LoginDebug", "checkUser返回值: $isExist")

            if (isExist) {
                Log.d("LoginDebug", "登录成功")
                getSharedPreferences("user_prefs", MODE_PRIVATE)
                    .edit()
                    .putString("username", email)
                    .apply()

                startActivity(Intent(this, ProfileActivity::class.java))
                finish()
            } else {
                Log.d("LoginDebug", "登录失败")
                Toast.makeText(this, "账号或密码错误", Toast.LENGTH_SHORT).show()
            }
        }

        // 设置点击事件（方法1：通过setOnClickListener）
        tvForgotPassword?.setOnClickListener {  // 添加安全调用操作符?
            onForgotPasswordClick()
        }

        btnWechat?.setOnClickListener {
            Toast.makeText(this, "微信登录（模拟）", Toast.LENGTH_SHORT).show()
        }

        btnApple?.setOnClickListener {
            Toast.makeText(this, "Apple登录（模拟）", Toast.LENGTH_SHORT).show()
        }
    }

    // 添加这个方法到LoginActivity
    private fun getAllUsersFromDB(dbHelper: UserDatabaseHelper): String {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM users", null)
        val result = StringBuilder("用户列表:\n")

        while (cursor.moveToNext()) {
            val id = cursor.getInt(0)
            val username = cursor.getString(1)
            val password = cursor.getString(2)
            result.append("ID: $id, 用户: '$username', 密码: '$password'\n")
        }
        cursor.close()
        return result.toString()
    }

    private fun togglePasswordVisibility() {
        isPasswordVisible = !isPasswordVisible

        if (etPassword == null) return

        if (isPasswordVisible) {
            // 显示密码
            etPassword?.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            btnTogglePassword?.setImageResource(R.drawable.ic_visibility)  // 眼睛睁开图标
            // 将光标移动到文本末尾
            etPassword?.setSelection(etPassword?.text?.length ?: 0)
        } else {
            // 隐藏密码
            etPassword?.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            btnTogglePassword?.setImageResource(R.drawable.ic_visibility_off)  // 眼睛关闭图标
            // 将光标移动到文本末尾
            etPassword?.setSelection(etPassword?.text?.length ?: 0)
        }
    }

    // 简单的邮箱格式验证函数
    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    // 忘记密码的点击事件
    private fun onForgotPasswordClick() {
        // 验证是否有邮箱输入（可选）
        val email = etEmail?.text.toString().trim()

        if (email.isEmpty()) {
            Toast.makeText(this, "请输入邮箱地址", Toast.LENGTH_SHORT).show()
            return
        }

        if (!isValidEmail(email)) {
            Toast.makeText(this, "请输入有效的邮箱地址", Toast.LENGTH_SHORT).show()
            return
        }

        // 创建Intent并传递数据
        val intent = Intent(this, ForgotPasswordActivity::class.java).apply {
            putExtra("email", email)  // 将当前邮箱传递过去
        }

        // 启动Activity
        startActivity(intent)
    }
}