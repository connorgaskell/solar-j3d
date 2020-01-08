package objects.space;

import behaviors.OrbitBehaviour;
import behaviors.RotationBehaviour;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.image.TextureLoader;
import javax.media.j3d.*;
import javax.vecmath.Quat4d;
import javax.vecmath.Vector3f;
import objects.GameObject;

public class Planet extends GameObject {
    public Shape3D planetObject;
    private final float radius;
    private final float speed;
    private final Planet centre;
    public OrbitBehaviour orbitBehaviour;
    public RotationBehaviour rotationBehaviour;
    
    public Planet(Vector3f pos, Vector3f rot, float scale, String material, BranchGroup branchGroup, String name, Planet orbitCentre, float orbitRadius, float speed, Vector3f rotationSpeed, boolean isStatic) {
        planetObject = new Sphere(scale, Sphere.GENERATE_NORMALS | Sphere.GENERATE_TEXTURE_COORDS, 100).getShape();
        bounds.addChild(planetObject.getParent());
        
        Appearance planetAppearance = new Appearance();
        planetAppearance.setTexture(new TextureLoader(material, null).getTexture());
        planetObject.setAppearance(planetAppearance);
        
        planetObject.setUserData(name);
        setUserData(name);
        
        origin = pos;
        setPickable(true);
        setPosition(pos);
        setRotation(new Quat4d(rot.x, rot.y, rot.z, 1));
        
        radius = orbitRadius;
        this.speed = speed;
        this.centre = orbitCentre;
        
        orbitBehaviour = new OrbitBehaviour(this, new Vector3f(0.1f, 0.0f, 0.0f));
        rotationBehaviour = new RotationBehaviour(this, rotationSpeed);
        
        branchGroup.addChild(orbitBehaviour);
        branchGroup.addChild(rotationBehaviour);
        
        branchGroup.addChild(gameObject);
    }
    
    public float getRadius() {
        return radius;
    }
    
    public float getSpeed() {
        return speed;
    }
    
    public Planet getOrbitCentre() {
        return centre;
    }
    
}
