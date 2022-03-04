package ru.kurant.spisoktest.datamanager

import android.util.Log
import kotlinx.serialization.json.Json
import ru.kurant.spisoktest.domain.Contact
import ru.kurant.spisoktest.domain.Spisok

class ContactListParser {

    fun parseStringToContact(str: String): List<Contact> {
        val spisok = Json.decodeFromString(Spisok.serializer(), str)
        Log.d(TAG, "parseStringToContact: ${spisok.contacts.lastOrNull()}")
        return spisok.contacts
    }

}
