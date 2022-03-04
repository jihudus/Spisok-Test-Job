package ru.kurant.spisoktest.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.kurant.spisoktest.domain.Contact

@Dao
interface ContactDao {

    @Query("SELECT * FROM Contact")
    suspend fun getContacts(): List<Contact>

    @Query("SELECT * FROM Contact WHERE name LIKE :name")
    suspend fun getContactsByName(name: String): List<Contact>

    @Query("SELECT * FROM Contact WHERE phone LIKE :phone")
    suspend fun getContactsByPhone(phone: String): List<Contact>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContacts(contacts: List<Contact>)
}

