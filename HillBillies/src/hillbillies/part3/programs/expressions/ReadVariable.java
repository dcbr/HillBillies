package hillbillies.part3.programs.expressions;

import hillbillies.part3.programs.SourceLocation;
import hillbillies.part3.programs.Task;
/**
 * 
 * @author kenneth
 *
 */
public class ReadVariable<T> implements Expression<T>{
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
	public T evaluate(Task.TaskBuilder taskBuilder) throws ClassCastException, IllegalArgumentException{
		try {
			return (T) taskBuilder.getVariableValue(variableName).evaluate(taskBuilder);
		}catch(IllegalArgumentException e){
			throw new IllegalStateException("The variable to evaluate isn't assigned.", e);
		}
	}

}
