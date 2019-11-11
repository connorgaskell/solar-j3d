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
    
    private float x, z;
    private float angle = 0;
    
    private void moveObject() {
        Transform3D transform3D = gameObject.getTransform(gameObject.position);
        
        float radius = gameObject.getRadius();
        angle += 1  * gameObject.getSpeed();
        
        float rad = (float)(angle * (Math.PI / 180));
        x = (float)(radius * Math.cos(rad));
        z = (float)(radius * Math.sin(rad));

        System.out.println(new Vector3f(x, 0, z));

        gameObject.setPosition(new Vector3f(x, 0, z));
    }
    
    public Movement(Planet gameObject, Vector3f velocity) {
        this.gameObject = gameObject;
        this.positionVector = gameObject.getPosition();
        this.velocity = velocity;
        
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
