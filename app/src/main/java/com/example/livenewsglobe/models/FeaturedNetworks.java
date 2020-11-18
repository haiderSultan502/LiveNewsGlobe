package com.example.livenewsglobe.models;


import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FeaturedNetworks {
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("guid")
    @Expose
    private String guid;
    @SerializedName("post_type")
    @Expose
    private String postType;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("term_taxonomy_id")
    @Expose
    private String termTaxonomyId;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("thumbnail_url")
    @Expose
    private List<String> thumbnailUrl = null;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getPostType() {
        return postType;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTermTaxonomyId() {
        return termTaxonomyId;
    }

    public void setTermTaxonomyId(String termTaxonomyId) {
        this.termTaxonomyId = termTaxonomyId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(List<String> thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }


}
