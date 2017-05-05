package com.example.mrehman.vieweditandroid;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.Toast;



/**
 * A simple {@link Fragment} subclass.
 */
public class LibraryFragment extends Fragment {

    private WebView mwebView;


    public LibraryFragment() {
        // Required empty public constructor

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mwebView = (WebView) getView().findViewById(R.id.lib_webview);
        mwebView.setOnTouchListener(new OnSwipeTouchListener(getActivity()){

            public void onSwipeLeft() {
//                Toast.makeText(getActivity(), "BACK", Toast.LENGTH_SHORT).show();
//                getActivity().onBackPressed();
                ((MainActivity)getActivity()).removeFragment();
            }


        });

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_library, container, false);

        WebView webView = (WebView)rootView.findViewById(R.id.lib_webview);

        webView.setInitialScale(1);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setScrollbarFadingEnabled(false);

        webView.loadUrl("https://get-served-viewedit.herokuapp.com/library.html");
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });



        return rootView;
    }

}
