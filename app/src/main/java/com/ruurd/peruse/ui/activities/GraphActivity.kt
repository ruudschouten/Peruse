package com.ruurd.peruse.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.formatter.ValueFormatter
import com.ruurd.peruse.R
import com.ruurd.peruse.data.pojo.FullBookPOJO
import com.ruurd.peruse.databinding.ActivityBookBinding
import com.ruurd.peruse.databinding.ActivityGraphBinding
import com.ruurd.peruse.models.Book
import com.ruurd.peruse.ui.activities.viewmodels.BookViewModel
import com.ruurd.peruse.util.GraphUtil
import java.text.SimpleDateFormat
import java.util.*

class GraphActivity : AppCompatActivity() {
    private lateinit var bookViewModel: BookViewModel

    private lateinit var book: Book
    private var bookId: Long = 0L

    private val dateTimeFormatter = SimpleDateFormat("d MMM HH:mm", Locale.getDefault())

    private lateinit var binding: ActivityGraphBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGraphBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bookId = intent.extras!!.getLong("book_id")
        bookViewModel = ViewModelProvider(this).get(BookViewModel::class.java)
        bookViewModel.getBook(bookId).observe(this, { book ->
            this.book = book.toModel()
            setupGraph()
        })
    }

    private fun setupGraph() {
        binding.labelBookChart.text = "Pages over time"
        val entries = GraphUtil.bookToEntries(book)
        val dataSet = GraphUtil.createStyledLineDataSet(this, entries, "Pages over time")

        val data = LineData(dataSet)
        data.setDrawValues(false)
        binding.bookChart.data = data
        GraphUtil.styleLineChart(binding.bookChart, this)
        binding.bookChart.xAxis.valueFormatter = object : ValueFormatter() {
            override fun getAxisLabel(value: Float, axis: AxisBase?): String {
                return dateTimeFormatter.format(value)
            }
        }
        binding.bookChart.invalidate()
    }
}