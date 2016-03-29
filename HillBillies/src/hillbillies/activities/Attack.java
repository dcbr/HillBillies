package hillbillies.activities;

import hillbillies.model.Unit;

/**
 * Created by Bram on 29-3-2016.
 */
public class Attack extends Activity{

    private Unit defender;

    public Attack(Unit attacker, Unit defender){
        super(attacker);
        this.defender = defender;
    }

    @Override
    public void startActivity() {
        double dx = (defender.getPosition().X()-unit.getPosition().X());
        double dy = (defender.getPosition().Y()-unit.getPosition().Y());
        defender.setOrientation((float)Math.atan2(-dy, -dx));
        unit.setOrientation((float)Math.atan2(dy, dx));

        defender.defend(unit);// TODO: maybe move defend code to this class too
        setCurrentActivity(Activity.ATTACK);
    }

    @Override
    public void stopActivity() {

    }

    @Override
    public void interruptActivity() {

    }

    @Override
    public void advanceActivity(double dt) {
        if (activityProgress > 1){
            //this.stopAttacking();
            unit.setCurrentActivity(new None(unit));// TODO: enum doing nothing
        }
    }

    @Override
    public String toString() {
        return "attack";
    }

    /**
     * Check whether this attack is a valid activity for this unit.
     *
     * @return 	true if the position of the attackers cube lies next to the defenders cube
     * 			or is the same cube and the attacker is not attacking itself and
     * 			the attacker is not attacking another unit at the same time and
     * 			the attacker is not in the initial rest mode and
     * 			the defender has more hitpoints than MIN_HITPOINTS
     *       | result == (this.getId()!=defender.getId() &&
     *				!this.isAttacking &&
     *				!this.isInitialRestMode() &&
     *				(defender.getHitpoints()> MIN_HITPOINTS)
     * 				(Math.abs(defender.getPosition().cubeX()-this.getPosition().cubeX())<=1) &&
     *				(Math.abs(defender.getPosition().cubeY()-this.getPosition().cubeY())<=1) &&
     *				(Math.abs(defender.getPosition().cubeZ()-this.getPosition().cubeZ())<=1))
     */
    @Override
    public boolean isAbleTo() {
        return unit.getId()!=defender.getId() &&
                !unit.isAttacking() &&
                !unit.isInitialRestMode() &&
                (defender.getHitpoints()> Unit.MIN_HITPOINTS) &&
                (Math.abs(defender.getPosition().cubeX()-unit.getPosition().cubeX())<=1) &&
                (Math.abs(defender.getPosition().cubeY()-unit.getPosition().cubeY())<=1) &&
                (Math.abs(defender.getPosition().cubeZ()-unit.getPosition().cubeZ())<=1);
    }
}
