package com.example.firebasetest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class PostActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore mStore = FirebaseFirestore.getInstance();

    private EditText mTitle, mContents, mDisease;
    private RatingBar mPenalty;
    private String name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testpost);

        mTitle = findViewById(R.id.post_title_edit);
        mContents = findViewById(R.id.post_contents_edit);
        mDisease = findViewById(R.id.post_disease_edit);
        mPenalty = findViewById(R.id.post_penalty_edit);



        findViewById(R.id.post_save_button).setOnClickListener(this);
        if(mAuth.getCurrentUser() !=null){
            mStore.collection(FirebaseID.user).document(mAuth.getCurrentUser().getUid())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.getResult() !=null){
                            name = (String) task.getResult().getData().get(FirebaseID.name);
                        }
                        }
                    });
        }
    }

    @Override
    public void onClick(View view) {
        if(mAuth.getCurrentUser() !=null){
            String postId = mStore.collection(FirebaseID.post).document().getId();
            Map<String, Object> data = new HashMap<>();
            data.put(FirebaseID.documentId, postId);
            data.put(FirebaseID.name, name);
            data.put(FirebaseID.title, mTitle.getText().toString());
            data.put(FirebaseID.content, mContents.getText().toString());
            data.put(FirebaseID.disease, mDisease.getText().toString());
            data.put(FirebaseID.penalty, String.valueOf(mPenalty.getRating()));
            data.put(FirebaseID.timestamp, FieldValue.serverTimestamp());
            mStore.collection(FirebaseID.post).document(postId).set(data, SetOptions.merge());
            finish();

        }
    }
}