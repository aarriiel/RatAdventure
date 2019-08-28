package com.aarriiel.game.Sprite.Player;

import com.aarriiel.game.RatAdventure;
import com.aarriiel.game.Scene.Bar;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Arthur extends Sprite {
    public enum State{RUNNING,STANDING,ATTACKING};
    public State currentState;
    public State previousState;
    public World world;
    public Body b2body;
    public Stage stage;

    private float stateTimer;
    //private TextureRegion arthurFall;
    public boolean arthurIsAttack;
    private boolean runningRight;
    private Animation arthurRun;
    private Animation arthurStand;
    private Animation arthurAttack;

    private static int health = 100;
    private static final int fullhealth = 100;
    private int power = 6;
    private float powerTimer = 0;

    //Arthur's bar
    private Table barTable;
    private final Bar healthBar;
    private final Bar firstBar;
    private final Bar secondBar;
    private final Bar thirdBar;

    private TextureRegion full;
    private TextureRegion reload;
    private TextureRegion used;


    private Viewport gamePort;

    public Arthur(World world, Viewport gamePort){
        this.world = world;
        this.gamePort = gamePort;
        currentState = State.STANDING;
        previousState = State.STANDING;
        Array<TextureRegion> frames = new Array<TextureRegion>();
        //arthurFall = new TextureRegion(RatAdventure.getAtlas().findRegion("arthur_stand"),80,0,80,64);
        for(int i=0;i<13;i++)
            frames.add(new TextureRegion(RatAdventure.getArthurAtlas().findRegion("arthur_attack"),i*80,0,80,64));
        arthurAttack = new Animation(0.08f,frames);
        frames.clear();
        for(int i=0;i<8;i++)
            frames.add(new TextureRegion(RatAdventure.getArthurAtlas().findRegion("arthur_run"),i*80,0,80,64));
        arthurRun = new Animation(0.1f,frames);
        frames.clear();
        for(int i=0;i<5;i++)
            frames.add(new TextureRegion(RatAdventure.getArthurAtlas().findRegion("arthur_stand"),i*80,0,80,64));
        arthurStand = new Animation(0.1f,frames);
        frames.clear();
        runningRight = true;
        arthurIsAttack = false;
        stateTimer = 0;
        defineArthur();
        setBounds(RatAdventure.V_WIDTH,RatAdventure.V_HEIGHT,80f/RatAdventure.PPM,64f/RatAdventure.PPM);
        setScale(2);
        setRegion(getFrame(0));

        stage = new Stage();

        full = new TextureRegion(new Texture("hud/power0.png"));
        reload = new TextureRegion(new Texture("hud/power1.png"));
        used = new TextureRegion(new Texture("hud/power2.png"));

        //Table is 1280*720 and the game is 1920*1080, so have to *2/3
        healthBar = new Bar(new TextureRegion(new Texture("hud/blood.png")),100);
        firstBar = new Bar(full,28);
        secondBar = new Bar(full,28);
        thirdBar = new Bar(full,28);
        barTable = new Table();
        barTable.bottom().left();
        barTable.setFillParent(true);
        barTable.setPosition(((b2body.getPosition().x)*RatAdventure.PPM-72)*2/3,((b2body.getPosition().y -getHeight())*RatAdventure.PPM-16)*2/3);


        barTable.add(healthBar).row();
        barTable.add(firstBar).padTop(2);
        barTable.add(secondBar).padTop(2).padLeft(35);
        barTable.add(thirdBar).padTop(2).padLeft(34);

        stage.addActor(barTable);
    }

    public void update(float dt){
        healthBar.update(health, fullhealth);
        setRegion(getFrame(dt));
        if(b2body.getPosition().x< gamePort.getWorldWidth()/2)
            barTable.setPosition(((b2body.getPosition().x )*RatAdventure.PPM-72)*2/3,((b2body.getPosition().y -getHeight())*RatAdventure.PPM-20)*2/3);
        else
            barTable.setPosition(592,((b2body.getPosition().y -getHeight())*RatAdventure.PPM-20)*2/3);
        if(runningRight)
            setPosition(b2body.getPosition().x - 64 / RatAdventure.PPM, b2body.getPosition().y - getHeight());
        else if(!runningRight)
            setPosition(b2body.getPosition().x - 96 / RatAdventure.PPM, b2body.getPosition().y - getHeight());
        if(power!=6){
            powerTimer+=dt;
            if(powerTimer>2){
                powerTimer = 0;
                power=power+1;
                calThePower();
            }
        }
    }

    public void calThePower(){
        switch (power){
            case 0:
                firstBar.changeStatus(used);
                secondBar.changeStatus(used);
                thirdBar.changeStatus(used);
                break;
            case 1:
                firstBar.changeStatus(reload);
                secondBar.changeStatus(used);
                thirdBar.changeStatus(used);
                break;
            case 2:
                firstBar.changeStatus(full);
                secondBar.changeStatus(used);
                thirdBar.changeStatus(used);
                break;
            case 3:
                firstBar.changeStatus(full);
                secondBar.changeStatus(reload);
                thirdBar.changeStatus(used);
                break;
            case 4:
                firstBar.changeStatus(full);
                secondBar.changeStatus(full);
                thirdBar.changeStatus(used);
                break;
            case 5:
                firstBar.changeStatus(full);
                secondBar.changeStatus(full);
                thirdBar.changeStatus(reload);
                break;
            case 6:
                firstBar.changeStatus(full);
                secondBar.changeStatus(full);
                thirdBar.changeStatus(full);
                break;
                default:
        }
    }
    public void minusThePower(){
        power = power-2;
    }

    public int getPower(){
        return power;
    }

    public TextureRegion getFrame(float dt){
        currentState = getState();
        TextureRegion region;
        switch (currentState){
            case ATTACKING:
                region = (TextureRegion) arthurAttack.getKeyFrame(stateTimer);
                if(arthurAttack.isAnimationFinished(stateTimer)) {
                    arthurIsAttack = false;
                }
                break;
            case RUNNING:
                region = (TextureRegion) arthurRun.getKeyFrame(stateTimer,true);
                break;
            default:
                region = (TextureRegion) arthurStand.getKeyFrame(stateTimer,true);
        }
        if((b2body.getLinearVelocity().x<0||!runningRight)&& region.isFlipX()){
            region.flip(true,false);
            runningRight = false;
        }
        else if((b2body.getLinearVelocity().x>0||runningRight)&& !region.isFlipX()){
            region.flip(true,false);
            runningRight = true;
        }
        stateTimer = currentState==previousState ? stateTimer+dt:0;
        previousState = currentState;
        return region;
    }

    public State getState(){
        if(arthurIsAttack)
            return State.ATTACKING;
        /*if(b2body.getLinearVelocity().y<0)
            return State.FALLING;*/
        else if(b2body.getLinearVelocity().x!=0)
            return State.RUNNING;
        else
            return State.STANDING;
    }

    public void defineArthur(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(384/RatAdventure.PPM,384/RatAdventure.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(32/RatAdventure.PPM,56/RatAdventure.PPM);
        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);

    }
}
