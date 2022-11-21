package com.example.firebasetest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebasetest.adapters.PostAdapter;
import com.example.firebasetest.models.Post;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, RecyclerViewItemClickListener.OnItemClickListener {
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore mStore = FirebaseFirestore.getInstance();

    private RecyclerView mPostRecyclerView;
    private PostAdapter mAdapter;
    private List<Post> mDatas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPostRecyclerView = findViewById(R.id.main_recyclerview);
        findViewById(R.id.main_post_edit).setOnClickListener(this);
        mPostRecyclerView.addOnItemTouchListener(new RecyclerViewItemClickListener(this, mPostRecyclerView, this));
    }

    @Override
    protected void onStart() {
        super.onStart();
        mDatas = new ArrayList<>();
        mStore.collection(FirebaseID.post)
                .orderBy(FirebaseID.timestamp, Query.Direction.DESCENDING) //내림차순 정렬
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException error) {
                      if(queryDocumentSnapshots !=null){
                          mDatas.clear();
                        for(DocumentSnapshot snap : queryDocumentSnapshots.getDocuments()) {
                            Map<String, Object> shot = snap.getData();
                            String documentId = String.valueOf(shot.get(FirebaseID.documentId));
                            String name = String.valueOf(shot.get(FirebaseID.name));
                            String disease = String.valueOf(shot.get(FirebaseID.disease));
                            String title = String.valueOf(shot.get(FirebaseID.title)); //파이어베이스는 스트링으로 강제형변환하쟈
                            String contents = String.valueOf(shot.get(FirebaseID.content));
                            Post data = new Post(documentId,name, disease,title, contents);
                            mDatas.add(data);
                        }
                          mAdapter = new PostAdapter(mDatas);
                          mPostRecyclerView.setAdapter(mAdapter);
                        }
                    }
                });

    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(this, PostActivity.class));
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(this, Post2Activity.class);
        intent.putExtra(FirebaseID.documentId, mDatas.get(position).getDocumentId());
        startActivity(intent);
    }

    @Override
    public void onItemLongClick(View view, int position) {

    }
}

