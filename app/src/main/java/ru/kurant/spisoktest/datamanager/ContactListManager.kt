package ru.kurant.spisoktest.datamanager

import android.content.Context
import android.util.Log
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.kurant.spisoktest.db.ContactDataBase

const val TAG = "DataManager_DEBUG"
const val DB_PATH = "contact_db"

class ContactListManager private constructor(){

    val urls = listOf(
        "https://github.com/SkbkonturMobile/mobile-test-droid/blob/master/json/generated-01.json?raw=true",
        "https://github.com/SkbkonturMobile/mobile-test-droid/blob/master/json/generated-02.json?raw=true",
        "https://github.com/SkbkonturMobile/mobile-test-droid/blob/master/json/generated-03.json?raw=true"
    )

    var db: ContactDataBase? = null

    companion object {
        private var instance: ContactListManager? = null
        private val block = Any()
        //Creates Singletone. Using only in MainActivity#onCreate
        fun createInstance(context: Context): ContactListManager {
            synchronized(block) {
                instance = ContactListManager().apply { this.db = createDb(context) }
            }
            return instance!!
        }
        //Created instance can be used in any place
        fun getInstance(): ContactListManager = instance!!
    }

    private fun createDb(context: Context): ContactDataBase =
        Room.databaseBuilder(context, ContactDataBase::class.java, DB_PATH).build()

    // Uses Downloader and Parser to create list of data class instances, then inserts it into db.
    // Opens separate parallel job for each url address
    suspend fun fetchData() {
        Log.d(TAG, "fetchData: >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>")
        withContext(Dispatchers.IO) {
            for (url in urls) {
                launch {
                    val json = ContactListDownloader().downloadToString(url)
                    val list = ContactListParser().parseStringToContact(json)
                    db!!.contactDao().insertContacts(list)
                }
            }
        }
    }
}
