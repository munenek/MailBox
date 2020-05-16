package com.cmk.mailbox;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "letter_table")
public class Letter {

        @PrimaryKey
        @NonNull
        @ColumnInfo(name = "letter")
        private String mLetter;

        public Letter(@ NonNull  String letter) {this.mLetter = letter;}

       @NonNull
        public String getLetter(){return this.mLetter;}
}