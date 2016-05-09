package hillbillies.part3.programs.expressions;

import hillbillies.model.Cube;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.model.Task.TaskRunner;

/**
 * @author kenneth
 *
 */
public class IsSolid extends Expression<Boolean> {
	private final Expression<Cube> cube;
	private final SourceLocation sourceLocation;
	/**
	 * 
	 */
	public IsSolid(Expression<Cube> cube, SourceLocation sourceLocation) {
		super(cube);
		this.cube = cube;
		this.sourceLocation = sourceLocation;
	}

	@Override
	public Boolean evaluate() {
		return !cube.run().isPassable();
	}

}
