package com.example.myapplication.task1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.data.NetWorkData
import com.google.gson.Gson
import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val data = JsonReader.readJsonFromAssets(this, "file.json")

        data?.routers?.forEach {
            Log.d("MyData", " ${it.routerName}")
        }

        val recyclerView: RecyclerView = findViewById(R.id.mainRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        data?.routers.let {
            if (it != null) {
                val adapter = RouterAdapter2(it)
                recyclerView.adapter = adapter
            }


//            it.routers.forEach{
//
//            }
        }


    }


}


object JsonReader {

    fun readJsonFromAssets(context: Context, fileName: String): NetWorkData? {
        val gson = Gson()
        var json: String? = null
        try {
            // Read the JSON file from the assets folder
            val inputStream = context.assets.open(fileName)
            val bufferedReader = BufferedReader(InputStreamReader(inputStream))
            val stringBuilder = StringBuilder()
            var line: String?
            while (bufferedReader.readLine().also { line = it } != null) {
                stringBuilder.append(line)
            }
            json = stringBuilder.toString()
            bufferedReader.close()
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }

        // Parse JSON using Gson into a list of MyData objects
        return gson.fromJson(json, NetWorkData::class.java)
    }
}




