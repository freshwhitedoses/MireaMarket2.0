package com.example.mireamarket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.mireamarket.Moduls.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

public class MainActivity extends AppCompatActivity {

Button btnReg, btnSign, btnRemember;
SharedPreferences sharedPreferences;
FirebaseAuth auth;
FirebaseDatabase db;
DatabaseReference users;
RelativeLayout   root;

    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_EMAIL = "name";
    private static final String KEY_PASSWORD = "email";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSign=findViewById(R.id.btnSign);
        btnReg=findViewById(R.id.btnReg);
        btnRemember = findViewById(R.id.button_remember);
        root=findViewById(R.id.rootElement);
        auth=FirebaseAuth.getInstance();
        db=FirebaseDatabase.getInstance();
        users=db.getReference("Users");
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRegWindow();
            }
        });
     btnSign.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showSignWindow();
        }
    });

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        String name = sharedPreferences.getString(KEY_EMAIL, null);

        if (name != null){
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            startActivity(intent);
        }

    }
private void showSignWindow(){
    AlertDialog.Builder dialod=new AlertDialog.Builder(this);
    dialod.setTitle("Войти");
    dialod.setMessage("Введите  данные для входа");

    LayoutInflater inflater=LayoutInflater.from(this);
    View signWindow=inflater.inflate(R.layout.sign_in_window,null);
    dialod.setView(signWindow);
    final MaterialEditText email=signWindow.findViewById(R.id.email);
    final MaterialEditText password=signWindow.findViewById(R.id.password);

    final EditText email_ = signWindow.findViewById(R.id.email);
    final EditText password_ = signWindow.findViewById(R.id.password);
    btnRemember = signWindow.findViewById(R.id.button_remember);
    sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);

    btnRemember.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(KEY_EMAIL, email_.getText().toString());
            editor.putString(KEY_PASSWORD, password_.getText().toString());
            Toast.makeText(MainActivity.this, "Запомнил!", Toast.LENGTH_SHORT).show();
            editor.apply();
        }
    });


    dialod.setNegativeButton("Отменить", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int which) {
            dialogInterface.dismiss();
        }
    });
    dialod.setPositiveButton("Войти", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int which) {
            if(TextUtils.isEmpty(email.getText().toString())){
                Snackbar.make(root, "Введите вашу почту",Snackbar.LENGTH_SHORT).show();
                return;
            }
            if(password.getText().toString().length()<5) {
                Snackbar.make(root, "Введите пароль, более 5 символов", Snackbar.LENGTH_SHORT).show();
                return;
            }
    auth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
 .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
     @Override
     public void onSuccess(AuthResult authResult) {
startActivity(new Intent(MainActivity.this, SecondActivity.class));// secondactiviti-новая актисность, перебрасываем
         finish();
     }

 }).addOnFailureListener(new OnFailureListener() {
     @Override
     public void onFailure(@NonNull Exception e) {
         Snackbar.make(root,"Ошибка авторизации"+e.getMessage(),Snackbar.LENGTH_SHORT).show();
     }
 });


        }
    });
    dialod.show();
}


    private void showRegWindow() {
        AlertDialog.Builder dialod=new AlertDialog.Builder(this);
        dialod.setTitle("Зарегистрироваться");
        dialod.setMessage("Введите все данные для регистрации");

        LayoutInflater inflater=LayoutInflater.from(this);
        View registerWindow=inflater.inflate(R.layout.register_win,null);
        dialod.setView(registerWindow);
        final MaterialEditText email=registerWindow.findViewById(R.id.email);
        final MaterialEditText number=registerWindow.findViewById(R.id.number);
        final MaterialEditText password=registerWindow.findViewById(R.id.password);
        final MaterialEditText name=registerWindow.findViewById(R.id.name);
        final MaterialEditText surname=registerWindow.findViewById(R.id.surname);
        final MaterialEditText group=registerWindow.findViewById(R.id.group);

        dialod.setNegativeButton("Отменить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();
            }
        });
        dialod.setPositiveButton("Добавить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                if(TextUtils.isEmpty(email.getText().toString())){
                    Snackbar.make(root, "Введите вашу почту",Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(number.getText().toString())){
                    Snackbar.make(root, "Введите ваш номер телефона",Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if(password.getText().toString().length()<5){
                    Snackbar.make(root, "Введите пароль, более 5 символов",Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(name.getText().toString())){
                    Snackbar.make(root, "Введите ваше имя",Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(surname.getText().toString())){
                    Snackbar.make(root, "Введите вашу фамилию",Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(group.getText().toString())){
                    Snackbar.make(root, "Введите вашу учебную группу",Snackbar.LENGTH_SHORT).show();
                    return;
                }
//регистрация
                auth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                            User user =new User();
                            user.setEmail(email.getText().toString());
                            user.setNumber(number.getText().toString());
                            user.setPassword(password.getText().toString());
                            user.setName(name.getText().toString());
                            user.setSurname(surname.getText().toString());
                            user.setGroup(group.getText().toString());

                            users.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Snackbar.make(root,"Пользователь зарегистрирован!",Snackbar.LENGTH_SHORT)
                                            .show();
                                }
                            });

                            }
                        });

            }
        });
        dialod.show();
    }
    //тут будет новый код
}