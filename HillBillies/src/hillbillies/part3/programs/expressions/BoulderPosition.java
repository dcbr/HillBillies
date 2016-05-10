package hillbillies.part3.programs.expressions;

import java.util.HashSet;
import java.util.Set;

import hillbillies.model.Boulder;
import hillbillies.model.Cube;
import hillbillies.model.Task.TaskRunner;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.utils.Vector;

/**
 * @author kenneth
 *
 */
public class BoulderPosition extends Expression<Cube> {
	private SourceLocation sourceLoation;
	/**
	 * 
	 */
	public BoulderPosition(SourceLocation sourceLocation) {
		super();
		this.sourceLoation = sourceLocation;
	}

	@Override
	public Cube evaluate() {
		Set<Vector> positions = new HashSet<>();
		Set<Boulder> boulders = this.getRunner().getExecutingWorld().getBoulders(true);
		for (Boulder boulder : boulders){
			positions.add(boulder.getPosition().getCubeCoordinates());
		}
		return this.getRunner().getExecutingWorld().getCube(Boulder); //TODO
	}

}