package com.example.alici.wfms_mobile;

/**
 * Created by Libby Jennings on 22/09/17.
 */

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.MySSLSocketFactory;
import com.loopj.android.http.RequestParams;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import cz.msebera.android.httpclient.Header;

public class WCHRestClient {

    //un-comment next line to develop on local server
    //private static final String BASE_URL = "https://10.0.2.2:1997";
    private static final String BASE_URL = "https://wchdomain.duckdns.org:1997";

    //Create async http client object from library
    private static AsyncHttpClient client = new AsyncHttpClient();


    static {

        //set ssl socket factory to accept all certificates
        client.setSSLSocketFactory(MySSLSocketFactory.getFixedSocketFactory());
    }

    public WCHRestClient() throws UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
    }

    //get request
    public static void get(Context context, String url, Header[] headers, RequestParams params,
                           AsyncHttpResponseHandler responseHandler) {
        client.get(context, getAbsoluteUrl(url), headers, params, responseHandler);
    }

    //post request
    static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
}

