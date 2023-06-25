package com.example.hatchatmobile1.Firebase;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.MutableLiveData;

import com.example.hatchatmobile1.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class FirebaseModalService extends FirebaseMessagingService {

    private static FirebaseModalService instance;
    private Context context;

    private FirebaseModalService(Context context) {
        this.context = context;
    }

    public static synchronized FirebaseModalService getInstance(Context context) {
        if (instance == null) {
            instance = new FirebaseModalService(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onMessageSent(@NonNull String s) {
        super.onMessageSent(s);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        if (message.getNotification() == null) {
            return;
        }
        createNotificationChannel();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "1")
                .setSmallIcon(R.drawable.haticon)
                .setContentTitle(message.getNotification().getTitle())
                .setContentText(message.getNotification().getBody())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(1, builder.build());
        Map<String, String> notificationMessage = message.getData();
        if (notificationMessage.isEmpty()) {
            return;
        }
        String chatID = notificationMessage.get("chatID");
        String contactUsername = notificationMessage.get("senderUsername");
        String contactDisplayName = notificationMessage.get("senderDisplayName");
        String sender = notificationMessage.get("receiver");
        String msgId = notificationMessage.get("msgID");
        String date = notificationMessage.get("date");

    }

    private void createNotificationChannel() {
        // Do Not Remove!
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return;
        }
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel("1", "Main Channel", importance);
        channel.setDescription("Best channel");
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }
}
