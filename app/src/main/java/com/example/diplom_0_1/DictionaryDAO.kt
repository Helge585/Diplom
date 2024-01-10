package com.example.diplom_0_1

object DictionaryDAO {

    @JvmStatic
    fun getAllDictionaries() : List<Dictionary> {
        val dicts = mutableListOf<Dictionary>()
        val db = DBUtils.getDataBase().readableDatabase
        val cursor = db.rawQuery("SELECT * FROM Dictionaries", null)
        while (cursor.moveToNext()) {
            val id = cursor.getInt(0)
            val name = cursor.getString(1)
            val dict = Dictionary(id, name)
            dicts.add(dict)
        }
        cursor.close()
        db.close()
        return dicts
    }

    @JvmStatic
    fun getDictionaryByName(name : String) : Dictionary {
        return Dictionary(0, "")
    }
}