package controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import model.User;

public class ProfileMenuController {
    public static ProfileMenuController INSTANCE = new ProfileMenuController();
    public static ProfileMenuController getINSTANCE() {
        return INSTANCE;
    }
    public Dialog changeUsername(String username, User user) {
        String message;
        Skin skin = new Skin(Gdx.files.internal("skin/MainMenu/freezing-ui.json"));
        Dialog dialog = new Dialog("", skin);
        dialog.setColor(Color.RED);
        if( User.getUserByUsername(username) != null) {
           dialog.text("Username is already used");
       }
        else {
            user.setName(username);
            User.updateUsers();
            dialog.text("Changed Successfully");
            dialog.setColor(Color.GREEN);
        }
        dialog.button("Ok");
        return dialog;
    }

    public Dialog changePassword(String password, User user) {
        String message;
        Skin skin = new Skin(Gdx.files.internal("skin/MainMenu/freezing-ui.json"));
        Dialog dialog = new Dialog("", skin);
        dialog.setColor(Color.RED);
        if(password.length() < 8) {
            dialog.text("Weak Password");
        }
        else {
            user.setName(password);
            User.updateUsers();
            dialog.text("Changed Successfully");
            dialog.setColor(Color.GREEN);
        }
        dialog.button("Ok");
        return dialog;
    }
}
