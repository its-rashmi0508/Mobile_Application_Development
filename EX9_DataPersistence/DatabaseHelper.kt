package com.example.datapersistenceapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "StudentDB.db"
        const val DATABASE_VERSION = 1
        const val TABLE_NAME = "students"
        const val COL_1 = "ID"
        const val COL_2 = "NAME"
        const val COL_3 = "COURSE"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = ("CREATE TABLE " + TABLE_NAME + " ("
                + COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_2 + " TEXT, "
                + COL_3 + " TEXT)")
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    // Insert Data
    fun insertData(name: String, course: String): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COL_2, name)
            put(COL_3, course)
        }
        return db.insert(TABLE_NAME, null, contentValues)
    }

    // Read Data (Get All)
    fun getAllData(): Cursor {
        val db = this.writableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME", null)
    }

    // Update Data
    fun updateData(id: String, name: String, course: String): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COL_1, id)
            put(COL_2, name)
            put(COL_3, course)
        }
        val result = db.update(TABLE_NAME, contentValues, "$COL_1 = ?", arrayOf(id))
        return result > 0
    }

    // Delete Data
    fun deleteData(id: String): Int {
        val db = this.writableDatabase
        return db.delete(TABLE_NAME, "$COL_1 = ?", arrayOf(id))
    }
}
