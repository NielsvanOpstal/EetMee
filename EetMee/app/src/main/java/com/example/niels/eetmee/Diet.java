/*
EetMee
Niels van Opstal 11021519

This class contains the Diet of an user or an offer.
 */
package com.example.niels.eetmee;

import java.io.Serializable;

public class Diet implements Serializable {     public boolean vegetarian = false;
    public boolean vegan = false;
    public boolean nutAllergy = false;
    public boolean glutenAllergy = false;
    public boolean lactoseAllergy = false;
    public boolean peanutAllergy = false;
    public boolean shellfishAllergy = false;
    public boolean soyAllergy = false;

    public void setVegetarian(boolean vegetarian) {
        this.vegetarian = vegetarian;
    }

    public void setVegan(boolean vegan) {
        this.vegan = vegan;
    }

    public void setNutAllergy(boolean nutAllergy) {
        this.nutAllergy = nutAllergy;
    }

    public void setGlutenAllergy(boolean glutenAllergy) {
        this.glutenAllergy = glutenAllergy;
    }

    public void setLactoseAllergy(boolean lactoseAllergy) {
        this.lactoseAllergy = lactoseAllergy;
    }

    public void setPeanutAllergy(boolean peanutAllergy) {
        this.peanutAllergy = peanutAllergy;
    }

    public void setShellfishAllergy(boolean shellfishAllergy) {
        this.shellfishAllergy = shellfishAllergy;
    }

    public void setSoyAllergy(boolean soyAllergy) {
        this.soyAllergy = soyAllergy;
    }

    public boolean isVegetarian() {
        return vegetarian;
    }

    public boolean isVegan() {
        return vegan;
    }

    public boolean isNutAllergy() {
        return nutAllergy;
    }

    public boolean isGlutenAllergy() {
        return glutenAllergy;
    }

    public boolean isLactoseAllergy() {
        return lactoseAllergy;
    }

    public boolean isPeanutAllergy() {
        return peanutAllergy;
    }

    public boolean isShellfishAllergy() {
        return shellfishAllergy;
    }

    public boolean isSoyAllergy() {
        return soyAllergy;
    }
}
