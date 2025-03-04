package com.example.bitfit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    lateinit var sleeps: List<Sleep>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val sleepRv = findViewById<RecyclerView>(R.id.sleepRv)
        //Set sleeps to empty list, change to pull from sqlite database
        sleeps = emptyList()

        var adapter = SleepAdapter(sleeps)
        sleepRv.adapter = adapter
        sleepRv.layoutManager = LinearLayoutManager(this)

        val hoursInput = findViewById<EditText>(R.id.hoursInput)
        val minutesInput = findViewById<EditText>(R.id.minutesInput)
        val dateInput = findViewById<EditText>(R.id.dateInput)
        val addButton = findViewById<Button>(R.id.addButton)
        addButton.setOnClickListener {
            //Add to list, add to DB
            val newSleep = Sleep(dateInput.text.toString(), hoursInput.text.toString(), minutesInput.text.toString())
            sleeps = sleeps + newSleep
            adapter = SleepAdapter(sleeps)
            sleepRv.adapter = adapter
            sleepRv.layoutManager = LinearLayoutManager(this)
        }
    }
}