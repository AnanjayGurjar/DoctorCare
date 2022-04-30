package com.holidayhack.doctorcare.data

import android.content.Context
import androidx.room.*
import com.holidayhack.doctorcare.Convertor
import com.holidayhack.doctorcare.modals.Doctor

@Database (entities = [Doctor::class], version = 1)
@TypeConverters(Convertor::class)
abstract class DoctorRoomDatabase : RoomDatabase (){

    abstract fun doctorDetailDao() : DoctorDetailDao

    companion object{
        @Volatile
        private var instance : DoctorRoomDatabase? = null

        fun getDataBase(context: Context) = instance ?: synchronized(this){
            Room.databaseBuilder(
                context.applicationContext,
                DoctorRoomDatabase::class.java,
                "movie_database"
            ).fallbackToDestructiveMigration().build().also { instance = it }
        }
    }
}