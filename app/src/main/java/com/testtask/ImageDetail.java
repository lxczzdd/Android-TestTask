package com.testtask;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class ImageDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_layout);

        ImageView imageView = findViewById(R.id.myZoomageView);

        Glide.with(this)
                .load(getIntent()
                        .getStringExtra("image"))
                .into(imageView);
    }

}
