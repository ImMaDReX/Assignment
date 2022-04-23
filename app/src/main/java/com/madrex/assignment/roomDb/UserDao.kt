package com.madrex.edvora.roomDb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.madrex.assignment.model.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUsers(rides: List<User>)

    @Query("SELECT * FROM User")
    suspend fun getUsers(): List<User>
}