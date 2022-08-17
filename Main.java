package com.mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.light.LightProbe;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.system.AppSettings;




public class Main extends SimpleApplication {    
    
    private float camMoveSpeed = 5;

    private DirectionalLight directionalLight;
    private AmbientLight ambientLight;
    
    private ElfCharacter elfCharacter;

    public static void main(String[] args) {
        Main app = new Main();
        AppSettings s = new AppSettings(true);
        
        s.put("FrameRate", 140);
        s.put("GammaCorrection", true);
        
                
        app.setSettings(s);
        app.start();
       
    }   
    
    private final ActionListener actionListener = new ActionListener() {
        @Override
        public void onAction(String name, boolean pressed, float tpf) {
        
            if (name.equals("playAttackOnUpperBody") && !pressed) {
                if(elfCharacter != null){
                    
                    elfCharacter.playAnimOnUpperBody();
                    
                }
                
            }
        }
    };
    

    @Override
    public void simpleInitApp() { 
        
        elfCharacter = new ElfCharacter(this);
        
        setUpCamera();
        setUpLights();
        setupKeys();
        

    }
 
    
    private void setupKeys() {
        flyCam.setMoveSpeed(50);
        inputManager.addMapping("playAttackOnUpperBody", new KeyTrigger(KeyInput.KEY_N));
        
        inputManager.addListener(actionListener, "playAttackOnUpperBody");   
        

        
    }
    
    @Override
    public void simpleUpdate(float tpf) {
        super.simpleUpdate(tpf);

        
        
    }

    private void setUpLights() {
        
        LightProbe probe = (LightProbe) assetManager.loadAsset("Scenes/LightProbes/quarry_Probe.j3o");
        
        probe.setAreaType(LightProbe.AreaType.Spherical);      
        probe.getArea().setRadius(2000);
        probe.getArea().setCenter(new Vector3f(0, 0, 0));        
        rootNode.addLight(probe);
        
        directionalLight = new DirectionalLight();
        directionalLight.setDirection((new Vector3f(-0.3f, -0.5f, -0.3f)).normalize());
        directionalLight.setColor(ColorRGBA.White);
        rootNode.addLight(directionalLight);
        
        ambientLight = new AmbientLight();
        directionalLight.setColor(ColorRGBA.White);
        rootNode.addLight(ambientLight);        
    }

    private void setUpCamera() {
        cam.setLocation(new Vector3f(14, 4, 0));
        cam.lookAtDirection(new Vector3f(-1, 0f, 0).normalizeLocal(), Vector3f.UNIT_Y);
        
        getFlyByCamera().setMoveSpeed(camMoveSpeed);        
    }    
    
    

    
}


