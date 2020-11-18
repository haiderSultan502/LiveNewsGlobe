package com.example.livenewsglobe.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.livenewsglobe.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class NewsVideoPlayer extends Fragment {

    View view;
    WebView web;
    Bundle bundle;
    String networkUrl;
    RelativeLayout imgBackButton;
    ImageView imageViewLoading;
    public static Boolean webview = false;


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
            Document document = null;
            try {
                document= Jsoup.connect(networkUrl).get();

                document.getElementsByClass("td-header-template-wrap").remove();
                document.getElementsByClass("td-footer-template-wrap").remove();
//                document.getElementsByClass("tdb-author-box td_block_wrap tdb_single_author_box tdi_90_0cd tdb-content-vert-top td-pb-border-top td_block_template_1").remove();
//                document.getElementsByClass("td-post-sharing tdb-block td-ps-bg td-ps-padding td-post-sharing-style2").remove();
//                document.getElementsByClass("vc_row tdi_98_cce  wpb_row td-pb-row tdc-element-style").remove();
//                document.getElementsByClass("tdb-block-inner td-fix-index").remove();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return document;
        }

        @Override
        protected void onPostExecute(Document document) {
            super.onPostExecute(document);

            web.loadDataWithBaseURL(networkUrl,document.toString(),"text/html","utf-8","");
            web.getSettings().setCacheMode( WebSettings.LOAD_CACHE_ELSE_NETWORK );
            //after adding below these two lines webview able to load the images and videos
            web.getSettings().setJavaScriptEnabled(true);
            web.getSettings().setUseWideViewPort(true);

            web.setWebViewClient(new WebViewClient(){
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                    view.loadUrl(networkUrl);
                    return super.shouldOverrideUrlLoading(view, request);
                }
            });
            imageViewLoading.setVisibility(View.GONE);
        }

    }


}
