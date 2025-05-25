
/**
 * Panel for rendering
 * 
 * AUTHOR: Brendan Laking
 * VERSION: 2025.05.25
 */


import java.awt.*; 
import java.awt.event.*; 
import java.awt.geom.*; 
import java.awt.image.*;
import javax.swing.*; 
import java.io.File;
import javax.imageio.ImageIO;

import Tiles.*;


public class Panel extends JPanel implements Runnable, KeyListener, MouseListener {
    
    Thread thread; //New CPU thread to run on.
    
    final int FPS = 60; 
    final double frameSkip = 1000 / FPS; //How many millisecods we need to skip to reach the next frame.
    final int maxFrameSkip = 5; 
    
    final int screenWidth;
    final int screenHeight;
    
    Game game; //Game game defines the game with type game.
    
    double mouseX;
    double mouseY;
    double mouseTileX;
    double mouseTileY;
    boolean mouseDown;
    
    TileSelect tileSelect = new TileSelect();
    
    ImageButton exitImageButton;
    
    public BufferedImage exitIcon = null;
    
    public State state = State.GAME;
    
    final Color highlightColor = new Color(0, 0, 0, 50);
    
    PauseMenu pauseMenu;
    
    public String savesFolder = "Saves/";
    
    Images images;
        
    public Panel(int width, int height) {
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.black);
        setDoubleBuffered(true);
        setFocusable(true);
        show();
        
        game = new Game(); //game = new Game(); sets the game to a new Game.
        
        addKeyListener(this); //Listen for key inputs.
        addMouseListener(this);
        
        screenWidth = width;
        screenHeight = height;
        
