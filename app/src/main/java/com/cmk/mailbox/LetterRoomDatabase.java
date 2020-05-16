package com.cmk.mailbox;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Letter.class}, version = 1)
public abstract class LetterRoomDatabase extends RoomDatabase {
    public abstract LetterDao letterDao();
    private static volatile LetterRoomDatabase INSTANCE;
    //private static final int NUMBER_OF_THREADS = 4;
    //static final ExecutorService databaseWriteExecutor =
           // Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static LetterRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (LetterRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            LetterRoomDatabase.class, "Letter_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
    /**
     * Override the onOpen method to populate the database.
     * For this sample, we clear the database every time it is created or opened.
     */
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){

        @Override
        public void onOpen (@NonNull SupportSQLiteDatabase db){
            super.onOpen(db);
            // If you want to keep the data through app restarts,
            // comment out the following line.
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    /**
     * Populate the database in the background.
     * If you want to start with more words, just add them.
     */
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final LetterDao mDao;
        String [] words = {"dolphin", "crocodile", "cobra"};

        PopulateDbAsync(LetterRoomDatabase db) {
            mDao = db.letterDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            // Start the app with a clean database every time.
            // Not needed if you only populate on creation.
            mDao.deleteAll();

            for( int i = 0; i <= words.length - 1; i++) {
                Letter word = new Letter(words[i]);
                mDao.insert(word);
            }
            return null;
        }
    }
}
