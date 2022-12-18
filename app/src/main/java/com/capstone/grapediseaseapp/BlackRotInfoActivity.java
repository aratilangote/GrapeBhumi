package com.capstone.grapediseaseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class BlackRotInfoActivity extends AppCompatActivity {

    ImageButton backArrow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_black_rot_info);
        backArrow = findViewById(R.id.backArrowId);

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BlackRotInfoActivity.this, RelatedInfoActivity.class));
            }
        });
    }
}