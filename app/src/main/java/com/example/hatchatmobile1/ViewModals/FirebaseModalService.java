package com.example.hatchatmobile1.ViewModals;

import static java.lang.Integer.parseInt;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.hatchatmobile1.DaoRelated.Contact;
import com.example.hatchatmobile1.Entities.FirebaseIncomeMessage;
import com.example.hatchatmobile1.R;
import com.example.hatchatmobile1.Repositories.ContactRepository;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
public class FirebaseModalService extends FirebaseMessagingService {
    private MutableLiveData<FirebaseIncomeMessage> firebaseLiveData;
    public FirebaseModalService() {
        super();
        FireBaseLiveData fireBaseLiveData = FireBaseLiveData.getInstance();
        firebaseLiveData = fireBaseLiveData.getLiveData();
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
        String created = notificationMessage.get("created");
        String content = notificationMessage.get("content");
        assert chatID != null;
        FirebaseIncomeMessage firebaseIncomeMessage = new FirebaseIncomeMessage(parseInt(chatID),
                contactUsername,
                created,
                content);
        firebaseLiveData.postValue(firebaseIncomeMessage);
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