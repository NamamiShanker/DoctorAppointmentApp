package com.example.android.medipoint;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN =100 ;
    private FirebaseAuth mAuth;
    private String mUsername="ANONYMOUS";
    TextView textView;
    private FirebaseAuth.AuthStateListener mFirebaseAuthStateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth= FirebaseAuth.getInstance();
        textView=(TextView) findViewById(R.id.username);
        mFirebaseAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    onSigninInitialized(user.getDisplayName());
                } else {
                    onSignoutCleanup();
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setIsSmartLockEnabled(false)
                                    .setAvailableProviders(Arrays.asList(
                                            new AuthUI.IdpConfig.EmailBuilder().build(),
                                            new AuthUI.IdpConfig.GoogleBuilder().build()))
                                    .build(),
                            RC_SIGN_IN);
                }
            }
        };
    }

    private void onSignoutCleanup() {
        mUsername="ANONYMOUS";
    }

    private void onSigninInitialized(String displayName) {
        mUsername="Hey! "+displayName;
        textView.setText(mUsername);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RC_SIGN_IN)
        {
            if(resultCode==RESULT_OK)
            {
                Toast.makeText(MainActivity.this,"Signin successfull!",Toast.LENGTH_SHORT).show();
            }
            else if(resultCode==RESULT_CANCELED)
            {
                Toast.makeText(MainActivity.this,"Signin failed!",Toast.LENGTH_SHORT).show();
                finish();
            }

        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        mAuth.addAuthStateListener(mFirebaseAuthStateListener);
    }
    @Override
    protected void onPause()
    {
        super.onPause();
        if (mFirebaseAuthStateListener != null) {
            mAuth.removeAuthStateListener(mFirebaseAuthStateListener);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.sign_out_menu:
            {
                AuthUI.getInstance().signOut(this);
            }
        }
        return super.onOptionsItemSelected(item);
    }
}