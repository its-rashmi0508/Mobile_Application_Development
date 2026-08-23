package com.example.notifyapp

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.app.RemoteInput

class NotificationActionReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        when (intent.action) {

            "ACTION_REPLY" -> {

                val replyText =
                    RemoteInput.getResultsFromIntent(intent)
                        ?.getCharSequence("key_text_reply")
                        ?.toString()

                if (!replyText.isNullOrEmpty()) {

                    Toast.makeText(
                        context,
                        "Reply sent: $replyText",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            "ACTION_MARK_READ" -> {

                val notificationManager =
                    context.getSystemService(
                        Context.NOTIFICATION_SERVICE
                    ) as NotificationManager

                notificationManager.cancel(1)

                Toast.makeText(
                    context,
                    "Message marked as read",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}