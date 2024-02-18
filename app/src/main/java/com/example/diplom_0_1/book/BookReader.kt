package com.example.diplom_0_1.book


import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import android.webkit.MimeTypeMap
import com.example.diplom_0_1.db.BookDAO
import com.example.diplom_0_1.setting.SettingsUtils
import com.example.diplom_0_1.test.TestUtils
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.util.Locale
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
    private var image = StringBuilder("")
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
    fun getImage() = image

    @JvmStatic
    fun setCurrentBook(_uri : Uri, _page : Int, context: Context?, _bookId : Int = -1) {
        uri = _uri

        parseBook(uri!!, context)
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
        if (getMimeType(BookReader.uri!!, context) == "text/plain") {
            val pages = mutableListOf<String>()
            val pageLen = SettingsUtils.getLetterCount()
            val text = parsePlainText(uri, context)
            var start = 0;
            while (start + pageLen < text.length) {
                var endWithoutRazryv = start + pageLen - 1;
                while (!text[endWithoutRazryv].isWhitespace()) {
                    --endWithoutRazryv
                }
                pages.add(text.substring(start, endWithoutRazryv))
                start = endWithoutRazryv
            }
            pages.add(text.substring(start, text.length))
            bookBodyPages = pages.toList()
            bookAnnotation = BookAnnotation(-1, getFileName(uri, context), "no author", uri, 0, "")
            image = StringBuilder("")
        } else {
            val factory = SAXParserFactory.newInstance()
            val saxParser = factory.newSAXParser()
            val fB2Parser = FB2Parser()
            context?.applicationContext?.contentResolver?.openInputStream(uri)?.use { inputStream ->
                saxParser.parse(inputStream, fB2Parser) }
            bookAnnotation = fB2Parser.getBookAnnotation()
            bookBodyPages = fB2Parser.getBookBodyParagraphes()
            image = fB2Parser.getImage()
            bookAnnotation?.uri = uri
        }
    }

    @JvmStatic
    private fun parsePlainText(uri: Uri, context: Context?): String {
        val stringBuilder = StringBuilder("")
        context?.contentResolver?.openInputStream(uri)?.use { inputStream ->
            BufferedReader(InputStreamReader(inputStream)).use { reader ->
                var line: String? = reader.readLine()
                while (line != null) {
                    stringBuilder.append(line)
                    line = reader.readLine()
                }
            }
        }
        return stringBuilder.toString()
    }
    private fun getMimeType(uri: Uri, context : Context?): String? {
        var mimeType : String? = ""
        if (uri.scheme == ContentResolver.SCHEME_CONTENT) {
            val cr: ContentResolver? = context?.contentResolver
            mimeType = cr?.getType(uri)
        } else {
            val fileExtension = MimeTypeMap.getFileExtensionFromUrl(
                uri.toString()
            )
            mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(
                fileExtension.lowercase(Locale.getDefault())
            )
        }
        return mimeType ?: ""
    }
    private fun getFileName(uri: Uri, context : Context?) : String{
        var fileName = "no name"
        val cursor = context?.contentResolver?.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val i = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (i >= 0) {
                    fileName = it.getString(i)
                }
            }
        }
        return fileName
    }
}