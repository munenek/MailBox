package com.cmk.mailbox.Model;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface LetterDao {
    // allowing the insert of the same word multiple times by passing a
    // conflict resolution strategy
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Letter letter);

    @Query("DELETE FROM letter_table")
    void deleteAll();

    @Query("SELECT * from letter_table ORDER BY letter ASC")
    //LiveData<List<Letter>> getLetters();
    public abstract DataSource.Factory<Integer,Letter> getAllUsers();
}
