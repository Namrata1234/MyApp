package com.example.myapp.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(
    entities = [UserData::class],
    version = 1
)

abstract class MyDatabase : RoomDatabase() {
    abstract fun userDao(): MyUserDao

    companion object {
        private var INSTANCE: MyDatabase? = null

        fun getInstance(context: Context): MyDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context = context,
                        klass = MyDatabase::class.java,
                        name = "my_database"
                    ).fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}