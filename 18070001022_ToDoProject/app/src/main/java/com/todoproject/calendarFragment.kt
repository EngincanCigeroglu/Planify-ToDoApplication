package com.todoproject

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.EditText
import android.widget.GridView
import java.util.*


class calendarFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_calendar, container, false)

        val calendarView = rootView.findViewById<CalendarView>(R.id.calendarView)
        val myNotes = rootView.findViewById<EditText>(R.id.Notes)

        calendarView.firstDayOfWeek = Calendar.MONDAY

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val selectedDate = "$dayOfMonth/${month + 1}/$year"
            myNotes.setText(selectedDate)
        }

        return rootView
    }

}