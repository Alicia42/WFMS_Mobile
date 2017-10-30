package com.example.alici.wfms_mobile;

/**
 * Created by libbyjennings on 22/09/17.
 */

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.MySSLSocketFactory;
import com.loopj.android.http.RequestParams;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import cz.msebera.android.httpclient.Header;

import javax.net.ssl.SSLContext;

import java.io.InputStream;
import java.security.cert.CertificateFactory;
import java.io.BufferedInputStream;
import java.security.cert.Certificate;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.HttpsURLConnection;
import java.net.URL;
import java.io.FileInputStream;
import java.security.cert.X509Certificate;

public class WCHRestClient {

    //un-comment next line to develop on local server
    //private static final String BASE_URL = "https://10.0.2.2:1997";
    //uncomment the next line to develop from cloud web service
    private static final String BASE_URL = "https://wchdomain.duckdns.org:1997";

    private static AsyncHttpClient client = new AsyncHttpClient();


    static {
        // Load CAs from an InputStream
        // (could be from a resource or ByteArrayInputStream or ...)
        /*CertificateFactory cf = null;
        try {
            cf = CertificateFactory.getInstance("X.509");
        } catch (CertificateException e) {
            e.printStackTrace();
        }

        Context context = null;
        context.getApplicationContext();
        // From https://www.washington.edu/itconnect/security/ca/load-der.crt
        InputStream is = null;
        try {
            is = context.getResources().getAssets().open("/Users/libbyjennings/Desktop/your-cert.crt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        InputStream caInput = new BufferedInputStream(is);
        Certificate ca;
        try {
            ca = cf.generateCertificate(caInput);
            // Create a KeyStore containing our trusted CAs
            String keyStoreType = KeyStore.getDefaultType();
            KeyStore keyStore = null;
            try {
                keyStore = KeyStore.getInstance(keyStoreType);
            } catch (KeyStoreException e) {
                e.printStackTrace();
            }
            try {
                keyStore.load(null, null);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (CertificateException e) {
                e.printStackTrace();
            }
            keyStore.setCertificateEntry("ca", ca);
            try {
                MyCustomSSLFactory socketFactory = new MyCustomSSLFactory(keyStore);
                client.setSSLSocketFactory(socketFactory);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (KeyManagementException e) {
                e.printStackTrace();
            } catch (KeyStoreException e) {
                e.printStackTrace();
            } catch (UnrecoverableKeyException e) {
                e.printStackTrace();
            }
            // System.out.println("ca=" + ((X509Certificate) ca).getSubjectDN());
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } finally {
            try {
                caInput.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/

        //AsyncHttpClient asycnHttpClient = new AsyncHttpClient(true, 80, 443);
        client.setSSLSocketFactory(MySSLSocketFactory.getFixedSocketFactory());
    }

    public WCHRestClient() throws UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
    }


    public static void get(Context context, String url, Header[] headers, RequestParams params,
                           AsyncHttpResponseHandler responseHandler) {
        client.get(context, getAbsoluteUrl(url), headers, params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
}

