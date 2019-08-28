package com.aarriiel.game.UI;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class Font {

    public static final BitmapFont HEADER;
    public static final BitmapFont REGULAR;

    static {
        // Initialize Header font.
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Font/setofont.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        FreeTypeFontGenerator.setMaxTextureSize(2048);
        parameter.size = 20;
        parameter.shadowColor = Color.BLACK;
        parameter.shadowOffsetY = 1;
        parameter.characters = FreeTypeFontGenerator.DEFAULT_CHARS+ "是吱亞瑟嗎我們的命運就交給你了加油在哪裡剛剛誰聲音先四處看吧";
        HEADER = generator.generateFont(parameter);
        //generator.dispose();

        // Initialize Regular font.
        //generator = new FreeTypeFontGenerator(Gdx.files.internal("Font/HonyaJi-Re.ttf"));
        //parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        //FreeTypeFontGenerator.setMaxTextureSize(2048);
        parameter.size = 16;
        parameter.shadowColor = Color.BLACK;
        parameter.shadowOffsetY = 1;
        REGULAR = generator.generateFont(parameter);
        generator.dispose();

    }

}