package com.example.timecalculator

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    private lateinit var inputTime1: EditText
    private lateinit var inputTime2: EditText
    private lateinit var resultTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inputTime1 = this.findViewById(R.id.inputTime1)
        inputTime2 = findViewById(R.id.inputTime2)
        resultTextView = findViewById(R.id.resultTextView)

        val buttonAdd: Button = findViewById(R.id.buttonAdd)
        val buttonSubtract: Button = findViewById(R.id.buttonSubtract)

        buttonAdd.setOnClickListener {
            val time1 = inputTime1.text.toString()
            val time2 = inputTime2.text.toString()
            val result = addTime(time1, time2)
            resultTextView.text = "Результат сложения: $result"
        }

        buttonSubtract.setOnClickListener {
            val time1 = inputTime1.text.toString()
            val time2 = inputTime2.text.toString()
            val result = subtractTime(time1, time2)
            resultTextView.text = "Результат вычитания: $result"
        }

    }
}
fun timeToSeconds(timeStr: String): Int {
    var totalSeconds = 0
    val regex = Regex("(\\d+)([hms])")
    val matches = regex.findAll(timeStr)

    for (match in matches) {
        val value = match.groups[1]?.value?.toInt() ?: 0
        when (match.groups[2]?.value) {
            "h" -> totalSeconds += value * 3600
            "m" -> totalSeconds += value * 60
            "s" -> totalSeconds += value
        }
    }
    return totalSeconds
}

fun secondsToTime(seconds: Int): String {
    val hours = seconds / 3600
    val minutes = (seconds % 3600) / 60
    val secs = seconds % 60
    val timeParts = mutableListOf<String>()

    if (hours > 0) timeParts.add("${hours}h")
    if (minutes > 0) timeParts.add("${minutes}m")
    if (secs > 0) timeParts.add("${secs}s")

    return timeParts.joinToString("")
}

fun addTime(timeStr1: String, timeStr2: String): String {
    val totalSeconds = timeToSeconds(timeStr1) + timeToSeconds(timeStr2)
    return secondsToTime(totalSeconds)
}

fun subtractTime(timeStr1: String, timeStr2: String): String {
    val totalSeconds = timeToSeconds(timeStr1) - timeToSeconds(timeStr2)
    return secondsToTime(maxOf(totalSeconds, 0)) // Возвращает неотрицательное значение
}