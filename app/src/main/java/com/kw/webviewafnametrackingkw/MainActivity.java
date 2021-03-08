package com.kw.webviewafnametrackingkw;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.webkit.CookieSyncManager;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import android.app.Application;
import android.util.Log;
import com.appsflyer.AppsFlyerLib;
import com.appsflyer.AppsFlyerConversionListener;
import java.util.Map;

import android.webkit.WebChromeClient;
import android.widget.Toast;

import static androidx.core.app.ActivityCompat.startActivityForResult;

public class MainActivity extends AppCompatActivity {

    WebView webView;
    WebSettings webSettings;
    private final String GO_URL = "https://pustvsegdabudetnebo.online/ffPRtn";
    SharedPreferences.Editor editor;
    SharedPreferences mSettings;
    public static final String APP_PREFERENCES_URL = "url";
    private static final String AF_DEV_KEY = "RL2v3yXB59zPRKatTMNKh9";
    private String urlSubs;
    private String[] subs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = (WebView)findViewById(R.id.webview);

        AppsFlyerConversionListener conversionListener = new AppsFlyerConversionListener() {


            @Override
            public void onConversionDataSuccess(Map<String, Object> conversionData) {
                for (String attrName : conversionData.keySet()) {
                    Log.d("LOG_TAG", "attribute: " + attrName + " = " + conversionData.get(attrName));
                    if (attrName.equals("campaign")){
                        Log.d("LOG_TAG","Campaign: "+conversionData.get(attrName).toString());
                        if (!conversionData.get(attrName).toString().equals("None")){
                            goWebView(setupURL(conversionData.get(attrName).toString()));
                        }
                        else{
                            //для варианта с None (для теста,потом можете убрать этот else)
                            goWebView("");
                        }
                    }
                }
            }

            @Override
            public void onConversionDataFail(String errorMessage) {
                Log.d("LOG_TAG", "error getting conversion data: " + errorMessage);
            }

            @Override
            public void onAppOpenAttribution(Map<String, String> attributionData) {
                for (String attrName : attributionData.keySet()) {
                    Log.d("LOG_TAG", "attributION: " + attrName + " = " + attributionData.get(attrName));
                }

            }

            @Override
            public void onAttributionFailure(String errorMessage) {
                Log.d("LOG_TAG", "error onAttributionFailure : " + errorMessage);
            }
        };


        AppsFlyerLib.getInstance().init(AF_DEV_KEY, conversionListener, this);
        AppsFlyerLib.getInstance().startTracking(this);

    }

    private String setupURL(String campaign){
        subs = campaign.split("_");
        urlSubs = "sub1="+subs[0]+"&sub2="+subs[1]+"&sub3="+subs[2]+"&sub4="+subs[3]+"&sub5="+subs[4];
        Log.d("LOG_TAG","urlSubs: "+urlSubs);
        return urlSubs;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppsFlyerLib.getInstance().unregisterConversionListener();
    }

    public void goWebView(String urlSubs) {
        runOnUiThread((() -> {
            webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webSettings.setDomStorageEnabled(true);
            webSettings.setBuiltInZoomControls(true);
            webSettings.setSupportZoom(false);
            webSettings.setDisplayZoomControls(false);
            webView.setInitialScale(1);
            webView.getSettings().setLoadWithOverviewMode(true);
            webView.getSettings().setUseWideViewPort(true);
//        webView.setWebChromeClient(new MyWebChromeClient());
            webView.getSettings().setAllowContentAccess(true);
            webView.getSettings().setAllowFileAccess(true);
            webView.loadUrl(GO_URL+urlSubs);
//        webView.addJavascriptInterface(new JavaClassEtap(), "HTMLOUT");
            webView.setWebViewClient(new WebViewClient());
//        public boolean shouldOverrideUrlLoading(WebView webView, WebResourceRequest request) {
//
//            return false;
//        }
        }));

}
}







