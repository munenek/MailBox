package com.cmk.mailbox;


import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import java.util.List;

public class LetterRepository {
    private LetterDao mLetterDao;
    //private LiveData<List<Letter>> mletters;
    private LiveData<PagedList<Letter>> mletters;

     public LetterRepository(Application application) {
        LetterRoomDatabase db = LetterRoomDatabase.getDatabase(application);
        mLetterDao = db.letterDao();
        //mletters = mLetterDao.getLetters();

         PagedList.Config config = (new PagedList.Config.Builder())
                 .setPageSize(20)
                 .setEnablePlaceholders(false)
                 .setPrefetchDistance(10)
                 .build();

         mletters = (new LivePagedListBuilder(mLetterDao.getAllUsers(),config)).build();
    }

   public LiveData<PagedList<Letter>> getLetters() {
       return mletters;
   }
    // You must call this on a non-UI thread or your app will crash.
    // Like this, Room ensures that you're not doing any long running operations on the main
    // thread, blocking the UI.
    public void insert(Letter letter) {
        new insertAsyncTask(mLetterDao).execute(letter);
    }

    private static class insertAsyncTask extends AsyncTask<Letter, Void, Void> {

        private LetterDao mAsyncTaskDao;

        insertAsyncTask(LetterDao dao) {
            mAsyncTaskDao = dao;
        }


        @Override
        protected Void doInBackground(Letter... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}