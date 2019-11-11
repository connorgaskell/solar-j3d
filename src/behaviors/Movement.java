package behaviors;

import java.util.*;
import javax.media.j3d.*;
import javax.vecmath.*;
import objects.GameObject;
import objects.space.Planet;

public class Movement extends Behavior {

    private WakeupCriterion[] wakeupCriterion;
    private WakeupOr wakeupOr;
    
    private final Planet gameObject;
    private final Vector3f positionVector;
    private final Vector3f velocity;
    private Vector3f rotationVelocity;
    
    private float angle = 0;
    private float radius;
    
    private void moveObject() {        
        angle += 1  * gameObject.getSpeed();
        
        float rad = (float)(angle * (Math.PI / 180));
        Vector3f orbit = gameObject.getOrbitCentre() != null ? gameObject.getOrbitCentre().getPosition() : new Vector3f(0, 0, 0);
        
        gameObject.setPosition(new Vector3f(
                (float)(radius * Math.cos(rad)) + orbit.x, 
                0, 
                (float)(radius * Math.sin(rad)) + orbit.z
        ));
    }
    
    public Movement(Planet gameObject, Vector3f velocity) {
        this.gameObject = gameObject;
        this.positionVector = gameObject.getPosition();
        this.velocity = velocity;
        this.radius = gameObject.getRadius();
        
        setSchedulingBounds(new BoundingSphere(new Point3d(0, 0, 0), 1e100));
    }
    
    @Override
    public void initialize() {
        wakeupCriterion = new WakeupCriterion[2];
        wakeupCriterion[0] = new WakeupOnElapsedFrames(0);
        wakeupCriterion[1] = new WakeupOnElapsedTime(1);
        
        wakeupOr = new WakeupOr(wakeupCriterion);
        wakeupOn(wakeupOr);
    }

    @Override
    public void processStimulus(Enumeration criteria) {
        WakeupCriterion wakeupCriterion = (WakeupCriterion)criteria.nextElement();
        
        if(wakeupCriterion instanceof WakeupOnElapsedFrames) {
            moveObject();
        } else if(wakeupCriterion instanceof WakeupOnElapsedTime) {
            //moveObject();
        }
        
        wakeupOn(wakeupOr);
    }
    
}
