package ru.kurant.spisoktest.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.kurant.spisoktest.R
import ru.kurant.spisoktest.databinding.ItemContactBinding
import ru.kurant.spisoktest.viewmodel.ContactModel
import ru.kurant.spisoktest.viewmodel.ListModel

class ContactAdapter(parentFragment: ListFragment, val contactListModel: ListModel) : RecyclerView.Adapter<ContactAdapter.ContactHolder>() {

    val fragmentManager = parentFragment.parentFragmentManager

    init {
        contactListModel.contactListData.observe(parentFragment) {
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactHolder {
        val itemBinding = ItemContactBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContactHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ContactHolder, position: Int) {
        holder.bind(ContactModel().apply {
            this.contactData.value = (contactListModel.contactListData.value?.get(position))
        })
    }

    override fun getItemCount(): Int {
        return contactListModel.contactListData.value?.size ?: 0
    }

    inner class ContactHolder(val itemBinding: ItemContactBinding) :
        RecyclerView.ViewHolder(itemBinding.root), View.OnClickListener {

        fun bind(contactModel: ContactModel) {
            itemBinding.contactModel = contactModel
            itemBinding.root.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            fragmentManager.beginTransaction()
                .replace(R.id.container_main, ContactFragment.getFragment(itemBinding.contactModel))
                .addToBackStack("spisok")
                .commit()
        }
    }
}