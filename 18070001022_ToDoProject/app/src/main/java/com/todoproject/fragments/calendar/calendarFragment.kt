package com.todoproject.fragments.calendar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.todoproject.R
import com.todoproject.note_database.Note_ViewModel
import com.todoproject.note_database.Note_databasedao
import com.todoproject.note_database.Note_repository
import com.todoproject.note_database.models.MyNotes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*
import androidx.lifecycle.Observer

class calendarFragment : Fragment() {

    private var selectedTimestamp: Long = 0
    private val noteViewModel: Note_ViewModel by viewModels()
    var noteRepository: Note_repository? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_calendar, container, false)
        val text_date = rootView.findViewById<TextView>(R.id.notes_for_date)
        val calendarView = rootView.findViewById<CalendarView>(R.id.calendarView)
        val notesEditText = rootView.findViewById<EditText>(R.id.Notes)

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val selectedDate = "$dayOfMonth/${month + 1}/$year"
            text_date.setText(selectedDate)

            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val date = dateFormat.parse(selectedDate)
            val timestamp = date?.time ?: 0

            var checked_data = searchThroughDatabase(timestamp).toString()

            selectedTimestamp = timestamp

            notesEditText.setText(checked_data)

            println("timestamp: $timestamp")

            /*
            if -> bastığımdaki timestamp == databasedeki herhangi bir date: Long,'e eşitse. O nesnemin note değerini
                    rootView.findViewById<EditText>(R.id.Notes) de bastır.

            else -> boş edit text bastır
             */

        }
        val button = rootView.findViewById<Button>(R.id.button)

           button.setOnClickListener {
               insertOrUpdateToDatabase()
        }

        return rootView
    }



    private fun insertOrUpdateToDatabase() {
        val noteText = view?.findViewById<EditText>(R.id.Notes)?.text.toString()
        val timestamp = selectedTimestamp

        CoroutineScope(Dispatchers.IO).launch {
            val checkData = CheckDatabaseForInsert(timestamp)
            println("checkdata: $checkData")

            if (checkData == null) {
                val newNote = MyNotes(
                    0,
                    date = timestamp,
                    note = noteText
                )
                noteViewModel.insertNote(newNote)
            } else {
                val updatedNote = MyNotes(
                    checkData.id,
                    checkData.date,
                    note = noteText
                )
                noteViewModel.updateNote(updatedNote)
            }
        }

        Toast.makeText(requireContext(), "Task Successfully Updated!", Toast.LENGTH_SHORT).show()
    }


    private fun searchThroughDatabase(query: Long): String? {
        val data = noteViewModel.searchDatabase(query).value
        return if (data?.note != null) {
            data.note
        } else {
            ""
        }
    }

    private fun CheckDatabaseForInsert(query: Long): MyNotes? {
        val data = noteViewModel.searchDatabase(query).value
        return if (data?.note != null) {
            data
        } else {
            null
        }
    }
}

