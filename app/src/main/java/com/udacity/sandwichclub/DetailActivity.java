package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.w3c.dom.Text;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private TextView mPlaceOfOriginLabelTextView;
    private TextView mPlaceOfOriginTextView;
    private TextView mAlsoKnownAsLabelTextView;
    private TextView mAlsoKnownAsTextView;
    private TextView mIngredientsLabelTextView;
    private TextView mIngredientsTextView;
    private TextView mDescriptionLabelTextView;
    private TextView mDescriptionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        mPlaceOfOriginLabelTextView = (TextView) findViewById(R.id.detail_place_of_origin_label);
        mPlaceOfOriginTextView = (TextView) findViewById(R.id.origin_tv);
        mAlsoKnownAsLabelTextView = (TextView) findViewById(R.id.detail_also_known_as_label);
        mAlsoKnownAsTextView = (TextView) findViewById(R.id.also_known_tv);
        mIngredientsLabelTextView = (TextView) findViewById(R.id.detail_ingredients_label);
        mIngredientsTextView = (TextView) findViewById(R.id.ingredients_tv);
        mDescriptionLabelTextView = (TextView) findViewById(R.id.detail_description_label);
        mDescriptionTextView = (TextView) findViewById(R.id.description_tv);

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

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void displayOrigin(Sandwich sandwich){
        if(sandwich.getPlaceOfOrigin() != null && !sandwich.getPlaceOfOrigin().equals("")) {
            mPlaceOfOriginTextView.setText(sandwich.getPlaceOfOrigin());
            mPlaceOfOriginTextView.setVisibility(View.VISIBLE);
            mPlaceOfOriginLabelTextView.setVisibility(View.VISIBLE);
        } else {
            mPlaceOfOriginTextView.setVisibility(View.GONE);
            mPlaceOfOriginLabelTextView.setVisibility(View.GONE);
        }
    }

    private void displayAlsoKnownAs(Sandwich sandwich){
        if(sandwich.getAlsoKnownAs().size() != 0) {
            for(int i = 0; i < sandwich.getAlsoKnownAs().size(); i++){
                if(i == sandwich.getAlsoKnownAs().size() - 1)
                    mAlsoKnownAsTextView.append("- " + sandwich.getAlsoKnownAs().get(i));
                else
                    mAlsoKnownAsTextView.append("- " + sandwich.getAlsoKnownAs().get(i) + "\n");
            }
            mAlsoKnownAsTextView.setVisibility(View.VISIBLE);
            mAlsoKnownAsLabelTextView.setVisibility(View.VISIBLE);
        } else {
            mAlsoKnownAsTextView.setVisibility(View.GONE);
            mAlsoKnownAsLabelTextView.setVisibility(View.GONE);
        }
    }

    private void displayIngredients(Sandwich sandwich){
        if(sandwich.getIngredients().size() != 0) {
            for(int i = 0; i < sandwich.getIngredients().size(); i++){
                if(i == sandwich.getIngredients().size() - 1)
                    mIngredientsTextView.append("- " + sandwich.getIngredients().get(i));
                else
                    mIngredientsTextView.append("- " + sandwich.getIngredients().get(i) + "\n");
            }
            mIngredientsTextView.setVisibility(View.VISIBLE);
            mIngredientsLabelTextView.setVisibility(View.VISIBLE);
        } else {
            mIngredientsTextView.setVisibility(View.GONE);
            mIngredientsLabelTextView.setVisibility(View.GONE);
        }
    }

    private void displayDescription(Sandwich sandwich){
        if(sandwich.getDescription() != null) {
            mDescriptionTextView.setText(sandwich.getDescription());
            mDescriptionTextView.setVisibility(View.VISIBLE);
            mDescriptionLabelTextView.setVisibility(View.VISIBLE);
        } else {
            mDescriptionTextView.setVisibility(View.GONE);
            mDescriptionLabelTextView.setVisibility(View.GONE);
        }
    }

    private void populateUI(Sandwich sandwich) {
        displayOrigin(sandwich);
        displayAlsoKnownAs(sandwich);
        displayIngredients(sandwich);
        displayDescription(sandwich);
    }
}
