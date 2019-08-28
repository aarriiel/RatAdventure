package com.aarriiel.game.Scene;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class Bar extends HorizontalGroup {

    private Image barImage;
    private float maxLength;

    public Bar(TextureRegion bar, float maxLength) {
        this.barImage = new Image(bar);
        this.maxLength = maxLength;

        barImage.setScaleX(maxLength);
        addActor(barImage);
    }

    public void changeStatus(TextureRegion bar){
        barImage.setDrawable(new TextureRegionDrawable(new TextureRegion(bar)));
    }

    public void update(int currentValue, int fullValue) {
        //barImage.setScaleX(maxLength * currentValue / fullValue);
    }
}

