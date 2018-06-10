/*
 *  Copyright 2018 Soojeong Shin
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.udacity.sandwichclub;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

    /** TextViews for showing Sandwich details */
    private TextView mAlsoKnownTv;
    private TextView mOriginTv;
    private TextView mDescriptionTv;
    private TextView mIngredientsTv;

    /** TextViews for showing the label */
    private TextView mAlsoKnownLabelTv;
    private TextView mOriginLabelTv;
    private TextView mDescriptionLabelTv;
    private TextView mIngredientsLabelTv;

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

        // Initializes views
        initViews();
        // Sets typeface
        setTypeface();
        // Shows Sandwich details in each TextView
        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        ((CollapsingToolbarLayout) findViewById(R.id.collapsing_toobar_layout)).setTitle(sandwich.getMainName());

        // Show back button in Collapsing Toolbar
        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Called from onCreate to initialize the views
     */
    private void initViews() {
        // Get a reference to the also known TextView and also known label TextView
        mAlsoKnownTv = findViewById(R.id.also_known_tv);
        mAlsoKnownLabelTv = findViewById(R.id.also_known_label_tv);

        // Get a reference to the origin TextView and origin label TextView
        mOriginTv = findViewById(R.id.origin_tv);
        mOriginLabelTv = findViewById(R.id.origin_label_tv);

        // Get a reference to the description TextView and description label TextView
        mDescriptionTv = findViewById(R.id.description_tv);
        mDescriptionLabelTv = findViewById(R.id.description_label_tv);

        // Get a reference to the ingredients TextView and ingredients label TextView
        mIngredientsTv = findViewById(R.id.ingredients_tv);
        mIngredientsLabelTv = findViewById(R.id.ingredients_label_tv);
    }

    /**
     * Called from onCreate to show sandwich details. Get Sandwich details and set the text to the TextView.
     * @param sandwich Sandwich object
     */
    private void populateUI(Sandwich sandwich) {
        // Get alsoKnownAs list of Strings
        List<String> alsoKnownList = sandwich.getAlsoKnownAs();
        // If alsoKnownList is not null, append each String from the alsoKnownList to the TextView,
        // otherwise hide the TextView
        if (alsoKnownList != null) {
            for (String alsoKnown: alsoKnownList) {
                mAlsoKnownTv.append(alsoKnown + getString(R.string.new_line));
            }
        } else {
            mAlsoKnownLabelTv.setVisibility(View.GONE);
            mAlsoKnownTv.setVisibility(View.GONE);
        }

        // Get place of origin string
        String originString = sandwich.getPlaceOfOrigin();
        // If origin string is not null, set the text to the origin TextView, otherwise hide the TextView
        if (originString != null) {
            mOriginTv.setText(originString);
        } else {
            mOriginLabelTv.setVisibility(View.GONE);
            mOriginTv.setVisibility(View.GONE);
        }

        // Set the Description String to the description TextView
        mDescriptionTv.setText(sandwich.getDescription());

        // Get ingredients list of Strings
        List<String> ingredientsList = sandwich.getIngredients();
        // If ingredientsList is not null, append each String from the ingredientsList to the TextView
        if (ingredientsList != null) {
            for (String ingredients: ingredientsList) {
                mIngredientsTv.append(ingredients + getString(R.string.new_line));
            }
        }

    }

    /**
     * Sets the typeface for the TextView
     */
    private void setTypeface() {
        // Retrieves fonts by calling getFont method
        Typeface raleway = ResourcesCompat.getFont(this, R.font.raleway_regular);
        Typeface righteous = ResourcesCompat.getFont(this, R.font.righteous_regular);

        // Sets the typeface for the TextViews
        mAlsoKnownTv.setTypeface(raleway);
        mOriginTv.setTypeface(raleway);
        mIngredientsTv.setTypeface(raleway);
        mDescriptionTv.setTypeface(raleway);

        mAlsoKnownLabelTv.setTypeface(righteous);
        mOriginLabelTv.setTypeface(righteous);
        mIngredientsLabelTv.setTypeface(righteous);
        mDescriptionLabelTv.setTypeface(righteous);
    }

    /**
     * When the arrow icon in the app bar is clicked, finishes DetailActivity.
     */
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
