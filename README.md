# 🔑 LoginApp - 登录与个人中心应用

这是一个基于 Android Kotlin 语言和 SQLite 数据库实现的简单登录和个人中心应用，旨在练习 Android 的基本组件、页面跳转、数据存储（SQLite 和 SharedPreferences）以及 UI 布局。



## 📝 项目要求

本项目根据课程学习内容，完成了以下两个核心页面的设计与实现：

### 一、 登录页面 (LoginActivity)

| 功能点 | 要求描述 | 实现技术 |
| :--- | :--- | :--- |
| **启动流程** | App 启动后首先进入登录页面。 | `AndroidManifest.xml` 配置 |
| **用户输入** | 使用 `EditText` 组件输入用户信息（邮箱）和密码。 | `item_login_email.xml`, `item_login_secret.xml` |
| **数据存储** | 账号和密码采用 **SQLite 数据库**存储，并通过程序预埋初始账号用于验证。 | `UserDatabaseHelper.kt` |
| **验证逻辑** | 具备非空验证、邮箱格式验证以及数据库查询验证。 | `LoginActivity.kt` |
| **成功跳转** | 登录成功后跳转至个人中心页面，并使用 `SharedPreferences` 存储登录的用户名。 | `LoginActivity.kt`, `ProfileActivity.kt` |
| **密码显示** | 提供按钮（眼睛图标）切换密码的可见性。 | `LoginActivity.kt` (`togglePasswordVisibility` 方法) |
| **第三方登录** | 微信登录和 Apple 登录仅实现 UI 和点击事件，点击时触发 `Toast` 提示。 | `activity_login.xml`, `LoginActivity.kt` |

### 二、 个人中心页面 (ProfileActivity)

| 功能点 | 要求描述 | 实现技术 |
| :--- | :--- | :--- |
| **头像显示** | 顶栏显示**圆形头像**。 | `activity_profile.xml` (`ImageView` 与 `circle_background.xml`) |
| **数据存取** | 用户名称、签名等信息使用 **SharedPreferences** 存取（本项目中主要读取登录时存储的用户名）。 | `ProfileActivity.kt` (`loadUserData` 方法) |
| **功能列表** | 每个信息条目 (`LinearLayout`) 具备**全条目可点击**功能。 | `activity_profile.xml` (`item_personal_info` 等) |
| **点击反馈** | 点击列表项后弹出 `Toast` 提示，模拟功能跳转。 | `ProfileActivity.kt` (`setupClickListeners` 方法) |

## 🛠️ 技术实现与文件结构

### 1. Kotlin 代码 (`.kt` 文件)

| 文件名 | 职责描述 | 核心实现 |
| :--- | :--- | :--- |
| `LoginActivity.kt` | **登录页面的核心逻辑**。 | 包含数据库操作、输入验证、密码可见性切换 (`togglePasswordVisibility`)，以及登录成功后的 `SharedPreferences` 写入和页面跳转。 |
| `ProfileActivity.kt` | **个人中心页面的核心逻辑**。 | 从 `SharedPreferences` 读取用户名 (`loadUserData`)，初始化并设置功能列表项的点击事件 (`setupClickListeners`)，点击后弹出 `Toast` 提示。|
| `UserDatabaseHelper.kt` | **SQLite 数据库管理**。 | 负责创建 `users` 表，**预埋**初始账号 `1234567890@qq.com` 和密码 `123456`，以及提供 `checkUser` 方法用于登录验证。|
| `MainActivity.kt` | 项目默认的启动 Activity，已集成 `EdgeToEdge` 兼容性设置。 | - |

### 2. XML 布局 (`.xml` 文件)

| 文件名 | 职责描述 | 关键点 |
| :--- | :--- | :--- |
| `activity_login.xml` | **登录页面的整体布局**。 | 使用 `include` 引入输入卡片；包含忘记密码、注册 `TextView`；微信和 Apple 登录使用 `LinearLayout` 模拟按钮。|
| `activity_profile.xml` | **个人中心页面的整体布局**。 | 顶部蓝色背景；圆形头像 (`circle_background.xml`)；功能列表项均使用**可点击的 `LinearLayout`**。|
| `item_login_email.xml` | **邮箱输入卡片**。 | 使用 `CardView` 实现圆角卡片效果；`EditText` 设置 `inputType="textEmailAddress"`。|
| `item_login_secret.xml` | **密码输入卡片**。 | 使用 `CardView`；包含一个 `ImageButton` 用于切换密码可见性，`EditText` 设置 `inputType="textPassword"`。|

### 3. Drawable 资源

* **`button_primary.xml`**: 蓝色背景、圆角的主按钮样式。
* **`button_secondary.xml`**: 白色背景、灰色边框的次要按钮样式。
* **`circle_background.xml`**: 用于头像的白色圆形背景。
* **`edittext_border.xml`**: 用于输入框的白色背景和圆角。

## ⚙️ 如何运行

1.  使用 Android Studio 打开本项目。
2.  确保您的环境已配置 Kotlin 和 Gradle。
3.  直接在模拟器或真机上运行项目。
4.  **初始登录账号：** `1234567890@qq.com`，密码：`123456`。