package com.ilnur.modimporter

import android.content.res.AssetManager
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream


fun copyAssetFolder(
    assetManager: AssetManager,
    fromAssetPath: String, toPath: String
): Boolean {
    return try {
        val files = assetManager.list(fromAssetPath)
        File(toPath).mkdirs()
        var res = true
        for (file in files!!) res = if (file.contains(".")) res and copyAsset(
            assetManager,
            "$fromAssetPath/$file",
            "$toPath/$file"
        ) else res and copyAssetFolder(
            assetManager,
            "$fromAssetPath/$file",
            "$toPath/$file"
        )
        res
    } catch (e: Exception) {
        e.printStackTrace()
        false
    }
}

fun copyAsset(
    assetManager: AssetManager,
    fromAssetPath: String, toPath: String
): Boolean {


    val input = assetManager.open(fromAssetPath)
    File(toPath).createNewFile()
    val out = FileOutputStream(toPath)
    val copied = input.copyTo(out)
    input.close()
    out.close()
    return copied > 0
}

private fun copyFile(`in`: InputStream, out: OutputStream) {

    `in`.copyTo(out)
    /*while (`in`.read(buffer).also { read = it } != -1) {
        out.write(buffer, 0, read)
    }*/
}