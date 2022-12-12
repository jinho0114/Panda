package com.example.firebasetest;

import android.content.Intent;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth firebaseAuth;
    private EditText editTextEmail, editTextPassword;


    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private RecyclerView mPost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();
        editTextEmail = (EditText) findViewById(R.id.login_email);
        editTextPassword = (EditText) findViewById(R.id.login_password);
        findViewById(R.id.login_signup).setOnClickListener(this);
        findViewById(R.id.login_success).setOnClickListener(this);
    }


//        @Override
//    protected void onStart() {
//        super.onStart();
//        FirebaseUser user = firebaseAuth.getCurrentUser();
//        if(user !=null){
//            Toast.makeText(this, "자동 로그인 :"+user.getUid(), Toast.LENGTH_SHORT).show();
////            startActivity(new Intent(this, MainActivity.class));
//        }
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_signup:
                startActivity(new Intent(this, SignUpActivity.class));
                break;
            case R.id.login_success:
                firebaseAuth.signInWithEmailAndPassword(editTextEmail.getText().toString(), editTextPassword.getText().toString())
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // 로그인 성공
                                    FirebaseUser user = firebaseAuth.getCurrentUser();
                                    if(user !=null) {
                                        Toast.makeText(LoginActivity.this, "로그인 성공 : " + user.getUid(), Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(LoginActivity.this, InfoActivity.class));
                                    }
                                } else {
                                    // 로그인 실패
                                    Toast.makeText(LoginActivity.this, "아이디 또는 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

        break;
    }
        }
    }

//
//        buttonLogIn = (Button) findViewById(R.id.btn_login);
//        buttonLogIn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!editTextEmail.getText().toString().equals("") && !editTextPassword.getText().toString().equals("")) {
//                    loginUser(editTextEmail.getText().toString(), editTextPassword.getText().toString());
//                    startActivity(new Intent(LoginActivity.this, BoardActivity.class));
//                } else {
//                    Toast.makeText(LoginActivity.this, "계정과 비밀번호를 입력하세요.", Toast.LENGTH_LONG).show();
//                }
//            }
//        });
//        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                FirebaseUser user = firebaseAuth.getCurrentUser();
//                if (user != null) {
//                    Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
//                    startActivity(intent);
//                    finish();
//                } else {
//                }
//            }
//        };
//    }
//
//    public void loginUser(String email, String password) {
//        firebaseAuth.signInWithEmailAndPassword(email, password)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // 로그인 성공
//                            Toast.makeText(LoginActivity.this, "로그인 성공", Toast.LENGTH_SHORT).show();
//                            firebaseAuth.addAuthStateListener(firebaseAuthListener);
//                            startActivity(new Intent(LoginActivity.this, BoardActivity.class));
//                        } else {
//                            // 로그인 실패
//                            Toast.makeText(LoginActivity.this, "아이디 또는 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//    }
//
////    private void addLogin(String userId, String email, String password, String name) {
////        Login user = new Login(email, password, name);
////
////        databaseReference.child("users").child(userId).setValue(user)
////                .addOnSuccessListener(new OnSuccessListener<Void>() {
////                    @Override
////                    public void onSuccess(Void aVoid) {
////                        // Write was successful!
////                        Toast.makeText(MainActivity.this, "저장을 완료했습니다.", Toast.LENGTH_SHORT).show();
////                    }
////                })
////                .addOnFailureListener(new OnFailureListener() {
////                    @Override
////                    public void onFailure(@NonNull Exception e) {
////                        // Write failed
////                        Toast.makeText(MainActivity.this, "저장을 실패했습니다.", Toast.LENGTH_SHORT).show();
////                    }
////                });
////
////    }
//
//

//    @Override
//    protected void onStop() {
//        super.onStop();
//        if (firebaseAuthListener != null) {
//            firebaseAuth.removeAuthStateListener(firebaseAuthListener);
//        }
//    }
//
//    @Override
//    public void onClick(View view) {
//
//    }
//}
//
