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

package com.udacity.sandwichclub.utils;

import android.text.TextUtils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    /** Tag for the log message */
    private static final String TAG = JsonUtils.class.getSimpleName();

    /** Strings for the key associated with the JSON */
    private static final String KEY_NAME = "name";
    private static final String KEY_MAIN_NAME = "mainName";
    private static final String KEY_ALSO_KNOWN_AS = "alsoKnownAs";
    private static final String KEY_PLACE_OF_ORIGIN = "placeOfOrigin";
    private static final String KEY_DESCRIPTION ="description";
    private static final String KEY_IMAGE = "image";
    private static final String KEY_INGREDIENTS = "ingredients";

    /**
     * Return a {@link Sandwich} Object that can be used to populate the UI by parsing the JSON
     */
    public static Sandwich parseSandwichJson(String json) {
        // If the JSON String is empty or null, then return early
        if (TextUtils.isEmpty(json)) {
            return null;
        }

        // Try to parse the JSON string. If there's a problem with the way the JSON is formatted,
        // a JSONException exception object will be thrown.
        try {
            // Create a JSONObject from the JSON string
            JSONObject baseJson = new JSONObject(json);

            // Extract the JSONObject associated with the key called "name"
            JSONObject name = baseJson.getJSONObject(KEY_NAME);

            // For a given name, extract the value fro the key called "mainName"
            String mainName = name.getString(KEY_MAIN_NAME);

            // For a given name, extract the JSONArray associated with the key called "alsoKnownAs"
            JSONArray alsoKnownAsArray = name.getJSONArray(KEY_ALSO_KNOWN_AS);

            // Create an empty ArrayList
            List<String> alsoKnownAs = new ArrayList<>();
            // If the JSONArray is not empty, add each element in the JSONArray to the ArrayList
            if (alsoKnownAsArray.length() != 0) {
                for (int i = 0; i < alsoKnownAsArray.length(); i++) {
                    alsoKnownAs.add(alsoKnownAsArray.get(i).toString());
                }
            } else {
                alsoKnownAs = null;
            }

            // Extract the value for the key called "placeOfOrigin"
            String placeOfOrigin = baseJson.getString(KEY_PLACE_OF_ORIGIN);
            if (placeOfOrigin.isEmpty()) {
                placeOfOrigin = null;
            }

            // Extract the value for the key called "description"
            String description = baseJson.getString(KEY_DESCRIPTION);

            // Extract the value for the key called "image"
            String imageUrl = baseJson.getString(KEY_IMAGE);

            // Extract the JSONArray associated with the key "ingredients"
            JSONArray ingredientsArray = baseJson.getJSONArray(KEY_INGREDIENTS);

            // Create an empty ArrayList
            List<String> ingredients = new ArrayList<>();
            // If the JSONArray is not empty, add each element in the JSONArray to the ArrayList
            if (ingredientsArray.length() != 0) {
                for (int j = 0; j < ingredientsArray.length(); j++) {
                    ingredients.add(ingredientsArray.get(j).toString());
                }
            } else {
                ingredients = null;
            }

            // Return the Sandwich object
            return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, imageUrl, ingredients);

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app does not crash.
            e.printStackTrace();
            return null;
        }
    }
}
