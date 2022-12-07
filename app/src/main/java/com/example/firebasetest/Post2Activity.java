package com.example.firebasetest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firebasetest.adapters.CommentAdapter;
import com.example.firebasetest.models.Comment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Post2Activity extends AppCompatActivity implements View.OnClickListener, RecyclerViewItemClickListener.OnItemClickListener{
    private TextView mTitleText, mContentsText, mNameText, mDiseaseText, mPenaltyText;
    private FirebaseFirestore mstore = FirebaseFirestore.getInstance();
    private String id;
    private String documentId;
    private Button mComment;
    private RecyclerView mCommentList;
    private List<Comment> mDatas;
    private CommentAdapter cAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post2);
        mTitleText = findViewById(R.id.post2_title);
        mContentsText = findViewById(R.id.post2_contents);
//        mNameText = findViewById(R.id.post2_name);
        mDiseaseText = findViewById(R.id.post2_disease);
        mPenaltyText = findViewById(R.id.post2_penalty);
        mComment = findViewById(R.id.comment_button);

        mCommentList = findViewById(R.id.comment_recyclerview);




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
        findViewById(R.id.comment_button).setOnClickListener(this);
        mCommentList.addOnItemTouchListener(new RecyclerViewItemClickListener(this, mCommentList, this));

    }


    @Override
    protected void onStart() {
        super.onStart();
        Intent getintent = getIntent();
        id = getIntent().getStringExtra(FirebaseID.documentId);
        Log.e("ITEM DOCUMENT ID: ", id);

        DocumentReference ref = mstore.collection("comment").document(id);
        String commentId = ref.getId();
        Log.e("ITEM DOCUMENT ID: ", ref.getId());
        mDatas = new ArrayList<>();
        mstore.collection(FirebaseID.comment).
                document(commentId)
                .collection(FirebaseID.documentId)
                .orderBy(FirebaseID.timestamp, Query.Direction.ASCENDING)//댓글 오름차순 정렬
                .whereEqualTo("documentId" ,id)  //해당 게시글 id와 같은 id를 가진 댓글 가져오기
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException error) {
                        if(queryDocumentSnapshots !=null){
                            mDatas.clear();

                                for (DocumentSnapshot snap : queryDocumentSnapshots.getDocuments()) {
                                    Map<String, Object> shot = snap.getData();
                                    documentId = String.valueOf(shot.get(FirebaseID.documentId));
                                    String name = String.valueOf(shot.get(FirebaseID.name));
                                    String contents = String.valueOf(shot.get(FirebaseID.content));
                                    Comment data = new Comment(documentId, name, contents);
                                    mDatas.add(data);
                            }

                                cAdapter = new CommentAdapter(mDatas);
                                mCommentList.setAdapter(cAdapter);



                        }
                    }
                });

    }

    @Override
    public void onItemClick(View view, int position) {

    }

    @Override
    public void onItemLongClick(View view, int position) {

    }

    @Override
    public void onClick(View view) {

        Intent putIntent = new Intent(this, CommentActivity.class);
        putIntent.putExtra("commentId", id);
        Log.e("ITEM DOCUMENT postID: ", id);
        startActivity(putIntent);
    }
}