package com.example.firebasetest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class Post2Activity extends AppCompatActivity {
    private TextView mTitleText, mContentsText, mNameText, mDiseaseText, mPenaltyText;
    private FirebaseFirestore mstore = FirebaseFirestore.getInstance();
    private String id;
    private Button mComment;
    private RecyclerView mCommentList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post2);
        mTitleText = findViewById(R.id.post2_title);
        mContentsText = findViewById(R.id.post2_contents);
//        mNameText = findViewById(R.id.post2_name);
        mDiseaseText = findViewById(R.id.post2_disease);
        mPenaltyText = findViewById(R.id.post2_penalty);
//        mComment = findViewById(R.id.comment_submit);
//        mCommentList = findViewById(R.id.comment_recyclerview);



        Intent getintent = getIntent();
        id = getIntent().getStringExtra(FirebaseID.documentId);
        Log.e("ITEM DOCUMENT ID: ", id);
        mstore.collection(FirebaseID.post).document(id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            if(task.getResult().exists()) {
                                if (task.getResult() != null) {
                                    Map<String, Object> snap = task.getResult().getData();
                                    String disease = String.valueOf(snap.get(FirebaseID.disease));
                                    String title = String.valueOf(snap.get(FirebaseID.title));
                                    String contents = String.valueOf(snap.get(FirebaseID.content));
                                    String name = String.valueOf(snap.get(FirebaseID.name));
                                    String penalty = String.valueOf(snap.get(FirebaseID.penalty));
                                    mDiseaseText.setText(disease);
                                    mTitleText.setText(title);
                                    mContentsText.setText(contents);
//                                    mNameText.setText(name);
                                    mPenaltyText.setText(penalty);
                                }
                            }else{
                                Toast.makeText(Post2Activity.this, "삭제된 문서입니다 ", Toast.LENGTH_SHORT).show();

                            }
                        }
                    }
                });
    }

}