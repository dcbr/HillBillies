package hillbillies.part3.programs.expressions;

import java.util.ArrayList;

import hillbillies.model.Cube;
import hillbillies.model.Task.TaskRunner;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.utils.Vector;

import static hillbillies.utils.Utils.randInt;

/**
 * @author kenneth
 *
 */
public class NextToPosition extends Expression<Cube> {
	private Expression<Cube> position;
	private SourceLocation sourceLocation;
	/**
	 * 
	 */
	public NextToPosition(Expression<Cube> position, SourceLocation sourceLocation) {
		super(position);
		this.position = position;
		this.sourceLocation = sourceLocation;
	}

	@Override
	public Cube evaluate() {
		//TODO: fix unavailable methods
		Vector pos = position.run().getPosition();
		ArrayList<Cube> positions = new ArrayList<>();
		this.getRunner().getExecutingWorld().getNeighbouringCubesSatisfying(
				positions,
				pos,
				cube->isValidNextPosition(pos, cube.getPosition())
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
