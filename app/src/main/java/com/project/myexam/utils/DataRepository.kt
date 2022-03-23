package com.project.myexam.utils

import android.util.Log
import androidx.fragment.app.FragmentActivity
import com.project.myexam.R
import org.json.JSONArray
import org.json.JSONException
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

object DataRepository {

    fun addItemFromJSON(dataList: ArrayList<DataModel>, activity: FragmentActivity?) {
        try {
            val jsonDataString: String = readJSONDataFromFile(activity)
            val jsonArray = JSONArray(jsonDataString)
            for (i in 0 until jsonArray.length()) {
                val itemObj = jsonArray.getJSONObject(i)
                val name = itemObj.getString("name")
                val description = itemObj.getString("description")
                val address = itemObj.getString("address")
                val image = itemObj.getString("image")
                val latitude = itemObj.getDouble("latitude")
                val longitude = itemObj.getDouble("longitude")
                val tempName = itemObj.getString("tempName")
                val schedule = itemObj.getString("schedule")
                val model = DataModel(name, description, address, image, latitude, longitude, tempName, schedule)
                dataList.add(model)
            }
        } catch (e: JSONException) {
            Log.d("Error", "addItemsFromJSON: ", e)
        } catch (e: IOException) {
            Log.d("Error", "addItemsFromJSON: ", e)
        }
    }

    @Throws(IOException::class)
    private fun readJSONDataFromFile(activity: FragmentActivity?): String {
        var inputStream: InputStream? = null
        val builder = StringBuilder()
        try {
            var jsonString: String?
            inputStream = activity?.resources?.openRawResource(R.raw.data)
            val bufferedReader = BufferedReader(
                InputStreamReader(inputStream, "UTF-8")
            )
            while (bufferedReader.readLine().also { jsonString = it } != null) {
                builder.append(jsonString)
            }
        } finally {
            inputStream?.close()
        }
        return String(builder)
    }

}