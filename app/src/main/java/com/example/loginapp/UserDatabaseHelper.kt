package com.example.loginapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class UserDatabaseHelper(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE)
        // 预埋一个账号
        val values = ContentValues()
        values.put(COLUMN_USERNAME, "1234567890@qq.com")
        values.put(COLUMN_PASSWORD, "123456")
        db.insert(TABLE_USERS, null, values)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS)
        onCreate(db)
    }

    // 验证账号密码
    fun checkUser(username: String?, password: String?): Boolean {
        val db = this.getReadableDatabase()
        val query = "SELECT * FROM " + TABLE_USERS +
                " WHERE " + COLUMN_USERNAME + " = ? AND " + COLUMN_PASSWORD + " = ?"
        val cursor = db.rawQuery(query, arrayOf<String?>(username, password))
        val exists = cursor.getCount() > 0
        cursor.close()
        return exists
    }

    companion object {
        private const val DATABASE_NAME = "user.db"
        private const val DATABASE_VERSION = 3

        const val TABLE_USERS: String = "users"
        const val COLUMN_ID: String = "_id"
        const val COLUMN_USERNAME: String = "username"
        const val COLUMN_PASSWORD: String = "password"

        private val CREATE_TABLE = "CREATE TABLE " + TABLE_USERS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USERNAME + " TEXT NOT NULL, " +
                COLUMN_PASSWORD + " TEXT NOT NULL);"
    }
}