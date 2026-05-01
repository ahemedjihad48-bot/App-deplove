package com.jihadhost.app;

import android.annotation.SuppressLint; import android.os.Bundle; import android.view.View; import android.webkit.WebChromeClient; import android.webkit.WebSettings; import android.webkit.WebView; import android.webkit.WebViewClient; import android.widget.Button; import android.widget.ProgressBar; import android.widget.TextView; import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher; import androidx.activity.result.contract.ActivityResultContracts; import androidx.appcompat.app.AppCompatActivity; import androidx.drawerlayout.widget.DrawerLayout;

public class MainActivity extends AppCompatActivity {

DrawerLayout drawerLayout;
TextView menuBtn;
WebView webView;
ProgressBar progressBar;
Button uploadBtn;
ActivityResultLauncher<String> picker;

@SuppressLint("SetJavaScriptEnabled")
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    drawerLayout = findViewById(R.id.drawerLayout);
    menuBtn = findViewById(R.id.menuBtn);
    webView = findViewById(R.id.webview);
    progressBar = findViewById(R.id.progressBar);
    uploadBtn = findViewById(R.id.uploadBtn);

    menuBtn.setOnClickListener(v -> drawerLayout.openDrawer(android.view.Gravity.LEFT));

    WebSettings settings = webView.getSettings();
    settings.setJavaScriptEnabled(true);
    settings.setDomStorageEnabled(true);

    webView.setWebViewClient(new WebViewClient());
    webView.setWebChromeClient(new WebChromeClient(){
        public void onProgressChanged(WebView view, int progress){
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(progress);
            if(progress == 100){
                progressBar.setVisibility(View.GONE);
            }
        }
    });

    webView.loadUrl("https://wispbyte.com");

    picker = registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {
        if(uri != null){
            Toast.makeText(this, "File Selected", Toast.LENGTH_SHORT).show();
        }
    });

    if(uploadBtn != null){
        uploadBtn.setOnClickListener(v -> picker.launch("*/*"));
    }

    Toast.makeText(this,"Welcome Back!",Toast.LENGTH_LONG).show();
}

@Override
public void onBackPressed() {
    if(drawerLayout.isDrawerOpen(android.view.Gravity.LEFT)){
        drawerLayout.closeDrawers();
    } else if(webView.canGoBack()){
        webView.goBack();
    } else {
        super.onBackPressed();
    }
}

}