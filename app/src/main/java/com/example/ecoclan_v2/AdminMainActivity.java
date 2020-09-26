package com.example.ecoclan_v2;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class AdminMainActivity extends AppCompatActivity {

    GridLayout mainGrid;
    CardView customerCard;
    CardView collectorCard;
    CardView recycleCard;
    CardView verificationCard;
    CardView requestCard;
    CardView historyCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);

        mainGrid = (GridLayout) findViewById(R.id.mainGrid);

        customerCard = findViewById(R.id.customerCard);
     collectorCard = findViewById(R.id.collectorCard);
     recycleCard= findViewById(R.id.recycleCard) ;
     verificationCard= findViewById(R.id.verificationCard);
     requestCard= findViewById(R.id.requestCard);
     historyCard= findViewById(R.id.historyCard);



         setSingleEvent(mainGrid);

        customerCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminMainActivity.this, customer.class));

            }
        });

        collectorCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminMainActivity.this, collector.class));
            }
        });
        recycleCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminMainActivity.this, recycle.class));
            }
        });

        historyCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminMainActivity.this, history.class));
            }
        });

        requestCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminMainActivity.this,verification.class));
            }
        });


        verificationCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminMainActivity.this,request_recycle.class));
            }
        });


    }

    private void setToggleEvent(GridLayout mainGrid) {

        for (int i = 0; i < mainGrid.getChildCount(); i++) {

            final CardView cardView = (CardView) mainGrid.getChildAt(i);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (cardView.getCardBackgroundColor().getDefaultColor() == -1) {

                        cardView.setCardBackgroundColor(Color.parseColor("#FF6F00"));
                        Toast.makeText(AdminMainActivity.this, "State : True", Toast.LENGTH_SHORT).show();

                    } else {

                        cardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                        Toast.makeText(AdminMainActivity.this, "State : False", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void setSingleEvent(GridLayout mainGrid) {

        for (int i = 0; i < mainGrid.getChildCount(); i++) {

            CardView cardView = (CardView) mainGrid.getChildAt(i);
            final int finalI = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(AdminMainActivity.this,ActivityOne.class);
                    intent.putExtra("info","This is activity from card item index  "+finalI);
                    startActivity(intent);

                }
            });
        }
    }
}
