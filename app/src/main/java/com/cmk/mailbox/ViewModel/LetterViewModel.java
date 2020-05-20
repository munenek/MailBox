package com.cmk.mailbox.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import com.cmk.mailbox.Model.Letter;


public class LetterViewModel extends AndroidViewModel {
    private LetterRepository mRepository;

    private LiveData<PagedList<Letter>> mletter;

     public LetterViewModel (Application application) {
        super(application);
       mRepository = new LetterRepository(application);
       mletter = mRepository.getLetters();
    }

     public LiveData<PagedList<Letter>> getLetters() { return mletter; }

    public void insert(Letter letter) { mRepository.insert(letter); }
}
