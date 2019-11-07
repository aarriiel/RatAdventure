package com.aarriiel.game.Screen;

import com.aarriiel.game.RatAdventure;
import com.aarriiel.game.UI.Font;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class gameOverScreen implements Screen {

    private Viewport viewport;
    private Stage stage;
    private Game game;

    public gameOverScreen(Game game){
        this.game = game;
        viewport = new FitViewport(RatAdventure.V_WIDTH,RatAdventure.V_HEIGHT,new OrthographicCamera());
        stage = new Stage(viewport,RatAdventure.getBatch());

        Label.LabelStyle font = new Label.LabelStyle(Font.REGULAR, Color.WHITE);

        Table table = new Table();
        table.center();
        table.setFillParent(true);

        Label gameOverLabel = new Label("Game Over",font);
        Label playAgainLabel = new Label("Click to Back the Main Menu",font);
        gameOverLabel.setFontScale(2.5f);
        playAgainLabel.setFontScale(2.5f);
        table.add(gameOverLabel).expandX();
        table.row();
        table.add(playAgainLabel).expandX().padTop(10);
        stage.addActor(table);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if(Gdx.input.justTouched()){
            game.setScreen(new mainScreen((RatAdventure) game));
            dispose();
        }
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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
        stage.dispose();
    }
}
