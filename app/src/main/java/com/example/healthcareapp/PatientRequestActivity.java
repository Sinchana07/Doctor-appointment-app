package com.example.healthcareapp;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class PatientRequestActivity extends AppCompatActivity {

    private TextView textViewNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_request);

        textViewNews = findViewById(R.id.textViewNews);

        // Fetch and display news articles
        fetchNewsArticles();
    }

    private void fetchNewsArticles() {
        try {
            // Read the JSON file from assets folder
            InputStream inputStream = getAssets().open("newsarticle.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();

            // Parse the JSON data
            String jsonContent = new String(buffer, "UTF-8");
            JSONArray articlesArray = new JSONArray(jsonContent);

            // Update UI on the main thread
            runOnUiThread(() -> displayNewsArticles(articlesArray));

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    private void displayNewsArticles(JSONArray articlesArray) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < articlesArray.length(); i++) {
            try {
                JSONObject articleObject = articlesArray.getJSONObject(i);
                String title = articleObject.getString("title");
                String description = articleObject.getString("description");

                sb.append("<b>").append(Html.escapeHtml(title)).append("</b><br>");
                sb.append("Description: ").append(Html.escapeHtml(description)).append("<br>");
                sb.append("----------------------------------------------------------------------------------").append("<br>");

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        textViewNews.setText(Html.fromHtml(sb.toString()));
    }
}
