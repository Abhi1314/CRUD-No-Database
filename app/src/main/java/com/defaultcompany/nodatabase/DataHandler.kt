package com.defaultcompany.nodatabase

class DataHandler {
    private val dataList: MutableList<String>

    init {
        dataList = ArrayList()
    }

    fun createData(inputlist:ArrayList<DBdata>) {
        dataList.add(inputlist.toString())
    }

    fun readData(): List<String> {
        return dataList
    }

    fun updateData(position: Int, inputlist:ArrayList<DBdata>) {
        if (position >= 0 && position < dataList.size) {
            dataList[position] = inputlist.toString()
        }
    }

    fun deleteData(position: Int) {
        if (position >= 0 && position < dataList.size) {
            dataList.removeAt(position)
        }
    }

    fun clearData() {
        dataList.clear()
    }
}