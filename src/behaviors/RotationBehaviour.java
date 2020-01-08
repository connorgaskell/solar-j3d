package behaviors;

import java.util.Enumeration;
import javax.media.j3d.Behavior;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.WakeupCriterion;
import javax.media.j3d.WakeupOnElapsedFrames;
import javax.media.j3d.WakeupOnElapsedTime;
import javax.media.j3d.WakeupOr;
import javax.vecmath.Point3d;
import javax.vecmath.Quat4d;
import javax.vecmath.Vector3f;
import objects.space.Planet;

public class RotationBehaviour extends Behavior {

    private WakeupCriterion[] wakeupCriterion;
    private WakeupOr wakeupOr;
    
    private final Planet gameObject;
    private Vector3f rotationVelocity;

    private void rotateObject() {        
        gameObject.setRotation(new Quat4d(rotationVelocity.x, rotationVelocity.y, rotationVelocity.z, 1));
    }
    
    public RotationBehaviour(Planet gameObject, Vector3f rotationVelocity) {
        this.gameObject = gameObject;
        this.rotationVelocity = rotationVelocity;
        
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
        WakeupCriterion criterion = (WakeupCriterion)criteria.nextElement();

        if(criterion instanceof WakeupOnElapsedFrames) {
            rotateObject();
        } else if(criterion instanceof WakeupOnElapsedTime) {

        }
        
        wakeupOn(wakeupOr);
    }
    
}
