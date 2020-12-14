package com.example.livenewsglobe.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FavouritesModel {

    @SerializedName("posts_id")
    @Expose
    private String postsId;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("post_title")
    @Expose
    private String postTitle;
    @SerializedName("post_content")
    @Expose
    private String postContent;
    @SerializedName("post_name")
    @Expose
    private String postName;
    @SerializedName("guid")
    @Expose
    private String guid;

    public String getPostsId() {
        return postsId;
    }

    public void setPostsId(String postsId) {
        this.postsId = postsId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

}
