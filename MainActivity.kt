package com.example.sqlitekotlin

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.example.sqlitekotlin.database.AutorModel
import com.example.sqlitekotlin.database.SqliteHelper

class MainActivity : AppCompatActivity() {

    lateinit var db: SQLiteDatabase
    lateinit var txtNombre:EditText
    lateinit var txtDesc:EditText
    lateinit var txtLista: TextView
    lateinit var dbHelper:SqliteHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dbHelper = SqliteHelper(this)
        db = dbHelper.writableDatabase      // Lectura y escritura
        //db = dbHelper.readableDatabase    // Solo lectura


        initView()
    }

    fun initView() {
        txtNombre = findViewById(R.id.txt_nombre)
        txtDesc = findViewById(R.id.txt_desc)
        txtLista = findViewById(R.id.txt_listado)
        cargarDatos()
        //Log.d("DATA",data)
    }

    fun cargarDatos(){
        val lista = dbHelper.listado
        var data:String = ""
        lista.forEach{
            data += "ID: ${it.id} - ${it.nombre} - ${it.descripcion} \n"
        }
        txtLista.setText(data)
    }

    fun agregar(view: View) {
        val values = ContentValues().apply {
            put("nombre", txtNombre.text.toString())
            put("descripcion", txtDesc.text.toString())
        }
        val newRowId = db.insert("autor", null, values)

        // Limpiar
        txtNombre.setText("")
        txtDesc.setText("")

        //Actualizar lista
        cargarDatos()
    }
}
