package view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import controller.ProfileMenuController;
import model.User;

public class ProfileMenu implements Screen {
    private Stage stage;
    private MainGame game;
    private Skin skin;

    public ProfileMenu(MainGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        Viewport viewport = new ExtendViewport(1280, 720);
        stage = new Stage(viewport);
        skin = new Skin(Gdx.files.internal("skin/MainMenu/freezing-ui.json"));
        Gdx.input.setInputProcessor(stage);
        Table root = new Table();
        root.setFillParent(true);
        stage.addActor(root);
        TextField changeUsername = new TextField("", skin);
        TextField changePassword = new TextField("", skin);
        changePassword.setMessageText("New Password");
        changeUsername.setMessageText("New Username");
        TextButton changePassButton = new TextButton("Change Password", skin);
        TextButton changeUsernameButton = new TextButton("Change Username", skin);
        Button backButton = new Button(skin);
        backButton.add(new Image(new Texture(Gdx.files.internal("Menu/back.png"))));
        changeUsernameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ProfileMenuController.getINSTANCE().changeUsername(changeUsername.getText(), User.getLoggedInUser()).show(stage);
            }
        });
        changePassButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ProfileMenuController.getINSTANCE().changePassword(changeUsername.getText(), User.getLoggedInUser()).show(stage);
            }
        });
        TextButton goToAvatarMenu = new TextButton("Go To Avatar Menu", skin);
        goToAvatarMenu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                game.setScreen(new AvatarMenu(game));
            }
        });
        Button backToLoginMenu = new Button(skin);
        backToLoginMenu.add(new Image(new Texture(Gdx.files.internal("Menu/exit.png"))));
        backToLoginMenu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                User.setLoggedInUser(null);
                dispose();
                game.setScreen(new LoginMenu(game));
            }
        });
        TextButton deleteAccountButton = new TextButton("Delete Account", skin);
        deleteAccountButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Dialog dialog = new Dialog("Delete Account", skin);
                dialog.text("Are you sure you want to delete your account?");
                TextButton Yes = new TextButton("Yes", skin);
                dialog.add(Yes); // Add a "Yes" button
                dialog.show(stage).setPosition(100, 100);
                Yes.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        {
                            User.getUsers().remove(User.getLoggedInUser());
                            User.setLoggedInUser(null);
                            User.updateUsers();
                            dispose();
                            game.setScreen(new LoginMenu(game));
                        }
                    }
                });
                dialog.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        {
                            dialog.hide(); // Close the dialog after handling the button click
                        }
                    }
                });

            }
        });
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                game.setScreen(new MainMenu(game));
            }
        });
        deleteAccountButton.setColor(Color.RED);
        goToAvatarMenu.setColor(Color.PINK);
        root.add(changeUsername).width(720).height(50).row();
        root.add(changePassword).width(720).height(50).row();
        root.add(changePassButton).row();
        root.add(changeUsernameButton).row();
        root.add(goToAvatarMenu).row();
        root.add(backToLoginMenu).size(100, 100).row();
        root.add(deleteAccountButton).height(100).row();
        root.add(backButton).size(50, 50);
    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(Color.BLACK);
        game.batch.begin();
        game.batch.draw(new Texture(Gdx.files.internal("Backgrounds/back3.png")), 0, 0,
                Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        game.batch.end();
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int i, int i1) {
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
