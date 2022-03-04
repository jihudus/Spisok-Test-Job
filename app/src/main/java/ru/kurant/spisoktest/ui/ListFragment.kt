package ru.kurant.spisoktest.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_LONG
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import ru.kurant.spisoktest.databinding.FragmentListBinding
import ru.kurant.spisoktest.datamanager.ContactListManager
import ru.kurant.spisoktest.datamanager.DB_PATH
import ru.kurant.spisoktest.viewmodel.ListModel
import java.io.IOException


class ListFragment: Fragment() {

    companion object {
        fun getFragment() = ListFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentListBinding.inflate(inflater)

        // If db file is more than 1 minute old -> refresh data
        if (dbMoreThanMinuteOld()) refreshDataFromNetwork(binding)

        setupRecyclerView(binding)

        setupSwipeRefresh(binding)

        return binding.root
    }

    // returns true if db file is either null or older than 3600000 ms
    private fun dbMoreThanMinuteOld(): Boolean {
        val dbFile = context?.getDatabasePath(DB_PATH)
        val age = System.currentTimeMillis() - (dbFile?.lastModified() ?: 0)
        return age > 3_600_000
    }

    private fun setupSwipeRefresh(binding: FragmentListBinding) {
        binding.refresher.setProgressViewEndTarget(false,0) // Reduce built-in indicator to 0 size
        binding.refresher.setOnRefreshListener {
            binding.refresher.isRefreshing = false // Turn off refreshing state to enable future refreshing
            refreshDataFromNetwork(binding)
        }
    }

    // Adapter needs LifeCycleOwner to observe on LiveData changes and FragmentManager to init fragment transaction
    // so let us give this fragment to adapter
    private fun setupRecyclerView(binding: FragmentListBinding) {
        val recyclerView = binding.recycler
        val contactListModel = ViewModelProvider.NewInstanceFactory().create(ListModel::class.java)
        binding.listModel = contactListModel
        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        recyclerView.adapter = ContactAdapter(this, contactListModel)
    }

    //Async task to load data from network using ContactListManager object. On progress indicator is shown
    private fun refreshDataFromNetwork(binding: FragmentListBinding) {
        lifecycleScope.launch {
            binding.indicator.show()
            val job = launch {
                try {
                    ContactListManager.getInstance().fetchData()
                } catch (e: IOException) {
                    showSnackBar(binding)
                }
            }
            job.join()
            binding.indicator.hide()
        }
    }

    private fun showSnackBar(binding: FragmentListBinding) {
        Snackbar.make(binding.root, "Нет подключения к сети", LENGTH_LONG).show()
    }
}