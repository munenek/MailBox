package com.cmk.mailbox;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private LetterViewModel mLetterViewModel;
    public static final int NEW_Letter_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final LetterListAdapter adapter = new LetterListAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

       // mLetterViewModel = new ViewModelProviders(this).of(this).get(LetterViewModel.class);
        mLetterViewModel = ViewModelProviders.of(this).get(LetterViewModel.class);
        //mLetterViewModel = new LetterViewModel(getApplication());
        //mLetterViewModel = new ViewModelProvider(this).get(LetterViewModel.class);

        mLetterViewModel.getLetters().observe(this, new Observer<PagedList<Letter>>() {
            @Override
            public void onChanged(@Nullable final PagedList<Letter> letters) {
                // Update the cached copy of the words in the adapter.
                adapter.submitList(letters);
            }
        });
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AddLetter.class);
                //startActivity(intent);
                startActivityForResult(intent, NEW_Letter_ACTIVITY_REQUEST_CODE);
            }
        });


    }
//    private fun subscribeUi(adapter: PersonAdapter) {
//        viewModel.getPersonLiveData().observe(this, Observer { names ->
//            if (names != null) adapter.submitList(names)
//        })
  //}


//    private  void subscribeUi(LetterListAdapter adapter){
//        mLetterViewModel.g
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_Letter_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Letter letter = new Letter(data.getStringExtra(AddLetter.EXTRA_REPLY));
            mLetterViewModel.insert(letter);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }
}
