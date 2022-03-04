package ru.kurant.spisoktest.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.kurant.spisoktest.R
import ru.kurant.spisoktest.datamanager.ContactListManager

const val TAG = "UI_DEBUG"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Creates singletone for data management
        val contactListManager = ContactListManager.createInstance(this)

        if (savedInstanceState == null) {
            val transaction = supportFragmentManager
                .beginTransaction()
                .add(R.id.container_main, ListFragment.getFragment())
            transaction.commit()
        }
    }

}