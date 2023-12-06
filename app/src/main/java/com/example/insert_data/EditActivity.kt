package com.example.insert_data

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class EditActivity : AppCompatActivity() {

    private lateinit var dbHelper: DbAdapter
    private lateinit var currentPerson: Person
    private lateinit var etName: EditText
    private lateinit var etEmail:EditText
    private lateinit var etContact:EditText
    private lateinit var etAddress:EditText

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)


        dbHelper = DbAdapter(this)

        // Retrieve the person data from Intent
        val personId = intent.getIntExtra("personId", -1)
        currentPerson = dbHelper.getPersonById(personId)

        etName = findViewById(R.id.etName)
        etEmail = findViewById(R.id.etEmail)
        etContact = findViewById(R.id.etContact)
        etAddress = findViewById(R.id.etAddress)


        // Set initial values in EditText fields
        etName.setText(currentPerson.name)
        etEmail.setText(currentPerson.email)
        etContact.setText(currentPerson.contact)
        etAddress.setText(currentPerson.address)

        val btnUpdate=findViewById<Button>(R.id.btnUpdate)
        btnUpdate.setOnClickListener {
            updatePerson()
        }

        val btnDelete=findViewById<Button>(R.id.btnDelete)
        btnDelete.setOnClickListener {
            deletePerson()
        }

    }
    private fun updatePerson() {
        // Validate input
        if (etName.text.isBlank() || etEmail.text.isBlank()
            || etContact.text.isBlank() || etAddress.text.isBlank()
        ) {
            Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val updatedPerson = Person(

            id = currentPerson.id,
            name = etName.text.toString(),
            email = etEmail.text.toString(),
            contact = etContact.text.toString(),
            address = etAddress.text.toString()
        )

        dbHelper.updatePerson(updatedPerson)

        // Return to the DisplayActivity after updating
        val intent = Intent(this, DisplayActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun deletePerson() {
        dbHelper.deletePerson(currentPerson.id)

        // Return to the DisplayActivity after deleting
        val intent = Intent(this, DisplayActivity::class.java)
        startActivity(intent)
        finish()
    }

}
