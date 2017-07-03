package br.ipti.org.apptag.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import br.ipti.org.apptag.R;

public class WebViewActivity extends AppCompatActivity {

    private WebView mWebView;

    private String url = "http://demo.tag.ipti.org.br/?r=schoolreport/default/login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        mWebView = (WebView) findViewById(R.id.wvTAG);
        mWebView.loadUrl(url);
        this.configureWebview();
    }

    private void configureWebview() {
        mWebView.setFocusable(true);
        mWebView.setFocusableInTouchMode(true);
        mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        mWebView.setInitialScale(300);
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setJavaScriptEnabled(true);

        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setSaveFormData(true);
        mWebView.getSettings().setDatabaseEnabled(true);
        mWebView.getSettings().setAppCacheEnabled(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mWebView.getSettings().setBuiltInZoomControls(false);
        mWebView.getSettings().setUseWideViewPort(true);

        mWebView.clearHistory();
        mWebView.clearFormData();
        mWebView.clearCache(true);

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public final void onPageFinished(final WebView view, final String url) {
                super.onPageFinished(view, url);
            }
        });
    }
}
