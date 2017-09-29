package com.example.alici.wfms_mobile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Button;
import android.text.TextWatcher;
import android.text.Editable;
import android.widget.Toast;
import android.view.View;
import android.view.View.OnClickListener;

public class NewCustomerFormActivity extends AppCompatActivity {

    private EditText fNameTxtBx;
    private EditText lNameTxtBx;
    private EditText EmailAddress;
    private EditText HomeNumber;
    private EditText MobileNumber;
    private EditText address;
    private EditText suburb;
    private EditText areaCode;
    private CheckBox sameAddChBx;
    private EditText postalAddress;
    private EditText postalSuburb;
    private EditText postalAreaCode;
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
                CustomerFormValidation.isHomePhoneNumber(HomeNumber, true);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });

        MobileNumber = (EditText) findViewById(R.id.mNumberTxtBx);
        MobileNumber.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                CustomerFormValidation.isMobileNumber(MobileNumber, true);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });

        address = (EditText) findViewById(R.id.addressTxtBx);
        address.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                CustomerFormValidation.hasText(address);
                CustomerFormValidation.isAddress(address, true);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });

        suburb = (EditText) findViewById(R.id.suburbTxtBx);
        suburb.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                CustomerFormValidation.hasText(suburb);
                CustomerFormValidation.isSuburb(suburb, true);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });

        areaCode = (EditText) findViewById(R.id.areaCodeTxtBx);
        areaCode.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                CustomerFormValidation.hasText(areaCode);
                CustomerFormValidation.isAreaCode(areaCode, true);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });

        postalAddress = (EditText) findViewById(R.id.pAddressTxtBx);
        postalAddress.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                CustomerFormValidation.hasText(postalAddress);
                CustomerFormValidation.isAddress(postalAddress, true);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });

        postalSuburb = (EditText) findViewById(R.id.pSuburbTxtBx);
        postalSuburb.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                CustomerFormValidation.hasText(postalSuburb);
                CustomerFormValidation.isSuburb(postalSuburb, true);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });
        postalAreaCode = (EditText) findViewById(R.id.pAreaCodeTxtBx);
        postalAreaCode.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                CustomerFormValidation.hasText(postalAreaCode);
                CustomerFormValidation.isAreaCode(postalAreaCode, true);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });
        sameAddChBx = (CheckBox) findViewById(R.id.sameAddChBx);
        sameAddChBx.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(sameAddChBx.isChecked()){

                    if(CustomerFormValidation.hasText(areaCode) || CustomerFormValidation.hasText(suburb) ||CustomerFormValidation.hasText(address)) {
                        System.out.println("Checked");
                        postalAddress.setText(address.getText());
                        postalSuburb.setText(suburb.getText());
                        postalAreaCode.setText(areaCode.getText());
                    }

                }else{
                    System.out.println("Un-Checked");
                    postalAddress.setText("");
                    postalSuburb.setText("");
                    postalAreaCode.setText("");
                }
            }
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
        /*if(CustomerFormValidation.hasText(HomeNumber) || !CustomerFormValidation.hasText(MobileNumber)){
            ret = false;
        }*/
        if (!CustomerFormValidation.isAddress(address, true)) ret = false;
        if (!CustomerFormValidation.isAddress(postalAddress, true)) ret = false;
        if (!CustomerFormValidation.isSuburb(suburb, true)) ret = false;
        if (!CustomerFormValidation.isSuburb(postalSuburb, true)) ret = false;
        if (!CustomerFormValidation.isAreaCode(areaCode, true)) ret = false;
        if (!CustomerFormValidation.isAreaCode(postalAreaCode, true)) ret = false;

        return ret;
    }
}
