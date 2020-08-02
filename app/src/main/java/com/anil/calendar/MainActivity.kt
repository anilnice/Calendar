package com.anil.calendar

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.prolificinteractive.materialcalendarview.CalendarDay
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {
    private var dates: HashSet<CalendarDay>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dates = HashSet()
        calendarView.setOnDateChangedListener { widget, date, selected ->
            Toast.makeText(this,"${date.day}-${date.month}-${date.year}",Toast.LENGTH_LONG).show()
            val calendar = Calendar.getInstance()
            calendar.set(date.year,date.month-1,date.day)
            val month_date = SimpleDateFormat("EEEE dd MMMM yyyy")
            val month_name = month_date.format(calendar.getTime())

            val curr = Date()
            var day = curr.day
            var firstday = Date(curr.time- 60 * 60 * 24 * day * 1000)
            var lastday = Date(firstday.time + 60 * 60 *24 * 6 * 1000);

            var intent=Intent(this,TimescheduleActivity::class.java)
            Log.d("TAG", "onCreate: ${month_name}\n ${firstday} -- $lastday")
            intent.putExtra("date",month_name)
            startActivity(intent)
        }
        calendarView.topbarVisible=true
        /*calendarView.state().edit()
            .setMinimumDate(CalendarDay.from(2020, 8, 3))
            .setMaximumDate(CalendarDay.from(2020, 8, 12))
            .setCalendarDisplayMode(CalendarMode.WEEKS)
            .commit();*/
        val mydate=CalendarDay.from(2020,  8, 15) // year, month, date
        val mydate1=CalendarDay.from(2020,  8, 5) // year, month, date
        val mydate2=CalendarDay.from(2020,  8, 20) // year, month, date
        val mydate3=CalendarDay.from(2020,  8, 29) // year, month, date
        dates!!.add(mydate)
        dates!!.add(mydate1)
        dates!!.add(mydate2)
        dates!!.add(mydate3)
        calendarView.addDecorators(EventDecorator(Color.RED,dates))
    }
}