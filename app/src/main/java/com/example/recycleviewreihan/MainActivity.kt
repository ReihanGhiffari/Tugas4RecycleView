package com.example.recycleviewreihan

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecyclerViewAdapter
    private val dataList = ArrayList<DataModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set up RecyclerView
        recyclerView = findViewById(R.id.recyclerView)
        adapter = RecyclerViewAdapter(dataList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // Tombol untuk menambah data
        val addButton: Button = findViewById(R.id.addButton)

        // Event ketika tombol "Add" ditekan
        addButton.setOnClickListener {
            showAddItemDialog() // Menampilkan dialog untuk input data baru
        }

        // (Opsional) Menambahkan beberapa data awal
        addSampleData()
    }

    // Fungsi untuk menampilkan dialog input data baru
    private fun showAddItemDialog() {
        // Inflate layout dialog_add_item.xml
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_item, null)
        val etTitle = dialogView.findViewById<EditText>(R.id.etTitle)
        val etDescription = dialogView.findViewById<EditText>(R.id.etDescription)

        // Membuat AlertDialog untuk input data
        val dialog = AlertDialog.Builder(this)
            .setTitle("Add New Item")
            .setView(dialogView)
            .setPositiveButton("Add") { _, _ ->
                val title = etTitle.text.toString()
                val description = etDescription.text.toString()

                // Validasi input kosong
                if (title.isNotEmpty() && description.isNotEmpty()) {
                    addNewData(title, description)
                } else {
                    Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .create()

        dialog.show()
    }

    // Fungsi untuk menambah item baru ke RecyclerView
    private fun addNewData(name: String, description: String) {
        val newData = DataModel(name, description)
        dataList.add(newData)
        adapter.notifyItemInserted(dataList.size - 1)
        Toast.makeText(this, "Data Added", Toast.LENGTH_SHORT).show()
    }

    // Fungsi opsional untuk menambahkan data sample
    private fun addSampleData() {
        dataList.add(DataModel("Item 1", "Description 1"))
        dataList.add(DataModel("Item 2", "Description 2"))
        dataList.add(DataModel("Item 3", "Description 3"))
        adapter.notifyDataSetChanged()
    }
}
