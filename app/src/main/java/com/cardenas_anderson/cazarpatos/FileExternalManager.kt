package com.cardenas_anderson.cazarpatos

import android.app.Activity
import android.os.Environment
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

class FileExternalManager(val actividad: Activity): FileHandler {
    fun isExternalStorageWritable(): Boolean {
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
    }
    override fun SaveInformation(datosAGrabar:Pair<String,String>) {
        //try {
        if (isExternalStorageWritable()) {
            FileOutputStream(
                File(
                    actividad.getExternalFilesDir(null),
                    SHAREDINFO_FILENAME
                )
            ).bufferedWriter().use { outputStream ->
                outputStream.write(datosAGrabar.first)
                outputStream.write(System.lineSeparator())
                outputStream.write(datosAGrabar.second)
            }
        }
        //}catch (e: IOException){
          //  print(e)
        //}
    }
    fun isExternalStorageReadable(): Boolean {
        return Environment.getExternalStorageState() in
                setOf(Environment.MEDIA_MOUNTED, Environment.MEDIA_MOUNTED_READ_ONLY)
    }
    override fun ReadInformation(): Pair<String, String> {
        //try{
            if (isExternalStorageReadable()) {
                return FileInputStream(
                    File(
                        actividad.getExternalFilesDir(null),
                        SHAREDINFO_FILENAME
                    )
                ).bufferedReader().use {
                    val datoLeido = it.readText()
                    val textArray = datoLeido.split(System.lineSeparator())
                    val correo = textArray[0]
                    val clave = textArray[1]
                    return (correo to clave)
                }

            }
            return "" to ""
        //}catch (e: IOException){
          //  print(e)
            //val myFile = File(SHAREDINFO_FILENAME)
            //return ("Error" to "Error")
        //}
    }
}
