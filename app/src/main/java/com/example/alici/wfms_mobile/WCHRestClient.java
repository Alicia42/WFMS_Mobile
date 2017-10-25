package com.example.alici.wfms_mobile;

/**
 * Created by libbyjennings on 22/09/17.
 */

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;

public class WCHRestClient {

    //un-comment next line to develop on local server
    //private static final String BASE_URL = "http://10.0.2.2:1997";
    //uncomment the next line to develop from cloud web service
    private static final String BASE_URL = "http://wchdomain.duckdns.org:1997";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(Context context, String url, Header[] headers, RequestParams params,
                           AsyncHttpResponseHandler responseHandler) {
        client.get(context, getAbsoluteUrl(url), headers, params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
}
