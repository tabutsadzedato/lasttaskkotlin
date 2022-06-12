package com.example.myapplication

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log


class AppDatabase(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onConfigure(db: SQLiteDatabase) {
        super.onConfigure(db)
        db.setForeignKeyConstraintsEnabled(true)
    }

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_ACTIONS_TABLE = "CREATE TABLE " + TABLE_ACTIONS +
                "(" +
                KEY_ACTION_ID + " INTEGER PRIMARY KEY," +
                KEY_ACTION_TIME + " LONG," +
                KEY_ACTION_STATE + " TEXT" +
                ")"

        db.execSQL(CREATE_ACTIONS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS $TABLE_ACTIONS")
            onCreate(db)
        }
    }

    fun addAction(action: Action) {
        val db = writableDatabase
        db.beginTransaction()
        try {
            val values = ContentValues()
            values.put(KEY_ACTION_TIME, action.time)
            values.put(KEY_ACTION_STATE, action.state)

            db.insertOrThrow(TABLE_ACTIONS, null, values)
            db.setTransactionSuccessful()
        } catch (e: Exception) {
            Log.d("AddAction", "Error while inserting new action")
        } finally {
            db.endTransaction()
        }
    }

    @SuppressLint("Range")
    fun getActions(): List<Action> {
        val actions = mutableListOf<Action>()
        val QUERY = String.format("SELECT * FROM $TABLE_ACTIONS")

        val db = readableDatabase
        val cursor: Cursor? = db.rawQuery(QUERY, null)
        try {
            if (cursor?.moveToFirst() == true) {
                do {
                    val time = cursor.getLong(cursor.getColumnIndex(KEY_ACTION_TIME))
                    val state = cursor.getString(cursor.getColumnIndex(KEY_ACTION_STATE))
                    val action = Action(time, state)
                    actions.add(action)
                } while (cursor.moveToNext())
            }
        } catch (e: java.lang.Exception) {
            Log.d("getActions", "Error while querying get actions")
        } finally {
            if (cursor != null && !cursor.isClosed) {
                cursor.close()
            }
        }
        return actions
    }

    companion object {
        private const val DATABASE_NAME = "postsDatabase"
        private const val DATABASE_VERSION = 1
        private const val TABLE_ACTIONS = "actions"
        private const val KEY_ACTION_ID = "id"
        private const val KEY_ACTION_TIME = "time"
        private const val KEY_ACTION_STATE = "state"

        private var instance: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            return instance ?: AppDatabase(context).also { instance = it }
        }
    }
}