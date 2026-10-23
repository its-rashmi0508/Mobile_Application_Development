package com.example.contactprovider

import android.content.ContentProviderOperation
import android.content.ContentResolver
import android.provider.ContactsContract

object IndianContactsProvider {

    val sampleContacts = listOf(
        Contact("1", "Aarav Sharma", "+91 98765 43210"),
        Contact("2", "Aditi Rao", "+91 96321 87654"),
        Contact("3", "Amit Shah", "+91 98250 44556"),
        Contact("4", "Ananya Patel", "+91 98123 45678"),
        Contact("5", "Arjun Joshi", "+91 98230 66778"),
        Contact("6", "Deepa Menon", "+91 98470 88990"),
        Contact("7", "Diya Choudhury", "+91 99300 77889"),
        Contact("8", "Ishaan Mehta", "+91 98920 44556"),
        Contact("9", "Kavya Reddy", "+91 94400 55667"),
        Contact("10", "Neha Iyer", "+91 94440 11223"),
        Contact("11", "Pooja Deshmukh", "+91 98220 33445"),
        Contact("12", "Priya Nair", "+91 99001 12233"),
        Contact("13", "Rahul Banerjee", "+91 98310 88990"),
        Contact("14", "Rajesh Kumar", "+91 98200 11223"),
        Contact("15", "Riya Kapoor", "+91 98100 55667"),
        Contact("16", "Rohan Verma", "+91 97110 22334"),
        Contact("17", "Siddharth Das", "+91 98300 22334"),
        Contact("18", "Sneha Kulkarni", "+91 97654 32109"),
        Contact("19", "Suresh Raina", "+91 98480 66778"),
        Contact("20", "Tanvi Agarwal", "+91 98710 77889"),
        Contact("21", "Vikram Singh", "+91 99887 76655"),
        Contact("22", "Vivaan Gupta", "+91 98450 98765")
    )

    fun seedSampleContactsToProvider(contentResolver: ContentResolver) {
        try {
            for (contact in sampleContacts) {
                val ops = ArrayList<ContentProviderOperation>()

                val rawContactInsertIndex = ops.size
                ops.add(
                    ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                        .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                        .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                        .build()
                )

                ops.add(
                    ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                        .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
                        .withValue(
                            ContactsContract.Data.MIMETYPE,
                            ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE
                        )
                        .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, contact.name)
                        .build()
                )

                ops.add(
                    ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                        .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
                        .withValue(
                            ContactsContract.Data.MIMETYPE,
                            ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE
                        )
                        .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, contact.phoneNumber)
                        .withValue(
                            ContactsContract.CommonDataKinds.Phone.TYPE,
                            ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE
                        )
                        .build()
                )

                contentResolver.applyBatch(ContactsContract.AUTHORITY, ops)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
