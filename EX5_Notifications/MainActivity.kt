package com.example.notifyapp

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.RemoteInput

class MainActivity : AppCompatActivity() {

    private val channelId = "notifyapp_channel"
    private val notificationId = 1

    private lateinit var messageInput: EditText
    private lateinit var chatContainer: LinearLayout

    // Notification permission launcher
    private val notificationPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->

            if (isGranted) {
                showIncomingNotification()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        createNotificationChannel()

        messageInput = findViewById(R.id.messageInput)
        chatContainer = findViewById(R.id.chatContainer)

        val sendButton =
            findViewById<Button>(R.id.sendButton)

        val notificationButton =
            findViewById<Button>(R.id.notificationButton)

        // Send chat message
        sendButton.setOnClickListener {

            val message =
                messageInput.text.toString().trim()

            if (message.isNotEmpty()) {

                addMyMessage(message)

                messageInput.text.clear()
            }
        }

        // Test incoming message + notification
        notificationButton.setOnClickListener {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

                if (
                    checkSelfPermission(
                        Manifest.permission.POST_NOTIFICATIONS
                    ) == PackageManager.PERMISSION_GRANTED
                ) {

                    showIncomingNotification()

                } else {

                    notificationPermissionLauncher.launch(
                        Manifest.permission.POST_NOTIFICATIONS
                    )
                }

            } else {

                showIncomingNotification()
            }
        }
    }

    // Add user's message to chat
    private fun addMyMessage(message: String) {

        val textView = TextView(this)

        textView.text = message

        textView.textSize = 16f

        textView.setPadding(
            25,
            18,
            25,
            18
        )

        textView.setBackgroundResource(
            R.drawable.my_message_background
        )

        val params =
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )

        params.gravity =
            android.view.Gravity.END

        params.setMargins(
            50,
            8,
            10,
            8
        )

        textView.layoutParams = params

        chatContainer.addView(textView)
    }

    // Add incoming message to chat
    private fun addIncomingMessage(message: String) {

        val textView = TextView(this)

        textView.text = message

        textView.textSize = 16f

        textView.setPadding(
            25,
            18,
            25,
            18
        )

        textView.setBackgroundResource(
            R.drawable.incoming_message_background
        )

        val params =
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )

        params.gravity =
            android.view.Gravity.START

        params.setMargins(
            10,
            8,
            50,
            8
        )

        textView.layoutParams = params

        chatContainer.addView(textView)
    }

    // Creates notification channel
    private fun createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channel = NotificationChannel(
                channelId,
                "NotifyApp Messages",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {

                description =
                    "Notifications for new chat messages"
            }

            val manager =
                getSystemService(
                    Context.NOTIFICATION_SERVICE
                ) as NotificationManager

            manager.createNotificationChannel(channel)
        }
    }

    // Shows incoming message notification
    private fun showIncomingNotification() {
            if (
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                checkSelfPermission(
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            // REPLY ACTION
            val replyIntent = Intent(
                this,
                NotificationActionReceiver::class.java
            ).apply {
                action = "ACTION_REPLY"
            }

            val replyPendingIntent = PendingIntent.getBroadcast(
                this,
                100,
                replyIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or
                        PendingIntent.FLAG_MUTABLE
            )
            // Text input shown when Reply is pressed
            val remoteInput = RemoteInput.Builder(
                "key_text_reply"
            )
                .setLabel("Type your reply...")
                .build()

            val replyAction =
                NotificationCompat.Action.Builder(
                    android.R.drawable.ic_menu_send,
                    "Reply",
                    replyPendingIntent
                )
                    .addRemoteInput(remoteInput)
                    .build()

            // MARK AS READ ACTION
            val markReadIntent = Intent(
                this,
                NotificationActionReceiver::class.java
            ).apply {
                action = "ACTION_MARK_READ"
            }
            val markReadPendingIntent =
                PendingIntent.getBroadcast(
                    this,
                    101,
                    markReadIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT or
                            PendingIntent.FLAG_IMMUTABLE
                )
            val markReadAction =
                NotificationCompat.Action.Builder(
                    android.R.drawable.ic_menu_close_clear_cancel,
                    "Mark as Read",
                    markReadPendingIntent
                ).build()

            // BUILD NOTIFICATION
            val notification =
                NotificationCompat.Builder(
                    this,
                    channelId
                )
                    .setSmallIcon(
                        android.R.drawable.ic_dialog_email
                    )
                    .setContentTitle(
                        "Rahul"
                    )
                    .setContentText(
                        "Hey Rashmi! Don't forget the assignment."
                    )
                    .setStyle(
                        NotificationCompat.BigTextStyle()
                            .bigText(
                                "Hey Rashmi! Don't forget the assignment."
                            )
                    )
                    .setPriority(
                        NotificationCompat.PRIORITY_DEFAULT
                    )
                    .setAutoCancel(true)
                    .addAction(replyAction)
                    .addAction(markReadAction)

            // Show notification
            NotificationManagerCompat
                .from(this)
                .notify(
                    notificationId,
                    notification.build()
                )

            // Also show the message inside the chat
            addIncomingMessage(
                "Rahul: Hey Rashmi! Don't forget the assignment."
            )
        }
    }