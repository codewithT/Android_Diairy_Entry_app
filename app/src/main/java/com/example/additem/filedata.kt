package com.example.additem

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.additem.model.UserData
import com.example.additem.view.UserAdapter
import java.util.Calendar

class filedata : AppCompatActivity() {
    private var mYear:Int = 0;
    private var mMonth:Int = 0;
    private var mDay:Int = 0;
    var file = "Date"
    var Mtitle ="title"
    lateinit var data: String
    private lateinit var userList:ArrayList<UserData>
    private lateinit var userAdapter: UserAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filedata)


        //val title : TextView=findViewById(R.id.gettext)
        val topic : TextView=findViewById(R.id.gettext)
        val title : TextView=findViewById(R.id.title)
        val date : TextView=findViewById(R.id.date)

        val bundle:Bundle?=intent.extras
        val fdate= bundle!!.getString("date")
        val Ttitle= bundle.getString("title")
        var position1:Int = bundle.getInt("position")
//        val position = userList[position1]
//        val popupMenus = PopupMenu(c,v)

//        topic.text = fdate

        title.text = Ttitle
        date.text=fdate
        try {
            val fin = openFileInput(fdate)
            var c: Int
            var temp = ""
            while (fin.read().also { c = it } != -1) {
                temp += c.toChar().toString()
            }
            fin.close()
            topic.text = temp

        } catch (e: Exception) {
            e.printStackTrace()
        }



    }



}