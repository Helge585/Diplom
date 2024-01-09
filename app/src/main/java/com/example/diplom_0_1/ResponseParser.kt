package com.example.diplom_0_1

import org.xml.sax.helpers.DefaultHandler
import java.util.jar.Attributes

class ResponseParser() : DefaultHandler() {

    private val TR = "tr"
    private val EX = "ex"
    private val TEXT = "text"
    private val SYN = "syn"
    private val MEAN = "mean"

    private var inTr = false
    private var inEx = false
    private var inText = false
    private var inSyn = false
    private var inMean = false

    private var words = mutableListOf<String>()
    private var word = StringBuilder("")

    override fun startDocument() {
        //super.startDocument()
        if (words.size > 0) {
            words.clear()
        }
        if (word.length > 0) {
            word = StringBuilder("")
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
        attributes: org.xml.sax.Attributes?
    ) {
        //super.startElement(uri, localName, qName, attributes)
        setElementStatus(qName, true)
    }

    override fun endElement(uri: String?, localName: String?, qName: String?) {
        //super.endElement(uri, localName, qName)
        if (word.length > 0) {
            words.add(word.toString())
            word = StringBuilder("")
        }
        setElementStatus(qName, false)
    }

    override fun characters(ch: CharArray?, start: Int, length: Int){
        //super.characters(ch, start, length)
        if (inTr && !inEx && !inMean && !inSyn && inText) {
            word.append(ch, start, length)
        }
    }

    fun getWords() : List<String> {
        return words.toList()
    }

    private fun setElementStatus(qName : String?, status: Boolean) {
        when (qName) {
            TR -> {
                inTr = status
            }
            EX -> {
                inEx = status
            }
            TEXT -> {
                inText = status
            }
            MEAN -> {
                inMean = status
            }
            SYN -> {
                inSyn = status
            }
        }
    }
}