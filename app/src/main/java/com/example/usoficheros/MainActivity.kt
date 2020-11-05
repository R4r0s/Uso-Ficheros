package com.example.usoficheros

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {
    var id = "0"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val objetoIntent: Intent = intent
        id = objetoIntent.getStringExtra("id").toString()

        var botonLogin = findViewById<Button>(R.id.botonLogin)
        var botonInformacion = findViewById<Button>(R.id.botonInformacion)
        botonInformacion.setEnabled(false)
        botonLogin.setEnabled(false)

        var fileName = "JsonText.json"

        var noText = "{\"users\": []}"

        var file = File(getFilesDir().getAbsolutePath(), fileName)
        if (!file.exists()) {
            var fileOutput = openFileOutput(fileName, Context.MODE_PRIVATE)
            fileOutput.write(noText.toByteArray())
            fileOutput.close()
        }
    }

    override fun onStart() {
        var botonLogin = findViewById<Button>(R.id.botonLogin)
        var nombreFichero = "JsonText.json"


        var bufferedReader = BufferedReader(InputStreamReader(openFileInput(nombreFichero)))
        var textoLeido = bufferedReader.readLine()
        val todo = StringBuilder()
        while (textoLeido != null) {
            todo.append(textoLeido + "\n")
            textoLeido = bufferedReader.readLine()
        }
        bufferedReader.close()
        textoLeido = todo.toString()



        val jsonObject = JSONObject(textoLeido)
        val jsonArray = jsonObject.optJSONArray("users")
        if (!jsonArray.toString().equals("[]")) {
            botonLogin.setEnabled(true)
        }
        var botonInformacion = findViewById<Button>(R.id.botonInformacion)
        if (id != "null") {
            botonInformacion.setEnabled(true)
        }
        super.onStart()
    }

    fun registro(view: View) {
        var miIntent = Intent(this, Registro::class.java)
        startActivity(miIntent)

    }

    fun login(view: View) {
        var miIntent = Intent(this, Login::class.java)
        startActivity(miIntent)

    }

    fun informacion(view: View) {
        val objetoIntent: Intent = intent
        var miIntent = Intent(this, Informacion::class.java)
        id = objetoIntent.getStringExtra("id").toString()
        miIntent.putExtra("id", id)
        startActivity(miIntent)
    }
}