        try {
            exitImageButton = new ImageButton(0, 0, tileSelect.tileSize, tileSelect.tileSize, ImageIO.read(new File("Assets/exit.png")));
            pauseMenu = new PauseMenu(width, height);
            images = new Images();
        }
        catch (java.io.IOException ioe) {
            ioe.printStackTrace();
        }
        
        
    }
    
    public void startThread() {
        thread = new Thread(this); 
        thread.start(); //BEGIN THE TREAD, runs the run() function.
    }
    
    @Override 
    public void run() {
        double nextFrame = System.currentTimeMillis(); //What is the next frame to render on?
        double lastTime = System.currentTimeMillis(); //Used to calculate the delta.
        double delta;
        int loops; 
       
        while (thread != null) {
            loops = 0;
            
            while(System.currentTimeMillis() > nextFrame && loops < maxFrameSkip) {
                delta = System.currentTimeMillis() - lastTime; 
                lastTime = System.currentTimeMillis();

                nextFrame += frameSkip;
                
                game.update();
                    
                repaint();
                
                loops++;
            }
        }
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Graphics2D g2 = (Graphics2D)g;
        
        mouseX = getRelativePoint().getX();
        mouseY = getRelativePoint().getY();
        mouseTileX = (((mouseX + (game.camera.x * game.camera.zoom) - (screenWidth  / 2)) / game.camera.zoom / Tile.tileSize));         
        mouseTileY = (((mouseY + (game.camera.y * game.camera.zoom) - (screenHeight / 2)) / game.camera.zoom / Tile.tileSize));
        
        switch(state) {
            case TILE_SELECT: {
                int x = 0;
                for(TileType type : TileType.values()) {
                    try {
                        g2.drawImage(images.getImageFromType(type, Direction.NORTH), x * tileSelect.tileSize, tileSelect.closedHeight, tileSelect.tileSize, tileSelect.tileSize, null);
                    }
                    catch (java.io.IOException ioe) {
                        ioe.printStackTrace();
                    }

                    if(Math.floor(mouseX / tileSelect.tileSize) == x && mouseY > tileSelect.tileSize && mouseY < tileSelect.tileSize * 2) {
                        g2.setColor(highlightColor);
                        g2.fillRect(x * tileSelect.tileSize, tileSelect.closedHeight, tileSelect.tileSize, tileSelect.tileSize);
                    
                        if(mouseDown) {
                            tileSelect.selectedTile = type;
                            state = State.GAME;
                        }
                    }
                    x++;
                }
                exitImageButton.draw(g2);
                            
                if(mouseDown && exitImageButton.isHovered((int) mouseY, (int) mouseX)) {
                    state = State.GAME;
                } 
                break;
            }
            case GAME: {
                try {
                    for(int y = 0; y < game.level.height; y++) {
                        for(int x = 0; x < game.level.width; x++) {
                            g2.drawImage(images.getImageFromType(game.level.tiles[x][y].type, game.level.tiles[x][y].direction),
                                        (int) (x * Tile.tileSize * game.camera.zoom) - (int) (game.camera.x * game.camera.zoom) + (screenWidth  / 2), 
                                        (int) (y * Tile.tileSize * game.camera.zoom) - (int) (game.camera.y * game.camera.zoom) + (screenHeight  / 2),
                                        (int) (Tile.tileSize     * game.camera.zoom) + 1, 
                                        (int) (Tile.tileSize     * game.camera.zoom) + 1, null);
                            
                            if(Math.floor(mouseTileX) == x && Math.floor(mouseTileY) == y) {
                                g2.setColor(highlightColor);
                                g2.fillRect((int) (x * Tile.tileSize * game.camera.zoom) - (int) (game.camera.x * game.camera.zoom) + (screenWidth  / 2), 
                                            (int) (y * Tile.tileSize * game.camera.zoom) - (int) (game.camera.y * game.camera.zoom) + (screenHeight  / 2),
                                            (int) (Tile.tileSize     * game.camera.zoom) + 1, 
                                            (int) (Tile.tileSize     * game.camera.zoom) + 1);
                                if(mouseDown) {
                                try {
                                        game.level.tiles[x][y] = new Tile(tileSelect.selectedTile, tileSelect.selectedDirection);
                                    } catch (java.io.IOException ioe) {
                                        ioe.printStackTrace();
                                        }
                                }
                            }
                        }
                    }   
                    g2.drawImage(images.getImageFromType(tileSelect.selectedTile, tileSelect.selectedDirection), tileSelect.x, tileSelect.y, tileSelect.closedWidth, tileSelect.closedHeight, null);
                } catch (java.io.IOException ioe) {
                    ioe.printStackTrace();
                }
    
                if(mouseDown && mouseX > tileSelect.x && mouseX < tileSelect.x + tileSelect.closedWidth && mouseY > tileSelect.y && mouseY < tileSelect.y + tileSelect.closedHeight) {
                    state = State.TILE_SELECT;
                } 
                break;
            }
            
            case PAUSE_MENU: {
                switch(pauseMenu.state) {
                    case MAIN:
                        if(mouseDown) {
                            if(pauseMenu.loadGame.isHovered((int) mouseX, (int) mouseY)) {
                                pauseMenu.state = PauseMenuState.LOAD_GAME;
                            } else if(pauseMenu.saveGame.isHovered((int) mouseX, (int) mouseY)) {
                                state = State.GAME;
                                game.level.saveToFile(game.level.loadedFile);
                            }
                        }
                        pauseMenu.saveGame.draw(g2);
                        pauseMenu.loadGame.draw(g2);
                        break;
                    
                    case LOAD_GAME: 
                        for(int i = 0; i < pauseMenu.saves.size(); i++) {
                            pauseMenu.saves.get(i).draw(g2);
                            
                            if(mouseDown) {
                                if(pauseMenu.saves.get(i).isHovered((int) mouseX, (int) mouseY)) {
                                    game.level.loadFromFile(savesFolder + pauseMenu.saves.get(i).text);
                                    state = State.GAME;
                                }
                            }
                        }
                        break;
                    }
                
                break;
            }
        }
        
        if(mouseDown) {
            mouseDown = false;
        }
        
        g2.dispose();
        Toolkit.getDefaultToolkit().sync(); //I DON'T KNOW WHAT THIS DOES HELP IT FIXES MY PROBLEMS. 
    }
    

    //When key pressed down set all the values to true.
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                game.keys.W = true;
                break;
            case KeyEvent.VK_A:
                game.keys.A = true;
                break;
            case KeyEvent.VK_S:
                game.keys.S = true;
                break;
            case KeyEvent.VK_D:
                game.keys.D = true;
                break;
            case KeyEvent.VK_Q:
                game.keys.UP = true;
                break;
            case KeyEvent.VK_E:
                game.keys.DOWN = true;
                break;
            case KeyEvent.VK_UP:
                tileSelect.selectedDirection = Direction.NORTH;
                break;
            case KeyEvent.VK_DOWN:
                tileSelect.selectedDirection = Direction.SOUTH;
                break;
            case KeyEvent.VK_LEFT:
                tileSelect.selectedDirection = Direction.WEST;
                break;
            case KeyEvent.VK_RIGHT:
                tileSelect.selectedDirection = Direction.EAST;
                break;
            case KeyEvent.VK_ESCAPE:
                pauseMenu.state = PauseMenuState.MAIN;
                if(state == State.PAUSE_MENU) {
                    state = State.GAME;
                } else {
                    pauseMenu.getSaves(savesFolder);
                    state = State.PAUSE_MENU;
                }
                break;
        }
    }
        
    //When key released set all keys to false.
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                game.keys.W = false;
                break;
            case KeyEvent.VK_A:
                game.keys.A = false;
                break;
            case KeyEvent.VK_S:
                game.keys.S = false;
                break;
            case KeyEvent.VK_D:
                game.keys.D = false;
                break;
            case KeyEvent.VK_Q:
                game.keys.UP = false;
                break;
            case KeyEvent.VK_E:
                game.keys.DOWN = false;
                break;
        }
    }

    
    public void keyTyped(KeyEvent e) {} public void mousePressed(MouseEvent e) {} public void mouseReleased(MouseEvent e) {} public void mouseEntered(MouseEvent e) {}  public void mouseExited(MouseEvent e) {}//I have no use for this but I have to define it.
    
    
    public void mouseClicked(MouseEvent e) {
        mouseDown = true;
    }
    
    public Point getRelativePoint() {
        PointerInfo pointer = MouseInfo.getPointerInfo();
        Point point = pointer.getLocation();
        SwingUtilities.convertPointFromScreen(point, this);

        return point;
    }
    
    
}

