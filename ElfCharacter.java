/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygame;

import com.jme3.anim.AnimComposer;
import com.jme3.anim.ArmatureMask;
import com.jme3.anim.SkinningControl;
import com.jme3.anim.tween.Tween;
import com.jme3.anim.tween.Tweens;
import com.jme3.anim.tween.action.Action;
import com.jme3.app.SimpleApplication;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

/**
 *
 * @author ryan
 */
public class ElfCharacter {
    
    
    public Spatial characterSpatial;
    public SimpleApplication app;
    
    public AnimComposer animComposer;
    public SkinningControl skinningControl;
    
    public ArmatureMask lowerBodyMask, upperBodyMask;

    public ElfCharacter(SimpleApplication app) {
        this.app = app;
        
        characterSpatial = app.getAssetManager().loadModel("Models/baseFemaleSpatial_0.j3o");
        

        app.getRootNode().attachChild(characterSpatial);
        
        characterSpatial.lookAt(app.getCamera().getLocation(), Vector3f.UNIT_Y);
        
        animComposer = characterSpatial.getControl(AnimComposer.class);  
        skinningControl = characterSpatial.getControl(SkinningControl.class);  
        
       //lower body mask setup
        lowerBodyMask = new ArmatureMask(); 
        lowerBodyMask.addFromJoint(skinningControl.getArmature(), "thigh_l");
        lowerBodyMask.addFromJoint(skinningControl.getArmature(), "thigh_r");
        lowerBodyMask.addBones(skinningControl.getArmature(), "Root", "pelvis");
        animComposer.makeLayer("lowerBody", lowerBodyMask);
        
        
      //upper body mask setup
        upperBodyMask = new ArmatureMask();  
        upperBodyMask.addFromJoint(skinningControl.getArmature(), "spine_01");        
        animComposer.makeLayer("upperBody", upperBodyMask);
        
        animComposer.setCurrentAction("ladyWalk", "upperBody"); 
        animComposer.setCurrentAction("ladyWalk", "lowerBody"); 

        
    }
    
    
    
    public void playAnimOnUpperBody(){
         
   
    //         animComposer.setCurrentAction("heavySwing", "upperBody");  // <- this line alone works without bugging out the lowerBody, but causes the animation to loop because it is not using a sequence with a doneTween to end it 
        
         
         Tween doneTween = Tweens.callMethod(this, "stopUpperBodyAnimation");        
        Action singleAction = animComposer.action("heavySwing");
        
        Action sequencedAction = animComposer.actionSequence("sequencedAction", singleAction, doneTween);
        
        animComposer.setCurrentAction("sequencedAction", "upperBody");
        
    }
    
    public void stopUpperBodyAnimation(){
        
        animComposer.removeCurrentAction("upperBody");
        
        animComposer.setCurrentAction("ladyWalk", "upperBody");
    }
    

    
    
    
    
}
