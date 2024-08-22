package com.example.demoapp

import android.content.BroadcastReceiver
import android.content.ContentValues
import android.content.Intent
import android.content.IntentFilter
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.demoapp.databinding.ActivityMainBinding

typealias direction = com.example.demoapp.Direction

class MainActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var start: Button
    private lateinit var stop: Button
    private lateinit var broadcastReceiver: MyReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // Service
        start = activityMainBinding.startBtn
        stop = activityMainBinding.stopBtn
        start.setOnClickListener{
            startService(Intent(this, MyService::class.java))
        }
        stop.setOnClickListener{
            stopService(Intent(this, MyService::class.java))
        }
        //Broadcast Receiver
        broadcastReceiver = MyReceiver()
        val filter = IntentFilter("android.intent.action.AIRPLANE_MODE")
        registerReceiver(broadcastReceiver, filter)
        //Content Provider
        contentProviderDemo()
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(broadcastReceiver)
    }

    private fun contentProviderDemo(){
        val contentValues = ContentValues().apply {
            put(MyDatabaseHelper.COLUMN_TITLE, "Sample Title")
            put(MyDatabaseHelper.COLUMN_CONTENT, "Sample Content")
        }

        val newUri: Uri? = contentResolver.insert(MyContentProvider.CONTENT_URI, contentValues)

//        contentResolver.delete(newUri!!, null, null)
//        contentResolver.delete(MyContentProvider.CONTENT_URI,null, null)
        val cursor: Cursor? = contentResolver.query(
            MyContentProvider.CONTENT_URI,
            arrayOf("_id", "title", "content"),
            null, null, null
        )

        cursor?.let {
            while (it.moveToNext()) {
                Log.e("check", "success")
                val id = it.getLong(it.getColumnIndexOrThrow("_id"))
                val title = it.getString(it.getColumnIndexOrThrow("title"))
                val content = it.getString(it.getColumnIndexOrThrow("content"))
                // Xử lý dữ liệu
                Log.e("title", title.toString())
                Log.e("content", content.toString())
                activityMainBinding.textView1.text = title
                activityMainBinding.textView2.text = content
            }
            it.close()
        }

    }

//    fun getMessageDirection(direction: Direction): String{
//        return when(direction){
//            Direction.East -> "We're going East"
//            Direction.West -> "We're going West"
//            Direction.South -> "We're going South"
//            Direction.North -> "We're going North"
//        }
//    }

    

//    fun plus(a: Int, b: (c: Int, d: Int) -> Int): Int{
//        return a+b(3,4)
//    }
//
//    fun minus(a: Int, b: Int): Int{
//        return a-b
//    }

}