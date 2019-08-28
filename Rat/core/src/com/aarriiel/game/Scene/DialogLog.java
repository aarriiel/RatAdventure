package com.aarriiel.game.Scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import java.util.HashMap;

public class DialogLog {
    private JsonReader json = new JsonReader();
    private JsonValue base;
    private HashMap<String, Dialog> logDialog;

    public DialogLog(){
        logDialog = new HashMap<String, Dialog>();
    }

    public void load(String area) {
        base = json.parse(Gdx.files.internal("Json/Dialog.json"));
        int i=1;
        for (JsonValue component : base.get("Dialogs").get(area)) {
            logDialog.put("Dialog"+String.valueOf(i),new Dialog(component.getString("Name"),component.getString("Content")));
            i++;
        }
    }
    public int getSize() {
        return logDialog.size();
    }
    public Dialog getDialog(String key){
        return logDialog.get(key);
    }
    public void removeJson(String key){
        logDialog.remove(key);
    }
}
