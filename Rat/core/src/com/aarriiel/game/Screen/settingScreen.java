package com.aarriiel.game.Screen;

import com.aarriiel.game.RatAdventure;
import com.aarriiel.game.UI.Font;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class settingScreen implements Screen {
    private RatAdventure game;
    private TextureRegionDrawable background;
    private Stage stage;
    private Label[] allLabel;
    private String[] labelName;
    private int currentItem;
    private Viewport gamePort;

    public settingScreen(RatAdventure game){
        this.game = game;
        gamePort = new FitViewport(RatAdventure.V_WIDTH,RatAdventure.V_HEIGHT,new OrthographicCamera());
        stage = new Stage(gamePort,RatAdventure.getBatch());

        background = new TextureRegionDrawable(new TextureRegion(new Texture("main_background.jpg")));
        stage = new Stage(gamePort,RatAdventure.getBatch());
        Table table = new Table();
        table.center();
        table.setFillParent(true);

        Label.LabelStyle font = new Label.LabelStyle(Font.REGULAR, Color.WHITE);
        labelName = new String[]{"Setting","Setting","Setting","Back Main Menu"};
        allLabel = new Label[labelName.length];
        for (int i = 0; i < labelName.length; i++) {
            allLabel[i] = new Label(labelName[i], font);
            allLabel[i].setFontScale(2.5f);
            table.add(allLabel[i]).padTop(8f).row();
        }
        table.setBackground(background);

        Table tableBot = new Table();
        tableBot.bottom().padBottom(5);
        tableBot.setFillParent(true);
        Label copyRight = new Label("Copyright © 2019 Ariel Hsu / Chi Dai. All rights reserved",font);
        //copyRight.setAlignment(Align.center);
        copyRight.setFontScale(1.5f);
        tableBot.add(copyRight).padTop(10);

        stage.addActor(table);
        stage.addActor(tableBot);

    }
    public void handleInput(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            if (currentItem > 0)
                currentItem--;
            else
                currentItem =3;
        }
        else if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            if (currentItem < labelName.length - 1)
                currentItem++;
            else
                currentItem = 0;
        }
        else if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            select();
        }
    }

    public void select() {
        switch (currentItem) {
            case 0:
                break;
            case 1:
                break;
            case 2: ;
                break;
            case 3:
                game.setScreen(new mainScreen(game));
                break;
            default:
                Gdx.app.log("Main menu", "Unknown selected itemData!");
                break;
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        //clear the game screen with black
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        handleInput(delta);

        for (int i = 0; i < allLabel.length; i++) {
            if (i == currentItem) allLabel[i].setColor(Color.RED);
            else allLabel[i].setColor(Color.WHITE);
        }

        //draw the stage
        RatAdventure.getBatch().setProjectionMatrix(stage.getCamera().combined);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
