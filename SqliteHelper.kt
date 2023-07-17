package com.example.sqlitekotlin.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import android.util.Log

class SqliteHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "biblioteca.db"
        const val TABLA = "autor"
        const val COLUMNA_NOMBRE = "nombre"
        const val COLUMNA_DESCRIPCION = "descripcion"
        private const val SQL_CREATE_ENTRIES = "CREATE TABLE ${TABLA} (" +
                    "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                    "${COLUMNA_NOMBRE} TEXT," +
                    "${COLUMNA_DESCRIPCION} TEXT)"
        private const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${TABLA}"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    val listado: List<AutorModel>
        get() {
            val lista = ArrayList<AutorModel>()
            val db = writableDatabase
            val selectQuery = "SELECT * FROM $TABLA"
            val cursor = db.rawQuery(selectQuery, null)
            if (cursor != null) {
                cursor.moveToFirst()
                while (cursor.moveToNext()) {
                    val autor = AutorModel()
                    autor.id = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("_id")))
                    autor.nombre = cursor.getString(cursor.getColumnIndexOrThrow(COLUMNA_NOMBRE))
                    autor.descripcion = cursor.getString(cursor.getColumnIndexOrThrow(COLUMNA_DESCRIPCION))
                    lista.add(autor)
                }
            }
            cursor.close()
            return lista
        }


}
