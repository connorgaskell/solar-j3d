package solar;

import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.universe.*;
import java.awt.*;
import java.util.*;
import javax.media.j3d.*;
import javax.swing.*;
import javax.vecmath.*;
import objects.camera.OrbitCamera;
import objects.lights.Ambient;
import objects.space.Planet;

public class Solar extends JPanel {
    private BranchGroup rootGroup, planetGroup, starGroup, fogGroup, behaviourGroup;
    public static ArrayList<Sphere> planets = new ArrayList<>();
    public static double mouseX = 0, mouseY = 0;
    
    public Solar() {
        setLayout(new BorderLayout());
        GraphicsConfigTemplate3D configTemplate3D = new GraphicsConfigTemplate3D();
        configTemplate3D.setSceneAntialiasing(GraphicsConfigTemplate.PREFERRED);
        GraphicsConfiguration config = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getBestConfiguration(configTemplate3D);
        
        Canvas3D canvas = new Canvas3D(config) {
            Graphics2D g = this.getGraphics2D();

            @Override
            public void postRender() {
                Dimension dimensions = this.getSize();
                double screenWidth = dimensions.getWidth();
                double screenHeight = dimensions.getHeight();

                g.setColor(Color.WHITE);

                g.drawString("-- THE SOLAR SYSTEM --",(int)(screenWidth / 2) - 45, (int)(screenHeight) - 20);

                this.getGraphics2D().flush(false);
                revalidate();
            }
        };
        
        canvas.setFocusable(true);
        canvas.requestFocus();
        
        SimpleUniverse universe = new SimpleUniverse(canvas);

        fogGroup = new BranchGroup();
        behaviourGroup = new BranchGroup();
        
        renderPlanets();
        
        add("Center", canvas);

        Viewer viewer = universe.getViewer();
        View view = viewer.getView();
        view.setBackClipDistance(800.0f);
        view.setSceneAntialiasingEnable(true);
        view.setDepthBufferFreezeTransparent(true);
        view.setTransparencySortingPolicy(View.PERSPECTIVE_PROJECTION);
        view.setScreenScalePolicy(View.SCALE_EXPLICIT);
        view.setProjectionPolicy(View.PERSPECTIVE_PROJECTION);
        view.setWindowEyepointPolicy(View.RELATIVE_TO_FIELD_OF_VIEW);
        view.setFieldOfView(1.5f);
        
        OrbitCamera camera = new OrbitCamera(universe, canvas, 4.0f, 3.5f);
        
        camera.setOrbit(new Point3d(5.1f, 0, 0f));
        
        universe.getViewingPlatform().setNominalViewingTransform();
        Transform3D viewPosTransform = new Transform3D();
        viewPosTransform.set(new Vector3f(0.0f, 0.0f, 6.0f));
        Transform3D viewRotTransform = new Transform3D();
        viewRotTransform.setRotation(new Quat4d(1.0f, 0.0f, 0.0f, -1.0f));
        viewRotTransform.mul(viewPosTransform);
        universe.getViewingPlatform().getViewPlatformTransform().setTransform(viewRotTransform);
        universe.addBranchGraph(rootGroup);
    }
    
    public void renderPlanets() {
        rootGroup = new BranchGroup();
        planetGroup = new BranchGroup();
        
        planetGroup.addChild(new Ambient());
        rootGroup.addChild(renderStars());
        
        Appearance earthAppearance = new Appearance();
        earthAppearance.setTexture(new TextureLoader("./res/earth.png", null).getTexture());
        
        planetGroup.addChild(new Planet(new Vector3f(0, 0, 8.7f), new Vector3f(0.0f, 0.0f, 0.0f), 0.267f, "./res/mercury.png", planetGroup, "Mercury", false));
        planetGroup.addChild(new Planet(new Vector3f(0, 0, 16.2f), new Vector3f(0.0f, 0.0f, 0.0f), 0.664f, "./res/venus.png", planetGroup, "Venus", false));
        planetGroup.addChild(new Planet(new Vector3f(0, 0, 22.4f), new Vector3f(0.0f, 0.0f, 0.0f), 0.699f, "./res/earth.png", planetGroup, "Earth", false));
        planetGroup.addChild(new Planet(new Vector3f(0, 0, 34.0f), new Vector3f(0.0f, 0.0f, 0.0f), 0.371f, "./res/mars.png", planetGroup, "Mars", false));
        planetGroup.addChild(new Planet(new Vector3f(0, 0, 116.3f), new Vector3f(0.0f, 0.0f, 0.0f), 7.658f, "./res/jupiter.png", planetGroup, "Jupiter", false));
        planetGroup.addChild(new Planet(new Vector3f(0, 0, 213.6f), new Vector3f(0.0f, 0.0f, 0.0f), 6.391f, "./res/saturn.png", planetGroup, "Saturn", false));
        planetGroup.addChild(new Planet(new Vector3f(0, 0, 429.1f), new Vector3f(0.0f, 0.0f, 0.0f), 2.576f, "./res/uranus.png", planetGroup, "Uranus", false));
        planetGroup.addChild(new Planet(new Vector3f(0, 0, 673.2f), new Vector3f(0.0f, 0.0f, 0.0f), 2.494f, "./res/Neptune.png", planetGroup, "Neptune", false));
        
        rootGroup.addChild(planetGroup);
        rootGroup.compile();
    }
    
    public Background renderStars() {
        starGroup = new BranchGroup();
        Background starBg = new Background();
        starBg.setApplicationBounds(new BoundingSphere(new Point3d(0, 0, 0), 1e100));
        
        Appearance starAppearance = new Appearance();
        
        // Resource: https://www.solarsystemscope.com/textures/download/8k_stars_milky_way.jpg
        starAppearance.setTexture(new TextureLoader("./res/stars.png", null).getTexture());
        
        Sphere starSphere = new Sphere(1.0f, Sphere.GENERATE_NORMALS | Sphere.GENERATE_NORMALS_INWARD | Sphere.GENERATE_TEXTURE_COORDS, 40, starAppearance);
        
        starGroup.addChild(starSphere);
        starBg.setGeometry(starGroup);
        
        return starBg;
    }
}
