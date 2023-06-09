package com.defaultcompany.nodatabase

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlin.collections.ArrayList

class AccountAdapter(val context: Context, val accountList: ArrayList<DBdata>,private val onItemClicker: OnItemClicker) :
    RecyclerView.Adapter<AccountAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.acoountlist, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: AccountAdapter.ViewHolder, position: Int) {
        holder.name.text = accountList[position].name
        holder.email.text = accountList[position].email
        holder.editbtn.setOnClickListener {
           onItemClicker.onEditItemClick(position)
        }
        holder.itemView.setOnClickListener {
            onItemClicker.onDeleteItemClick(position)
        }
    }

    override fun getItemCount(): Int {
        return accountList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.txtName)
        val email: TextView = itemView.findViewById(R.id.txtEmail)
        val editbtn: ImageView = itemView.findViewById(R.id.iv_edit)
    }
    interface OnItemClicker {
        fun onEditItemClick(position: Int)
        fun onDeleteItemClick(position: Int)

    }
}