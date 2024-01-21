package com.example.diplom_0_1.translate

import android.util.Log
import org.xml.sax.helpers.DefaultHandler

class ResponseParser() : DefaultHandler() {

    private val TR = "tr"
    private val EX = "ex"
    private val TEXT = "text"
    private val SYN = "syn"
    private val MEAN = "mean"
    private val DEF = "def"

    private var inTr = false
    private var inEx = false
    private var inText = false
    private var inSyn = false
    private var inMean = false
    private var inDef = false

    private var words = mutableListOf<String>()
    private var word = StringBuilder("")

    private var translatings = mutableListOf<Translating>()
    private var translating = Translating()
    private var currentText = StringBuilder("")

    override fun startDocument() {
        //super.startDocument()
//        if (words.size > 0) {
//            words.clear()
//        }
//        if (word.length > 0) {
//            word = StringBuilder("")
//        }
        if (translatings.size > 0) {
            translatings.clear()
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
        when (qName) {
            DEF -> {
                currentText = StringBuilder("")
                translating = Translating()
                val len  = attributes?.length ?: 0
                for (i in 0..len - 1) {
                    if (attributes?.getQName(i) == "pos") {
                        translating?.partOfSpeech = attributes.getValue(i)
                    }
                }
            }
        }
        setElementStatus(qName, true)
    }

    override fun endElement(uri: String?, localName: String?, qName: String?) {
        //super.endElement(uri, localName, qName)
//        if (word.length > 0) {
//            words.add(word.toString())
//            word = StringBuilder("")
//        }
        when (qName) {
            DEF -> {
                translatings.add(translating)
            }
            TEXT -> {
                if (!inTr) {
                    translating.word = currentText.toString()
                    currentText = StringBuilder("")
                }
                if (inEx) {
                    translating.examples.add(currentText.toString())
                    //Log.i("ResponseParser", "saving " + currentText.toString())
                    currentText = StringBuilder("")
                }
                if (inDef && inTr && !inEx && !inSyn && !inMean) {
                    translating.translatings.add(currentText.toString())
                    currentText = StringBuilder("")
                }
            }
        }
        setElementStatus(qName, false)

    }

    override fun characters(ch: CharArray?, start: Int, length: Int){
        //super.characters(ch, start, length)
//        if (inTr && !inEx && !inMean && !inSyn && inText) {
//            word.append(ch, start, length)
//        }
        if (!inDef) {return}
        if (!inTr && inText) {
            currentText.append(ch, start, length)
            return
        }
        if (inSyn || inMean) {
            return
        }
        if (inEx && inText) {
            currentText.append(ch, start, length)
            //Log.i("ResponseParser", "creating " + currentText.toString())
        }
        if (inTr && inText && !inEx) {
            currentText.append(ch, start, length)
        }
    }

    fun getTranslatings() : List<Translating> {
        return translatings
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
            DEF -> {
                inDef = status
            }
        }
    }
}