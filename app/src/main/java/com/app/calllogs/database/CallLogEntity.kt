package com.app.calllogs.database

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "call_logs")
data class CallLogEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val _cid: String,
    val number: String,
    val name: String,
    val start_time: Long,
    val end_time: Long,
    val type: String,
    val timestamp: Long,
    val note: String
)

@Entity(tableName = "all_contacts")
data class AllContactEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val _cid: String,
    val number: String,
    val name: String,
    val type: String,
    val note: String
)

@Dao
interface CallLogDao {
    @Insert
    suspend fun insert(call: CallLogEntity)
    @Query("SELECT * FROM call_logs ORDER BY timestamp DESC")
    suspend fun getAll(): List<CallLogEntity>
    @Update
    suspend fun update(call: CallLogEntity)

    @Query("SELECT * FROM call_logs ORDER BY timestamp DESC")
    fun observeAll(): Flow<List<CallLogEntity>>

    @Query("SELECT * FROM call_logs Where _cid = :id ORDER BY timestamp DESC")
    fun observeAllById(id : String): Flow<List<CallLogEntity>>
}

@Dao
interface ContactsDao {
    @Insert
    suspend fun insert(contact: AllContactEntity)
    @Query("SELECT * FROM all_contacts")
    suspend fun getAll(): List<AllContactEntity>

    @Query("DELETE FROM all_contacts Where type = :type")
    suspend fun delAll(type : String)

    @Query("SELECT * FROM all_contacts Where number = :number")
    suspend fun getContact(number : String): AllContactEntity?
    @Update
    suspend fun update(call: AllContactEntity)

//    @Query("SELECT * FROM call_logs ORDER BY timestamp DESC")
//    fun observeAll(): Flow<List<CallLogEntity>>
}

@Database(entities = [CallLogEntity::class,AllContactEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun callLogDao(): CallLogDao
    abstract fun contactsDao(): ContactsDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "crm_database")
                    .build().also { INSTANCE = it }
            }
    }
}