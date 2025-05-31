
/**
 * Game managment and updating functions
 * 
 * AUTHOR: Brendan Laking
 * VERSION: 2025.05.25
 */

import Tiles.*;
import java.awt.event.*;
import java.awt.*;


public class Game  {
    public Level level;
    public Camera camera;
    public Game.Keys keys;
    
    final int speed = 3;
    
    public Game() {
        level = new Level();
        
        camera = new Camera(1, 0, 0);
        
        keys = new Game.Keys();
    }
    
    public void update() {
        if(keys.W) {
            camera.y -= speed / camera.zoom + 1;
        }
        
        if(keys.S) {
            camera.y += speed / camera.zoom + 1;
        }
        
        if(keys.A) {
            camera.x -= speed / camera.zoom + 1;
        }
        
        if(keys.D) {
            camera.x += speed / camera.zoom + 1;
        }
        
        
        if(keys.DOWN) {
            if(camera.zoom < camera.minZoom) {
                camera.zoom = camera.minZoom;  
            }
            camera.zoom *= 0.99;
        } else if(keys.UP){
            if(camera.zoom > camera.maxZoom) {
                camera.zoom = camera.maxZoom;  
            }
                camera.zoom *= 1.01;
        } 
    }
    
    public class Keys {
        boolean W, A, S, D, UP, DOWN = false;
    }
}
