package hillbillies.part3.programs.expressions;

import hillbillies.part3.programs.SourceLocation;

/**
 * 
 * @author kenneth
 *
 */
public class ReadVariable<T> extends Expression<T> {
	private final String variableName;
	/**
	 * 
	 * @param variableName
	 */
	public ReadVariable(String variableName) {
		super();
		this.variableName = variableName;
	}

	public String getVariableName(){
		return this.variableName;
	}

	@Override
	public T evaluate() throws NullPointerException {
		try {
			return this.getRunner().getVariableValue(variableName);
		}catch(IllegalArgumentException e){
			throw new IllegalStateException("The variable to evaluate isn't assigned. This may not happen?", e);
		}
	}

}
