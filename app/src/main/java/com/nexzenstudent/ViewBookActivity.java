package com.nexzenstudent;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import Config.ConstValue;

public class ViewBookActivity extends CommonAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_book);

        String title = getIntent().getStringExtra("title");

        getSupportActionBar().setTitle(title);

        WebView book = (WebView) findViewById(R.id.wv_book);
        book.getSettings().setJavaScriptEnabled(true);
        book.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);

        String pdf = getIntent().getStringExtra("book_pdf");

        String getExtension = pdf.substring(pdf.lastIndexOf("."));
        if (getExtension.contentEquals(".pdf") || getExtension.contentEquals(".pptx")) {
            book.loadUrl("https://docs.google.com/viewer?url=" + ConstValue.PDF_BOOK_URL + pdf);
        } else {
            book.setWebViewClient(new WebViewClient() {
                ProgressDialog progressDialog;
                public boolean shouldOverrideUrlLoading(WebView view, String url){
                    // do your handling codes here, which url is the requested url
                    // probably you need to open that url rather than redirect:
                    view.loadUrl(url);
                    return false; // then it is not handled by default action
                }
                //should load on url load
                public void onLoadResource (WebView view, String url) {
                    if (progressDialog == null) {
                        // in standard case YourActivity.this

                        progressDialog = new ProgressDialog(ViewBookActivity.this);
                        progressDialog.setMessage("Loading...");
                        progressDialog.show();
                    }
                }

                public void onPageFinished(WebView view, String url) {
                    try{
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                            progressDialog = null;
                        }
                    }catch(Exception exception){
                        exception.printStackTrace();
                    }
                }
            });
            book.loadUrl(pdf);
        }

    }
}
