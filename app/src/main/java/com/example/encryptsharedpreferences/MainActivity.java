package com.example.encryptsharedpreferences;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MA";
    public Button btn,btnDecrypt;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.button);
        btnDecrypt = findViewById(R.id.btn_decrypt);

        btn.setOnClickListener(v -> {
            createEncryptedSharedPreferences();
        });

        btnDecrypt.setOnClickListener(v ->{
            String encryptor = null;

            try {
                encryptor = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
            }catch (Exception e){
                Log.e(TAG, "onCreate: "+e.getMessage() );
            }

            SharedPreferences sharedPreferences = null;

            try {
                sharedPreferences = EncryptedSharedPreferences.create(
                        "test",
                        encryptor,
                        getApplicationContext(),
                        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
                );

                String stringg = sharedPreferences.getString("Temp1", "");
                boolean bool = sharedPreferences.getBoolean("is_Encrypted", false);

                Toast.makeText(this, stringg+" "+bool, Toast.LENGTH_LONG).show();


            }catch (Exception e){
                Log.e(TAG, "onCreate: "+e.getMessage() );
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void createEncryptedSharedPreferences(){
        String encrytor = null;

        try {
            encrytor = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
        }catch (Exception e){
            Log.e(TAG, "createEncryptedSharedPreferences: " +e.getMessage() );
        }
        SharedPreferences sharedPreferences = null;
        try {
            sharedPreferences = EncryptedSharedPreferences.create(
                    "test",
                    encrytor,
                    this,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
        }catch (Exception e){
            Log.e(TAG, "createEncryptedSharedPreferences: "+ e.getMessage() );

        }

        sharedPreferences.edit()
                .putString("Temp1", "This a message1 to be encrypted")
                .putBoolean("is_Encrypted", true)
                .apply();
    }
}