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

public final class Solar extends JPanel {
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
        //view.setMinimumFrameCycleTime(60);
        
        OrbitCamera camera = new OrbitCamera(universe, canvas, 4.0f, 3.5f);
        
        camera.setOrbit(new Point3d(0f, 0f, 0f));
        
        universe.getViewingPlatform().setNominalViewingTransform();
        Transform3D viewPosTransform = new Transform3D();
        viewPosTransform.set(new Vector3f(0.0f, 0.0f, 125.0f));
        Transform3D viewRotTransform = new Transform3D();
        viewRotTransform.setRotation(new Quat4d(25 * (Math.PI / 180), 0.0f, 0.0f, -1.0f));
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
        
        Planet sun = new Planet(new Vector3f(0, 0, 0.0f), new Vector3f(0.0f, 0.0f, 0.0f), 4.267f, "./res/sun.png", planetGroup, "Sun", null, 0, 0.0f, false);
        Planet mercury = new Planet(new Vector3f(0, 0, 10.0f), new Vector3f(0.0f, 0.0f, 0.0f), 1.267f, "./res/mercury.png", planetGroup, "Mercury", sun, 10, 0.4787f, false);
        Planet venus = new Planet(new Vector3f(0, 0, 20.0f), new Vector3f(0.0f, 0.0f, 0.0f), 1.664f, "./res/venus.png", planetGroup, "Venus", sun, 20, 0.3502f, false);
        Planet earth = new Planet(new Vector3f(0, 0, 30.0f), new Vector3f(0.0f, 0.0f, 0.0f), 1.699f, "./res/earth.png", planetGroup, "Earth", sun, 30, 0.2978f, false);
        Planet moon = new Planet(new Vector3f(0, 0, 30.0f), new Vector3f(0.0f, 0.0f, 0.0f), 0.699f, "./res/moon.png", planetGroup, "Moon", earth, 3, 1.2678f, false);
        
        Planet mars = new Planet(new Vector3f(0, 0, 40.0f), new Vector3f(0.0f, 0.0f, 0.0f), 1.371f, "./res/mars.png", planetGroup, "Mars", sun, 40, 0.24077f, false);
        Planet jupiter = new Planet(new Vector3f(0, 0, 50.0f), new Vector3f(0.0f, 0.0f, 0.0f), 3.658f, "./res/jupiter.png", planetGroup, "Jupiter", sun, 50, 0.1307f, false);
        Planet saturn = new Planet(new Vector3f(0, 0, 60.0f), new Vector3f(0.0f, 0.0f, 0.0f), 3.391f, "./res/saturn.png", planetGroup, "Saturn", sun, 60, 0.0969f, false);
        Planet uranus = new Planet(new Vector3f(0, 0, 70.0f), new Vector3f(0.0f, 0.0f, 0.0f), 2.576f, "./res/uranus.png", planetGroup, "Uranus", sun, 70, 0.0681f, false);
        Planet neptune = new Planet(new Vector3f(0, 0, 80.0f), new Vector3f(0.0f, 0.0f, 0.0f), 2.494f, "./res/Neptune.png", planetGroup, "Neptune", sun, 80, 0.0543f, false);
        
        for(int i = 0; i <= 8; i++) {
            drawOrbitLines(10.0f * i, 80);
        }

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
    
    private void drawOrbitLines(float radius, int numLines) {
        LineStripArray line = new LineStripArray(numLines + 1, GeometryArray.COORDINATES, new int[] { numLines + 1 });
        line.setCoordinate(0, new Point3f(radius, 0.0f, 0.0f));
        
        Point3f pt = new Point3f();
        for(int i = 1; i < numLines; i++) {
            pt.x = (float)(radius * Math.cos(i * Math.PI / (numLines / 2)));
            pt.z = (float)(radius * Math.sin(i * Math.PI / (numLines / 2)));
            line.setCoordinate(i, pt);
        }
        
        line.setCoordinate(numLines, new Point3f(radius, 0.0f, 0.0f));
        rootGroup.addChild(new Shape3D(line));
    }
}
