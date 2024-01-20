package com.example.diplom_0_1.test

class Word(val id : Int, val dictId : Int, var firstWord : String, var secondWord : String,
           var isGuessed : Int = 0, var guessedRank : Int = 0) {
    override fun toString(): String {
        return "Word(id=$id, dictId=$dictId, firstWord='$firstWord', secondWord='$secondWord', isGuessed=$isGuessed, guessedRank=$guessedRank)"
    }

}
