public class Node {
    boolean start;
    boolean goal;
    
    boolean solid;
    
    int fCost;
    int gCost;
    int hCost;
    
    int x, y;
    
    public Node(int x, int y, boolean solid) {
        this.x = x;
        this.y = y;
        
        goal = false;
        start = false;
        
        this.solid = solid;
    }
    
    public void setSolid() {
        solid = true;
    }
    
    public void setNotSolid() {
        solid = false;
    }
    
    public void setGoal() {
        goal = true;
    }
    
    public void setStart() {
        start = true;
    }
    
    public void reset() {
        start = false;
        goal = false;
    }
}