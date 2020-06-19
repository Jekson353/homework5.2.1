package com.samoylenko.homework521;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        final EditText mLoginEdTxt = findViewById(R.id.text_login);
        final EditText mPasswdEdTxt = findViewById(R.id.text_password);
        Button btnLogin = findViewById(R.id.btn_login);
        Button btnReg = findViewById(R.id.btn_reg);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String login = mLoginEdTxt.getText().toString();
                String password = mPasswdEdTxt.getText().toString();
                login(login, password);
            }
        });

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String login = mLoginEdTxt.getText().toString();
                String password = mPasswdEdTxt.getText().toString();
                registration(login, password);
            }
        });


    }

    public void login(String login, String passwd) {
        if (login.isEmpty() | passwd.isEmpty()) {
            Toast.makeText(MainActivity.this, R.string.no_all_value
                    , Toast.LENGTH_LONG)
                    .show();
        } else {
            String pwd = fromFile(login);
            if (pwd != null) {
                if (passwd.equals(pwd)) {
                    Intent intent = new Intent(getApplicationContext(), SuccessActivity.class);
                    intent.putExtra("login", login);
                    startActivity(intent);
                    Toast.makeText(MainActivity.this, R.string.login_ok
                            , Toast.LENGTH_LONG)
                            .show();
                } else {
                    Toast.makeText(MainActivity.this, R.string.password_bad
                            , Toast.LENGTH_LONG)
                            .show();
                }
            } else {
                Toast.makeText(MainActivity.this, R.string.no_login
                        , Toast.LENGTH_LONG)
                        .show();
            }

        }
    }

    public void registration(String login, String passwd) {
        if (login.isEmpty() | passwd.isEmpty()) {
            Toast.makeText(MainActivity.this, R.string.no_all_value
                    , Toast.LENGTH_LONG)
                    .show();
        } else {
            int result = toFile(login, passwd);
            if (result == 1) {
                Toast.makeText(MainActivity.this, R.string.reg_ok
                        , Toast.LENGTH_LONG)
                        .show();
            } else if (result == 2) {
                Toast.makeText(MainActivity.this, R.string.login_is_active
                        , Toast.LENGTH_SHORT)
                        .show();
            }else if (result==3){
                Toast.makeText(MainActivity.this, R.string.error_reg
                        , Toast.LENGTH_SHORT)
                        .show();
            }else{
                Toast.makeText(MainActivity.this, R.string.error_unknown
                        , Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    public int toFile(String login, String passwd) {
        FileOutputStream outputStream;
        try {
            File file = new File(getFilesDir(), login);
            if (!file.exists()) {
                outputStream = openFileOutput(login, Context.MODE_PRIVATE);
                outputStream.write(passwd.getBytes());
                outputStream.close();
                return 1;
            } else {
                return 2;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 3;
        }
    }

    public String fromFile(String login) {
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(openFileInput(login));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            StringBuilder output = new StringBuilder();
            while (line != null) {
                output = output.append(line);
                line = reader.readLine();
            }
            return output.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
