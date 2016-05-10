package hillbillies.part3.programs.expressions;

import java.util.HashSet;
import java.util.Set;

import hillbillies.activities.TargetMove;
import hillbillies.activities.TargetMove.Path;
import hillbillies.model.Cube;
import hillbillies.model.Log;
import hillbillies.model.Task.TaskRunner;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.utils.Vector;

/**
 * @author kenneth
 *
 */
public class LogPosition extends Expression<Cube> {
	private SourceLocation sourceLocation;
	/**
	 * 
	 */
	public LogPosition(SourceLocation sourceLocation) {
		super();
		this.sourceLocation = sourceLocation;
	}

	@Override
	public Cube evaluate() {
		Set<Vector> positions = new HashSet<>();
		Set<Log> logs = this.getRunner().getExecutingWorld().getLogs(true);
		for (Log log : logs){
			positions.add(log.getPosition().getCubeCoordinates());
		}
		//TODO: path vinden uit lijst?
		return this.getRunner().getExecutingWorld().(Log.class); //TODO
	}

}
