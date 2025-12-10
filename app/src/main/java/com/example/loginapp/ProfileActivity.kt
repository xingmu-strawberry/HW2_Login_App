package com.example.loginapp

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ProfileActivity : AppCompatActivity() {

    // 顶部区域组件
    private lateinit var ivAvatar: ImageView
    private lateinit var tvUsername: TextView
    private lateinit var tvWelcome: TextView

    // 功能列表项 - 注意：这里应该是 LinearLayout，不是 ConstraintLayout
    private lateinit var itemPersonalInfo: LinearLayout
    private lateinit var itemMyCollection: LinearLayout
    private lateinit var itemBrowseHistory: LinearLayout
    private lateinit var itemSettings: LinearLayout
    private lateinit var itemAboutUs: LinearLayout
    private lateinit var itemSuggestions: LinearLayout


    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            setContentView(R.layout.activity_profile)
            Log.d("ProfileActivity", "布局加载成功")
        } catch (e: Exception) {
            Log.e("ProfileActivity", "布局加载失败", e)
            Toast.makeText(this, "页面加载失败", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // 初始化视图组件
        try {
            initViews()
        } catch (e: Exception) {
            Log.e("ProfileActivity", "初始化视图失败", e)
            Toast.makeText(this, "初始化失败，请重启应用", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // 初始化 SharedPreferences
        prefs = getSharedPreferences("user_prefs", MODE_PRIVATE)

        // 加载用户数据
        loadUserData()

        // 设置点击监听器
        setupClickListeners()

        Log.d("ProfileActivity", "页面初始化完成")
    }

    private fun initViews() {
        // 顶部区域
        ivAvatar = findViewById(R.id.iv_avatar)
        tvUsername = findViewById(R.id.tv_username)
        tvWelcome = findViewById(R.id.tv_welcome)

        // 功能列表项 - 注意：这里使用的是 LinearLayout
        itemPersonalInfo = findViewById(R.id.item_personal_info)
        itemMyCollection = findViewById(R.id.item_my_collection)
        itemBrowseHistory = findViewById(R.id.item_browse_history)
        itemSettings = findViewById(R.id.item_settings)
        itemAboutUs = findViewById(R.id.item_about_us)
        itemSuggestions = findViewById(R.id.item_suggestions)
    }

    private fun loadUserData() {
        try {
            // 从 SharedPreferences 读取数据
            val username = prefs.getString("username", "用户") ?: "用户"

            // 设置用户信息
            tvUsername.text = username

            // 根据用户数据个性化欢迎语（可选）
            val welcomeText = "欢迎来到信息App"
            tvWelcome.text = welcomeText

            Log.d("ProfileActivity", "用户数据加载完成: $username")
        } catch (e: Exception) {
            Log.e("ProfileActivity", "加载用户数据失败", e)
            Toast.makeText(this, "加载用户信息失败", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupClickListeners() {
        try {
            // 头像点击
            ivAvatar.setOnClickListener {
                Toast.makeText(this, "更换头像", Toast.LENGTH_SHORT).show()
            }

            // 用户名点击
            tvUsername.setOnClickListener {
                Toast.makeText(this, "编辑用户名", Toast.LENGTH_SHORT).show()
            }

            // 个人信息点击
            itemPersonalInfo.setOnClickListener {
                Toast.makeText(this, "查看个人信息", Toast.LENGTH_SHORT).show()
            }

            // 我的收藏点击
            itemMyCollection.setOnClickListener {
                Toast.makeText(this, "查看我的收藏", Toast.LENGTH_SHORT).show()
            }

            // 浏览历史点击
            itemBrowseHistory.setOnClickListener {
                Toast.makeText(this, "查看浏览历史", Toast.LENGTH_SHORT).show()
            }

            // 设置点击
            itemSettings.setOnClickListener {
                Toast.makeText(this, "查看设置", Toast.LENGTH_SHORT).show()
            }

            // 关于我们点击
            itemAboutUs.setOnClickListener {
                Toast.makeText(this, "查看关于我们", Toast.LENGTH_SHORT).show()
            }

            // 意见反馈点击
            itemSuggestions.setOnClickListener {
                Toast.makeText(this, "查看意见反馈", Toast.LENGTH_SHORT).show()
            }

            Log.d("ProfileActivity", "点击监听器设置完成")
        } catch (e: Exception) {
            Log.e("ProfileActivity", "设置点击监听器失败", e)
        }
    }

    override fun onResume() {
        super.onResume()
        // 当从其他页面返回时，刷新数据（例如修改了用户名）
        loadUserData()
    }
}