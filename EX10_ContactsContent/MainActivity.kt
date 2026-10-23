package com.example.contactprovider

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.ContactsContract
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var tvContactCount: TextView
    private lateinit var etSearch: EditText
    private lateinit var cardPermission: MaterialCardView
    private lateinit var btnGrantPermission: MaterialButton
    private lateinit var progressBar: ProgressBar
    private lateinit var tvEmpty: TextView
    private lateinit var rvContacts: RecyclerView

    private lateinit var contactAdapter: ContactAdapter

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            loadContacts()
        } else {
            showPermissionNeededUi()
            Toast.makeText(
                this,
                "Permission denied. Cannot access contacts.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        tvContactCount = findViewById(R.id.tvContactCount)
        etSearch = findViewById(R.id.etSearch)
        cardPermission = findViewById(R.id.cardPermission)
        btnGrantPermission = findViewById(R.id.btnGrantPermission)
        progressBar = findViewById(R.id.progressBar)
        tvEmpty = findViewById(R.id.tvEmpty)
        rvContacts = findViewById(R.id.rvContacts)

        contactAdapter = ContactAdapter(
            onContactClick = { contact ->
                shareContactDetails(contact)
            },
            onShareClick = { contact ->
                shareContactDetails(contact)
            }
        )
        rvContacts.adapter = contactAdapter

        btnGrantPermission.setOnClickListener {
            requestPermissionLauncher.launch(Manifest.permission.READ_CONTACTS)
        }

        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                contactAdapter.filter(s?.toString() ?: "")
                updateCountBadge()
                if (contactAdapter.itemCount == 0) {
                    tvEmpty.visibility = View.VISIBLE
                    rvContacts.visibility = View.GONE
                } else {
                    tvEmpty.visibility = View.GONE
                    rvContacts.visibility = View.VISIBLE
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        checkAndRequestContactsPermission()
    }

    private fun checkAndRequestContactsPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_CONTACTS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            loadContacts()
        } else {
            showPermissionNeededUi()
        }
    }

    private fun showPermissionNeededUi() {
        cardPermission.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
        tvEmpty.visibility = View.GONE
        rvContacts.visibility = View.GONE
        tvContactCount.text = getString(R.string.contacts_count_fmt, 0)
    }

    private fun loadContacts() {
        cardPermission.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
        tvEmpty.visibility = View.GONE
        rvContacts.visibility = View.GONE

        lifecycleScope.launch(Dispatchers.IO) {
            var contactsList = fetchContactsFromProvider()

            // If device ContentProvider has no contacts, seed sample Indian contacts into provider & reload
            if (contactsList.isEmpty()) {
                if (ContextCompat.checkSelfPermission(
                        this@MainActivity,
                        Manifest.permission.WRITE_CONTACTS
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    IndianContactsProvider.seedSampleContactsToProvider(contentResolver)
                    contactsList = fetchContactsFromProvider()
                }
                
                // Fallback to sample contacts if provider remains empty or read-only
                if (contactsList.isEmpty()) {
                    contactsList = IndianContactsProvider.sampleContacts
                }
            }

            withContext(Dispatchers.Main) {
                progressBar.visibility = View.GONE
                if (contactsList.isEmpty()) {
                    tvEmpty.visibility = View.VISIBLE
                    rvContacts.visibility = View.GONE
                } else {
                    tvEmpty.visibility = View.GONE
                    rvContacts.visibility = View.VISIBLE
                    contactAdapter.updateContacts(contactsList)
                    updateCountBadge()
                }
            }
        }
    }

    private fun updateCountBadge() {
        val count = contactAdapter.getFilteredCount()
        tvContactCount.text = getString(R.string.contacts_count_fmt, count)
    }

    private fun fetchContactsFromProvider(): List<Contact> {
        val contacts = mutableListOf<Contact>()
        val contentResolver = contentResolver

        val projection = arrayOf(
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER
        )

        val sortOrder = "${ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME} ASC"

        try {
            contentResolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                projection,
                null,
                null,
                sortOrder
            )?.use { cursor ->
                val idIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID)
                val nameIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
                val numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)

                while (cursor.moveToNext()) {
                    val id = if (idIndex >= 0) cursor.getString(idIndex) ?: "" else ""
                    val name = if (nameIndex >= 0) cursor.getString(nameIndex) ?: "Unknown" else "Unknown"
                    val number = if (numberIndex >= 0) cursor.getString(numberIndex) ?: "" else ""

                    if (name.isNotBlank() && number.isNotBlank()) {
                        contacts.add(Contact(id, name, number))
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return contacts
    }

    private fun shareContactDetails(contact: Contact) {
        val shareBody = "Contact Details:\nName: ${contact.name}\nPhone: ${contact.phoneNumber}"

        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, "Contact: ${contact.name}")
            putExtra(Intent.EXTRA_TEXT, shareBody)
        }

        val chooser = Intent.createChooser(shareIntent, getString(R.string.share_contact_title))
        startActivity(chooser)
    }
}
