package com.betox.mygame;



import android.net.Uri;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
/**
 * Created by matej on 5. 01. 2016.
 */
public class Connection {

    private final static String PAGE = "http://10.0.2.2:8080/";


    /**
     * Method used for POST connection to HTTP server
     *
     * @param URL
     * @return answer from database
     * @throws IOException
     */
    public static String postConnection(String URL) throws IOException {
        // To encode URL, so it works over POST connection also with spaces and things like this
        URL = PAGE + URL;
        final String ALLOWED_URI_CHARS = "@#&=*+-_.,:!?()/~'% ";
        URL = Uri.encode(URL, ALLOWED_URI_CHARS);
        HttpClient hc = new DefaultHttpClient();
        HttpPost httpPostRequest = new HttpPost(URL);
        HttpResponse httpResponse = hc.execute(httpPostRequest);
        HttpEntity httpEntity = httpResponse.getEntity();
        return EntityUtils.toString(httpEntity);
    }

    /**
     * Method used for GET connection to HTTP server
     *
     * @param URL
     * @return resoult from database
     * @throws IOException
     */
    public static String getConnection(String URL) throws IOException {
        URL = PAGE + URL;
        final String ALLOWED_URI_CHARS = "@#&=*+-_.,:!?()/~'%";
        URL = Uri.encode(URL, ALLOWED_URI_CHARS);
        HttpClient hc = new DefaultHttpClient();
        HttpGet httpOffers = new HttpGet(URL.replace(" ", "%20"));
        HttpResponse httpResponse = hc.execute(httpOffers);
        HttpEntity httpEntity = httpResponse.getEntity();
        return EntityUtils.toString(httpEntity);
    }
}


