package com.example.timecalculator

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    private lateinit var toolbarMain: androidx.appcompat.widget.Toolbar
    private lateinit var inputTime1: EditText
    private lateinit var inputTime2: EditText
    private lateinit var resultTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbarMain = findViewById(R.id.toolbarMain)
        setSupportActionBar(toolbarMain)
        title = "Калькулятор"
        toolbarMain.subtitle = "Версия 1"
        toolbarMain.setLogo(R.drawable.ic_calculate)

        inputTime1 = findViewById(R.id.inputTime1)
        inputTime2 = findViewById(R.id.inputTime2)

        resultTextView = findViewById(R.id.resultTextView)


        val buttonAdd: Button = findViewById(R.id.buttonAdd)
        val buttonSubtract: Button = findViewById(R.id.buttonSubtract)

        buttonAdd.setOnClickListener {
            val time1 = inputTime1.text.toString()
            val time2 = inputTime2.text.toString()
            val result = addTime(time1, time2)
            resultTextView.text = "Результат сложения: $result"
            Toast.makeText(
                applicationContext,
                "Результат $result",
                Toast.LENGTH_LONG
            ).show()

        }

        buttonSubtract.setOnClickListener {
            val time1 = inputTime1.text.toString()
            val time2 = inputTime2.text.toString()
            val result = subtractTime(time1, time2)
            resultTextView.text = "Результат вычитания: $result"

            val toast = Toast.makeText(
                applicationContext,
                "Результат $result",
                Toast.LENGTH_LONG
            ).show()

        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.resetMenuMain -> {
                inputTime1.text.clear()
                inputTime2.text.clear()
                resultTextView.text = "Результат"
                val toast = Toast.makeText(
                    applicationContext,
                    "Данные очищены",
                    Toast.LENGTH_LONG
                ).show()
            }

            R.id.exitMenuMain -> {
                val toast = Toast.makeText(
                    applicationContext,
                    "Работа завершена",
                    Toast.LENGTH_LONG
                ).show()
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
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