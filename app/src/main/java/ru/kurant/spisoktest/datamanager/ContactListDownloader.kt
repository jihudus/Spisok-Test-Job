package ru.kurant.spisoktest.datamanager

import android.util.Log
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.URL

class ContactListDownloader {

    fun downloadToString(urlString: String): String {
        val url = URL(urlString)
        var result: String?
        try {
            BufferedReader(InputStreamReader(url.openStream())).use {
                result = it.readText()
            }
            return "{\"contacts\": $result}"
        } catch (e: IOException) {
            throw IOException("Input is unavailable")
        } catch (other: Exception) {
            Log.d(TAG, "downloadToString: $other")
        }
        return "{\"contacts\": \"none\"}"
    }
}