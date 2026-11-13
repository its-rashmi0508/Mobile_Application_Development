package com.example.ecotrack

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class WaterDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_AMOUNT INTEGER NOT NULL,
                $COLUMN_TIMESTAMP TEXT NOT NULL,
                $COLUMN_DATE TEXT NOT NULL,
                $COLUMN_LATITUDE REAL NOT NULL,
                $COLUMN_LONGITUDE REAL NOT NULL,
                $COLUMN_DRINK_TYPE TEXT DEFAULT 'Water',
                $COLUMN_LOCATION_NAME TEXT DEFAULT 'Unknown Location'
            )
        """.trimIndent()
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 2) {
            try {
                db.execSQL("ALTER TABLE $TABLE_NAME ADD COLUMN $COLUMN_DRINK_TYPE TEXT DEFAULT 'Water'")
                db.execSQL("ALTER TABLE $TABLE_NAME ADD COLUMN $COLUMN_LOCATION_NAME TEXT DEFAULT 'Unknown Location'")
            } catch (e: Exception) {
                db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
                onCreate(db)
            }
        }
    }

    fun insertEntry(
        amount: Int,
        timestamp: String,
        date: String,
        latitude: Double,
        longitude: Double,
        drinkType: String = "Water",
        locationName: String = "Unknown Location"
    ): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_AMOUNT, amount)
            put(COLUMN_TIMESTAMP, timestamp)
            put(COLUMN_DATE, date)
            put(COLUMN_LATITUDE, latitude)
            put(COLUMN_LONGITUDE, longitude)
            put(COLUMN_DRINK_TYPE, drinkType)
            put(COLUMN_LOCATION_NAME, locationName)
        }
        val id = db.insert(TABLE_NAME, null, values)
        db.close()
        return id
    }

    fun getAllEntries(): List<WaterEntry> {
        val entryList = mutableListOf<WaterEntry>()
        val db = readableDatabase
        val cursor = db.query(
            TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            "$COLUMN_ID DESC"
        )

        if (cursor.moveToFirst()) {
            val idIndex = cursor.getColumnIndexOrThrow(COLUMN_ID)
            val amountIndex = cursor.getColumnIndexOrThrow(COLUMN_AMOUNT)
            val timestampIndex = cursor.getColumnIndexOrThrow(COLUMN_TIMESTAMP)
            val dateIndex = cursor.getColumnIndexOrThrow(COLUMN_DATE)
            val latIndex = cursor.getColumnIndexOrThrow(COLUMN_LATITUDE)
            val lngIndex = cursor.getColumnIndexOrThrow(COLUMN_LONGITUDE)
            val drinkTypeIndex = cursor.getColumnIndex(COLUMN_DRINK_TYPE)
            val locationNameIndex = cursor.getColumnIndex(COLUMN_LOCATION_NAME)

            do {
                val drinkType = if (drinkTypeIndex != -1) cursor.getString(drinkTypeIndex) ?: "Water" else "Water"
                val locationName = if (locationNameIndex != -1) cursor.getString(locationNameIndex) ?: "Unknown Location" else "Unknown Location"

                val entry = WaterEntry(
                    id = cursor.getLong(idIndex),
                    amount = cursor.getInt(amountIndex),
                    timestamp = cursor.getString(timestampIndex),
                    date = cursor.getString(dateIndex),
                    latitude = cursor.getDouble(latIndex),
                    longitude = cursor.getDouble(lngIndex),
                    drinkType = drinkType,
                    locationName = locationName
                )
                entryList.add(entry)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return entryList
    }

    fun getTodayTotalConsumption(todayDate: String): Int {
        var total = 0
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT SUM($COLUMN_AMOUNT) FROM $TABLE_NAME WHERE $COLUMN_DATE = ?",
            arrayOf(todayDate)
        )
        if (cursor.moveToFirst()) {
            total = cursor.getInt(0)
        }
        cursor.close()
        db.close()
        return total
    }

    fun deleteEntry(id: Long): Int {
        val db = writableDatabase
        val rows = db.delete(TABLE_NAME, "$COLUMN_ID = ?", arrayOf(id.toString()))
        db.close()
        return rows
    }

    companion object {
        private const val DATABASE_NAME = "ecotrack_water.db"
        private const val DATABASE_VERSION = 2

        const val TABLE_NAME = "water_entries"
        const val COLUMN_ID = "id"
        const val COLUMN_AMOUNT = "amount"
        const val COLUMN_TIMESTAMP = "timestamp"
        const val COLUMN_DATE = "date"
        const val COLUMN_LATITUDE = "latitude"
        const val COLUMN_LONGITUDE = "longitude"
        const val COLUMN_DRINK_TYPE = "drink_type"
        const val COLUMN_LOCATION_NAME = "location_name"
    }
}
