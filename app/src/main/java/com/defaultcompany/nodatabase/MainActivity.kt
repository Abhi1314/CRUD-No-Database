package com.defaultcompany.nodatabase

import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.defaultcompany.nodatabase.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val mainBinding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_main)
    }
    private val Add_accountDialog: Dialog by lazy {
        Dialog(this@MainActivity, R.style.DialogCustomTheme)
    }

    private val Edit_accountDialog: Dialog by lazy {
        Dialog(this, R.style.DialogCustomTheme)
    }
    private val deleteAccountDialog: Dialog by lazy {
        Dialog(this, R.style.DialogCustomTheme)
    }
    val dataHandler = DataHandler()
    var mainPos = -1
    lateinit var Account: EditText
    lateinit var email: EditText
    private var list:ArrayList<DBdata> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Add_accountDialog.setContentView(R.layout.addaccount_dialog)
        Add_accountDialog.window!!.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        deleteAccountDialog.setContentView(R.layout.delete_dialog)
        deleteAccountDialog.window!!.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        val delete = deleteAccountDialog.findViewById<Button>(R.id.btnDelete)
        val cancel = deleteAccountDialog.findViewById<Button>(R.id.btnCancle)
        Edit_accountDialog.setContentView(R.layout.edit_layout)
        Edit_accountDialog.window!!.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        Account = Add_accountDialog.findViewById(R.id.edaccountNameTextView)
        email = Add_accountDialog.findViewById(R.id.edemail)
        val btn = Add_accountDialog.findViewById<Button>(R.id.btnAdd)
         mainBinding.add.setOnClickListener {
             Add_accountDialog.show()
             Account.text.clear()
             email.text.clear()
         }


        btn.setOnClickListener {
            if (Account.text.toString().isEmpty()) {
                Account.error = getString(R.string.AccountEmpty)
            } else if (email.text.toString().isEmpty()) {
                Account.error = null
                email.error = getString(R.string.Accountemail)
            } else {
                Account.error = null
                email.error = null
                list.add(DBdata(Account.text.toString(),email.text.toString()))
                dataHandler.createData(list)
                setRecycleview()
                Add_accountDialog.dismiss()
            }
        }
        delete.setOnClickListener {
            list.removeAt(mainPos)
            dataHandler.deleteData(mainPos)
            setRecycleview()
            deleteAccountDialog.dismiss()
        }
        cancel.setOnClickListener {
            deleteAccountDialog.dismiss()
        }
        dataHandler.readData()

    }
    fun setRecycleview(){
        mainBinding.rvAccount.adapter= AccountAdapter(this@MainActivity,list,object :AccountAdapter.OnItemClicker{
            override fun onEditItemClick(position: Int) {
                val editBtn = Edit_accountDialog.findViewById<Button>(R.id.btnEdit)
                val updateAcc = Edit_accountDialog.findViewById<EditText>(R.id.edaccountNameTextView)
                val updateEmail = Edit_accountDialog.findViewById<EditText>(R.id.edemail)
                updateAcc.setText(list[position].name)
                updateEmail.setText(list[position].email)
                editBtn.setOnClickListener {
                    if (updateAcc.text.toString().isEmpty()||updateEmail.text.toString().isEmpty()) {
                        updateAcc.error = getString(R.string.AccountEmpty)
                        updateEmail.error = getString(R.string.Accountemail)
                    } else {
                        list[position].name = updateAcc.text.toString()
                        list[position].email = updateEmail.text.toString()
                        dataHandler.updateData(position,list)
                        mainBinding.rvAccount.adapter!!.notifyItemChanged(position)
                    }
                    Edit_accountDialog.dismiss()
                }
                Edit_accountDialog.show()
            }

            override fun onDeleteItemClick(position: Int) {
                    mainPos = position
                    deleteAccountDialog.show()
            }
        })
    }
}