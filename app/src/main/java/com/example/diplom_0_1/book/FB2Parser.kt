package com.example.diplom_0_1.book

import android.util.Log
import org.xml.sax.Attributes
import org.xml.sax.helpers.DefaultHandler

class FB2Parser() : DefaultHandler() {

    private val DESCRIPTION = "description"
    private val AUTHOR = "author"
    private val BOOKTITLE = "book-title"
    private val TITLEINFO = "title-info"
    private val TITLE = "title"
    private val SECTION = "section"

    private val BODY = "body"
    private val PARAGRAPH = "p"

    private var authorName = StringBuilder("")
    private var bookName = StringBuilder("")
    private var result = StringBuilder("")

    private var pagesList = mutableListOf<String>()
    private var pageStrBuilder = StringBuilder("")

    private var inAuthor = false
    private var inBookTitle = false
    private var inTitleInfo = false
    private var inTitle = false
    private var inSection = false

    private var inBody = false
    private var inParagraph = false


    private val pageLen = 500

    public fun getBookAnnotation() : BookAnnotation {
        val authorName = authorName.toString().replace("\\s+".toRegex(), " ").trim()
        val bookName = bookName.toString().replace("\\s+".toRegex(), " ").trim()
        return BookAnnotation(-1, bookName, authorName, null, 0)
    }

    public fun getBookBodyParagraphes() : List<String> {
        return pagesList.toList()
    }

    override fun startDocument() {
        //super.startDocument()
//        if (result.length > 0) {
//            result = StringBuilder("")
//        }
        if (authorName.length > 0) {
            authorName = StringBuilder("")
        }
        if (bookName.length > 0) {
            bookName = StringBuilder("")
        }
        if (pagesList.size > 0) {
            pagesList.clear()
        }
        if (pageStrBuilder.length > 0) {
            pageStrBuilder = StringBuilder("")
        }
    }

    override fun endDocument() {
        //super.endDocument()
        //result.trim()
    }

    override fun startElement(
        uri: String?,
        localName: String?,
        qName: String?,
        attributes: Attributes?
    ) {
        //super.startElement(uri, localName, qName, attributes)
        val name = qName ?: ""
        setElementStatus(name, true)
    }

    override fun endElement(uri: String?, localName: String?, qName: String?) {
        //super.endElement(uri, localName, qName)
        val name = qName ?: ""
        setElementStatus(name, false)
        when (name) {
            SECTION -> {
                while (pageStrBuilder.length > pageLen) {
                    var subLen = pageLen - 1
                    while (subLen < pageStrBuilder.length) {
                        if (pageStrBuilder[subLen].isWhitespace()) {
                            break
                        }
                        ++subLen
                    }
                    val page = pageStrBuilder.substring(0, subLen)
                    val ost = pageStrBuilder.substring(subLen)
                    pagesList.add(page)
                    pageStrBuilder = StringBuilder(ost)
                }
                if (pageStrBuilder.length > 0) {
                    pagesList.add(pageStrBuilder.toString())
                    pageStrBuilder = StringBuilder("")
                }
            }
            PARAGRAPH -> {
                while (pageStrBuilder.length > pageLen) {
                    var subLen = pageLen - 1
                    while (subLen < pageStrBuilder.length) {
                        if (pageStrBuilder[subLen].isWhitespace()) {
                            break
                        }
                        ++subLen
                    }
                    val page = pageStrBuilder.substring(0, subLen)
                    val ost = pageStrBuilder.substring(subLen)
                    pagesList.add(page)
                    pageStrBuilder = StringBuilder(ost)
                }
            }
        }
    }

    override fun characters(ch: CharArray?, start: Int, length: Int) {
        //super.characters(ch, start, length)
//        if (!inTitleInfo) {
//            return
//        }
        if (inAuthor && inTitleInfo) {
            authorName.append(ch, start, length)
        }
        if (inBookTitle) {
            bookName.append(ch, start, length)
        }
        if (inSection && inParagraph) {

            pageStrBuilder.append(ch, start, length)
            //Log.i("FB2Parser", pageStrBuilder.toString())
//            if (pageStrBuilder.length > pageLen) {
//                val page = pageStrBuilder.substring(0, pageLen)
//                val ost = pageStrBuilder.substring(pageLen)
//                pagesList.add(page)
//                pageStrBuilder = StringBuilder(ost)
//            }
        }
    }

    private fun setElementStatus(el : String, status : Boolean) {
        when (el) {
            AUTHOR -> {
                inAuthor = status
            }

            BOOKTITLE -> {
                inBookTitle = status
            }

            TITLEINFO -> {
                inTitleInfo = status
            }

            BODY -> {
                inBody = status
            }

            PARAGRAPH -> {
                inParagraph = status
            }

            SECTION -> {
                inSection = status
            }
        }
    }
}