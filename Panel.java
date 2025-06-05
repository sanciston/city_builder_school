
/**
 * JPanel for rendering and drawing
 * 
 * AUTHOR: Brendan Laking
 * VERSION: 2025.05.27
 */


import java.awt.*; 
import java.awt.event.*; 
import java.awt.image.*;
import javax.swing.*; 
import java.io.File;
import javax.imageio.ImageIO;
import java.util.ArrayList;

import Tiles.*;


public class Panel extends JPanel implements Runnable, KeyListener, MouseListener, MouseWheelListener {
    
    Thread thread; // New CPU thread to run on.
    
    final int FPS = 60; 
    final double frameSkip = 1000 / FPS; // How many millisecods we need to skip to reach the next frame.
    final int maxFrameSkip = 5; 
    
    final int screenWidth;
    final int screenHeight;
    
    
    Game game; // Game game defines the game with type game.
    
    // Mouse coords, relative to the JPanel.
    double mouseX;
    double mouseY;
    
    ArrayList<KeyEvent> keys = new ArrayList<KeyEvent>();
    final int newGameMaxLength = 16;
    String newGame = "";
    
    // Mouse coords, relative to the tile system.
    double mouseTileX;
    double mouseTileY;
    double mouseWorldX;
    double mouseWorldY;
    boolean mouseDown;
    boolean mouseClicked;
    
    TileSelect tileSelect = new TileSelect(); // The tile select menu.
    PauseMenu pauseMenu; // The pause menu.
    MainMenu mainMenu;
    
    ImageButton exitImageButton; // The button for exiting menus.
    public BufferedImage exitIcon = null; 
    
    public State state = State.MAIN_MENU; // The game state as an enum.
        
    final Color highlightColor = new Color(0, 0, 0, 50); // When you hover over a tile, this is the colour.
        
    public String savesFolder = "Saves/";
    
    Images images; // Pre load all the images.
        
    public Panel(int width, int height) {
        setPreferredSize(new Dimension(width, height)); 
        setBackground(Color.black);
        setDoubleBuffered(true); 
        setFocusable(true);
        show();
        
        game = new Game(); // game = new Game(); sets the game to a new Game.
        
        addKeyListener(this); // Listen for key inputs.
        addMouseListener(this); // Listens for Mouse clicks.
        addMouseWheelListener(this); // Listens for mouse scrolling.

        
        screenWidth = width;
        screenHeight = height;
        
        
        try { // Handel image loading errors.
            exitImageButton = new ImageButton(0, 0, tileSelect.tileSize, tileSelect.tileSize, ImageIO.read(new File("Assets/exit.png")));  
            pauseMenu = new PauseMenu(width, height);
            mainMenu = new MainMenu(width, height);
            images = new Images(); 
        }
        catch (java.io.IOException ioe) {
            System.out.println("whoops");
            ioe.printStackTrace();
        }
        
        mainMenu.getSaves(savesFolder); // load all the saves for the load screen.
    }
    
    public void startThread() {
        thread = new Thread(this); 
        thread.start(); // BEGIN THE TREAD, runs the run() function.
    }
    
    @Override 
    public void run() {
        double nextFrame = System.currentTimeMillis(); // What is the next frame to render on?
        double lastTime = System.currentTimeMillis(); // Used to calculate the delta.
        int loops; 
       
        while (thread != null) {
            loops = 0;
            
            while(System.currentTimeMillis() > nextFrame && loops < maxFrameSkip) {
                lastTime = System.currentTimeMillis();

                nextFrame += frameSkip;
                
                game.update(); // Update the game.
                    
                repaint(); // Render the games.
                
                loops++;
            }
        }
    }
    
