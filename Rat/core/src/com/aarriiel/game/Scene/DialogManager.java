package com.aarriiel.game.Scene;

import com.aarriiel.game.RatAdventure;
import com.aarriiel.game.UI.Font;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class DialogManager extends Stage {

        private static final float SHOW_NEXT_CHARACTER_INTERVAL = .02f;

        private Array<Dialog> pendingDialogs;
        private Dialog currentDialog;
        private float timer;
        private boolean isEnd;

        private final Label currentSpeakerName;
        private final Label message;
        private final Table dialogTable;

        public DialogManager() {
            super(new FitViewport(RatAdventure.V_WIDTH, RatAdventure.V_HEIGHT), RatAdventure.getBatch());

            Texture background = RatAdventure.getManager().get("hud/dialog.png");

            pendingDialogs = new Array<Dialog>();

            currentSpeakerName = new Label("", new Label.LabelStyle(Font.HEADER, Color.WHITE));
            currentSpeakerName.setFontScale(2.8f);
            message = new Label("", new Label.LabelStyle(Font.REGULAR, Color.WHITE));
            message.setFontScale(2.2f);
            message.setWrap(true);

            dialogTable = new Table();
            dialogTable.top().left();
            dialogTable.setPosition(getWidth() / 2 - background.getWidth() / 2, getHeight() / 2 - 2*background.getHeight());
            dialogTable.setSize(background.getWidth(), background.getHeight());

            dialogTable.defaults().padLeft(10f).spaceBottom(3f).left();
            dialogTable.background(new TextureRegionDrawable(new TextureRegion(background)));
            dialogTable.add(currentSpeakerName).padTop(5f).spaceBottom(5f).row();
            dialogTable.add(message).width(background.getWidth());
            dialogTable.setVisible(false);

            addActor(dialogTable);
        }


        public void show(Array<Dialog> dialogMessages) {
            pendingDialogs.addAll(dialogMessages);
            dialogTable.setVisible(true);
            showNext();
        }

        private void showNext() {
            currentDialog = pendingDialogs.first();
            pendingDialogs.removeIndex(0);

            currentSpeakerName.setText(currentDialog.getSpeakerName());
            message.setText("     ");
        }

        private boolean hasDialogFinishedShowing() {
            return message.getText().length == currentDialog.getDialog().length()+5;
        }

        public void handleInput(float delta) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                if (pendingDialogs.size > 0) {
                    showNext();
                } else {
                    dialogTable.setVisible(false);
                    isEnd = true;
                }
            }
        }

        public boolean isDialogEnd(){
            return isEnd;
        }

        public void update(float delta) {
            if (dialogTable.isVisible()) {
                if (hasDialogFinishedShowing()) {
                    handleInput(delta);
                } else {
                    if (timer >= SHOW_NEXT_CHARACTER_INTERVAL) {
                        int nextCharIdx = message.getText().length-5;
                        message.setText(message.getText().toString() + currentDialog.getDialog().charAt(nextCharIdx));
                        timer = 0;
                    }
                    timer += delta;
                }
            }

            act(delta);
        }
    @Override
    public void dispose() {

    }
}
