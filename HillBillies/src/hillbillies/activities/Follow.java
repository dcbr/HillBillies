package hillbillies.activities;

import hillbillies.model.Unit;

public class Follow extends Activity{
	private final Unit leader;
	public Follow(Unit unit, Unit leader) {
		super(unit);
		this.leader = leader;
	}

	@Override
	protected void startActivity() {	
	}

	@Override
	protected void stopActivity() {
		}

	@Override
	protected void interruptActivity() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void advanceActivity(double dt) {
		//TODO
		
	}

	@Override
	public boolean isAbleTo() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean shouldInterruptFor(Activity nextActivity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getXp() {
		// TODO Auto-generated method stub
		return 0;
	}

}
