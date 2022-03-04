package ru.kurant.spisoktest.viewmodel

import android.util.Log
import android.widget.SearchView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.kurant.spisoktest.datamanager.ContactListManager
import ru.kurant.spisoktest.domain.Contact

const val TAG = "ViewModel_DEBUG"

class ListModel: ViewModel(), SearchView.OnQueryTextListener {

    val contactListData: MutableLiveData<List<Contact>> by lazy {
        MutableLiveData<List<Contact>>().also { updateLiveData() }
    }

    //Async task gets list from DB and applies it to LiveData
    fun updateLiveData(str: String = "") {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val result = if(str == "") {
                    ContactListManager.getInstance().db?.contactDao()?.getContacts()
                } else if (str.trim().first().digitToIntOrNull() != null) { //If input starts with digit symbol
                    ContactListManager.getInstance().db?.contactDao()?.getContactsByPhone("%$str%")
                } else { //If input starts with non-digit symbol
                    ContactListManager.getInstance().db?.contactDao()?.getContactsByName("%$str%")
                }
                //setValue() requires main thread
                withContext(Dispatchers.Main) {
                    contactListData.value = result
                }
            }
            Log.d(TAG, "updateLiveData : ${contactListData.value?.size}")
        }
    }

    //Listeners for search input
    override fun onQueryTextSubmit(query: String?): Boolean {
        updateLiveData(query ?: "")
        return !query.isNullOrEmpty()
    }

    //Listeners for search input
    override fun onQueryTextChange(newText: String?): Boolean {
        updateLiveData(newText ?: "")
        return !newText.isNullOrEmpty()
    }
}