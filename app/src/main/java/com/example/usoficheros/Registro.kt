package com.example.usoficheros

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader

class Registro : AppCompatActivity() {
    var num = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)
    }

    fun Add(view: View) {
        var nombre = findViewById<EditText>(R.id.editTextTextPersonName)
        var apellidos = findViewById<EditText>(R.id.editTextTextPersonSurname)
        var user = findViewById<EditText>(R.id.editTextTextPersonUser)
        var pass = findViewById<EditText>(R.id.editTextTextPassword)
        var fileName = "JsonText.json"
        var bufferedReader = BufferedReader(InputStreamReader(openFileInput(fileName)))
        var textoLeido = bufferedReader.readLine()
        val todo = StringBuilder()
        while (textoLeido != null) {
            todo.append(textoLeido + "\n")
            textoLeido = bufferedReader.readLine()
        }
        textoLeido = todo.toString()
        val jsonObject = JSONObject(textoLeido)
        val jsonArray = jsonObject.optJSONArray("users")
        if (jsonArray.length() == 0){
            val jsonObject = JSONObject(textoLeido)
            val jsonArray = jsonObject.optJSONArray("users")
            for (i in 0 until jsonArray.length()) {
                num = i+1
            }
            val main = JSONObject()
            main.put("id", num)
            main.put("name", nombre.text)
            main.put("surname", apellidos.text)
            main.put("user", user.text)
            main.put("password", pass.text)
            jsonArray.put(main)
            var fileOutput = openFileOutput(fileName, Context.MODE_PRIVATE)
            fileOutput.write(jsonObject.toString().toByteArray())
            fileOutput.close()
        }
        for (i in 0 until jsonArray.length()) {

            val jsonObject = jsonArray.getJSONObject(i)

            if (jsonObject.optString("user").equals(user.text.toString())) {

                val id = jsonObject.optString("id").toInt()

                val jsonObject = JSONObject(textoLeido)
                val jsonArray = jsonObject.optJSONArray("users")

                jsonArray.remove(id)

                val main = JSONObject()
                main.put("id", num)
                main.put("name", nombre.text)
                main.put("surname", apellidos.text)
                main.put("user", user.text)
                main.put("password", pass.text)
                jsonArray.put(main)
                var fileOutput = openFileOutput(fileName, Context.MODE_PRIVATE)
                fileOutput.write(jsonObject.toString().toByteArray())
                fileOutput.close()


            }
        }
        for (i in 0 until jsonArray.length()) {

            val jsonObject = jsonArray.getJSONObject(i)

            if (!jsonObject.optString("user").equals(user.text.toString())) {

                val jsonObject = JSONObject(textoLeido)
                val jsonArray = jsonObject.optJSONArray("users")
                for (i in 0 until jsonArray.length()) {
                    num = i+1
                }
                val main = JSONObject()
                main.put("id", num)
                main.put("name", nombre.text)
                main.put("surname", apellidos.text)
                main.put("user", user.text)
                main.put("password", pass.text)
                jsonArray.put(main)
                var fileOutput = openFileOutput(fileName, Context.MODE_PRIVATE)
                fileOutput.write(jsonObject.toString().toByteArray())
                fileOutput.close()
            }
        }

    }
    fun Cancel(view: View) {
        onBackPressed()
    }
}