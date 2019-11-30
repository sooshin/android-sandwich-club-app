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
import com.google.android.material.appbar.CollapsingToolbarLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    /** TextView for showing also known as */
    @BindView(R.id.also_known_tv) TextView mAlsoKnownTv;
    /** TextView for showing place of origin of the Sandwich */
    @BindView(R.id.origin_tv) TextView mOriginTv;
    /** TextView for showing description of the Sandwich */
    @BindView(R.id.description_tv) TextView mDescriptionTv;
    /** TextView for showing ingredients of the Sandwich */
    @BindView(R.id.ingredients_tv) TextView mIngredientsTv;

    /** TextView for also known as label */
    @BindView(R.id.also_known_label_tv) TextView mAlsoKnownLabelTv;
    /** TextView for place of origin label */
    @BindView(R.id.origin_label_tv) TextView mOriginLabelTv;
    /** TextView for description label */
    @BindView(R.id.description_label_tv) TextView mDescriptionLabelTv;
    /** TextView for ingredients label */
    @BindView(R.id.ingredients_label_tv) TextView mIngredientsLabelTv;

    /** ImageView for Sandwich image */
    @BindView(R.id.image_iv) ImageView mIngredientsIv;

    /** Collapsing Toolbar Layout */
    @BindView(R.id.collapsing_toolbar_layout) CollapsingToolbarLayout mCollapsingToolbar;

    /** Toolbar */
    @BindView(R.id.app_bar) Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Bind the view using ButterKnife
        ButterKnife.bind(this);

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

        // Sets typeface
        setTypeface();
        // Shows Sandwich details in each TextView
        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(mIngredientsIv);

        mCollapsingToolbar.setTitle(sandwich.getMainName());

        // Show back button in Collapsing Toolbar
        setSupportActionBar(mToolbar);
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
     * Called from onCreate to show sandwich details. Get Sandwich details and set the text to the TextView.
     * @param sandwich Sandwich object
     */
    private void populateUI(Sandwich sandwich) {
        // Get alsoKnownAs list of Strings
        List<String> alsoKnownList = sandwich.getAlsoKnownAs();
        if (alsoKnownList != null) {
            // If alsoKnownList is not null, use TextUtils.join method that returns a string containing
            // the tokens joined by delimiters.
            String alsoKnown = TextUtils.join(getString(R.string.new_line), alsoKnownList);
            // Set also known as String to the TextView
            mAlsoKnownTv.setText(alsoKnown);
        } else {
            // If the alsoKnownList is null, show message so that the user can be aware of
            // the information availability
            mAlsoKnownTv.setText(getString(R.string.message_not_available));
        }

        // Get place of origin string
        String originString = sandwich.getPlaceOfOrigin();
        // If origin string is not null, set the text to the origin TextView, otherwise hide the TextView
        if (originString != null) {
            mOriginTv.setText(originString);
        } else {
            // If the alsoKnownList is null, show message so that the user can be aware of
            // the information availability
            mOriginTv.setText(getString(R.string.message_not_available));
        }

        // Set the Description String to the description TextView
        mDescriptionTv.setText(sandwich.getDescription());

        // Get ingredients list of Strings
        List<String> ingredientsList = sandwich.getIngredients();
        if (ingredientsList != null) {
            // If ingredientsList is not null, use TextUtils.join method that returns a string containing
            // the tokens joined by delimiters.
            String ingredients = TextUtils.join(getString(R.string.new_line), ingredientsList);
            // Set ingredients String to the TextView
            mIngredientsTv.setText(ingredients);
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
