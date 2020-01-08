package objects.lights;

import javax.media.j3d.BoundingSphere;
import javax.media.j3d.PointLight;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;

public class Sun extends PointLight {
    
    public Sun() {
        setColor(new Color3f(1.5f, 1.5f, 1.5f));
        setPosition(0, 0, 0);
        setAttenuation(1f, 3f, 0f);
        setEnable(true);
        setInfluencingBounds(new BoundingSphere(new Point3d(0, 0, 0), 1e100));
    }
    
    public void changeColor(Color3f color) {
        setColor(color);
    }
    
}
