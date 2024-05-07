package com.example.additem

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar

class AlarmManager : AppCompatActivity() {

    private lateinit var timePicker: TimePicker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.alarm_managing)

        timePicker = findViewById(R.id.timePicker)
        val start = findViewById<Button>(R.id.startButton)
        val cancel = findViewById<Button>(R.id.cancelButton)

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, MyAlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this, 234, intent, PendingIntent.FLAG_IMMUTABLE)

        start.setOnClickListener {
            val hour = timePicker.hour
            val minute = timePicker.minute
            val calendar = Calendar.getInstance()
            val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
            val currentMinute = calendar.get(Calendar.MINUTE)

            val totalSeconds = ((hour - currentHour) * 60 * 60) + ((minute - currentMinute) * 60)
            alarmManager.set(
                AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (totalSeconds * 1000), pendingIntent
            )
            Toast.makeText(this, "Alarm set in $totalSeconds seconds", Toast.LENGTH_LONG).show()
        }

        cancel.setOnClickListener {
            alarmManager.cancel(pendingIntent)
            Toast.makeText(this, "Alarm Cancelled", Toast.LENGTH_SHORT).show()
        }
    }
}
