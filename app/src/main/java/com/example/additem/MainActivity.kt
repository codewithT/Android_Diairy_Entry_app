package com.example.additem

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.additem.model.UserData
import com.example.additem.view.UserAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import java.util.Calendar

lateinit var bdate : Button
class MainActivity : AppCompatActivity() {
    private var mYear:Int = 0;
    private var mMonth:Int = 0;
    private var mDay:Int = 0;
    var file = "Date"
    var dfile="mydata"
    lateinit var data: String
    private lateinit var addsBtn: FloatingActionButton
    private lateinit var recv: RecyclerView
    private lateinit var userList:ArrayList<UserData>
    private lateinit var userAdapter: UserAdapter
    lateinit var drawerLayout: DrawerLayout
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    lateinit var navView: NavigationView
    lateinit var navView2: NavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.drawer_layout)
        actionBarDrawerToggle = ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close)
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView = findViewById(R.id.nav_view)
        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_account -> {
                    showToast("My Account clicked")
                    true
                }
                R.id.nav_settings -> {
                    showToast("Settings clicked")
                    true
                }
                R.id.nav_logout -> {
                    showToast("Logout clicked")
                    true
                }
                else -> false
            }
        }

        navView2 = findViewById(R.id.nav_view2)
        navView2.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                // Handle items for the second navigation view if needed
                else -> false
            }
        }

        userList = ArrayList()
        addsBtn = findViewById(R.id.addingBtn)
        recv = findViewById(R.id.mRecycler)
        userAdapter = UserAdapter(this,userList)
        recv.layoutManager = LinearLayoutManager(this)
        recv.adapter = userAdapter

        getUserdata()

        userAdapter.setOnItemClickListener(object :UserAdapter.OnItemClickListener{
            override fun onItemClick(position: Int) {
                intent = Intent(this@MainActivity, filedata::class.java)
                intent.putExtra("title",userList[position].userName)
                intent.putExtra("date",userList[position].userMb)
                intent.putExtra("position",position)
                startActivity(intent)
            }
        })

        addsBtn.setOnClickListener { addInfo(it) }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun getUserdata() {
        val dates = arrayOf("29-3-2024", "30-3-2024", "31-3-2024")
        val titles = arrayOf("Greate Day", "Memoires in Ink","Dreams and Doodles")
        for(i in dates.indices){
            val news = UserData(titles[i],dates[i],"hello every one")
            userList.add(news)
        }
        userAdapter.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            android.R.id.home -> {
                if (drawerLayout.isDrawerOpen(navView)) {
                    drawerLayout.closeDrawer(navView)
                } else {
                    drawerLayout.openDrawer(navView)
                }
                true
            }
            R.id.calendar ->{
                var file1 ="31-3-2024"
                val c = Calendar.getInstance()
                mYear = c[Calendar.YEAR]
                mMonth = c[Calendar.MONTH]
                mDay = c[Calendar.DAY_OF_MONTH]

                val datePickerDialog = DatePickerDialog(this,{
                        view,year,monthofYear,dayOfMonth
                    ->
                    file1 = dayOfMonth.toString() + "-"+ (monthofYear+1)+"-"+year
                    intent = Intent(this@MainActivity, filedata::class.java)
                    intent.putExtra("title","Title : Memoreble Day !")
                    intent.putExtra("date","$file1")
                    intent.putExtra("position",1)
                    startActivity(intent)

                },mYear,mMonth,mDay)
                datePickerDialog.show()
                true
            }
            R.id.settings -> {
                if (isSettingsFragmentVisible()) {
                    supportFragmentManager.popBackStack()
                } else {
                    replaceWithSettingsFragment()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun isSettingsFragmentVisible(): Boolean {
        val fragment = supportFragmentManager.findFragmentById(R.id.container)
        return fragment is SettingFragment
    }

    private fun replaceWithSettingsFragment() {
        val settingsFragment = SettingFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, settingsFragment)
            .addToBackStack(null)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()
    }

    private fun addInfo(view:View) {
        val inflter = LayoutInflater.from(this)
        val v = inflter.inflate(R.layout.add_item,null)
        val alarm = v.findViewById<Button>(R.id.set_alarm)
        alarm.setOnClickListener {
            val intent = Intent(this@MainActivity, AlarmManager::class.java)
            startActivity(intent)
        }
        val userName = v.findViewById<EditText>(R.id.userName)
        val userNo = v.findViewById<EditText>(R.id.userNo)
        userNo.setSelection(0)
        bdate = v.findViewById(R.id.date)
        bdate.setOnClickListener(){
            val c = Calendar.getInstance()
            mYear = c[Calendar.YEAR]
            mMonth = c[Calendar.MONTH]
            mDay = c[Calendar.DAY_OF_MONTH]
            val datePickerDialog = DatePickerDialog(this,{
                    view,year,monthofYear,dayOfMonth
                ->
                file = dayOfMonth.toString() + "-"+ (monthofYear+1)+"-"+year
            },mYear,mMonth,mDay)
            datePickerDialog.show()
        }
        val addDialog = AlertDialog.Builder(this)
        addDialog.setView(v)
        addDialog.setPositiveButton("Save"){
                dialog,_->
            data = userNo.text.toString()
            try {
                val fOut = openFileOutput(file, MODE_APPEND)
                fOut.write(data.toByteArray())
                fOut.close()
                Toast.makeText(baseContext, "file saved", Toast.LENGTH_SHORT).show()
            }
            catch (e: Exception) {
                e.printStackTrace()
            }
            val names = userName.text.toString()
            val number = userNo.text.toString()
            userList.add(UserData("$names","$file","$number"))
            userAdapter.notifyDataSetChanged()
            Toast.makeText(this,"Adding User Information Success",Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        addDialog.setNegativeButton("Cancel"){
                dialog,_->
            dialog.dismiss()
            Toast.makeText(this,"Cancel",Toast.LENGTH_SHORT).show()
        }
        addDialog.create()
        addDialog.show()
    }
}
