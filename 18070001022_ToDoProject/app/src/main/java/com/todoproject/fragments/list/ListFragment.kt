package com.todoproject.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.support.v4.os.IResultReceiver._Parcel
import android.util.Log
import android.view.*
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.todoproject.R
import com.todoproject.database.AppBindingAdapters.Companion.emptyDatabase
import com.todoproject.database.AppDatabase
import com.todoproject.database.AppSharedViewModel
import com.todoproject.database.AppViewModel
import com.todoproject.databinding.FragmentListBinding

class ListFragment : Fragment(), SearchView.OnQueryTextListener {

    private lateinit var floatingActionButton: FloatingActionButton
    private val adapter: ListAdapter by lazy { ListAdapter() }
    private val appViewModel: AppViewModel by viewModels()
    private val appSharedViewModel: AppSharedViewModel by viewModels()

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = binding.recyclerView.findViewById<RecyclerView>(R.id.recyclerView)

        val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.visibility = View.VISIBLE


        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())

        appViewModel.getAllData.observe(viewLifecycleOwner, Observer { data ->

            appSharedViewModel.checkIfDatabaseEmpty(data)
            adapter.setData(data) })


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentListBinding.inflate(inflater,container,false )
        binding.lifecycleOwner = this
        binding.appSharedViewModel = appSharedViewModel

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.listfragment_menu, menu)

        val search: MenuItem = menu.findItem(R.id.search_menu)
        val searchView = search.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.DeleteAll_menu -> deleteAllTask()

            R.id.menu_priority_high -> appViewModel.sortByHighPriority.observe(viewLifecycleOwner) { adapter.setData(it) }
            R.id.menu_priority_low -> appViewModel.sortByLowPriority.observe(viewLifecycleOwner) { adapter.setData(it) }

            R.id.menu_asc -> appViewModel.sortByDateDesc.observe(viewLifecycleOwner) { adapter.setData(it) }
            R.id.menu_desc -> appViewModel.sortByDateAsc.observe(viewLifecycleOwner) { adapter.setData(it) }

            android.R.id.home -> requireActivity().onBackPressed()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun deleteAllTask() {
        val alertbuilder = AlertDialog.Builder(requireContext())

        alertbuilder.setTitle("Do you want to delete all tasks?")

        alertbuilder.setNegativeButton("No"){_,_ ->}

        alertbuilder.setPositiveButton("Yes"){
                _,_ -> appViewModel.deleteAll()
            Toast.makeText(
                requireContext(),"Tasks are succesfully deleted ", Toast.LENGTH_SHORT
            ).show()
        }

        alertbuilder.create().show()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            searchThroughDatabase(query)
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null) {
            searchThroughDatabase(query)
        }
        return true
    }

    private fun searchThroughDatabase(query: String) {

        val searchQuery = "%$query%"

        appViewModel.searchDatabase(searchQuery).observe(viewLifecycleOwner) { list ->
            list?.let{
                adapter.setData(it)
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}



