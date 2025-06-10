import Tiles.*;

public class Car {
    Node start;
    Node goal;

    Node currentNode;
    
    Node[][] nodes;
    
    double x;
    double y;
    
    public Car(int x, int y) {
        start = null;
        goal = null;
        this.x = x;
        this.y = y;
    }
    
    public void setStartNode() {
        nodes[(int) Math.floor(x)][(int) Math.floor(x)].setStart();
        start = nodes[(int) Math.floor(x)][(int) Math.floor(x)];
        currentNode = start;
    }
    
    public void setGoalNode(int x, int y) {
        nodes[x][y].setGoal();
        goal = nodes[x][y];
    }
    
    public void generateNodes(Tile[][] tiles) {
        Node[][] nodes = new Node[tiles.length][tiles[0].length];
        for(int y = 0; y < tiles[0].length; y++) {
            for(int x = 0; x < tiles.length; x++) {
                nodes[x][y] = new Node(x, y, tiles[x][y].solid);
            }
        }
    }
}
