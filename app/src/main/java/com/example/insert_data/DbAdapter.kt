package com.example.insert_data

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DbAdapter (context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {

        val createTableQuery =
            "CREATE TABLE $TABLE_NAME (" +
                    "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$COLUMN_NAME TEXT NOT NULL, " +
                    "$COLUMN_EMAIL TEXT UNIQUE NOT NULL, " +
                    "$COLUMN_CONTACT TEXT UNIQUE NOT NULL, " +
                    "$COLUMN_ADDRESS TEXT NOT NULL)"
        db!!.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertPerson(person: Person) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_NAME, person.name)
        contentValues.put(COLUMN_EMAIL, person.email)
        contentValues.put(COLUMN_CONTACT, person.contact)
        contentValues.put(COLUMN_ADDRESS, person.address)

        // Insert data into the database
        db.insert(TABLE_NAME, null, contentValues)
        db.close()
    }

    @SuppressLint("Range")
    fun getAllPersons(): List<Person> {
        val personList = mutableListOf<Person>()
        val selectQuery = "SELECT * FROM $TABLE_NAME"
        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)



        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID))
                val name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
                val email = cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL))
                val contact = cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT))
                val address = cursor.getString(cursor.getColumnIndex(COLUMN_ADDRESS))

                val person = Person(id, name, email, contact, address)
                personList.add(person)
            } while (cursor.moveToNext())
        }


        cursor.close()
        db.close()
        return personList

    }
    //update and delete code from here
    @SuppressLint("Range")
// DbHelper.kt
    fun getPersonById(personId: Int): Person {
        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_NAME,
            null,
            "$COLUMN_ID = ?",
            arrayOf(personId.toString()),
            null,
            null,
            null
        )

        var person = Person(-1, "", "", "", "") // Default values in case person is not found

        if (cursor.moveToFirst()) {
            val id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID))
            val name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
            val email = cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL))
            val contact = cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT))
            val address = cursor.getString(cursor.getColumnIndex(COLUMN_ADDRESS))

            person = Person(id, name, email, contact, address)
        }

        cursor.close()
        db.close()

        return person
    }

    fun updatePerson(person: Person) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_NAME, person.name)
        contentValues.put(COLUMN_EMAIL, person.email)
        contentValues.put(COLUMN_CONTACT, person.contact)
        contentValues.put(COLUMN_ADDRESS, person.address)

        db.update(TABLE_NAME, contentValues, "$COLUMN_ID = ?", arrayOf(person.id.toString()))
        db.close()
    }

    fun deletePerson(personId: Int) {
        val db = this.writableDatabase
        db.delete(TABLE_NAME, "$COLUMN_ID = ?", arrayOf(personId.toString()))
        db.close()
    }

    companion object {
        // here we have defined variables for our database

        // below is variable for database name
        private val DATABASE_NAME = "GEEKS_FOR_GEEKS"

        // below is the variable for database version
        private val DATABASE_VERSION = 1

        // below is the variable for table name
        val TABLE_NAME = "gfg_table"

        // below is the variable for id column
        val COLUMN_ID = "id"

        // below is the variable for name column
        val COLUMN_NAME = "name"

        // below is the variable for age column
        val COLUMN_EMAIL = "email"

        val COLUMN_CONTACT="Contact"

        val COLUMN_ADDRESS="Xyz"
    }
}

