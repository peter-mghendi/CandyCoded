package com.codeschool.candycoded;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.codeschool.candycoded.DB.CandyContract;
import com.codeschool.candycoded.DB.CandyContract.CandyEntry;
import com.codeschool.candycoded.DB.CandyDbHelper;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    public static final String SHARE_DESCRIPTION = "Look at this delicious candy from Candy Coded - ";
    public static final String HASHTAG_CANDYCODED = " #candycoded";
    String mCandyImageUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = DetailActivity.this.getIntent();

        if (intent != null && intent.hasExtra("position")) {
            int position = intent.getIntExtra("position", 0);

            CandyDbHelper dbHelper = new CandyDbHelper(this);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM candy", null);
            cursor.moveToPosition(position);

            String candyName = cursor.getString(cursor.getColumnIndexOrThrow(
                    CandyContract.CandyEntry.COLUMN_NAME_NAME));
            String candyPrice = cursor.getString(cursor.getColumnIndexOrThrow(
                    CandyEntry.COLUMN_NAME_PRICE));
            mCandyImageUrl = cursor.getString(cursor.getColumnIndexOrThrow(
                    CandyEntry.COLUMN_NAME_IMAGE));
            String candyDesc = cursor.getString(cursor.getColumnIndexOrThrow(
                    CandyEntry.COLUMN_NAME_DESC));


            TextView textView = (TextView) this.findViewById(R.id.text_view_name);
            textView.setText(candyName);

            TextView textViewPrice = (TextView) this.findViewById(R.id.text_view_price);
            textViewPrice.setText(candyPrice);

            TextView textViewDesc = (TextView) this.findViewById(R.id.text_view_desc);
            textViewDesc.setText(candyDesc);

            ImageView imageView = (ImageView) this.findViewById(
                    R.id.image_view_candy);
            Picasso.with(this).load(mCandyImageUrl).into(imageView);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.detail, menu);
        return true;
    }

    // ***
    // Task 4 - Share the Current Candy with an Intent
    // ***
    // (1) Override the onOptionsItemSelected() method
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // (4)
        createShareIntent();
        // (2) return true
        return true;
    }

    // (3) Since there is a big chunk of code to create the Intent let's do it in a separate method
    // Create a method called...
    private void createShareIntent() {
        // (5) Create an Intent called shareIntent with action ACTION_SEND
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        // (6) Use the Intent's setType() method to set the type to "text/plain".
        shareIntent.setType("text/plain");
        // (7) Use the Intent's putExtra() method to add the text we want to share.
        // The first paramether is the type of content Intent.EXTRA_TEXT, and the second is our String ...
        shareIntent.putExtra(Intent.EXTRA_TEXT,
                SHARE_DESCRIPTION + mCandyImageUrl + HASHTAG_CANDYCODED);
        // (8) We can now call startActivity() with our Intent
        startActivity(shareIntent);
    }
}
