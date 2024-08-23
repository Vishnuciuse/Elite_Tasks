package com.example.myapplication.task2

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


class MainActivity3 : AppCompatActivity() {

    lateinit var swipeHelperRight: SwipeHelperRight

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main3)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val data = arrayListOf("Leonel Messi", "Neymer", "Cristiano Ronaldo")

        val recyclerView: RecyclerView = findViewById(R.id.swipeRecyclerVew)
        recyclerView.layoutManager = LinearLayoutManager(this)

        data.let {
            val adapter = SwipeAdapter(it)
            recyclerView.adapter = adapter
        }

        swipeHelperRight =
            object : SwipeHelperRight(this, recyclerView) {
                override fun instantiateUnderlayButton(
                    viewHolder: RecyclerView.ViewHolder?,
                    underlayButtons: MutableList<UnderlayButton?>
                ) {
//                    underlayButtons.add(UnderlayButton(
//                        this@MainActivity3,
//                        "Archive",
//                        R.drawable.ic_archive,
//                        Color.parseColor("#BBBBC3"),
//                        object : UnderlayButtonClickListener {
//                            override fun onClick(pos: Int) {
//                                Toast.makeText(
//                                    this@MainActivity3,
//                                    "Archive",
//                                    Toast.LENGTH_SHORT
//                                ).show()
//                            }
//                        }
//                    ))
                    underlayButtons.add(UnderlayButton(
                        this@MainActivity3,
                        "Delete",
                        R.drawable.ic_delete,
                        Color.parseColor("#FE3B30")
                    ) { pos ->
                        Toast.makeText(
                            this@MainActivity3,
                            "Delete item at $pos",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        data.removeAt(pos)
                        val adapter = SwipeAdapter(data)
                        recyclerView.adapter = adapter

                        val itemTouchHelper = ItemTouchHelper(swipeHelperRight)
                        itemTouchHelper.attachToRecyclerView(recyclerView)
                    })
                }
            }

        val itemTouchHelper = ItemTouchHelper(swipeHelperRight)
        itemTouchHelper.attachToRecyclerView(recyclerView)


        /*  val swipeHelperLeft: SwipeHelperLeft =
              object : SwipeHelperLeft(this, recyclerView) {
                  override fun instantiateUnderlayButton(
                      viewHolder: RecyclerView.ViewHolder?,
                      underlayButtons: MutableList<SwipeHelperLeft.UnderlayButton?>
                  ) {
                      underlayButtons.add(UnderlayButton(
                          this@MainActivity3,
                          "Delete",
                          R.drawable.ic_delete,
                          Color.parseColor("#FE3B30"),
                          object : UnderlayButtonClickListener {
                              override fun onClick(pos: Int) {
                                  // TODO: onDelete
                                  Toast.makeText(this@MainActivity3, "Delete item at $pos", Toast.LENGTH_SHORT)
                                      .show()
                                  data.removeAt(pos)
                                  val adapter = SwipeAdapter(data)
                                  recyclerView.adapter = adapter
                              }
                          }
                      ))
                  }
              }

          val itemTouchHelper = ItemTouchHelper(swipeHelperLeft)
          itemTouchHelper.attachToRecyclerView(recyclerView)*/


//        val simpleCallback: ItemTouchHelper.SimpleCallback = object :
//            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
//            override fun onMove(
//                recyclerView: RecyclerView,
//                viewHolder: RecyclerView.ViewHolder,
//                target: RecyclerView.ViewHolder
//            ): Boolean {
//                return false
//            }
//
//            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//                val position = viewHolder.getAdapterPosition()
//                data.removeAt(position)
////                data.notifyItemRemoved(position)
//            }
//        }
//
//        val itemTouchHelper = ItemTouchHelper(simpleCallback)
//        itemTouchHelper.attachToRecyclerView(recyclerView)


        //  -------------------

        val checkBtn = findViewById<Button>(R.id.checkBtn)
        checkBtn.setOnClickListener {
            val dateTime = convertTimestampToDateTime(1715073502567)
            println("The Time stamp value is $dateTime")
            Toast.makeText(this,dateTime,Toast.LENGTH_LONG).show()
        }

    }

    private fun convertTimestampToDateTime(timestamp: Long): String {
        val date = Date(timestamp)
        val formatP = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        println("The Time stamp value is ${formatP.format(date)}")

        return if (isToday(timestamp)) {
            val format = SimpleDateFormat("h:mm a", Locale.getDefault())
            /** for getting time eg- 11:30 AM  */
            val formattedTime = format.format(date)
            formattedTime.replace("am", "AM").replace("pm", "PM")
        } else if (isWithinLastSevenDays(timestamp)) {
            val format = SimpleDateFormat("EEEE", Locale.getDefault())
            /** for getting week eg- friday */
            format.format(date)
        } else {
            val format = SimpleDateFormat("MMMM d", Locale.getDefault())
            /** for getting month and date eg- April 12 */
            format.format(date)
        }

    }

    private fun isToday(timestamp: Long): Boolean {
        val dateFormat = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
        val timestampDate = Date(timestamp)
        val todayDate = Date()

        return dateFormat.format(timestampDate) == dateFormat.format(todayDate)
    }


    private fun isWithinLastSevenDays(timestamp: Long): Boolean {
//        val dateFormat = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
        val currentDate = Calendar.getInstance().time

        // Calculate the date 7 days ago
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, -7)
        val sevenDaysAgoDate = calendar.time

        // Convert the timestamp to a Date object
        val timestampDate = Date(timestamp)

        // Compare the timestamp date with the date range (7 days ago to today)
        return timestampDate.after(sevenDaysAgoDate) && !timestampDate.after(currentDate)
    }



}



