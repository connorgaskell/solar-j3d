package objects.lights;

import javax.media.j3d.AmbientLight;
import javax.media.j3d.BoundingSphere;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;

public class Ambient extends AmbientLight {
    
    public Ambient() {
        setColor(new Color3f(1.5f, 1.5f, 1.5f));
        setInfluencingBounds(new BoundingSphere(new Point3d(0, 0, 0), 1e100));
        
    }
    
    public void changeColor(Color3f color) {
        setColor(color);
    }
    
}
