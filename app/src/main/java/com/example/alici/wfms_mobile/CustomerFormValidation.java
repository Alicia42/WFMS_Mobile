package com.example.alici.wfms_mobile;

/**
 * Created by libbyjennings on 27/09/17.
 */

import android.util.Log;
import android.widget.EditText;
import java.util.regex.Pattern;

public class CustomerFormValidation {

    // Regular Expression
    private static final String Email_Regex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String REQUIRED_MSG = "required";
    private static final String Letter_Regex = "(([a-z]+|\\\\*)[:,-])*([a-z]+|\\\\*)";
    private static final String Home_Phone_Regex = "\\d{3}-\\d{7}";

    // Error Messages
    private static final String EMAIL_MSG = "invalid email";
    private static final String First_Name_MSG = "invalid first name";
    private static final String Last_Name_MSG = "invalid last name";
    private static final String Home_Phone_MSG = "invalid home phone number (e.g. 091231231)";

    // call this method when you need to check email validation
    public static boolean isEmailAddress(EditText editText, boolean required) {
        return isValid(editText, Email_Regex, EMAIL_MSG, required);
    }

    // call this method when you need to check the first name validation
    public static boolean isFirstName(EditText editText, boolean required) {
        return isValid(editText, Letter_Regex, First_Name_MSG, required);
    }

    // call this method when you need to check the last name validation
    public static boolean isLastName(EditText editText, boolean required) {
        return isValid(editText, Letter_Regex, Last_Name_MSG, required);
    }

    // call this method when you need to check phone number validation
    public static boolean isHomePhoneNumber(EditText editText, boolean required) {
        return isValid(editText, Home_Phone_Regex, Home_Phone_MSG, required);
    }

    // call this method when you need to check phone number validation
    public static boolean isMobileNumber(EditText editText, boolean required) {
        return isValid(editText, Home_Phone_Regex, Home_Phone_MSG, required);
    }

    // return true if the input field is valid, based on the parameter passed
    public static boolean isValid(EditText editText, String regex, String errMsg, boolean required) {

        String text = editText.getText().toString().trim();
        // clearing the error, if it was previously set by some other values
        editText.setError(null);

        // text required and editText is blank, so return false
        if ( required && !hasText(editText) ) return false;

        // pattern doesn't match so returning false
        if (required && !Pattern.matches(regex, text)) {
            editText.setError(errMsg);
            return false;
        };

        return true;
    }

    // check the input field has any text or not
    // return true if it contains text otherwise false
    public static boolean hasText(EditText editText) {

        String text = editText.getText().toString().trim();
        editText.setError(null);

        // length 0 means there is no text
        if (text.length() == 0) {
            editText.setError(REQUIRED_MSG);
            return false;
        }

        return true;
    }
}
