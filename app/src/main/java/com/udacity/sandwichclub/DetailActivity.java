package com.udacity.sandwichclub;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
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

    /** Handles Typeface */
    private Typeface mRaleway;
    private Typeface mRighteous;

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
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
     * Show sandwich details
     * @param sandwich
     */
    private void populateUI(Sandwich sandwich) {
        // Get alsoKnownAs list of Strings
        List<String> alsoKnownList = sandwich.getAlsoKnownAs();
        // If alsoKnownList is not null, append each String from the alsoKnownList to the TextView,
        // otherwise hide the TextView
        if (alsoKnownList != null) {
            for (String alsoKnown: alsoKnownList) {
                mAlsoKnownTv.append(alsoKnown + "\n");
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
                mIngredientsTv.append(ingredients + "\n");
            }
        }

    }

    /**
     * Sets Typeface
     */
    private void setTypeface() {
        mRaleway = Typeface.createFromAsset(getAssets(), "Raleway-Regular.ttf");
        mRighteous = Typeface.createFromAsset(getAssets(), "Righteous-Regular.ttf");

        mAlsoKnownTv.setTypeface(mRaleway);
        mOriginTv.setTypeface(mRaleway);
        mIngredientsTv.setTypeface(mRaleway);
        mDescriptionTv.setTypeface(mRaleway);

        mAlsoKnownLabelTv.setTypeface(mRighteous);
        mOriginLabelTv.setTypeface(mRighteous);
        mIngredientsLabelTv.setTypeface(mRighteous);
        mDescriptionLabelTv.setTypeface(mRighteous);
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
