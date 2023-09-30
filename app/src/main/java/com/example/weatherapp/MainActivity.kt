package com.example.weatherapp

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import org.json.JSONObject
import java.lang.Exception
import java.net.URL
import java.nio.channels.AsynchronousByteChannel
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {
    val CITY:String = "Sharjah"
    val API:String = "c0d10b07686e6242a760e864eee31a32"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        weatherTask().execute()
    }
    inner class weatherTask():AsyncTask<String,Void,String>(){
        override fun onPreExecute() {
            super.onPreExecute()
            findViewById<ProgressBar>(R.id.loader).visibility = View.VISIBLE
            findViewById<RelativeLayout>(R.id.mainContainer).visibility = View.GONE
            findViewById<TextView>(R.id.errortext).visibility = View.GONE
        }

        override fun doInBackground(vararg p0: String?): String? {
            var response:String?
            try{
                response = URL("https://api.openweathermap.org/data/3.0/onecall?q=$CITY&units=metric&appid=$API").readText(Charsets.UTF_8)
            }
            catch(e: Exception){
                response = null
            }
            return response
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            try{
                val jsonObj = JSONObject(result)
                val main = jsonObj.getJSONObject("main")
                val sys = jsonObj.getJSONObject("sys")
                val wind = jsonObj.getJSONObject("wind")
                val weather = jsonObj.getJSONObject("weather").getJSONObject(0.toString())//its supposed to show index , but i changed to toString()
                val updatedAt: JSONObject = jsonObj.getJSONObject("dt")//type was long , i changed to JSONObect
                val updatedAtText = "Updated at: "+SimpleDateFormat("dd/mm/yyyy hh:mm a", Locale.ENGLISH).format(
                    Date(updatedAt*1000))
            }
        }
    }
}