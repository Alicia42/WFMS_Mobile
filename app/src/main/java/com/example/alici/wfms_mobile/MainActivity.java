package com.example.alici.wfms_mobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.*;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.*;
import com.amazonaws.regions.Region;
import android.util.Log;
import android.app.ProgressDialog;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public Book selectedBook;
    public BooVariable bv;
    public DynamoDBMapper mapper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button loginBtn = (Button) findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(this);


        //create progress wheel
        final ProgressDialog dialog = new ProgressDialog(MainActivity.this); // this = YourActivity
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Loading. Please wait...");
        dialog.setIndeterminate(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();

        PrimeThread p = new PrimeThread(); //create thread for database queries
        p.start(); //start thread

        bv = new BooVariable(); //instantiate boolean variable
        //set listener for variable changes
        bv.setListener(new BooVariable.ChangeListener() {
            @Override
            //stop thread when variable changes
            public void onChange() {
                Log.i("tag", "thread done");
                dialog.cancel(); //stop progress wheel
            }
        });

    }

    public void onClick(View v) {
        Intent intent = new Intent(this, newCustomerFormActivity.class);
        startActivity(intent);
    }

    class PrimeThread extends Thread {

        public void run() {

            // Initialize the Amazon Cognito credentials provider
            CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                    getApplicationContext(),
                    "ap-southeast-2:dcba2db7-9ae2-45d7-a672-7fb8a4445a1c", // Identity pool ID
                    Regions.AP_SOUTHEAST_2 // Region
            );

            AmazonDynamoDBClient ddbClient = new AmazonDynamoDBClient(credentialsProvider);
            ddbClient.setRegion(Region.getRegion(Regions.AP_SOUTHEAST_2));
            mapper = new DynamoDBMapper(ddbClient);

            //uncomment this out to add a book to the db
            //addBook();

            readBook();

            bv.setBoo(true);
        }
    }

    public void addBook() {

        //add a book
        Book book = new Book();
        book.setTitle("The Dark Tower");
        book.setAuthor("Stephen King");
        book.setPrice(400);
        book.setIsbn("2345678913");
        book.setHardCover(false);

        //update book in db
        mapper.save(book);
        Log.i("tag", "Book added");
    }

    public void readBook() {

        Book selectedBook = mapper.load(Book.class, "2345678912"); //find certain book

        Log.i("tag", selectedBook.getTitle()); //display found book

    }

}
