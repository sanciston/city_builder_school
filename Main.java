
/**
 * The main class does the basic management and game loop
 *
 * AUTHOR: Brendan Laking
 * VERSION:
 */

import javax.swing.*;
import java.awt.*;
import Tiles.*;

public class Main {
    public static void main(String args[]) {
        Window window = new Window("Hello", 1600, 1000);
        Level level = new Level("level.txt");
    }
}
