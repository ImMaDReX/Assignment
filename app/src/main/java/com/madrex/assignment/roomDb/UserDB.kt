package com.madrex.assignment.roomDb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.madrex.assignment.model.User
import com.madrex.edvora.roomDb.UserDao

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class UserDB : RoomDatabase(){
    abstract fun getUserDao(): UserDao
}