    public void paintComponent(Graphics g) { // Called by repaint(); 
        super.paintComponent(g);
        
        Graphics2D g2 = (Graphics2D)g;
        
        // Mouse x and y relative to the JPanel object (this).
        mouseX = getRelativePoint().getX(); 
        mouseY = getRelativePoint().getY(); 
        // Mouse x and y relative to the coordinate system.
        mouseWorldX = (((mouseX + (game.camera.x * game.camera.zoom) - (screenWidth  / 2)) / game.camera.zoom));
        mouseWorldY = (((mouseY + (game.camera.y * game.camera.zoom) - (screenHeight / 2)) / game.camera.zoom));
        // Mouse x and y relative to the tile system.
        mouseTileX = mouseWorldX / Tile.tileSize;         
        mouseTileY = mouseWorldY / Tile.tileSize;
        
        
        switch(state) {
            case GAME: {
                g2.setColor(Color.BLUE);
                g2.fillRect(0, 0, screenWidth, screenHeight);
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
                                if(tileSelect.open) {
                                    if(mouseDown && !(Math.floor(mouseX / tileSelect.tileSize) > 0 && Math.floor(mouseX / tileSelect.tileSize) < TileType.values().length + 1 && mouseY > tileSelect.tileSize && mouseY < tileSelect.tileSize * 2)) {
                                        try {
                                            if(!tileSelect.houseSelected) {
                                                game.level.tiles[x][y] = new Tile(tileSelect.selectedTile, tileSelect.selectedDirection);
                                            }                                      
                                        } catch (java.io.IOException ioe) {
                                            ioe.printStackTrace();
                                        }
                                    }
                                    if(mouseClicked) {
                                        if(tileSelect.houseSelected) {
                                            placeHouse(x, y);
                                        } 
                                    }
                                } else {
                                    if(mouseDown) {
                                        try {
                                            if(!tileSelect.houseSelected) {
                                                game.level.tiles[x][y] = new Tile(tileSelect.selectedTile, tileSelect.selectedDirection);
                                            }                                      
                                        } catch (java.io.IOException ioe) {
                                            ioe.printStackTrace();
                                        }
                                    }
                                    if(mouseClicked) {
                                        if(tileSelect.houseSelected) {
                                            placeHouse(x, y);
                                        } 
                                    }
                                }
                            }
                        }
                        
                        for(int i = 0; i < game.level.houses.size(); i++) {
                            House house = game.level.houses.get(i);
                            g2.drawImage(images.getHouseImage(house.type, house.direction),
                                (int) (house.x * Tile.tileSize * game.camera.zoom) - (int) (game.camera.x * game.camera.zoom) + (screenWidth  / 2), 
                                (int) (house.y * Tile.tileSize * game.camera.zoom) - (int) (game.camera.y * game.camera.zoom) + (screenHeight  / 2),
                                (int) (Tile.tileSize           * game.camera.zoom) + 1, 
                                (int) (Tile.tileSize           * game.camera.zoom) + 1, null);
                        }
                    }   
                    g2.drawImage(images.getImageFromType(tileSelect.selectedTile, tileSelect.selectedDirection), tileSelect.x, tileSelect.y, tileSelect.closedWidth, tileSelect.closedHeight, null);

