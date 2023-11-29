package com.example.das;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
public class MainActivity extends AppCompatActivity implements RecyclerViewFragment.OnItemClickListener {
    private WebViewFragment mWebViewFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            mWebViewFragment = new WebViewFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container_recycler_view, new RecyclerViewFragment())
                    .add(R.id.fragment_container_web_view, mWebViewFragment)
                    .commit();
        }
    }

    @Override
    public void onItemClicked(String url) {
        if (mWebViewFragment != null) {
            mWebViewFragment.loadUrl(url);
        }
    }
}