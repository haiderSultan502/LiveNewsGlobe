package com.example.livenewsglobe.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.livenewsglobe.R;
import com.example.livenewsglobe.otherClasses.SweetAlertDialogGeneral;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class NewsVideoPlayer extends Fragment {

    View view;
    WebView web;
    Bundle bundle;
    static String networkUrl;
    RelativeLayout imgBackButton;
    ImageView imageViewLoading;
    public static Boolean webview = false,captureLinkClick;
    Document document = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webview = true;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.news_video_player, container, false);

        web=view.findViewById(R.id.simpleWebView);
        imgBackButton=view.findViewById(R.id.img_back_btn);
        imageViewLoading=view.findViewById(R.id.img_loadings);
        Glide.with(getContext()).load(R.drawable.loading).into(imageViewLoading);

        imageViewLoading.setVisibility(View.VISIBLE);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);//this line is used to when keyboard comes up then dwidgets not disturb

//        String url = "https://livenewsglobe.com/?p=3744";
        bundle=getArguments();
        networkUrl=bundle.getString("networkUrl");
        captureLinkClick=false;
        new MyAsynTask().execute();

        imgBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getFragmentManager().getBackStackEntryCount() != 0) {
                    getFragmentManager().popBackStack();
                }
            }
        });

return view;
    }

    private class MyAsynTask extends AsyncTask<Void,Void,Document>
    {

        @Override
        protected Document doInBackground(Void... voids) {
            if (captureLinkClick==false)
            {
                try {
                    document= Jsoup.connect(networkUrl).get();

                    document.getElementsByClass("td-header-template-wrap").remove();
                    document.getElementsByClass("td-footer-template-wrap").remove();
                    document.getElementById("comments").remove();
//                document.getElementsByClass("vc_column tdi_100_fc1  wpb_column vc_column_container tdc-column td-pb-span12").remove();
//                document.getElementsByClass("td-post-sharing-visible").remove();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else
            {
                try {
                    document= Jsoup.connect(networkUrl).get();
                    Log.d("NetworkUrl",networkUrl);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return document;
        }

        @Override
        protected void onPostExecute(final Document document) {
            super.onPostExecute(document);

            web.loadDataWithBaseURL(networkUrl,document.toString(),"text/html","utf-8","");
            web.getSettings().setCacheMode( WebSettings.LOAD_CACHE_ELSE_NETWORK );
            //after adding below these two lines webview able to load the images and videos
            web.getSettings().setJavaScriptEnabled(true);
            web.getSettings().setUseWideViewPort(true);
//            web.loadUrl(networkUrl);

            web.setWebViewClient(new WebViewClient(){

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                    view.loadUrl(networkUrl);
                    return super.shouldOverrideUrlLoading(view, request);
                }

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    imageViewLoading.setVisibility(View.VISIBLE);
                    Log.d("url",url);
                    networkUrl=url;
                    captureLinkClick=true;
                    new MyAsynTask().execute();
                    return true;
                }

            });
            imageViewLoading.setVisibility(View.GONE);
        }

    }

}
