package com.todoproject.fragments.update

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.todoproject.R
import com.todoproject.database.AppSharedViewModel
import com.todoproject.database.AppViewModel
import com.todoproject.database.models.MyData
import com.todoproject.databinding.FragmentUpdateBinding
import java.text.SimpleDateFormat
import java.util.*


class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()

    private val appViewModel: AppViewModel by viewModels()
    private val appSharedViewModel: AppSharedViewModel by viewModels()

    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        val bottomNavigationView =
            requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.visibility = View.INVISIBLE


        _binding = FragmentUpdateBinding.inflate(inflater,container,false)
        binding.args = args

        binding.UpdatePrioritySpinner.onItemSelectedListener = appSharedViewModel.listener

        val date = Date(args.currentItem.date)
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val formattedDate = dateFormat.format(date)

        binding.UpdateDate.text = formattedDate.toString()

        //calender
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val mycalender = binding.UpdateDatePickerButton
        val mydate = binding.UpdateDate


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

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.updatefragment_menu, menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.saveUpdate){
            updateTask()
        }else{
            if (item.itemId == R.id.deleteUpdate){
                deleteTask()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun updateTask() {

        val taskTitle = binding.UpdateTaskTitle.text.toString()
        val prioritySpinner = binding.UpdatePrioritySpinner.selectedItem.toString()
        val taskDescription = binding.UpdateTaskDescription.text.toString()
        val date_text = binding.UpdateDate.text.toString()

        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val date = dateFormat.parse(date_text)
        val mydate = date?.time ?: 0

        val validation = appSharedViewModel.verifyDataFromUser(taskTitle, prioritySpinner)
        if (validation)
        {
            val updatedData = MyData(
                args.currentItem.id,
                taskTitle,
                appSharedViewModel.parsePriority(prioritySpinner),
                taskDescription,
                mydate,
            )
            appViewModel.updateData(updatedData)
            Toast.makeText(requireContext(), "Task Successfully Updated!", Toast.LENGTH_SHORT).show()

            findNavController().navigate(R.id.action_updateFragment_to_listFragment)

        }else{
            Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun deleteTask() {
        val alertbuilder = AlertDialog.Builder(requireContext())

        alertbuilder.setTitle("Do you want to delete task: ${args.currentItem.title} ?")

        alertbuilder.setNegativeButton("No"){_,_ ->}

        alertbuilder.setPositiveButton("Yes"){
            _,_ -> appViewModel.deleteItem(args.currentItem)
            Toast.makeText(
                requireContext(),"Task: ${args.currentItem.title} Deleted!", Toast.LENGTH_SHORT
            ).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }

        alertbuilder.create().show()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}