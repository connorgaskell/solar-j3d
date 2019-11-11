package objects.camera;

import com.sun.j3d.utils.behaviors.vp.*;
import com.sun.j3d.utils.universe.*;
import javax.media.j3d.*;
import javax.vecmath.*;

public class OrbitCamera {

    private final OrbitBehavior orbitBehavior;
    
    public OrbitCamera(SimpleUniverse universe, Canvas3D canvas, float minRadius, float rotSpeed) {
        orbitBehavior = new OrbitBehavior(canvas, OrbitBehavior.REVERSE_TRANSLATE | OrbitBehavior.REVERSE_ROTATE | OrbitBehavior.PROPORTIONAL_ZOOM);
        orbitBehavior.setMinRadius(minRadius);
        orbitBehavior.setRotFactors(rotSpeed, rotSpeed);
        orbitBehavior.setZoomFactor(0.1f);
        orbitBehavior.setSchedulingBounds(new BoundingSphere(new Point3d(0, 0, 0), 1e100));
        
        ViewingPlatform viewingPlatform = universe.getViewingPlatform();
        viewingPlatform.setViewPlatformBehavior(orbitBehavior);
    }
    
    public void setOrbit(Point3d pos) {
        orbitBehavior.setRotationCenter(pos);
    }
    
}
