package com.example.hatchatmobile1.ViewModals;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.hatchatmobile1.Entities.FirebaseIncomeMessage;

public class FireBaseLiveData {

    private static FireBaseLiveData instance;
    private  MutableLiveData<FirebaseIncomeMessage> firebaseLiveDataList;

    private FireBaseLiveData() {
    }

    public static FireBaseLiveData getInstance() {
        if (instance == null) {
            synchronized (FireBaseLiveData.class) {
                instance = new FireBaseLiveData();
            }
        }
        return instance;
    }

    public MutableLiveData<FirebaseIncomeMessage> getLiveData(){
        if(firebaseLiveDataList == null){
            synchronized (FireBaseLiveData.class){
                firebaseLiveDataList = new MutableLiveData<>();
            }
        }
        return firebaseLiveDataList;
    }
}
