package com.example.firebasetest;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class CommentActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore mStore = FirebaseFirestore.getInstance();
    DocumentReference ref;
    private EditText cContents;
    private String name;
    private String postId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        cContents = findViewById(R.id.post_comment_edit);

        findViewById(R.id.comment_save_button).setOnClickListener(this);
        if(mAuth.getCurrentUser() !=null ){
            mStore.collection(FirebaseID.user).document(mAuth.getCurrentUser().getUid())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if(task.getResult() !=null){
                                name = (String) task.getResult().getData().get(FirebaseID.name);
                                postId = (String) task.getResult().getData().get(FirebaseID.postId);
                            }
                        }
                    });
        }
    }

    @Override
    public void onClick(View view) {
        if(mAuth.getCurrentUser() !=null){
            Intent intent = getIntent();
            String commentId = intent.getStringExtra("commentId");
            Log.e("Item : ", commentId);
//            String commentId = mStore.collection(FirebaseID.comment).document().getId();
//            DocumentReference ref = mStore.collection("post").document("6iT4SkHPTlgunRqRLlt7");
//            String commentId = ref.getId();
            Map<String, Object> data = new HashMap<>();
            data.put(FirebaseID.documentId, commentId);
            data.put(FirebaseID.name, name);
            data.put(FirebaseID.content, cContents.getText().toString());
            data.put(FirebaseID.timestamp, FieldValue.serverTimestamp());
            mStore.collection(FirebaseID.comment).document(commentId).collection(FirebaseID.documentId)
                    .document().set(data, SetOptions.merge());
            finish();

        }
    }
}