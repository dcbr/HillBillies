package hillbillies.part3.programs.expressions;

import hillbillies.part3.programs.SourceLocation;
import hillbillies.part3.programs.Task;
/**
 * 
 * @author kenneth
 *
 */
public class ReadVariable implements Expression{
	private final String variableName;
	private final SourceLocation sourceLocation;
	/**
	 * 
	 * @param variableName
	 * @param sourceLocation
	 */
	public ReadVariable(String variableName, SourceLocation sourceLocation) {
		this.variableName = variableName;
		this.sourceLocation = sourceLocation;
	}

	@Override
	public Object evaluate(Task.TaskBuilder taskBuilder) {
		return taskBuilder.getVariableValue(variableName);
		
	}

}
