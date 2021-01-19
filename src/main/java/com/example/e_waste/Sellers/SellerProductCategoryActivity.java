package com.example.e_waste.Sellers;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.e_waste.R;

public class SellerProductCategoryActivity extends AppCompatActivity
{
    private ImageView Hardware;
    private ImageView Software;





    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_product_category);




        Hardware = (ImageView) findViewById(R.id.hardwareCat);
        Software = (ImageView) findViewById(R.id.softwareCat);



        Hardware.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(SellerProductCategoryActivity.this, SellerAddNewProductActivity.class);
                intent.putExtra("category", "Hardware");
                startActivity(intent);
            }
        });


        Software.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(SellerProductCategoryActivity.this, SellerAddNewProductActivity.class);
                intent.putExtra("category", "Software");
                startActivity(intent);
            }
        });

    }
}
