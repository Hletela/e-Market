package com.example.e_waste.Sellers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.example.e_waste.Buyers.MainActivity;
import com.example.e_waste.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SellerRegistrationActivity extends AppCompatActivity {

    private Button sellerLoginBegin;
    private EditText nameInput, phoneInput, emailInput, addressInput, passwordInput, idInput, bankInput, accInput;
    private Button registerButton, nextButton, backButton;
    private ProgressDialog loadingBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_registration);

        mAuth = FirebaseAuth.getInstance();
        loadingBar = new ProgressDialog(this);

        sellerLoginBegin = findViewById(R.id.seller_already_have_account_btn);
        registerButton = findViewById(R.id.seller_register_btn);
        nextButton = findViewById(R.id.seller_register_nxt_btn);
        backButton = findViewById(R.id.seller_register_back_btn);
        nameInput = findViewById(R.id.seller_name);
        idInput = findViewById(R.id.seller_id_number);
        phoneInput = findViewById(R.id.seller_phone);
        emailInput = findViewById(R.id.seller_email);
        addressInput = findViewById(R.id.seller_address);
        passwordInput = findViewById(R.id.seller_password);
        bankInput = findViewById(R.id.seller_bank);
        accInput = findViewById(R.id.seller_account_number);

        final ViewSwitcher viewSwitcher = findViewById(R.id.sellerSwitcher);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String name = nameInput.getText().toString();
                final String idNumber = idInput.getText().toString();
                String password = passwordInput.getText().toString();
                final String email = emailInput.getText().toString();
                final String address = addressInput.getText().toString();

                viewSwitcher.showNext();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewSwitcher.showPrevious();
            }
        });


        sellerLoginBegin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(SellerRegistrationActivity.this, SellerLoginActivity.class);
                startActivity(intent);
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                registerSeller();
            }
        });
    }

    private void registerSeller() {

        final String name = nameInput.getText().toString();
        final String phone = phoneInput.getText().toString();
        final String email = emailInput.getText().toString();
        final String address = addressInput.getText().toString();
        final String bank = bankInput.getText().toString();
        final String accountNumber = accInput.getText().toString();
        final String idNumber = idInput.getText().toString();
        String password = passwordInput.getText().toString();

        if(!name.equals("") && !phone.equals("") && !email.equals("") && !address.equals("") && !password.equals("")
            && !bank.equals("") && !accountNumber.equals("") && !idNumber.equals(""))
        {
            loadingBar.setTitle("Seller Registration");
            loadingBar.setMessage("Please wait, while we are checking the credentials.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();


            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if(task.isSuccessful()){
                                final DatabaseReference rootRef;
                                rootRef = FirebaseDatabase.getInstance().getReference();

                                String sid = mAuth.getCurrentUser().getUid();

                                HashMap<String, Object> sellerMap = new HashMap<>();
                                sellerMap.put("sid", sid);
                                sellerMap.put("phone", phone);
                                sellerMap.put("email", email);
                                sellerMap.put("address", address);
                                sellerMap.put("name", name);
                                sellerMap.put("idNumber", idNumber);
                                sellerMap.put("bankName", bank);
                                sellerMap.put("accountNumber", accountNumber);

                                rootRef.child("Sellers").child(sid).updateChildren(sellerMap)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task)
                                            {
                                                loadingBar.dismiss();
                                                Toast.makeText(SellerRegistrationActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        }
                    });
        }
        else {
            Toast.makeText(this, "Please complete the registration form.", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(SellerRegistrationActivity.this, SellerRegistrationActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }

    }
}
