package hillbillies.part3.programs.expressions;

import java.util.ArrayList;

import hillbillies.activities.Move;
import hillbillies.model.Cube;
import hillbillies.model.Task.TaskRunner;
import hillbillies.model.WorldObject;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.utils.Vector;

import static hillbillies.utils.Utils.randInt;

/**
 * @author kenneth
 *
 */
public class NextToPosition extends Expression<Vector> {
	private Expression<Vector> position;
	private SourceLocation sourceLocation;
	/**
	 * 
	 */
	public NextToPosition(Expression<Vector> position, SourceLocation sourceLocation) {
		super(position);
		this.position = position;
		this.sourceLocation = sourceLocation;
	}

	@Override
	public Vector evaluate() {
		//TODO: fix unavailable methods
		Vector pos = position.run();
		ArrayList<Vector> positions = new ArrayList<>();
		this.getRunner().getExecutingWorld().getNeighbouringCubesSatisfying(
				positions,
				pos,
				cube -> Move.isValidNextPosition(this.getRunner().getExecutingUnit(), pos, cube.getPosition()),
				WorldObject::getPosition
		);
		if(positions.size()==0){
			// No accessible positions available => interrupt activity
			this.getRunner().stop();// TODO: call scheduler.deschedule(task, unit)
			return null;// TODO: check if taskRunner is stopped before executing next statement
		}else{
			return positions.get(randInt(0, positions.size()-1));
		}
	}

}
