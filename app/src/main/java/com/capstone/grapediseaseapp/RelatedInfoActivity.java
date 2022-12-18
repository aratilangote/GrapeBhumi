package com.capstone.grapediseaseapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class RelatedInfoActivity extends AppCompatActivity {

    ImageButton backArrow;
    TextView dBlackRot, dDowny, dEsca, dLeafBlight, dPowdery;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_related_info);

        backArrow = findViewById(R.id.backArrowId);
        dBlackRot = findViewById(R.id.d_blackrot);
        dDowny = findViewById(R.id.d_downy);
        dEsca = findViewById(R.id.d_esca);
        dLeafBlight = findViewById(R.id.d_leafblight);
        dPowdery = findViewById(R.id.d_powdery);

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RelatedInfoActivity.this, HomeActivity.class));
            }
        });

        dBlackRot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RelatedInfoActivity.this, BlackRotInfoActivity.class));
            }
        });

        dDowny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RelatedInfoActivity.this, DownyInfoActivity.class));
            }
        });

        dEsca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RelatedInfoActivity.this, EscaActivity.class));
            }
        });

        dLeafBlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RelatedInfoActivity.this, LeafBlightActivity.class));
            }
        });

        dPowdery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RelatedInfoActivity.this, PowderyActivity.class));
            }
        });
    }
}