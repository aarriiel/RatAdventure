package com.aarriiel.game.Screen;


import com.aarriiel.game.RatAdventure;
import com.aarriiel.game.Scene.Dialog;
import com.aarriiel.game.Scene.DialogLog;
import com.aarriiel.game.Scene.DialogManager;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.Array;

public class storyScreen implements Screen {
    private Game game;
    private DialogLog logD;
    private DialogManager dialogManager;


    public storyScreen(Game game,String dia){
        this.game = game;
        logD = new DialogLog();
        logD.load(dia);
        dialogManager = new DialogManager();
        Array<Dialog> dialogs = new Array<Dialog>();
        int i=1;
        while(logD.getSize()>0){
            dialogs.add(logD.getDialog("Dialog"+String.valueOf(i)));
            logD.removeJson("Dialog"+String.valueOf(i));
            i++;
        }
        dialogManager.show(dialogs);
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        RatAdventure.getBatch().setProjectionMatrix(dialogManager.getCamera().combined);
        dialogManager.update(delta);
        dialogManager.draw();
        if(dialogManager.isDialogEnd()){
            game.setScreen(new gameMainScreen((RatAdventure) game));
            dispose();
        }
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
        dialogManager.dispose();
    }
}
