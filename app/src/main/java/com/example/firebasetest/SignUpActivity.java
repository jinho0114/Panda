package com.example.firebasetest;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;
import static com.example.firebasetest.FirebaseID.email;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore mStore = FirebaseFirestore.getInstance();
    private EditText editTextEmail;
    private EditText editTextPassword,editTextPassword2;
    private EditText editTextName;
    String tpw, tpw2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        editTextEmail = (EditText) findViewById(R.id.editText_email);
        editTextPassword = (EditText) findViewById(R.id.editText_passWord);
        editTextPassword2 = (EditText) findViewById(R.id.editText_passWord2);
        editTextName = (EditText) findViewById(R.id.editText_name);

        findViewById(R.id.btn_join).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        firebaseAuth.createUserWithEmailAndPassword(editTextEmail.getText().toString(), editTextPassword.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = firebaseAuth.getCurrentUser();

                            if (user != null) {
                                Map<String, Object> userMap = new HashMap<>();
                                userMap.put(FirebaseID.documentId, user.getUid());
                                userMap.put(FirebaseID.email, editTextEmail.getText().toString());
                                userMap.put(FirebaseID.password, editTextPassword.getText().toString());
                                userMap.put(FirebaseID.name, editTextName.getText().toString());
                                mStore.collection(FirebaseID.user).document(user.getUid()).set(userMap, SetOptions.merge());
                                finish();
                            }
                        } else {
                            Toast.makeText(SignUpActivity.this, "인증 실패", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
        tpw = editTextPassword.getText().toString();
        tpw2 = editTextPassword2.getText().toString();
        if(!tpw.equals(tpw2)){
            Toast.makeText(this, "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
        }
    }

}
//                if (!editTextEmail.getText().toString().equals("") && !editTextPassword.getText().toString().equals("")) {
//                    // 이메일과 비밀번호가 공백이 아닌 경우
//                    createUser(editTextEmail.getText().toString(), editTextPassword.getText().toString(), editTextName.getText().toString());
//                } else {
//                    // 이메일과 비밀번호가 공백인 경우
//                    Toast.makeText(SignUpActivity.this, "계정과 비밀번호를 입력하세요.", Toast.LENGTH_LONG).show();
//                }



//    private void createUser(String email, String password, String name) {
//        firebaseAuth.createUserWithEmailAndPassword(email, password)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // 회원가입 성공시
//                            Toast.makeText(SignUpActivity.this, "회원가입 성공", Toast.LENGTH_SHORT).show();
//                            finish();
//                        } else {
//
//                            // 계정이 중복된 경우
//                            Toast.makeText(SignUpActivity.this, "이미 존재하는 계정입니다.", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//    }

