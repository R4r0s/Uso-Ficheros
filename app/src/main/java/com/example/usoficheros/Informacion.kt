package com.example.usoficheros

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader

class Informacion : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_informacion)
    }

    override fun onStart() {
        val objetoIntent: Intent = intent
        var id = objetoIntent.getStringExtra("id")
        var n = findViewById<EditText>(R.id.editTextTextPersonName2)
        var surrname = findViewById<EditText>(R.id.editTextTextPersonSurname2)
        var users = findViewById<EditText>(R.id.editTextTextPersonUser2)
        var pass = findViewById<EditText>(R.id.editTextTextPassword3)

        var fileName = "JsonText.json"
        var bufferedReader = BufferedReader(InputStreamReader(openFileInput(fileName)))
        var textoLeido = bufferedReader.readLine()
        val todo = StringBuilder()
        while (textoLeido != null) {
            todo.append(textoLeido + "\n")
            textoLeido = bufferedReader.readLine()
        }
        textoLeido = todo.toString()
        bufferedReader.close()

        val jsonObject = JSONObject(textoLeido)
        val jsonArray = jsonObject.optJSONArray("users")
        for (i in 0 until jsonArray.length()) {

            val jsonObject = jsonArray.getJSONObject(i)

            if (jsonObject.optString("id").equals(id)) {
                val id = jsonObject.optString("id").toInt()
                val name = jsonObject.optString("name")
                val surr = jsonObject.optString("surname")
                val user = jsonObject.optString("user")
                val pwd = jsonObject.optString("password")

                n.setText(name)
                surrname.setText(surr)
                users.setText(user)
                pass.setText(pwd)
            }
        }
        super.onStart()
    }

    fun update(view: View) {
        var num = 0
        val objetoIntent: Intent = intent
        var id = objetoIntent.getStringExtra("id")!!.toInt()
        var name = findViewById<EditText>(R.id.editTextTextPersonName2)
        var surrname = findViewById<EditText>(R.id.editTextTextPersonSurname2)
        var users = findViewById<EditText>(R.id.editTextTextPersonUser2)
        var pass = findViewById<EditText>(R.id.editTextTextPassword3)
        num = id

        var nombreFichero = "JsonText.json"
        var bufferedReader = BufferedReader(InputStreamReader(openFileInput(nombreFichero)))
        var textoLeido = bufferedReader.readLine()
        val todo = StringBuilder()
        while (textoLeido != null) {
            todo.append(textoLeido + "\n")
            textoLeido = bufferedReader.readLine()
        }
        textoLeido = todo.toString()
        bufferedReader.close()

        val jsonObject = JSONObject(textoLeido)
        val jsonArray = jsonObject.optJSONArray("users")

        jsonArray.remove(id)

        val main = JSONObject()
        main.put("id", num)
        main.put("name", name.text)
        main.put("surname", surrname.text)
        main.put("user", users.text)
        main.put("password", pass.text)
        jsonArray.put(main)
        var fileOutput = openFileOutput(nombreFichero, Context.MODE_PRIVATE)
        fileOutput.write(jsonObject.toString().toByteArray())
        fileOutput.close()
    }

    fun back(view: View) {
        onBackPressed()
    }
}