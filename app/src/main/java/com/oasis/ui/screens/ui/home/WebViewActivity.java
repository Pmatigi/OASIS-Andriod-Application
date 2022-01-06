package com.oasis.ui.screens.ui.home;

import android.net.http.SslError;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.oasis.R;
import com.oasis.ui.BaseActivity;

public class WebViewActivity extends BaseActivity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        webView = (WebView) findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setAllowContentAccess(true);
        webView.getSettings().setDisplayZoomControls(true);
        webView.getSettings().setBuiltInZoomControls(true);
        //WebSettings.PluginState ps=
        //webView.getSettings().setPluginState();
        webView.getSettings().setDefaultTextEncodingName("utf-8");


        String url =getIntent().getStringExtra("ticket_link");

        LinearLayout lin_lay_back=findViewById(R.id.lin_lay_back);
        lin_lay_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showLoading();
            }
        },500);


try {
    webView.setWebViewClient(new WebViewClient() {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onLoadResource(WebView view, String url) {

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            hideLoading();
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            hideLoading();
            Toast.makeText(WebViewActivity.this, "Error occured while fetching ticket", Toast.LENGTH_SHORT).show();
            finish();
        }

        @Override
        public void onReceivedHttpError(
                WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
            hideLoading();
            Toast.makeText(WebViewActivity.this, "Error occured while fetching ticket", Toast.LENGTH_SHORT).show();
            finish();
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler,
                                       SslError error) {
            hideLoading();
            Toast.makeText(WebViewActivity.this, "Error occured while fetching ticket", Toast.LENGTH_SHORT).show();
            finish();
        }

    });
    String webURL="https://docs.google.com/gview?embedded=true&url=" + url;
    Log.e("dkd","dkd webURL-"+webURL);
    webView.loadUrl(webURL);
    } catch (Exception e) {
    e.printStackTrace();
    hideLoading();
//    Toast.makeText(WebViewActivity.this, "Error occured while fetching ticket", Toast.LENGTH_SHORT).show();
//    finish();
    }
}

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
