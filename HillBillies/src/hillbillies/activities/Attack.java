package hillbillies.activities;

import be.kuleuven.cs.som.annotate.Basic;
import hillbillies.model.Cube;
import hillbillies.model.IWorld;
import hillbillies.model.Unit;
import hillbillies.utils.Vector;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static hillbillies.utils.Utils.randInt;

/**
 * Created by Bram on 29-3-2016.
 */
public class Attack extends Activity{

    /**
     * Constant reflecting the duration of an attack.
     */
    public static final double ATTACK_DURATION = 0.1d;

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
	/**
	 * Let the unit of this activity attack the defender.
	 * @post   	The orientation of this unit and the defender is changed. 
	 * 			They are orientated to each other.
	 *			| (new defender).getOrientation() == -this.setOrientation((float)Math.atan2(defender.getPosition().Y()-this.getPosition().Y(), (defender.getPosition().X()-this.getPosition().X())))
	 * 			| (new unit).getOrientation() == this.setOrientation((float)Math.atan2(defender.getPosition().Y()-this.getPosition().Y(), (defender.getPosition().X()-this.getPosition().X())))
	 * @post	the unit is attacking.
	 * 			| new.isAttacking() == true
	 * @post	the current activity of the unit is attack
	 * 			| new.getCurrentActivity() == Activity.Attack
	 * @effect	The defender defends this attack
	 * 			| defender.defend()	
	 */
    @Override
    public void startActivity() {
        double dx = (defender.getPosition().X()-unit.getPosition().X());
        double dy = (defender.getPosition().Y()-unit.getPosition().Y());
        defender.setOrientation((float)Math.atan2(-dy, -dx));
        unit.setOrientation((float)Math.atan2(dy, dx));

        this.defend();
        defender.restartActivity(true);
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
     * 			the defender has more hitpoints than MIN_HITPOINTS and
     * 			the defenders position is accessible to attack
     *       | result == (this.getId()!=defender.getId() &&
     *				!this.isAttacking &&
     *				!this.isInitialRestMode() &&
     *				(defender.getHitpoints()> MIN_HITPOINTS)
     * 				(Math.abs(defender.getPosition().cubeX()-this.getPosition().cubeX())<=1) &&
     *				(Math.abs(defender.getPosition().cubeY()-this.getPosition().cubeY())<=1) &&
     *				(Math.abs(defender.getPosition().cubeZ()-this.getPosition().cubeZ())<=1)) &&
     *				this.getFaction() != defender.getFaction() &&
     *				this.getCurrentActivity()!=Activity.FALLING &&
     *				defender.getCurrentActivity()!=Activity.FALLING &&
     *				this.isAccessible(defender.getPosition.getCubeCoordinates()
     */
    @Override
    public boolean isAbleTo() {
        return unit.getId() != defender.getId() &&
                !unit.isAttacking() &&
                !unit.isInitialRestMode() &&
                (defender.getHitpoints() > Unit.MIN_HITPOINTS) &&
                unit.getWorld().getNeighbouringCubesPositions(
                        defender.getPosition().getCubeCoordinates()
                ).contains(unit.getPosition().getCubeCoordinates()) &&
                unit.getFaction() != defender.getFaction() &&
                !defender.isFalling() &&
                !defender.isTerminated() &&
                !unit.isFalling() &&
                this.isAccessible( defender.getPosition().getCubeCoordinates());
    }

    /**
     * Activity specific code to check whether this Activity can be interrupted by nextActivity.
     *
     * @param nextActivity The Activity which will be started when this Activity is interrupted.
     * @return True if this Activity should be interrupted for nextActivity.
     */
    @Override
    protected boolean shouldInterruptFor(Activity nextActivity) {
        return false;// An attack cannot be interrupted
    }

    /**
     * Returns the amount of XP the Unit will get when this Activity stops successfully.
     */
    @Override
    public int getXp() {
        return ATTACK_XP;
    }

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
        //dodging
        if ((randInt(0,99)/100.0) < this.getDodgingProbability()){
            List<Vector> validDodgePositions = new ArrayList<>();
            unit.getWorld().getDirectlyAdjacentCubesSatisfying(
                    validDodgePositions,
                    defender.getPosition().getCubeCoordinates(),
                    cube -> cube.getPosition().difference(defender.getPosition()).Z() == 0 &&
                            isValidDodgePos(cube.getPosition().getCubeCoordinates()),
                    Cube::getPosition);
            if(validDodgePositions.size()>0)
                defender.setPosition(validDodgePositions.get(randInt(0,validDodgePositions.size()-1)));
            else {
                //PANIC
                assert false;
            }
            defender.addXP(ATTACK_XP);
        }// fails to block
        else if (!((randInt(0,99)/100.0) < this.getBlockingProbability())){
            defender.removeHitpoints(this.getDamagingPoints());
            unit.addXP(ATTACK_XP);
        }else
            defender.addXP(ATTACK_XP);

        defender.restartActivity();
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
    /**
     * Check whether other position is an accessible position to attack too from units position.
     * @param unit The unit that attacks.
     * @param otherPosition The position to attack another unit.
     * @return True if otherPosition is indeed a accessible position to attack to from  units position
     *          | if(!unit.getWorld().isCubePassable(otherPosition))
     *          |       result==false
     *          | if(foreach Vector d in nextPosition.difference(fromPosition).decompose() :
     *          |       !unit.getWorld().isCubePassable(unit.getPosition().add(d)) || !unit.getWorld.isCubePassable(otherPosition.difference(d))
     *          |           result==false
     *          | else
     *          |       result==true
     * @throws IllegalArgumentException
     *          When units position or otherPosition are not effective.
     *          | unit.getPosition()==null || nextPosition==null
     */
    protected static boolean isAccessible(Unit unit, Vector otherPosition) throws IllegalArgumentException{
        if(unit==null || otherPosition==null)
            throw new IllegalArgumentException("The other position must be an effective position in order to check his validity.");
    	Vector unitPosition = unit.getPosition().getCubeCoordinates();
    	otherPosition = otherPosition.getCubeCoordinates();
        if(!unit.isValidPosition(otherPosition)) return false;// Check if it's a valid position itself
        for(Vector d : otherPosition.difference(unitPosition).decompose()){
            if(!unit.getWorld().isCubePassable(unitPosition.add(d)) || !unit.getWorld().isCubePassable((otherPosition.difference(d)))) 
            	return false;// Check if surrounding positions are valid too (prevent corner glitch)
        }
        return true;
    }
    private boolean isAccessible(Vector otherPosition) throws IllegalArgumentException{
    	return isAccessible(unit,otherPosition);
    }
}
