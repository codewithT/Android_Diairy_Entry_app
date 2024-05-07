package com.example.additem.view
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.additem.R
import com.example.additem.filedata
import com.example.additem.model.UserData
import java.io.File


class UserAdapter(val c:Context,val userList:ArrayList<UserData>):RecyclerView.Adapter<UserAdapter.UserViewHolder>()
{
    private lateinit var  mListener :OnItemClickListener
    interface OnItemClickListener {
        fun onItemClick(position:Int)
    }
    fun setOnItemClickListener(listener: OnItemClickListener){
        mListener=listener
    }
    inner class UserViewHolder(val v:View,listener: OnItemClickListener):RecyclerView.ViewHolder(v){
        var name:TextView
        var mbNum:TextView
        var mMenus:ImageView
        var mdata :TextView


        init {
            name = v.findViewById<TextView>(R.id.mTitle)
            mbNum = v.findViewById<TextView>(R.id.mSubTitle)
            mMenus = v.findViewById(R.id.mMenus)
            mdata = v.findViewById<TextView>(R.id.mdata)

            mMenus.setOnClickListener { popupMenus(it,name.getText().toString(),mbNum.getText().toString(),mdata.getText().toString()) }
            v.setOnClickListener{
                listener.onItemClick(adapterPosition )
            }
        }

        private fun popupMenus(v:View,texttitle:String,finddate:String,writendata:String) {
            val position = userList[adapterPosition]
            val popupMenus = PopupMenu(c,v)
            popupMenus.inflate(R.menu.show_menu)
            popupMenus.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.editText->{
                        val v = LayoutInflater.from(c).inflate(R.layout.add_item,null)
                        val name = v.findViewById<EditText>(R.id.userName)
                        val number = v.findViewById<EditText>(R.id.userNo)
                        val edate = v.findViewById<TextView>(R.id.datetext)
                        name.setText(texttitle)
                        edate.setText(finddate)
                        number.setText(writendata)

                        AlertDialog.Builder(c)
                            .setView(v)
                            .setPositiveButton("Save"){
                                    dialog,_->
                                position.userName = name.text.toString()
                                position.userMb = edate.text.toString()
                                notifyDataSetChanged()
                                Toast.makeText(c,"User Information is Edited",Toast.LENGTH_SHORT).show()
                                dialog.dismiss()

                            }
                            .setNegativeButton("Cancel"){
                                    dialog,_->
                                dialog.dismiss()

                            }
                            .create()
                            .show()

                        true
                    }
                    R.id.delete->{
                        /**set delete*/
                        AlertDialog.Builder(c)
                            .setTitle("Delete")
                            .setIcon(R.drawable.ic_warning)
                            .setMessage("Are you sure delete this Information")
                            .setPositiveButton("Yes"){
                                    dialog,_->
                                userList.removeAt(adapterPosition)

                                notifyDataSetChanged()


                                Toast.makeText(c,"Deleted this Information",Toast.LENGTH_SHORT).show()
                                dialog.dismiss()
                            }
                            .setNegativeButton("No"){
                                    dialog,_->
                                dialog.dismiss()
                            }
                            .create()
                            .show()

                        true
                    }
                    else-> true
                }

            }
            popupMenus.show()
            val popup = PopupMenu::class.java.getDeclaredField("mPopup")
            popup.isAccessible = true
            val menu = popup.get(popupMenus)
            menu.javaClass.getDeclaredMethod("setForceShowIcon",Boolean::class.java)
                .invoke(menu,true)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v  = inflater.inflate(R.layout.list_item,parent,false)
        return UserViewHolder(v,mListener)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val newList = userList[position]
        holder.name.text = newList.userName
        holder.mbNum.text = newList.userMb
        holder.mdata.text = newList.userdata



    }

    override fun getItemCount(): Int {
        return  userList.size
    }
}