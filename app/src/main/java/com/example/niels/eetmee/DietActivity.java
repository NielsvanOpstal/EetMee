package com.example.niels.eetmee;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import static com.example.niels.eetmee.MainActivity.MYREF;
import static com.example.niels.eetmee.MakeOfferActivity.PUSHKEY;

public class DietActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diet_activity);
    }


    public void dietadded(View view) {
//        Adds the diet details to the offer just made
        MYREF.child("offers").child(PUSHKEY).child("diet").setValue(checkCheckBoxes());

        Toast.makeText(DietActivity.this, "Aanbod gemaakt!",
                Toast.LENGTH_SHORT).show();
        startActivity(new Intent(DietActivity.this, BaseActivity.class));
    }

    private Diet checkCheckBoxes() {
        Diet diet = new Diet();
        Switch vegetarian = findViewById(R.id.OfferVegetarianSwitch);
        Switch vegan = findViewById(R.id.OfferVeganSwitch);
        Switch nuts = findViewById(R.id.OfferNutSwitch);
        Switch peanuts = findViewById(R.id.OfferPeanutSwitch);
        Switch lactose = findViewById(R.id.OfferLactoseSwitch);
        Switch gluten = findViewById(R.id.OfferGlutenSwitch);
        Switch soy = findViewById(R.id.OfferSoySwitch);
        Switch shellfish = findViewById(R.id.OfferShellfishSwitch);

        diet.vegetarian = vegetarian.isChecked();
        diet.vegan = vegan.isChecked();
        diet.nutAllergy = nuts.isChecked();
        diet.peanutAllergy = peanuts.isChecked();
        diet.lactoseAllergy = lactose.isChecked();
        diet.glutenAllergy = gluten.isChecked();
        diet.soyAllergy = soy.isChecked();
        diet.shellfishAllergy = shellfish.isChecked();
        return diet;
    }
}
