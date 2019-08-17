package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich = new Sandwich();

        try {
            JSONObject sandwichJSON = new JSONObject(json);
            JSONObject name = sandwichJSON.getJSONObject("name");
            sandwich.setAlsoKnownAs(getAlsoKnownAs(name));
            sandwich.setDescription(getDescription(sandwichJSON));
            sandwich.setImage(getImage(sandwichJSON));
            sandwich.setIngredients(getIngredients(sandwichJSON));
            sandwich.setMainName(getMainName(name));
            sandwich.setPlaceOfOrigin(getPlaceOfOrigin(sandwichJSON));
        } catch (JSONException e){
            e.printStackTrace();
        }

        return sandwich;
    }

    private static String getMainName(JSONObject name) throws JSONException {
        return name.getString("mainName");
    }

    private static List<String> getAlsoKnownAs(JSONObject name) throws JSONException {
        JSONArray alsoKnownAsJSONArray = name.getJSONArray("alsoKnownAs");
        List<String> alsoKnownAs = new ArrayList<>();
        for(int i = 0; i < alsoKnownAsJSONArray.length(); i++)
            alsoKnownAs.add(alsoKnownAsJSONArray.getString(i));
        return alsoKnownAs;
    }

    private static String getPlaceOfOrigin(JSONObject sandwichJSON) throws JSONException {
        return sandwichJSON.getString("placeOfOrigin");
    }

    private static String getDescription(JSONObject sandwichJSON) throws JSONException {
        return sandwichJSON.getString("description");
    }

    private static String getImage(JSONObject sanwichJSON) throws JSONException {
        return sanwichJSON.getString("image");
    }

    private static List<String> getIngredients(JSONObject sanwichJSON) throws JSONException {
        JSONArray ingredientsJSONArray = sanwichJSON.getJSONArray("ingredients");
        List<String> ingredients = new ArrayList<>();
        for(int i = 0; i < ingredientsJSONArray.length(); i++)
            ingredients.add(ingredientsJSONArray.getString(i));
        return ingredients;
    }
}
