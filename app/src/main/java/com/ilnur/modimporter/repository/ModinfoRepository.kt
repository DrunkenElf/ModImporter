package com.ilnur.modimporter.repository

import android.content.Context
import com.google.gson.Gson
import com.ilnur.modimporter.database.AppDatabase
import com.ilnur.modimporter.database.ModInfo
import com.ilnur.modimporter.database.ModsCover
import javax.inject.Inject

class ModinfoRepository @Inject constructor(
    var context: Context,
    var db: AppDatabase
) {

    suspend fun getMod(filename: String): ModInfo {
        val jsonString = context.assets.open("data.json")
            .bufferedReader().use { it.readText() }
        val modsJson = Gson().fromJson(jsonString, ModsCover::class.java).google.mods.find {
            it.filename == filename
        }

        val modsDao = db.modsDao()
        var temp: ModInfo?

        temp = modsDao.getModByFilename(filename)

        if (temp != null && temp.isFave)
            return modsJson?.copy(isFave = true) ?: ModInfo("asd","asd","asd", "asd")
        else
            return modsJson ?: ModInfo("asd","asd","asd", "asd")

    }


}