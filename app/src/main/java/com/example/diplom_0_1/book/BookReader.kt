package com.example.diplom_0_1.book

import android.content.Context
import android.net.Uri
import android.util.Log
import com.example.diplom_0_1.db.BookDAO
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
    private  var bookBodyPages : List<String>? = null
    @JvmStatic
    private var page = 0
    @JvmStatic
    private var bookId = -1

    @JvmStatic
    fun getBookBody() : String? {
        return bookBody
    }

    @JvmStatic
    fun getBookBodyPagesList() : List<String> {
        return bookBodyPages ?: listOf("Nema")
    }

    @JvmStatic
    fun getPage() = page

    @JvmStatic
    fun getBookAnnotation() : BookAnnotation? {
        return bookAnnotation
    }

    @JvmStatic
    fun setCurrentBook(_uri : Uri, _page : Int, context: Context?, _bookId : Int = -1) {
        uri = _uri
        parseBook(uri!!, context)
        bookBody = parsePlainTextByURI(uri!!, context)
        page = _page
        bookId = _bookId
    }
    @JvmStatic
    fun updatePage(_page : Int) {
        page = _page
        Log.i("BookReader", "page = $page")
        BookDAO.updatePage(bookId, page)
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
        bookBodyPages = fB2Parser.getBookBodyParagraphes()
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