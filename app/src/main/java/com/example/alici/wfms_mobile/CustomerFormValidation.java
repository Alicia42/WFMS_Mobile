package com.example.alici.wfms_mobile;

/*
 * Created by Libby Jennings on 27/09/17.
 * Description: Class for validating input in the customer digital entry form
 */

/*
Copyright 2014 Raquib-ul-Alam

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
import android.widget.EditText;
import java.util.regex.Pattern;

class CustomerFormValidation {

    // Regular Expression
    private static final String Email_Regex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String REQUIRED_MSG = "required";
    private static final String Letter_Regex = "(([A-Za-z]+|\\\\*)[:,-])*([A-Za-z]+|\\\\*)";
    private static final String Home_Phone_Regex = "[0-9]{9}";
    private static final String Mobile_Phone_Regex = "[0-9]{9,10}";
    private static final String Area_Code_Regex = "[0-9]{4}";
    private static final String Suburb_Regex = "(([A-Za-z]+|\\\\*)[:,-])*([A-Za-z]+|\\\\*)\\s(([a-z]+|\\\\*)[:,-])*([A-Za-z]+|\\\\*)";
    private static final String Suburb_Regex2 = "(([A-Za-z]+|\\\\*)[:,-])*([A-Za-z]+|\\\\*)";

    // Error Messages
    private static final String EMAIL_MSG = "invalid email";
    private static final String First_Name_MSG = "invalid first name";
    private static final String Last_Name_MSG = "invalid last name";
    private static final String Home_Phone_MSG = "invalid home phone number (e.g. 091234567)";
    private static final String Mobile_Phone_MSG = "invalid mobile phone number (e.g. 0212345678)";
    private static final String Address_MSG = "invalid address, must be less than 30 characters";
    private static final String Suburb_MSG = "invalid suburb (e.g. Henderson)";
    private static final String Area_Code_MSG = "invalid area code (e.g. 1234)";

    // call this method when you need to check email validation
    static boolean isEmailAddress(EditText editText) {
        return isValid(editText, Email_Regex, EMAIL_MSG);
    }

    // call this method when you need to check the first name validation
    static boolean isFirstName(EditText editText) {
        return isValid(editText, Letter_Regex, First_Name_MSG);
    }

    // call this method when you need to check the last name validation
    static boolean isLastName(EditText editText) {
        return isValid(editText, Letter_Regex, Last_Name_MSG);
    }

    // call this method when you need to check phone number validation
    static void isHomePhoneNumber(EditText editText) {
        isValid(editText, Home_Phone_Regex, Home_Phone_MSG);
    }

    // call this method when you need to check phone number validation
    static void isMobileNumber(EditText editText) {
        isValid(editText, Mobile_Phone_Regex, Mobile_Phone_MSG);
    }

    // call this method when you need to check address validation
    static boolean isAddress(EditText editText) {
        return isAddressValid(editText, Address_MSG);
    }

    // call this method when you need to check suburb validation
    static boolean isSuburb(EditText editText) {
        String regex = "";
        if(isValid(editText, Suburb_Regex, Suburb_MSG)){
            regex = Suburb_Regex;
        }
        else if(isValid(editText, Suburb_Regex2, Suburb_MSG)){
            regex = Suburb_Regex2;
        }
        return isValid(editText, regex, Suburb_MSG);
    }

    // call this method when you need to check area code validation
    static boolean isAreaCode(EditText editText) {
        return isValid(editText, Area_Code_Regex, Area_Code_MSG);
    }

    // return true if the input field is valid, based on the parameter passed
    private static boolean isAddressValid(EditText editText, String errMsg) {

        String text = editText.getText().toString().trim();
        // clearing the error, if it was previously set by some other values
        editText.setError(null);

        // text required and editText is blank, so return false
        return hasText(editText) && lessThanLimit(editText);


    }

    // return true if the input field is valid, based on the parameter passed
    private static boolean isValid(EditText editText, String regex, String errMsg) {

        String text = editText.getText().toString().trim();
        // clearing the error, if it was previously set by some other values
        editText.setError(null);

        // text required and editText is blank, so return false
        if (!hasText(editText)) return false;

        // pattern doesn't match so returning false
        if (!Pattern.matches(regex, text)) {
            editText.setError(errMsg);
            return false;
        };

        return true;
    }

    // check the input field has any text or not
    // return true if it contains text otherwise false
    static boolean hasText(EditText editText) {

        String text = editText.getText().toString().trim();
        editText.setError(null);

        // length 0 means there is no text
        if (text.length() == 0) {
            editText.setError(REQUIRED_MSG);
            return false;
        }

        return true;
    }

    //Check that the address is less tha 30 characters
    private static boolean lessThanLimit(EditText editText) {

        String text = editText.getText().toString().trim();
        editText.setError(null);

        if (text.length() >= 30) {
            editText.setError(Address_MSG);
            return false;
        }

        return true;
    }
}
