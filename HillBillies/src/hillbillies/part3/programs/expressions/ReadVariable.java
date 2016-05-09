package hillbillies.part3.programs.expressions;

import hillbillies.part3.programs.SourceLocation;
import hillbillies.model.Task;
/**
 * 
 * @author kenneth
 *
 */
public class ReadVariable<T> extends Expression<T> {
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

	public String getVariableName(){
		return this.variableName;
	}

	@Override
	public T evaluate() throws ClassCastException, IllegalArgumentException{
		try {
			return this.getRunner().<T>getVariableValue(variableName).run();
		}catch(IllegalArgumentException e){
			throw new IllegalStateException("The variable to evaluate isn't assigned. This may not happen?", e);
		}
	}

}
