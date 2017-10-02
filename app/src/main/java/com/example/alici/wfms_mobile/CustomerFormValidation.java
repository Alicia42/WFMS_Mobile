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
    private static final String Letter_Regex = "(([A-Za-z]+|\\\\*)[:,-])*([A-Za-z]+|\\\\*)";
    private static final String Home_Phone_Regex = "[0-9]{9}";
    private static final String Mobile_Phone_Regex = "[0-9]{9,10}";
    private static final String Address_Regex = "^\\d+\\s[A-Za-z]+\\s[A-Za-z]+";
    private static final String Area_Code_Regex = "[0-9]{4}";
    private static final String Suburb_Regex = "(([A-Za-z]+|\\\\*)[:,-])*([A-Za-z]+|\\\\*)\\s(([a-z]+|\\\\*)[:,-])*([A-Za-z]+|\\\\*)";
    private static final String Suburb_Regex2 = "(([A-Za-z]+|\\\\*)[:,-])*([A-Za-z]+|\\\\*)";

    // Error Messages
    private static final String EMAIL_MSG = "invalid email";
    private static final String First_Name_MSG = "invalid first name";
    private static final String Last_Name_MSG = "invalid last name";
    private static final String Home_Phone_MSG = "invalid home phone number (e.g. 091234567)";
    private static final String Mobile_Phone_MSG = "invalid mobile phone number (e.g. 0212345678)";
    private static final String Address_MSG = "invalid address (e.g. 10 Fake Street)";
    private static final String Suburb_MSG = "invalid suburb (e.g. Henderson)";
    private static final String Area_Code_MSG = "invalid area code (e.g. 1234)";

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
        return isValid(editText, Mobile_Phone_Regex, Mobile_Phone_MSG, required);
    }

    // call this method when you need to check address validation
    public static boolean isAddress(EditText editText, boolean required) {
        return isValid(editText, Address_Regex, Address_MSG, required);
    }

    // call this method when you need to check suburb validation
    public static boolean isSuburb(EditText editText, boolean required) {
        String regex = "";
        if(isValid(editText, Suburb_Regex, Suburb_MSG, required)){
            regex = Suburb_Regex;
        }
        else if(isValid(editText, Suburb_Regex2, Suburb_MSG, required)){
            regex = Suburb_Regex2;
        }
        return isValid(editText, regex, Suburb_MSG, required);
    }

    // call this method when you need to check area code validation
    public static boolean isAreaCode(EditText editText, boolean required) {
        return isValid(editText, Area_Code_Regex, Area_Code_MSG, required);
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
