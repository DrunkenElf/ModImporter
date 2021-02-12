package com.ilnur.modimporter.database

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Keep
data class ModsCover( //
    @SerializedName("fieldg")val fieldg: String,
    @SerializedName("google")val google: ModGoogle,
)

@Keep
data class ModGoogle(
    @SerializedName("objectg")val mods: List<ModInfo>,
    )

@Keep
@Entity(tableName = "mods", primaryKeys = ["filename", "title"])
data class ModInfo(
    @SerializedName("google_fileg")val filename: String,
    @SerializedName("google_titleg")val title: String,
    @SerializedName("google_descg")val desc: String,
    //@SerializedName( "google_imageg")val imgPaths: List<String>,
    @SerializedName( "google_imageg")val imgPath: String,
    val isFave: Boolean = false
    )
