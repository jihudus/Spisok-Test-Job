package ru.kurant.spisoktest.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.kurant.spisoktest.domain.Contact

class ContactModel: ViewModel() {

    val contactData: MutableLiveData<Contact> by lazy {
        MutableLiveData<Contact>() }
}