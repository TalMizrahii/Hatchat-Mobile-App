package com.example.hatchatmobile1.ViewModals;

import static java.lang.Integer.parseInt;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.MutableLiveData;

import com.example.hatchatmobile1.Activities.ChatScreenActivity;
import com.example.hatchatmobile1.Entities.FirebaseIncomeMessage;
import com.example.hatchatmobile1.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
public class FirebaseModalService extends FirebaseMessagingService {
    private MutableLiveData<FirebaseIncomeMessage> firebaseLiveData;

    private Context context;

    private String mainUsername;

    private String token;

    public FirebaseModalService() {
        super();

        FireBaseLiveData fireBaseLiveData = FireBaseLiveData.getInstance();
        firebaseLiveData = fireBaseLiveData.getLiveData();
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setMainUsername(String mainUsername) {
        this.mainUsername = mainUsername;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public void onMessageSent(@NonNull String s) {
        super.onMessageSent(s);
    }

    @SuppressLint("WrongThread")
    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        if (message.getNotification() == null) {
            return;
        }


        createNotificationChannel();


        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        Map<String, String> notificationMessage = message.getData();
        if (notificationMessage.isEmpty()) {
            return;
        }
        String chatID = notificationMessage.get("chatID");
        String contactUsername = notificationMessage.get("senderUsername");
        String created = notificationMessage.get("created");
        String content = notificationMessage.get("content");
        assert chatID != null;
        FirebaseIncomeMessage firebaseIncomeMessage = new FirebaseIncomeMessage(parseInt(chatID),
                contactUsername,
                created,
                content);
        firebaseLiveData.postValue(firebaseIncomeMessage);

        Intent chatIntent = new Intent(this.context, ChatScreenActivity.class);
        chatIntent.putExtra("username", notificationMessage.get("senderUsername"));
        chatIntent.putExtra("mainUsername", this.mainUsername);
        chatIntent.putExtra("token", this.token);
        chatIntent.putExtra("contactId", notificationMessage.get("chatID"));
        PendingIntent pendingIntent = PendingIntent.getActivity(this.context, 0, chatIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "1")
                .setSmallIcon(R.drawable.haticon)
                .setContentTitle(message.getNotification().getTitle())
                .setContentText(message.getNotification().getBody())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);


        notificationManager.notify(1, builder.build());



    }

    private void createNotificationChannel() {
        // Do Not Remove!
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.O) {
            return;
        }
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel("1", "Main Channel", importance);
        channel.setDescription("Best channel");
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }

    public MutableLiveData<FirebaseIncomeMessage> getFirebaseIncomeMessageMutableLiveData() {
        return firebaseLiveData;
    }
}
