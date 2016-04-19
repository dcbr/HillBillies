package hillbillies.activities;

import be.kuleuven.cs.som.annotate.Basic;
import hillbillies.model.IWorld;
import hillbillies.model.Unit;
import hillbillies.utils.Vector;

import static hillbillies.utils.Utils.randInt;

/**
 * Created by Bram on 29-3-2016.
 */
public class Attack extends Activity{

    /**
     * Constant reflecting the duration of an attack.
     */
    public static final double ATTACK_DURATION = 1d;

    public static final int ATTACK_XP = 20;

    /**
     * Final variable registering the defender.
     */
    private final Unit defender;

    /**
     * Create a new Attack Activity for the attacker Unit with the given defender Unit.
     * @param attacker The Unit which will perform the attack. This is the Unit this
     *                 new Attack Activity will belong to.
     * @param defender The Unit which will be attacked when attacker executes this Activity.
     */
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

        this.defend();
        //setCurrentActivity(Activity.ATTACK);
    }

    @Override
    public void stopActivity() {

    }

    @Override
    public void interruptActivity() {

    }

    @Override
    public void advanceActivity(double dt) {
        if (activityProgress >= ATTACK_DURATION)
            this.requestFinish();
    }

    @Override
    public String toString() {
        return "attack";
    }

    /**
     * Check whether an attack is a valid for any unit.
     *
     * @return 	is true if the position of the attackers cube lies next to the defenders cube
     * 			or is the same cube and the attacker do not attacks itself and
     * 			the attacker is not attacking another unit at the same time and
     * 			the attacker is not in the initial rest mode and
     * 			the defender has more hitpoints than MIN_HITPOINTS
     *       | result == (this.getId()!=defender.getId() &&
     *				!this.isAttacking &&
     *				!this.isInitialRestMode() &&
     *				(defender.getHitpoints()> MIN_HITPOINTS)
     * 				(Math.abs(defender.getPosition().cubeX()-this.getPosition().cubeX())<=1) &&
     *				(Math.abs(defender.getPosition().cubeY()-this.getPosition().cubeY())<=1) &&
     *				(Math.abs(defender.getPosition().cubeZ()-this.getPosition().cubeZ())<=1)) &&
     *				this.getFaction() != defender.getFaction() &&
     *				this.getCurrentActivity()!=Activity.FALLING &&
     *				defender.getCurrentActivity()!=Activity.FALLING
     */
    @Override
    public boolean isAbleTo() {
        return unit.getId() != defender.getId() &&
                !unit.isAttacking() &&
                !unit.isInitialRestMode() &&
                (defender.getHitpoints() > Unit.MIN_HITPOINTS) && // TODO: !defender.isTerminated() ?
                (Math.abs(defender.getPosition().cubeX() - unit.getPosition().cubeX()) <= 1) && // TODO: unit.getWorld().getDirectlyAdjacentCubesPositions(
                (Math.abs(defender.getPosition().cubeY() - unit.getPosition().cubeY()) <= 1) && //          defender.getPosition().getCubeCoordinates()
                (Math.abs(defender.getPosition().cubeZ() - unit.getPosition().cubeZ()) <= 1) && //          .contains(unit.getPosition().getCubeCoordinates())
                unit.getFaction() != defender.getFaction() &&
                !defender.isFalling() &&
                !unit.isFalling();
    }

    /**
     * Activity specific code to check whether this Activity can be stopped by nextActivity.
     *
     * @param nextActivity The Activity which will be started when this Activity stops.
     * @return True if this Activity should stop for nextActivity.
     */
    @Override
    protected boolean shouldStopFor(Activity nextActivity) {
        return false;// Falling?
    }

    /**
     * Activity specific code to check whether this Activity can be interrupted by nextActivity.
     *
     * @param nextActivity The Activity which will be started when this Activity is interrupted.
     * @return True if this Activity should be interrupted for nextActivity.
     */
    @Override
    protected boolean shouldInterruptFor(Activity nextActivity) {
        return false;
    }

    /**
     * Returns the amount of XP the Unit will get when this Activity stops successfully.
     */
    @Override
    public int getXp() {
        return ATTACK_XP;
    }


    // Maybe move defend code to separate Activity Defend? which immediately calls requestActivityFinish?
    /**
     * Return the probability a unit can block an attack.
     * @return 	The probability to block an attack is positive for all units.
     *       	| result >= 0.0
     */
    @Basic
    public double getBlockingProbability() {
        return (0.25*(defender.getAgility()+defender.getStrength())/
                (unit.getAgility()+unit.getStrength()));
    }

    /**
     * Return the hitpoints a unit lose when he is taking damage.
     * @return 	The hitpoints a unit loses is positive for all units.
     *       	| result >= 0
     * @return	The hitpoints a unit loses is less than or equal the current hitpoints of
     * 			that unit for all units.
     * 			| result <= this.getHitpoints()
     */
    @Basic
    public int getDamagingPoints() {
        int damage = (int)Math.round(unit.getStrength()/10.0);
        if (damage < defender.getHitpoints())
            return damage;
        return defender.getHitpoints();
    }

    /**
     * Return the probability a unit can dodge an attack.
     * @return 	The probability to dodge an attack is positive for all units.
     *       	| result >= 0.0
     */
    @Basic
    public double getDodgingProbability() {
        return (0.20*(defender.getAgility())/(unit.getAgility()));
    }

    /**
     * Let defend this unit against the given attacker.
     *
     * @effect The unit first tries to dodge the attack, then blocks it.
     * 			If it fails to dodge and block, this unit will lose hitpoins.
     */
    public void defend(){
        //setCurrentActivity(this.getCurrentActivity());// TODO: check if this is still correct
        //dodging
        if ((randInt(0,99)/100.0) < this.getDodgingProbability()){
            Boolean validDodge = false;
            while(! validDodge) {// TODO: in part2 replace this by a list of valid positions and choose a random element from that list
                int dodgeX = randInt(-1, 1);
                int dodgeY = randInt(-1, 1);
                Vector newPos = defender.getPosition().add(new Vector(dodgeX, dodgeY, 0));
                if ((dodgeX != 0 || dodgeY != 0) &&
                        (isValidDodgePos(newPos.getCubeCoordinates()))) {
                    validDodge = true;
                    defender.setPosition(defender.getPosition().add(new Vector(dodgeX,dodgeY,0)));
                }
            }
        }// fails to block
        else if (!((randInt(0,99)/100.0) < this.getBlockingProbability())){
            defender.removeHitpoints(this.getDamagingPoints());
            //this.setHitpoints(this.getHitpoints()- this.getDamagingPoints(attacker));
            unit.addXP(ATTACK_XP);// TODO: let ActivityController set the XP via getXp and wasSuccessful
            return;
        }
        defender.addXP(ATTACK_XP);
    }

    /**
     * Check whether a given position is a good dodging position.
     * @param position
     * The position to check against
     * @return true if this.getWorld().isValidPosition(position)
     * 				&& this.getWorld().isCubePassable(position)
     */
    private boolean isValidDodgePos(Vector position){
        if(unit.getWorld().isValidPosition(position) && unit.getWorld().isCubePassable(position))
            return true;
        return false;
    }
}
