package com.example.diplom_0_1.translate

class Translating() {
    val translatings : MutableList<String> = mutableListOf()
    val examples : MutableList<String> = mutableListOf()
    var word = ""
    var partOfSpeech = ""

    override fun toString(): String {
        return "Translating(translatings=$translatings, examples=$examples, word='$word', partOfSpeech='$partOfSpeech')"
    }
}