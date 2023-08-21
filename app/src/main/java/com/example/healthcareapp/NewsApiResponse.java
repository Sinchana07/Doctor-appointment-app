package com.example.healthcareapp;

import java.util.List;

public class NewsApiResponse {
    private String status;
    private int totalResults;
    private List<NewsArticle> articles;

    public String getStatus() {
        return status;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public List<NewsArticle> getArticles() {
        return articles;
    }
}
