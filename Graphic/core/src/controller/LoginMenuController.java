package controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import model.User;

public class LoginMenuController {
    private static LoginMenuController controller;

    private LoginMenuController() {

    }

    public static LoginMenuController INSTANCE() {
        if (controller == null) controller = new LoginMenuController();
        return controller;
    }

    public Dialog Login(String username, String password) {
        User user = User.getUserByUsername(username);
        Skin skin = new Skin(Gdx.files.internal("skin/MainMenu/freezing-ui.json"));
        Dialog dialog = new Dialog("", skin);
        dialog.setColor(Color.RED);
        String message;
        if (user == null) message = "No Such User!!!";
        else if (!user.getPassword().equals(password)) message = "Wrong Password :(";
        else {
            User.setLoggedInUser(user);
            message = "Welcome :D";
            dialog.setColor(Color.GREEN);
        }
        dialog.text(message);
        if (!dialog.getColor().equals(Color.GREEN))
        {
            TextButton closeButton = new TextButton("Close", skin);
            dialog.button(closeButton);
        }
        return dialog;
    }

    public Dialog register(String username, String password) {
        User user = User.getUserByUsername(username);
        Skin skin = new Skin(Gdx.files.internal("skin/MainMenu/freezing-ui.json"));
        Dialog dialog = new Dialog("", skin);
        dialog.setColor(Color.RED);
        String message;
        if (user != null) message = "User already exists!!!";
        else if (password.length() < 8) message = "Short Password :(";
        else {
            User.addUser(new User(username, password));
            message = "Registered Successfully :D";
            dialog.setColor(Color.GREEN);
        }
        dialog.text(message);
        TextButton closeButton = new TextButton("Ok", skin);
        dialog.button(closeButton);
        return dialog;
    }
}
