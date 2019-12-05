package com.example.android.firebasetest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        TextView Vaibhav=findViewById(R.id.Vaibhav);
        TextView Samarth=findViewById(R.id.Samarth);
        TextView Utsav=findViewById(R.id.Utsav);
        TextView Jayesh=findViewById(R.id.Jayesh);

        Vaibhav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:8871125428"));
                startActivity(intent);
            }
        });


        Samarth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:8739990058"));
                startActivity(intent);
            }
        });


        Utsav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:9161684098"));
                startActivity(intent);
            }
        });


        Jayesh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:9774630020"));
                startActivity(intent);
            }
        });

    }
}
