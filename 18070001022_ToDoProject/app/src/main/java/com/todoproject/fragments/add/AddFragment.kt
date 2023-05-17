package com.todoproject.fragments.add

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.todoproject.R
import com.todoproject.database.AppSharedViewModel
import com.todoproject.database.AppViewModel
import com.todoproject.database.models.MyData
import java.util.*

class AddFragment : Fragment() {

    private val appViewModel: AppViewModel by viewModels()
    private val appSharedViewModel: AppSharedViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add, container, false)
        val prioritySpinner = view?.findViewById<Spinner>(R.id.PrioritySpinner)
        prioritySpinner?.onItemSelectedListener = appSharedViewModel.listener

        val bottomNavigationView =
            requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.visibility = View.INVISIBLE

        //calender
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val mycalender = view?.findViewById<Button>(R.id.DatePickerButton)
        val mydate = view?.findViewById<TextView>(R.id.Show_date)

        mycalender?.setOnClickListener{
            val datePickerDialog = DatePickerDialog(requireActivity(),
                { _, mYear, mMonth, mDay ->
                    val calendar = Calendar.getInstance()
                    calendar.set(Calendar.YEAR, mYear)
                    calendar.set(Calendar.MONTH, mMonth)
                    calendar.set(Calendar.DAY_OF_MONTH, mDay)
                    if (mydate != null) {
                        mydate.text = "" + mDay + "/" + (mMonth + 1) + "/" + mYear
                    }
                }, year, month, day)
            datePickerDialog.show()
        }

        setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        return view
    }
/*
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.addfragment_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                if (menuItem.itemId == R.id.add_menu) {
                    insertToDatabase()
                } else if (menuItem.itemId == android.R.id.home) {
                    requireActivity().onBackPressedDispatcher.onBackPressed()
                }
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }
*/
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.addfragment_menu, menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean{
        if (item.itemId == R.id.add_menu){
            insertToDatabase()
        }
        return super.onOptionsItemSelected(item)
    }


    private fun insertToDatabase() {

        val view = getView()
        val taskTitle = view?.findViewById<EditText>(R.id.TaskTitle)
        val prioritySpinner = view?.findViewById<Spinner>(R.id.PrioritySpinner)
        val taskDescription = view?.findViewById<EditText>(R.id.TaskDescription)
        val date = view?.findViewById<TextView>(R.id.Show_date)

        val mTitle = taskTitle?.text.toString()
        val mPriority = prioritySpinner?.selectedItem.toString()
        val mDescription = taskDescription?.text.toString()
        val mdate = date?.text.toString()

        val validation = appSharedViewModel.verifyDataFromUser(mTitle, mDescription)
        if (validation) {
            // Insert Data to Database
            val newAddedData = MyData(
                0,
                mTitle,
                //mSharedViewModel.parsePriority(mPriority),
                appSharedViewModel.parsePriority(mPriority),
                mDescription,
                mdate
            )
            appViewModel.insertData(newAddedData)
            Toast.makeText(requireContext(), "Task Successfully added!", Toast.LENGTH_SHORT).show()
            // Navigate Back
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_SHORT)
                .show()
        }
    }


}