package com.anil.calendar

import android.app.TimePickerDialog
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity.apply
import android.view.View
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.core.view.get
import androidx.core.view.marginLeft
import com.roundtableapps.timelinedayviewlibrary.Event
import com.roundtableapps.timelinedayviewlibrary.EventView
import kotlinx.android.synthetic.main.activity_timeschedule.*
import java.util.*
import kotlin.properties.Delegates

class TimescheduleActivity : AppCompatActivity() {
    var sfrom_date:Float?=null
    var sto_date :Float?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timeschedule)

        title = intent.getStringExtra("date")
        /*var myEventView = EventView(this,
            Event().apply {
                startTime = 23f
                endTime = 24f
            },
            itemsMargin = 1, //optional
            layoutResourceId = R.layout.item_event_two, //optional
            setupView = { myView ->
                //SETUP VIEW
                myView.findViewById<TextView>(R.id.tvTitle).text = "first"
            },
            onItemClick = {  event->
                //CLICK EVENT
                Toast.makeText(this,"first",Toast.LENGTH_LONG).show()
            }
        )
        myEventView.setBackgroundColor(Color.GREEN)

        timeLine.addEvent(myEventView)*/

        var mTimePicker: TimePickerDialog
        val mcurrentTime = Calendar.getInstance()
        val hour = mcurrentTime.get(Calendar.HOUR_OF_DAY)
        val minute = mcurrentTime.get(Calendar.MINUTE)



        from_date.setOnClickListener {
            mTimePicker = TimePickerDialog(this,
                TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                    sfrom_date= "${hourOfDay}.${minute}".toFloat()
                    if(sto_date==null) {
                        Toast.makeText(this,"Select To Time",Toast.LENGTH_LONG).show()
                    }else{
                        if (sfrom_date!! < sto_date!!) {
                            add_event.isEnabled = true
                            from_date.setTextColor(Color.BLACK)
                        } else {
                            add_event.isEnabled = false
                            from_date.setTextColor(Color.RED)
                        }
                    }
                    from_date.setText(String.format("%2d : %2d", hourOfDay, minute)) }, hour, minute, true)
            mTimePicker.show()
        }
        add_event_layout.setOnClickListener {
            event_constraintLayout.visibility=View.VISIBLE
            add_event_layout.visibility=View.GONE
        }
        close_add_event.setOnClickListener {
            event_constraintLayout.visibility=View.GONE
            add_event_layout.visibility=View.VISIBLE
        }

        to_date.setOnClickListener {
            mTimePicker = TimePickerDialog(this,
                TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                    sto_date= "${hourOfDay}.${minute}".toFloat()
                    if(sfrom_date==null) {
                        Toast.makeText(this,"Select From Time",Toast.LENGTH_LONG).show()
                    }else{
                        if (sfrom_date!! < sto_date!!) {
                            add_event.isEnabled = true
                            from_date.setTextColor(Color.BLACK)
                        } else {
                            add_event.isEnabled = false
                            from_date.setTextColor(Color.RED)
                        }
                    }
                    to_date.setText(String.format("%2d : %2d", hourOfDay, minute)) }, hour, minute, true)
            mTimePicker.show()
        }

        add_event.setOnClickListener {
            var fdate=from_date.text.toString()
            var f_date=fdate.replace(':','.').replace(" ","")
            var tdate=to_date.text.toString()
            var t_date=tdate.replace(':','.').replace(" ","")
            Log.d("TAG", "onCreate: ${f_date} - ${t_date}")
            var myEventView = EventView(this,
                Event().apply {
                    startTime = f_date.toFloat()
                    endTime = t_date.toFloat()
                },
                itemsMargin = 1, //optional
                layoutResourceId = R.layout.item_event_two, //optional
                setupView = { myView ->
                    //SETUP VIEW
                    myView.findViewById<TextView>(R.id.tvTitle).text = event_title.text.toString()
                },
                onItemClick = {  event->
                    //CLICK EVENT
                    //Toast.makeText(this,event.title,Toast.LENGTH_LONG).show()
                    add_event_layout.visibility=View.GONE
                    event_constraintLayout.visibility=View.VISIBLE
                    from_date.text=event.startTime.toString()
                    to_date.text=event.endTime.toString()
                    event_title.setText(event.title)
                }
            )
            myEventView.setBackgroundColor(Color.GRAY)

            timeLine.addEvent(myEventView)

            add_event_layout.visibility=View.VISIBLE
            event_constraintLayout.visibility=View.GONE

        }

    }
}