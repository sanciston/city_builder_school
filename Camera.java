
/**
 * Basic camera type
 *
 * AUTHOR: Brendan Laking
 * VERSION: 2025.05.18
 */
public class Camera {
    public double zoom;
    public final double maxZoom = 10;
    public final double minZoom = 0.1;
    
    public int x;
    public int y;
    
    
    
    public Camera(int zoom, int x, int y) {
        this.x = x;
        this.y = y;
        
        this.zoom = zoom;
    }
}
