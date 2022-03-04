package ru.kurant.spisoktest.ui

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_LONG
import com.google.android.material.snackbar.Snackbar
import ru.kurant.spisoktest.databinding.FragmentContactBinding
import ru.kurant.spisoktest.viewmodel.ContactModel

class ContactFragment(val model: ContactModel?): Fragment() {

    companion object {
        fun getFragment(model: ContactModel?) = ContactFragment(model)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentContactBinding.inflate(inflater)

        // This app is 2 screens only so i use simple options menu instead of navigation library

        val parentActivity = activity as AppCompatActivity
        parentActivity.setSupportActionBar(binding.toolbar)
        val toolbar = parentActivity.supportActionBar!! //AppCompatActivity realizes cast Toolbar as ActionBar!
        toolbar.setDisplayHomeAsUpEnabled(true)
        binding.contactModel = model

        binding.contactPhone.setOnClickListener {
            val number = binding.contactPhone.text.toString().trim()
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + Uri.parse(number)))
            try {
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                Snackbar.make(binding.root, "Нечем звонить", LENGTH_LONG).show()
            }
        }

        return binding.root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            parentFragmentManager.popBackStack()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}