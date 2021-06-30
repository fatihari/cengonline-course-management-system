package com.example.cengonline.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cengonline.MainActivity;
import com.example.cengonline.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    EditText name, mail, password;
    RadioGroup radioGroup;
    RadioButton radioButton;
    Button register;
    TextView login;
    FirebaseAuth auth;
    DatabaseReference reference;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = findViewById(R.id.reg_edit_person_name);
        mail = findViewById(R.id.reg_edit_email);
        password = findViewById(R.id.reg_edit_password);
        radioGroup = findViewById(R.id.reg_radio_group);
        register = findViewById(R.id.reg_btn_register);
        login = findViewById(R.id.reg_txt_login);

        auth= FirebaseAuth.getInstance();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //progressBar = new ProgressBar(RegisterActivity.this);
                int radioId = radioGroup.getCheckedRadioButtonId();
                radioButton = findViewById(radioId);
                String nameSurname = name.getText().toString();
                String email = mail.getText().toString();
                String pass = password.getText().toString();

                if(TextUtils.isEmpty(nameSurname)||TextUtils.isEmpty(email)||TextUtils.isEmpty(pass))
                {
                    Toast.makeText(RegisterActivity.this,"Please fill all the fields.", Toast.LENGTH_LONG).show();
                }
                else if(pass.length()<6){
                    Toast.makeText(RegisterActivity.this,"Your password must be at least 6 characters.", Toast.LENGTH_LONG).show();
                }
                else{
                    //Yeni kullanıcıyı kaydet.
                    addUserToDb(nameSurname,email,pass,radioButton.getText().toString());
                }
            }
        });

    }
    private void addUserToDb(final String nameSurname, final String email, String password, final String type){

        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful())
                        {
                            FirebaseUser currentUser = auth.getCurrentUser();
                            assert currentUser != null;
                            String id = currentUser.getUid();
                            reference = FirebaseDatabase.getInstance().getReference().child("Users").child(id);

                            HashMap <String, Object> hashMap = new HashMap<>();
                            hashMap.put("id", id);
                            hashMap.put("nameSurname", nameSurname);
                            hashMap.put("email", email);
                            hashMap.put("type", type); // student or teacher
                            hashMap.put("pictureUrl", "https://firebasestorage.googleapis.com/v0/b/cengonline-e8060.appspot.com/o/placeholder.png?alt=media&token=b15a060b-b9ca-4f12-86c8-3b10cd8d330c");

                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    //progressbar.dismiss();
                                    Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);

                                }
                            });
                        }

                        else{
                            //progressbar.dismiss();
                            Toast.makeText(RegisterActivity.this,
                                    "Registration failed.", Toast.LENGTH_LONG).show();
                        }


                    }
                });

    }

    public void checkButton(View view) {
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
    }
}
