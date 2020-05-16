package com.cmk.mailbox;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

public class LetterListAdapter extends PagedListAdapter<Letter, LetterListAdapter.LetterViewHolder> {

    private List<Letter> mLetters = Collections.emptyList(); // Cached copy of words
    //private List<Letter> mLetters;



    public  LetterListAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public LetterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);
        return new LetterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LetterViewHolder holder, int position) {
        Letter currLetter = getItem(position);
        if (currLetter != null) {
            holder.letterItemView.setText(currLetter.getLetter());
//        Letter current = mLetters.get(position);
//        holder.letterItemView.setText(current.getLetter());
        }

    }
    class LetterViewHolder extends RecyclerView.ViewHolder {
        private final TextView letterItemView;

        private LetterViewHolder(View itemView) {
            super(itemView);
            letterItemView = itemView.findViewById(R.id.textView);
        }
    }





    public static DiffUtil.ItemCallback<Letter> DIFF_CALLBACK = new DiffUtil.ItemCallback<Letter>() {
        @Override
        public boolean areItemsTheSame(Letter oldItem, Letter newItem) {
            return oldItem.getLetter() == newItem.getLetter();
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(Letter oldItem, @NonNull Letter newItem) {
            return oldItem.equals(newItem);
        }

    };
//    void setLetters(List<Letter> words){
//        mLetters = words;
//        notifyDataSetChanged();
   // }
}