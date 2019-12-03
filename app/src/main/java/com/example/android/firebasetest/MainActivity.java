package com.example.android.firebasetest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
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

public class MainActivity extends AppCompatActivity {

    final FirebaseFirestore database = FirebaseFirestore.getInstance();
    String contents=null;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        final String str1="https://docs.google.com/forms/d/e/1FAIpQLSc6ZiiIAiZsmM0bFGhKtkzqG3Ad80EAf5tau6EjEit9EKLsNA/formResponse?usp=pp_url&entry.1778953173=";
        final String str2="&entry.489934517=";
        final String str3="&entry.1520593281=";
        final String str4="&submit=Submit";


        if (requestCode == 0) {

            if (resultCode == RESULT_OK) {

                contents = data.getStringExtra("SCAN_RESULT");
                Toast.makeText(MainActivity.this,contents,Toast.LENGTH_SHORT).show();

                // Create a reference to the cities collection
                CollectionReference Students = database.collection("Students");
                // Create a query against the collection.
                Query query = Students.whereEqualTo("id", contents);
                query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                TextView Data=findViewById(R.id.retrieved_data);
                                String College=(String) document.get("College");
                                String Name=(String) document.get("Name");
                                String Sport=(String) document.get("Sport");
                                String url = (str1+Name+str2+Sport+str3+College+str4);
                                Data.setText(College+"\n"+Name+"\n"+Sport);
                                Intent i = new Intent(Intent.ACTION_VIEW);
                                i.setData(Uri.parse(url));
                                startActivity(i);
                                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                }

                        } else {
                            Log.d("DATA", "Error getting documents: ", task.getException());
                        }
                    }
                });

        /*        DocumentReference docRef = database.collection("Players").document(contents);
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {

                                TextView Data=findViewById(R.id.retrieved_data);
                                String College=(String) document.get("College");
                                String Name=(String) document.get("Name");
                                String Sport=(String) document.get("Sport");
                                String url = (str1+Name+str2+Sport+str3+College+str4);
                                Data.setText(College+"\n"+Name+"\n"+Sport);
                                Intent i = new Intent(Intent.ACTION_VIEW);
                                i.setData(Uri.parse(url));
                                startActivity(i);
                                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                            } else {

                                Log.d("yo", "No such document");

                            }
                        }
                        else {

                            Log.d("yo", "get failed with ", task.getException());

                        }
                    }
                });*/
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
    }
}
