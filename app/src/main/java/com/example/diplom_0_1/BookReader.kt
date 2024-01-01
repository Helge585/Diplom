package com.example.diplom_0_1

import android.content.Context
import android.net.Uri
import android.os.Bundle
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import javax.xml.parsers.SAXParserFactory


object BookReader {
    @JvmStatic
    private  var uri : Uri? = null
    @JvmStatic
    private  var bookAnnotation: BookAnnotation? = null
    @JvmStatic
    private  var bookBody: String? = null
    @JvmStatic
    private  var bookBodyParagraphes : List<String>? = null


    @JvmStatic
    fun getBookBody() : String? {
        return bookBody
    }

    @JvmStatic
    fun getBookBodyParagraphesList() : List<String> {
        return bookBodyParagraphes ?: listOf("Nema")
    }

    @JvmStatic
    fun getBookAnnotation() : BookAnnotation? {
        return bookAnnotation
    }

    @JvmStatic
    fun setCurrentBook(_uri: Uri, context: Context?) {
        uri = _uri
        parseBook(_uri, context)
        bookBody = parsePlainTextByURI(_uri, context)
    }

    @JvmStatic
    private fun parseBook(uri: Uri, context: Context?) {
        val result = StringBuilder("")

        val factory = SAXParserFactory.newInstance()
        val saxParser = factory.newSAXParser()
        val fB2Parser = FB2Parser()
        context?.applicationContext?.contentResolver?.openInputStream(uri)?.use { inputStream ->
            saxParser.parse(inputStream, fB2Parser)
        }
        bookAnnotation = fB2Parser.getBookAnnotation()
        bookAnnotation?.uri = uri
        bookBodyParagraphes = fB2Parser.getBookBodyParagraphes()
    }

    @JvmStatic
    private fun parsePlainTextByURI(uri: Uri, context: Context?): String {
        val stringBuilder = StringBuilder("!!!!! ")
//        contentResolver.openInputStream(uri)?.use { inputStream ->
//            BufferedReader(InputStreamReader(inputStream)).use { reader ->
//                var line: String? = reader.readLine()
//                while (line != null) {
//                    stringBuilder.append(line)
//                    line = reader.readLine()
//                }
//            }
//        }

        context?.applicationContext?.contentResolver?.openInputStream(uri)?.use { inputStream ->
            val factory = XmlPullParserFactory.newInstance()
            factory.isNamespaceAware = true
            val xpp = factory.newPullParser()
            xpp.setInput(inputStream, null)

            var isInBody = false;
            var eventType = xpp.eventType
            while (eventType != XmlPullParser.END_DOCUMENT) {

                val tagName = xpp.getName()
                when (eventType) {
                    XmlPullParser.START_TAG -> {
                        if ("body".equals(tagName)) {
                            isInBody = true
                        }
                    }
                    XmlPullParser.TEXT -> {
                        if (isInBody) {
                            stringBuilder.append(xpp.text)
                        }
                    }
                    XmlPullParser.END_TAG -> {
                        if ("body".equals(tagName)) {
                            isInBody = false
                        }
                    }
                }
                eventType = xpp.next()
            }
        }
        return stringBuilder.toString()
    }
}