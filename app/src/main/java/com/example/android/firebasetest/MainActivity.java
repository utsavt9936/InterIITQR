package com.example.android.firebasetest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    final FirebaseFirestore database = FirebaseFirestore.getInstance();
    String contents = null;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        final String str1="https://docs.google.com/forms/d/e/1FAIpQLSc6ZiiIAiZsmM0bFGhKtkzqG3Ad80EAf5tau6EjEit9EKLsNA/formResponse?usp=pp_url&entry.1949318479=";
        final String str1a="&entry.1778953173=";
        final String str2="&entry.489934517=";
        final String str3="&entry.1520593281=";
        final String str4="&submit=Submit";


        if (requestCode == 0) {

            if (resultCode == RESULT_OK) {

                contents = data.getStringExtra("SCAN_RESULT");
                Toast.makeText(MainActivity.this,contents,Toast.LENGTH_SHORT).show();

                Date currentTime= Calendar.getInstance().getTime();
                String ss=currentTime.toString();
                final String sd=String.valueOf(ss.charAt(8))+String.valueOf(ss.charAt(9));
                String st=String.valueOf(ss.charAt(11))+String.valueOf(ss.charAt(12));
                final int t=Integer.parseInt(st);


                // Create a reference to the cities collection
                CollectionReference Students = database.collection("Students");
                // Create a query against the collection.
                Query query = Students.whereEqualTo("id", contents);
                query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Date currentTime= Calendar.getInstance().getTime();
                                String ss=currentTime.toString();
                                String sd=String.valueOf(ss.charAt(8))+String.valueOf(ss.charAt(9));
                                String st=String.valueOf(ss.charAt(11))+String.valueOf(ss.charAt(12));
                                int t=Integer.parseInt(st);
                                String statusUpdate="0";
                                if(t>=7&&t<=10)
                                    statusUpdate=sd+"M";
                                else if (t>=12&&t<=15)
                                    statusUpdate=sd+"A";
                                else if (t>=19&&t<=23)
                                    statusUpdate=sd+"N";


                                // Toast.makeText(MainActivity.this,statusUpdate,Toast.LENGTH_SHORT).show();


                                TextView Data=findViewById(R.id.retrieved_data);
                                String Designation=(String)document.get("Designation");
                                String College=(String) document.get("IIT Name");
                                String Name=(String) document.get("Name");
                                String Sport=(String) document.get("Sports");
                                String currentStatus=(String)document.get("Status");

                                if(currentStatus.equals(statusUpdate))
                                    //Toast.makeText(MainActivity.this,"Chori",Toast.LENGTH_LONG).show();
                                    Data.setText(Name+"\n"+Sport+"\n"+College+"\nAlready Recorded");
                                else {
                                    document.getReference().update("Status",statusUpdate);
                                    String url = (str1+Designation+str1a+Name+str2+Sport+str3+College+str4);
                                    Intent i = new Intent(Intent.ACTION_VIEW);
                                    i.setData(Uri.parse(url));
                                    startActivity(i);
                                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    Data.setText(Name+"\n"+Sport+"\n"+College);
                                }

                            }


                        } else {
                            TextView Data=findViewById(R.id.retrieved_data);
                            Data.setText("Record not Found");
                            Log.d("DATA", "Error getting documents: ", task.getException());
                        }
                    }
                });

                    }
            if(resultCode == RESULT_CANCELED){

                //handle cancel
                Toast.makeText(MainActivity.this,"2"+contents, Toast.LENGTH_SHORT).show();

            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button qrs=(Button) findViewById(R.id.qrs);




        qrs.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {



                try {

                    Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                    startActivityForResult(intent, 0);

                } catch (Exception e) {

                    Uri marketUri = Uri.parse("market://details?id=com.google.zxing.client.android");
                    Intent marketIntent = new Intent(Intent.ACTION_VIEW,marketUri);
                    startActivity(marketIntent);

                }

            }

        });


        Button help=findViewById(R.id.help_button);
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this,HelpActivity.class);
                startActivity(intent);
            }
        });
    }
}
