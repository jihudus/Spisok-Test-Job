package ru.kurant.spisoktest.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.kurant.spisoktest.domain.Contact

@Database(entities = [Contact::class], version = 1)
abstract class ContactDataBase : RoomDatabase() {

    abstract fun contactDao(): ContactDao
}