                    if(tileSelect.open) {
                        int x = 0;
                        for(TileType type : TileType.values()) { // Iterate the tile types;
                            try {
                                g2.drawImage(images.getImageFromType(type, Direction.NORTH), x * tileSelect.tileSize, tileSelect.closedHeight, tileSelect.tileSize, tileSelect.tileSize, null); //Draw the tiles.
                            }
                            catch (java.io.IOException ioe) {
                                ioe.printStackTrace();
                            }
        
                            if(Math.floor(mouseX / tileSelect.tileSize) == x && mouseY > tileSelect.tileSize && mouseY < tileSelect.tileSize * 2) {  // If the mouse is hovering the tile.
                                g2.setColor(highlightColor);
                                g2.fillRect(x * tileSelect.tileSize, tileSelect.closedHeight, tileSelect.tileSize, tileSelect.tileSize);
                            
                                if(mouseClicked) { // If mouse clicked, set tile.
                                    tileSelect.selectedTile = type;
                                    tileSelect.open = false;
                                    tileSelect.houseSelected = false;
                                }
                            }
                            x++;
                        }
                        
                        for(HouseType type : HouseType.values()) { // Iterate the tile types;
                            try {
                                g2.drawImage(images.getHouseImage(type, Direction.NORTH), x * tileSelect.tileSize, tileSelect.closedHeight, tileSelect.tileSize, tileSelect.tileSize, null); //Draw the tiles.
                            }
                            catch (java.io.IOException ioe) {
                                ioe.printStackTrace();
                            }
        
                            if(Math.floor(mouseX / tileSelect.tileSize) == x && mouseY > tileSelect.tileSize && mouseY < tileSelect.tileSize * 2) {  // If the mouse is hovering the tile.
                                g2.setColor(highlightColor);
                                g2.fillRect(x * tileSelect.tileSize, tileSelect.closedHeight, tileSelect.tileSize, tileSelect.tileSize);
                            
                                if(mouseClicked) { // If mouse clicked, set tile.
                                    tileSelect.selectedHouse = type;
                                    tileSelect.open = false;
                                    tileSelect.houseSelected = true;
                                }
                            }
                            x++;
                        }
                        
                        if(Math.floor(mouseX / tileSelect.tileSize) == x && mouseY > tileSelect.tileSize && mouseY < tileSelect.tileSize * 2) {  // If the mouse is hovering the tile.
                            g2.setColor(highlightColor);
                            g2.fillRect(x * tileSelect.tileSize, tileSelect.closedHeight, tileSelect.tileSize, tileSelect.tileSize);
                        
                            if(mouseClicked) { // If mouse clicked, set tile.
                                tileSelect.houseSelected = true;
                                tileSelect.open = false;                                
                            }
                        }
                    }
                } catch (java.io.IOException ioe) {
                    ioe.printStackTrace();
                }
    
                if(mouseClicked && mouseX > tileSelect.x && mouseX < tileSelect.x + tileSelect.closedWidth && mouseY > tileSelect.y && mouseY < tileSelect.y + tileSelect.closedHeight) {
                    tileSelect.open = true;
                } 
                break;
            }
            
            case PAUSE_MENU: {
                System.out.println(game.level.loadedFile);
                switch(pauseMenu.state) {
                    case MAIN:
                        if(mouseClicked) {
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
                            
                            if(mouseClicked) {
                                if(pauseMenu.saves.get(i).isHovered((int) mouseX, (int) mouseY)) {
                                    game.level.loadFromFile(savesFolder + pauseMenu.saves.get(i).text);
                                    state = State.GAME;
                                }
                            }
                        }
                        break;
                    }
                
                break;
            } case MAIN_MENU: {
                switch(mainMenu.state) {
                    case MAIN:
                        if(mouseClicked) {
                            if(mainMenu.loadGame.isHovered((int) mouseX, (int) mouseY) && mainMenu.saves.size() > 0) {
                                mainMenu.state = MainMenuState.LOAD_GAME;
                            } else if(mainMenu.newGame.isHovered((int) mouseX, (int) mouseY)) {
                                mainMenu.state = MainMenuState.NEW_GAME;
                            } 
                        }
                        
                        if(mainMenu.saves.size() > 0) {
                            mainMenu.loadGame.draw(g2);
                        }
                        mainMenu.newGame.draw(g2);
                        g2.drawImage(mainMenu.logo, 500, 100, 600, 200, null);

                        break;
                    
                    case LOAD_GAME: 
                        for(int i = 0; i < mainMenu.saves.size(); i++) {
                            mainMenu.saves.get(i).draw(g2);
                            if(mouseClicked) {
                                if(mainMenu.saves.get(i).isHovered((int) mouseX, (int) mouseY)) {
                                    game.level.loadFromFile(savesFolder + mainMenu.saves.get(i).text);
                                    state = State.GAME;
                                }
                            } 
                        }
                        break;
                    case NEW_GAME:
                        for(int i = 0; i < keys.size(); i++) {
                            if(Character.isLetter(keys.get(i).getKeyChar()) && newGame.length() < newGameMaxLength) {
                               newGame += keys.get(i).getKeyChar();
                            } else if(keys.get(i).getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                                if(newGame.length() > 0) {
                                    newGame = newGame.substring(0, newGame.length() - 1);
                                }
                            } else if(keys.get(i).getKeyCode() == KeyEvent.VK_ENTER && newGame.length() > 0) {
                                game.level.newGame(savesFolder + newGame, newGame);
                                this.state = State.GAME;
                            } else if(keys.get(i).getKeyCode() == KeyEvent.VK_ESCAPE) {
                                mainMenu.state = MainMenuState.MAIN;
                            }
                        }
                        
                        g2.setColor(Color.WHITE);
                        g2.setFont(new Font("TimesRoman", Font.PLAIN, 100)); 
                        g2.drawString(newGame, 10, 120);
                        g2.drawString("Press Enter To Confirm", 10, 240);
                        g2.drawString("Press Esc To Exit", 10, 340);
                        keys = new ArrayList<KeyEvent>();
                        break;
                }
            }
        }
        
        if (mouseClicked) {
            mouseClicked = false;
        }
        
        g2.dispose();
        Toolkit.getDefaultToolkit().sync(); // I DON'T KNOW WHAT THIS DOES HELP IT FIXES MY PROBLEMS. 
    }
    

    // When key pressed down set all the values to true.
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
                        } else if(state == State.GAME)  {
                            pauseMenu.getSaves(savesFolder);
                            state = State.PAUSE_MENU;
                        }
                        break;
            
        }
    }
        
    // When key released set all keys to false.
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
        keys.add(e);
    }
    
    public void mouseWheelMoved(MouseWheelEvent e) {
        if(e.getWheelRotation() == 1) {
            if(game.camera.zoom < game.camera.minZoom) {
                game.camera.zoom = game.camera.minZoom;  
            }
            game.camera.zoom *= 0.9;
        } else {
            if(game.camera.zoom > game.camera.maxZoom) {
                game.camera.zoom = game.camera.maxZoom;  
            }
            game.camera.zoom *= 1.1;
        } 
    }
    
    public void keyTyped(KeyEvent e) {} public void mouseEntered(MouseEvent e) {}  public void mouseExited(MouseEvent e) {}// I have no use for this but I have to define it.
    
    public void mouseClicked(MouseEvent e) {
        mouseClicked = true;
    } 
    
    public void mousePressed(MouseEvent e) {
        mouseDown = true;
    }
    
    public void mouseReleased(MouseEvent e) {
        mouseDown = false;
    }
        
    
    public Point getRelativePoint() {
        PointerInfo pointer = MouseInfo.getPointerInfo();
        Point point = pointer.getLocation();
        SwingUtilities.convertPointFromScreen(point, this);

        return point;
    }
    
    public void placeHouse(int x, int y) {
        boolean housePlaceable = false;
        Direction houseDirection = Direction.NORTH;

        if(y - 1 > 0) { //CHECK TOP        
            if(game.level.tiles[x][y - 1].type == TileType.ROAD) {
                houseDirection = Direction.NORTH;
                housePlaceable = true;
            }
        } 
        if(y + 1 > 0) { //CHECK BOTTOM        
            if(game.level.tiles[x][y + 1].type == TileType.ROAD) {
                houseDirection = Direction.SOUTH;
                housePlaceable = true;
            }
        } 
        if(x - 1 > 0) { //CHECK LEFT
            if(game.level.tiles[x - 1][y].type == TileType.ROAD) {
                houseDirection = Direction.WEST;
                housePlaceable = true;
            }
        }
        if(x + 1 > 0) { //CHECK RIGHT
            if(game.level.tiles[x + 1][y].type == TileType.ROAD) {
                houseDirection = Direction.EAST;
                housePlaceable = true;
            }
        }
        
        for(int i = 0; i < game.level.houses.size(); i++) {
            if(game.level.houses.get(i).x == x && game.level.houses.get(i).y == y) {
                housePlaceable = false;
            }
        }
        
        if(game.level.tiles[x][y].type != TileType.GRASS) {
            housePlaceable = false;
        }
        
        if(housePlaceable) {
            game.level.houses.add(new House(1, x, y, HouseType.RESIDENTIAL, houseDirection));
        }
    }
}

