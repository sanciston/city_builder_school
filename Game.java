
/**
 * Game managment and updating functions
 * 
 * AUTHOR: Brendan Laking
 * VERSION: 2025.05.21
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
        level = new Level("level.txt");
        
        camera = new Camera(1, 0, 0);
        
        keys = new Game.Keys();
    }
    
    public void update() {
        if(keys.W) {
            camera.y -= speed;
        }
        
        if(keys.S) {
            camera.y += speed;
        }
        
        if(keys.A) {
            camera.x -= speed;
        }
        
        if(keys.D) {
            camera.x += speed;
        }
        
        if (keys.UP && camera.zoom < camera.maxZoom) {
            camera.zoom *= 1.01;
        }
        
        if (keys.DOWN && camera.zoom > camera.minZoom) {
            camera.zoom *= 0.99;
        }    
    }
    
    public class Keys {
        boolean W, A, S, D, UP, DOWN;
        
        public Keys() {
            this.W = false;
            this.A = false;
            this.S = false;
            this.D = false;
            this.UP = false;
            this.DOWN = false;
        }
    }
}
