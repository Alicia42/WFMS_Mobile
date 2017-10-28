package com.example.alici.wfms_mobile;

/**
 * Created by libbyjennings on 22/09/17.
 */

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.MySSLSocketFactory;
import com.loopj.android.http.RequestParams;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.conn.ssl.SSLSocketFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ByteArrayInputStream;
import android.util.Base64;
import java.security.cert.CertificateFactory;
import javax.net.ssl.KeyManagerFactory;

public class WCHRestClient {

    //un-comment next line to develop on local server
    //private static final String BASE_URL = "https://10.0.2.2:1997";
    //uncomment the next line to develop from cloud web service
    private static final String BASE_URL = "https://wchdomain.duckdns.org:1997";

    private static AsyncHttpClient client = new AsyncHttpClient();


    static {
        client.setSSLSocketFactory(MySSLSocketFactory.getFixedSocketFactory());
    }


    public static void get(Context context, String url, Header[] headers, RequestParams params,
                           AsyncHttpResponseHandler responseHandler) {
        client.get(context, getAbsoluteUrl(url), headers, params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
}

