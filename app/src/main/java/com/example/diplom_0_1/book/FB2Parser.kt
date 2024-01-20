package com.example.diplom_0_1.book

import org.xml.sax.Attributes
import org.xml.sax.helpers.DefaultHandler

class FB2Parser() : DefaultHandler() {

    private val DESCRIPTION = "description"
    private val AUTHOR = "author"
    private val BOOKTITLE = "book-title"
    private val TITLEINFO = "title-info"

    private val BODY = "body"
    private val PARAGRAPH = "p"

    private var authorName = StringBuilder("")
    private var bookName = StringBuilder("")
    private var result = StringBuilder("")

    private var bookBodyParaghes = mutableListOf<String>()
    private var paragraphStrBuilder = StringBuilder("")

    private var inAuthor = false
    private var inBookTitle = false
    private var inTitleInfo = false

    private var inBody = false
    private var inParagraph = false

    public fun getBookAnnotation() : BookAnnotation {
        val authorName = authorName.toString().replace("\\s+".toRegex(), " ").trim()
        val bookName = bookName.toString().replace("\\s+".toRegex(), " ").trim()
        return BookAnnotation(-1, bookName, authorName, null)
    }

    public fun getBookBodyParagraphes() : List<String> {
        return bookBodyParaghes.toList()
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
        if (bookBodyParaghes.size > 0) {
            bookBodyParaghes.clear()
        }
        if (paragraphStrBuilder.length > 0) {
            paragraphStrBuilder = StringBuilder("")
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
        when (qName) {
            AUTHOR -> {
                inAuthor = true
            }

            BOOKTITLE -> {
                inBookTitle = true
            }

            TITLEINFO -> {
                inTitleInfo = true
            }

            BODY -> {
                inBody = true
            }

            PARAGRAPH -> {
                inParagraph = true
            }
        }
//        if (qName.equals(AUTHOR)) {
//            inAuthor = true
//        }
//        if (qName.equals(BOOKTITLE)) {
//            inBookTitle = true
//        }
//        if (qName.equals(TITLEINFO)) {
//            inTitleInfo = true
//        }
    }

    override fun endElement(uri: String?, localName: String?, qName: String?) {
        //super.endElement(uri, localName, qName)
        when (qName) {
            AUTHOR -> {
                inAuthor = false
            }

            BOOKTITLE -> {
                inBookTitle = false
            }

            TITLEINFO -> {
                inTitleInfo = false
            }

            BODY -> {
                inBody = false
            }

            PARAGRAPH -> {
                inParagraph = false
                if (paragraphStrBuilder.length > 0) {
                    bookBodyParaghes.add(paragraphStrBuilder.toString())
                    paragraphStrBuilder = StringBuilder("")
                }
            }
        }
//        if (qName.equals(AUTHOR)) {
//            inAuthor = false
//        }
//        if (qName.equals(BOOKTITLE)) {
//            inBookTitle = false
//            //bookName.trim()
//        }
//        if (qName.equals(TITLEINFO)) {
//            inTitleInfo = false
//        }
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
        if (inBody && inParagraph) {
            paragraphStrBuilder.append(ch, start, length)
        }
    }
}