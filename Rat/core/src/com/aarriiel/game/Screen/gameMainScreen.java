package com.aarriiel.game.Screen;


import com.aarriiel.game.RatAdventure;
import com.aarriiel.game.Scene.Dialog;
import com.aarriiel.game.Scene.DialogLog;
import com.aarriiel.game.Scene.DialogManager;
import com.aarriiel.game.Sprite.Player.Arthur;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.*;
public class gameMainScreen implements Screen {
    private RatAdventure game;


    private OrthographicCamera camera;
    private Viewport gamePort;

    //Dialog
    private DialogLog logD;
    private DialogManager dialogManager;

    //TiledMap
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    //Box2d
    private World world;
    private Box2DDebugRenderer b2dr;

    private Arthur arthur;
    private boolean isTwoJump;

    public gameMainScreen(RatAdventure game){
        this.game = game;
        camera=new OrthographicCamera();
        gamePort=new FitViewport(RatAdventure.V_WIDTH/RatAdventure.PPM,RatAdventure.V_HEIGHT/RatAdventure.PPM,camera);
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("map/test.tmx");
        renderer = new OrthogonalTiledMapRenderer(map,1/RatAdventure.PPM);
        camera.position.set(gamePort.getWorldWidth()/2,gamePort.getWorldHeight()/2,0);

        //Dialog
        logD = new DialogLog();
        logD.load("SecondDialog");
        dialogManager = new DialogManager();
        Array<Dialog> dialogs = new Array<Dialog>();
        int i=1;
        while(logD.getSize()>0){
            dialogs.add(logD.getDialog("Dialog"+String.valueOf(i)));
            logD.removeJson("Dialog"+String.valueOf(i));
            i++;
        }
        dialogManager.show(dialogs);

        world = new World(new Vector2(0,-10),true);
        b2dr = new Box2DDebugRenderer();
        arthur = new Arthur(world,gamePort);

        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        for(MapObject object: map.getLayers().get(1).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rectangle.getX()+rectangle.getWidth()/2)/RatAdventure.PPM,(rectangle.getY()+rectangle.getHeight()/2)/RatAdventure.PPM);

            body = world.createBody(bdef);
            shape.setAsBox((rectangle.getWidth()/2)/RatAdventure.PPM,(rectangle.getHeight()/2)/RatAdventure.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

    }

    public Viewport getGamePort(){
        return gamePort;
    }

    public void handleInput(float dt){
            if (Gdx.input.isKeyJustPressed(Input.Keys.C)&&arthur.getPower()>1) {
                if(arthur.arthurIsAttack1){
                    if(arthur.arthurIsAttack2)
                        arthur.arthurIsAttack3 = true;
                    else
                        arthur.arthurIsAttack2 = true;
                }
                else
                    arthur.arthurIsAttack1 = true;
                //arthur.minusThePower();
                //arthur.calThePower();
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && arthur.b2body.getLinearVelocity().y == 0) {
                arthur.b2body.applyLinearImpulse(new Vector2(0, 6f), arthur.b2body.getWorldCenter(), true);
                isTwoJump = false;
            }
            else if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && arthur.b2body.getLinearVelocity().y != 0 && !isTwoJump  ) {
                arthur.b2body.applyLinearImpulse(new Vector2(0, 4f), arthur.b2body.getWorldCenter(), true);
                isTwoJump = true;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && arthur.b2body.getLinearVelocity().x <= 2)
                arthur.b2body.applyLinearImpulse(new Vector2(0.2f, 0), arthur.b2body.getWorldCenter(), true);
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && arthur.b2body.getLinearVelocity().x >= -2)
                arthur.b2body.applyLinearImpulse(new Vector2(-0.2f, 0), arthur.b2body.getWorldCenter(), true);
    }

    public void update(float dt){
        handleInput(dt);
        world.step(1/60f,6,2);
        arthur.update(dt);
        //update our camera with correct coordinates after changes.
        if(arthur.b2body.getPosition().x>gamePort.getWorldWidth()/2)
            camera.position.x = arthur.b2body.getPosition().x;
        camera.update();
        //tell our renderer to draw only what our camera can see in our game world.
        renderer.setView(camera);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        update(delta);

        //clear the game screen with black
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //render our game map
        renderer.render();
        //render our box2ddebuglines
        b2dr.render(world,camera.combined);
        //draw the arthur
        RatAdventure.getBatch().setProjectionMatrix(camera.combined);
        RatAdventure.getBatch().begin();
        arthur.draw(RatAdventure.getBatch());
        RatAdventure.getBatch().end();

        arthur.stage.draw();

        RatAdventure.getBatch().setProjectionMatrix(dialogManager.getCamera().combined);
        dialogManager.update(delta);
        dialogManager.draw();
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width,height);
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
        map.dispose();
        renderer.dispose();
    }
}
