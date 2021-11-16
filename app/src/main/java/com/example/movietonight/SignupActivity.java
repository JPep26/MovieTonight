package com.example.movietonight;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseRef;
    private EditText mEtId, mEtNickname, mEtPw;
    private Button mBtnOverlap1, mBtnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("MovieTonight");

        mEtId = findViewById(R.id.et_id);
        mEtNickname = findViewById(R.id.et_nickname);
        mEtPw = findViewById(R.id.et_pw);
        mBtnOverlap1 = findViewById(R.id.btn_overlap);
        mBtnSignup = findViewById(R.id.btn_signup_signup);

        mBtnOverlap1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userId = mEtId.getText().toString();

            }
        });

        mBtnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //회원가입 처리 시작
                String userId = mEtId.getText().toString();
                String userNickname = mEtNickname.getText().toString();
                String passwd = mEtPw.getText().toString();

                //Firebase Auth 진행
                mFirebaseAuth.createUserWithEmailAndPassword(userId, passwd).addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();


                            UserAccount account = new UserAccount();
                            account.setIdToken(firebaseUser.getUid());
                            account.setUserId(firebaseUser.getEmail());
                            account.setUserNickname(userNickname);
                            account.setPasswd(passwd);

                            //setValue : database에 삽입 동작
                            mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).setValue(account);

                            Toast.makeText(SignupActivity.this, "회원가입에 성공하셨습니다.", Toast.LENGTH_SHORT).show();

                            //메인으로 이동
                            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        } else {

                            Toast.makeText(SignupActivity.this, "회원가입에 실패하셨습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
           }
        });



        ImageView img_backmove = findViewById(R.id.img_backmove);
        img_backmove.setOnClickListener(view ->
                startActivity(new Intent(SignupActivity.this, StartActivity.class)));

    }
}