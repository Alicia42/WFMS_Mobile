package com.example.alici.wfms_mobile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.text.TextWatcher;
import android.text.Editable;
import android.widget.Toast;
import android.view.View;

public class NewCustomerFormActivity extends AppCompatActivity {

    private EditText fNameTxtBx;
    private EditText lNameTxtBx;
    private EditText EmailAddress;
    private EditText HomeNumber;
    private EditText MobileNumber;
    private Button createCustomerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_customer_form);

        registerViews();
    }

    private void registerViews() {

        fNameTxtBx = (EditText) findViewById(R.id.fNameTxtBx);
        // TextWatcher would let us check validation error on the fly
        fNameTxtBx.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                CustomerFormValidation.hasText(fNameTxtBx);
                CustomerFormValidation.isFirstName(fNameTxtBx, true);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });

        lNameTxtBx = (EditText) findViewById(R.id.lNameTxtBx);
        // TextWatcher would let us check validation error on the fly
        lNameTxtBx.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                CustomerFormValidation.hasText(lNameTxtBx);
                CustomerFormValidation.isLastName(lNameTxtBx, true);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });

        EmailAddress = (EditText) findViewById(R.id.emailTxtBx);
        EmailAddress.addTextChangedListener(new TextWatcher() {
            // after every change has been made to this editText, we would like to check validity
            public void afterTextChanged(Editable s) {
                CustomerFormValidation.hasText(EmailAddress);
                CustomerFormValidation.isEmailAddress(EmailAddress, true);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });

        HomeNumber = (EditText) findViewById(R.id.hNumberTxtBx);
        HomeNumber.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                CustomerFormValidation.hasText(HomeNumber);
                CustomerFormValidation.isHomePhoneNumber(HomeNumber, false);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });

        MobileNumber = (EditText) findViewById(R.id.mNumberTxtBx);
        MobileNumber.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                CustomerFormValidation.hasText(MobileNumber);
                CustomerFormValidation.isMobileNumber(MobileNumber, false);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });

        createCustomerBtn = (Button) findViewById(R.id.registerBtn);
        createCustomerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Validation class will check the error and display the error on respective fields
                but it won't resist the form submission, so we need to check again before submit
                 */
                if ( checkValidation () )
                    submitForm();
                else
                    Toast.makeText(NewCustomerFormActivity.this, "Form contains error", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void submitForm() {
        // Submit your form here. your form is valid
        Toast.makeText(this, "Submitting form...", Toast.LENGTH_LONG).show();
    }

    private boolean checkValidation() {
        boolean ret = true;

        if (!CustomerFormValidation.hasText(fNameTxtBx)) ret = false;
        if (!CustomerFormValidation.isEmailAddress(EmailAddress, true)) ret = false;
        if (!CustomerFormValidation.isFirstName(fNameTxtBx, true)) ret = false;
        if (!CustomerFormValidation.isLastName(lNameTxtBx, true)) ret = false;
        if (!CustomerFormValidation.isHomePhoneNumber(HomeNumber, false)) ret = false;

        return ret;
    }
}
