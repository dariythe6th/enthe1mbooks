package com.enthe1m.myapplication;



import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private BookAdapter bookAdapter;
    private List<Book> bookList;
    private UserDatabase db;
    private TextView bookCounter;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        bookCounter = findViewById(R.id.bookCounter);

        db = Room.databaseBuilder(getApplicationContext(), UserDatabase.class, "user-database")
                .addMigrations(UserDatabase.MIGRATION_1_2, UserDatabase.MIGRATION_2_3)
                .allowMainThreadQueries()
                .build();

        userId = getIntent().getIntExtra("USER_ID", -1);
        bookList = db.bookDao().getAllBooksByUser(userId);

        bookAdapter = new BookAdapter(bookList, new BookAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Book book) {
                Intent intent = new Intent(RecyclerViewActivity.this, NotesActivity.class);
                intent.putExtra("BOOK_ID", book.id);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(bookAdapter);

        updateBookCounter();

        Button addBookButton = findViewById(R.id.addBookButton);
        addBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecyclerViewActivity.this, AddBookActivity.class);
                intent.putExtra("USER_ID", userId);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            Intent intent = new Intent(RecyclerViewActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        bookList.clear();
        bookList.addAll(db.bookDao().getAllBooksByUser(userId));
        bookAdapter.notifyDataSetChanged();
        updateBookCounter();
    }

    private void updateBookCounter() {
        bookCounter.setText(String.valueOf(bookList.size()));
    }
}
