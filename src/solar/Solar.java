package solar;

import com.sun.j3d.utils.universe.*;
import java.awt.*;
import java.util.*;
import javafx.scene.shape.*;
import javax.media.j3d.*;
import javax.swing.*;

public class Solar extends JPanel {
    private BranchGroup rootGroup, planetGroup, skyGroup, fogGroup, behaviourGroup;
    public static ArrayList<Sphere> planets = new ArrayList<>();
    public static double mouseX = 0, mouseY = 0;
    
    public Solar() {
        setLayout(new BorderLayout());
        GraphicsConfigTemplate3D configTemplate3D = new GraphicsConfigTemplate3D();
        configTemplate3D.setSceneAntialiasing(GraphicsConfigTemplate.PREFERRED);
        GraphicsConfiguration config = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getBestConfiguration(configTemplate3D);
        
        Canvas3D canvas = new Canvas3D(config) {
            Graphics2D g = this.getGraphics2D();

            public void postRender() {
                Dimension dimensions = this.getSize();
                double screenWidth = dimensions.getWidth();
                double screenHeight = dimensions.getHeight();

                g.setColor(Color.WHITE);

                g.drawString("-- INFORMATION --",(int)(screenWidth / 2) - 45, (int)(screenHeight) - 20);
                g.drawString("HIT BALL - RIGHT MOUSE   |   ROTATE CAMERA - LEFT MOUSE   |   ZOOM - SCROLL MIDDLE MOUSE   |   RESTART - 'R' KEY",(int)(screenWidth / 2) - 350, (int)(screenHeight) - 5);

                this.getGraphics2D().flush(false);
                revalidate();
            }
        };
        
        canvas.setFocusable(true);
        canvas.requestFocus();
        
        SimpleUniverse universe = new SimpleUniverse(canvas);

        fogGroup = new BranchGroup();
        behaviourGroup = new BranchGroup();
        
        add("Center", canvas);
        
        Viewer viewer = universe.getViewer();
        View view = viewer.getView();
        view.setBackClipDistance(300.0f);
        view.setSceneAntialiasingEnable(true);
        view.setDepthBufferFreezeTransparent(true);
        view.setTransparencySortingPolicy(View.PERSPECTIVE_PROJECTION);
        view.setScreenScalePolicy(View.SCALE_EXPLICIT);
        view.setProjectionPolicy(View.PERSPECTIVE_PROJECTION);
        view.setWindowEyepointPolicy(View.RELATIVE_TO_FIELD_OF_VIEW);
        view.setFieldOfView(1.5f);
        
    }
}
