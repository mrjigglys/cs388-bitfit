package com.example.bitfit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val sleeps = mutableListOf<Sleep>()
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
        var adapter = SleepAdapter(sleeps)
        sleepRv.adapter = adapter
        lifecycleScope.launch{
            (application as SleepApplication).db.sleepDao().getAll().collect { databaseList ->
                databaseList.map { entity ->
                    Sleep(
                        entity.date,
                        entity.hours,
                        entity.minutes
                    )
                }.also {mappedList ->
                    sleeps.clear()
                    sleeps.addAll(mappedList)
                    adapter.notifyDataSetChanged()
                }
            }
        }
        sleepRv.layoutManager = LinearLayoutManager(this)

        val hoursInput = findViewById<EditText>(R.id.hoursInput)
        val minutesInput = findViewById<EditText>(R.id.minutesInput)
        val dateInput = findViewById<EditText>(R.id.dateInput)
        val addButton = findViewById<Button>(R.id.addButton)
        addButton.setOnClickListener {
            //Add to list, add to DB
            val newSleep = Sleep(dateInput.text.toString(), hoursInput.text.toString(), minutesInput.text.toString())
            sleeps.add(newSleep)
            adapter = SleepAdapter(sleeps)
            sleepRv.adapter = adapter
            sleepRv.layoutManager = LinearLayoutManager(this)
            lifecycleScope.launch(IO) {
            (application as SleepApplication).db.sleepDao().deleteAll()
            (application as SleepApplication).db.sleepDao().insertAll(sleeps.map {
                SleepEntity(
                    date = it.date,
                    hours = it.hours,
                    minutes = it.minutes
                )
            })}
        }
    }
}