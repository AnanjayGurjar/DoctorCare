package com.holidayhack.doctorcare

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream

class Convertor {

    @TypeConverter
    fun fromList (list : List<String>) : String{
        return list.toString()
    }

    @TypeConverter
    fun toList (stringList : String) : List<String>{
        val newString = stringList.substring(1, stringList.length-2)
        val list : List<String>  = newString.split(",")
        return list
    }

    @TypeConverter
    fun bitmapToByteArray (bitmap : Bitmap) : ByteArray {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        return outputStream.toByteArray()
    }

    @TypeConverter
    fun byteArrayToBitmap (byteArray : ByteArray) : Bitmap{
        return BitmapFactory.decodeByteArray(byteArray,0,byteArray.size)
    }
}