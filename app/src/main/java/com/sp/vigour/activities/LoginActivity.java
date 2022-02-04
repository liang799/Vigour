package com.sp.vigour.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import com.shobhitpuri.custombuttons.GoogleSignInButton;
import com.sp.vigour.R;

import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;

import java.io.File;

public class LoginActivity extends AppCompatActivity {
    GoogleSignInButton btSignIn;
    GoogleSignInClient googleSignInClient;
    FirebaseAuth firebaseAuth;
    ImageView debug;
    File file = new File("Vigour/path");
    String Walletname;
    Credentials credentials;
    String password = "pw";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        //Assing variable
        btSignIn = findViewById(R.id.buttonSignin);
        debug = findViewById(R.id.debugButt);

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(
                GoogleSignInOptions.DEFAULT_SIGN_IN
        ).requestIdToken("132756598481-tu6m8ccv7f54r0ju5ullsbij11njcghb.apps.googleusercontent.com")
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(LoginActivity.this
        ,googleSignInOptions);

        btSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = googleSignInClient.getSignInIntent();

                startActivityForResult(intent,100);
            }
        });

        debug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if(firebaseUser != null){
            startActivity(new Intent(LoginActivity.this
            , MainActivity.class)
            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Check condition
        if(requestCode == 100){
            Task<GoogleSignInAccount> signInAccountTask = GoogleSignIn
                    .getSignedInAccountFromIntent(data);

            if(signInAccountTask.isSuccessful()){
                String s = "Google sign in successful";
                displayToast(s);

                try {
                    GoogleSignInAccount googleSignInAccount = signInAccountTask
                            .getResult(ApiException.class);

                    if(googleSignInAccount != null){
                        AuthCredential authCredential = GoogleAuthProvider
                                .getCredential(googleSignInAccount.getIdToken()
                                ,null);

                        firebaseAuth.signInWithCredential(authCredential)
                                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()){
                                            startActivity(new Intent(LoginActivity.this
                                            , MainActivity.class)
                                            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));

                                            displayToast("Firebase Authentication succesful");
                                            createWallet();
                                        }else{
                                            displayToast("Authentication Failed: " + task.getException()
                                            .getMessage());
                                        }
                                    }
                                });


                    }
                } catch (ApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void displayToast(String s) {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
    }

    public void createWallet()  {

        //EditText Edtpassword = findViewById(R.id.password);
        //final String password = Edtpassword.getText().toString();  // this will be your etherium password
        try {
            // generating the etherium wallet

            Walletname = WalletUtils.generateLightNewWalletFile(password, file);
            //ShowToast("Wallet generated wallet name is ");
            Toast.makeText(getApplicationContext(), "Wallet generated wallet name is" + Walletname, Toast.LENGTH_SHORT).show();
            credentials = WalletUtils.loadCredentials(password, file + "/" + Walletname);
            Log.d("Wallet text","Wallet generated wallet name is" + Walletname );
            //txtaddress.setText(getString(R.string.your_address) + credentials.getAddress());
        }
        catch(Exception e){
            //ShowToast("failed");
            Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();

        }
    }
}