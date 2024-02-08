package com.example.diplom_0_1.translate

import android.util.Log
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import javax.xml.parsers.SAXParserFactory

object Translator {
    @JvmStatic
    private var currentTranslatings : List<Translating> = listOf(Translating())

    @JvmStatic
    fun translate(word : String) {
        Thread {
            val url = URL("https://dictionary.yandex.net/api/v1/dicservice/lookup?key=dict.1.1.20231219T204207Z.9540b67d9cf706ca.ce8ca752ce01b3e75303d82a10f1404b8e4fab94&lang=en-ru&text=$word&flags=4")

            val con = url.openConnection() as HttpURLConnection
            con.setRequestMethod("GET")

            val responseCode = con.responseCode
            println("GET Response Code :: $responseCode")
            if (responseCode == HttpURLConnection.HTTP_OK) {
                val iinn = BufferedReader(InputStreamReader(con.inputStream))
                var inputLine: String?
                val response = StringBuffer()
                while (iinn.readLine().also { inputLine = it } != null) {
                    response.append(inputLine)
                }
                iinn.close()

                parseResponse(response.toString())
            } else {

            }
        }.start()
    }

    @JvmStatic
    fun getTranslatings() : List<Translating> {
        return currentTranslatings
    }

    @JvmStatic
    private fun parseResponse(response : String) {
        val factory = SAXParserFactory.newInstance()
        val saxParser = factory.newSAXParser()
        val responseParser = ResponseParser()
        saxParser.parse(response.byteInputStream(), responseParser)
        currentTranslatings = responseParser.getTranslatings()
    }
}