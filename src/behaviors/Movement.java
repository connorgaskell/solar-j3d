package behaviors;

import java.util.*;
import javax.media.j3d.*;
import javax.vecmath.*;
import objects.GameObject;

public class Movement extends Behavior {

    private WakeupCriterion[] wakeupCriterion;
    private WakeupOr wakeupOr;
    
    private final GameObject gameObject;
    private final Vector3f positionVector;
    private final Vector3f velocity;
    private Vector3f rotationVelocity;
    
    private void moveObject() {
        Transform3D transform3D = gameObject.getTransform(gameObject.position);
        positionVector.add(velocity);
        
        transform3D.setTranslation(positionVector);
        gameObject.position.setTransform(transform3D);
    }
    
    public Movement(GameObject gameObject, Vector3f velocity) {
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
            
        }
        
        wakeupOn(wakeupOr);
    }
    
}
