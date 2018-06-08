package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        ((CollapsingToolbarLayout) findViewById(R.id.collapsing_toobar_layout)).setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        TextView alsoKnownTv = findViewById(R.id.also_known_tv);
        TextView alsoKnownLabelTv = findViewById(R.id.also_known_label_tv);
        List<String> alsoKnownList = sandwich.getAlsoKnownAs();
        if (alsoKnownList != null) {
            for (String alsoKnown: alsoKnownList) {
                alsoKnownTv.append(alsoKnown + "\n");
            }
        } else {
            alsoKnownLabelTv.setVisibility(View.GONE);
            alsoKnownTv.setVisibility(View.GONE);
        }

        TextView originTv = findViewById(R.id.origin_tv);
        TextView originLabelTv = findViewById(R.id.origin_label_tv);
        String originString = sandwich.getPlaceOfOrigin();
        if (originString != null) {
            originTv.setText(originString);
        } else {
            originLabelTv.setVisibility(View.GONE);
            originTv.setVisibility(View.GONE);
        }

        TextView descriptionTv = findViewById(R.id.description_tv);
        descriptionTv.setText(sandwich.getDescription());

        TextView ingredientsTv = findViewById(R.id.ingredients_tv);
        List<String> ingredientsList = sandwich.getIngredients();
        if (ingredientsList != null) {
            for (String ingredients: ingredientsList) {
                ingredientsTv.append(ingredients + "\n");
            }
        }

    }
}
