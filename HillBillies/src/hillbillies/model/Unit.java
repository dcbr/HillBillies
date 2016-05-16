package hillbillies.model;

import be.kuleuven.cs.som.annotate.*;
import hillbillies.utils.Vector;
import hillbillies.activities.*;

import java.util.Stack;
import static hillbillies.utils.Utils.*;

import java.util.*;

/**
 * Class representing a Hillbilly unit
 * @author Kenneth & Bram
 * @version 2.5
 *
 * @invar Each Unit has a unique Id.
 * | for each Unit u, for each Unit o -> u.getId()!=o.getId() if u!=o
 * @invar The name of each Unit must be a valid name for any
 * Unit.
 * | isValidName(getName())
 * @invar  The strength of each unit must be a valid strength for any
 *         unit.
 *       | isValidStrength(getStrength())
 * @invar  The agility of each unit must be a valid agility for any
 *         unit.
 *       | isValidAgility(getAgility())
 * @invar  The toughness of each unit must be a valid toughness for any
 *         unit.
 *       | isValidToughness(getToughness())
 * @invar  The weight of each unit must be a valid weight for any
 *         unit.
 *       | isValidWeight(getWeight())
 * @invar  The stamina of each unit must be a valid stamina for any
 *         unit.
 *       | isValidStamina(getStamina())
 * @invar  The hitpoints of each unit must be a valid hitpoints for any
 *         unit.
 *       | isValidHitpoints(getHitpoints())
 * @invar The orientation of each Unit must be a valid orientation for any
 * Unit.
 * | isValidOrientation(getOrientation())
 * @invar The faction of each Unit must be a valid faction for any
 * Unit.
 * | isValidFaction(getFaction())
 * @invar The task of each Unit must be a valid task for any
 * Unit.
 * | isValidTask(getTask())
 */
public class Unit extends WorldObject {

	//region Constants

	/**
	 * Constant reflecting the length of a units ID.     
	 */
	private static long ID = 0;
	/**
	 * Constant reflecting the allowed name pattern    
	 */
    private static final String ALLOWED_NAME_PATTERN = "[a-zA-Z \"']+";
	/**
	 * Constant reflecting the minimum strength of a unit.    
	 */
    public static final int MIN_STRENGTH = 1;
	/**
	 * Constant reflecting the maximum strength of a unit.    
	 */
    public static final int MAX_STRENGTH = 200;
	/**
	 * Constant reflecting the minimum agility of a unit.    
	 */
    public static final int MIN_AGILITY = 1;
	/**
	 * Constant reflecting the maximum agility of a unit.    
	 */
    public static final int MAX_AGILITY = 200;
	/**
	 * Constant reflecting the minimum toughness of a unit.    
	 */
    public static final int MIN_TOUGHNESS = 1;
	/**
	 * Constant reflecting the maximum toughness of a unit.    
	 */
    public static final int MAX_TOUGHNESS = 200;
	/**
	 * Constant reflecting the minimum weight of a unit.    
	 */
    public static final int MIN_WEIGHT = 1;
	/**
	 * Constant reflecting the maximum weight of a unit.    
	 */
    public static final int MAX_WEIGHT = 200;
	/**
	 * Constant reflecting the maximum XP of a unit.
	 */
    public static final int MAX_XP = 10;
	/**
	 * Constant reflecting the minimum stamina of a unit.
	 */
    public static final int MIN_STAMINA = 0;
	/**
	 * Constant reflecting the minimum hitpoints of a unit.    
	 */
    public static final int MIN_HITPOINTS = 0;
	/**
	 * Constant reflecting the minimum orientation of a unit.    
	 */
	public static final float MIN_ORIENTATION = 0;
	/**
	 * Constant reflecting the maximum strength of a unit.    
	 */
	public static final float MAX_ORIENTATION = 2*(float)Math.PI;
	
	/**
	 * Constant reflecting the minimum initial strength of a unit.    
	 */
    public static final int INITIAL_MIN_STRENGTH = 25;
	/**
	 * Constant reflecting the maximal initial strength of a unit.    
	 */
    public static final int INITIAL_MAX_STRENGTH = 100;
	/**
	 * Constant reflecting the minimum initial agility of a unit.    
	 */
    public static final int INITIAL_MIN_AGILITY = 25;
	/**
	 * Constant reflecting the maximal initial agility of a unit.    
	 */
    public static final int INITIAL_MAX_AGILITY = 100;
	/**
	 * Constant reflecting the minimum initial toughness of a unit.    
	 */
    public static final int INITIAL_MIN_TOUGHNESS = 25;
	/**
	 * Constant reflecting the maximal initial toughness of a unit.    
	 */
    public static final int INITIAL_MAX_TOUGHNESS = 100;
	/**
	 * Constant reflecting the minimum initial weight of a unit.    
	 */
    public static final int INITIAL_MIN_WEIGHT = 25;
	/**
	 * Constant reflecting the maximal initial weight of a unit.    
	 */
    public static final int INITIAL_MAX_WEIGHT = 100;
	/**
	 * Constant reflecting the minimum initial stamina points of a unit.    
	 */
	public static final int INITIAL_MIN_STAMINA = 1;
	/**
	 * Constant reflecting the minimum initial hitpoints of a unit.    
	 */
	public static final int INITIAL_MIN_HITPOINTS = 1;
	/**
	 * Constant reflecting the initial orientation of a unit.    
	 */
	public static final float INITIAL_ORIENTATION = (float)Math.PI/2;
	//endregion

	//region Private members

	/**
	 * Variable registering the Id of this Unit.
	 */
	private final long Id;

	/**
	 * Variable registering the name of this Unit.
	 */
	private String name;

	/**
	 * Variable registering the strength of this unit.
	 */
	private int strength;

	/**
	 * Variable registering the agility of this unit.
	 */
	private int agility;

	/**
	 * Variable registering the toughness of this unit.
	 */
	private int toughness;

	/**
	 * Variable registering the weight of this unit.
	 */
	private int weight;

	/**
	 * Variable registering the stamina of this unit.
	 */
	private int stamina;

	/**
	 * Variable registering the hitpoints of this unit.
	 */
	private int hitpoints;

	/**
	 * Variable registering the orientation of this Unit.
	 */
	private float orientation;
	//endregion

	//region Static getters

	/**
	 * Return the lowest possible value for initial weight of this unit.
	 * @param	strength
	 * 			The strength to check against.
	 * @param	agility
	 * 			The agility to check against.
	 * @return 	The lowest possible value for stamina of all
	 *         	units is not below MIN_WEIGHT for all units.
	 *       	| result >= INITIAL_MIN_WEIGHT
	 */
	@Basic
	public static int getInitialMinWeight(int strength, int agility) {
		int minWeight = (strength + agility)/2;
		if (minWeight <= INITIAL_MIN_WEIGHT)
			return (INITIAL_MIN_WEIGHT);
		return minWeight;
	}
	/**
	 * Return the highest possible value for hitpoints of this unit.
	 * @param	weight
	 * 			The weight to check against.
	 * @param	toughness
	 * 			The toughness to check against
	 * @return The highest possible value for hitpoints of all
	 *         units is not below the lowest possible value
	 *         for hitpoints of all units.
	 *       | result >= MIN_HITPOINTS
	 */
	@Basic
	public static int getMaxHitpoints(int weight, int toughness) {
		return ((int)Math.ceil(200*weight/100.0 * toughness/100.0));
	}
	/**
	 * Return the highest possible value for stamina of this unit.
	 * @param	weight
	 * 			The weight to check against.
	 * @param	toughness
	 * 			The toughness to check against
	 * @return 	The highest possible value for stamina of all
	 *         	units is not below the lowest possible value
	 *         	for stamina of all units.
	 *       	| result >= MIN_STAMINA
	 */
	@Basic
	public static int getMaxStamina(int weight, int toughness) {
		return ((int)Math.ceil(200*weight/100.0 * toughness/100.0));
	}
	
	/**
	 * Return the highest possible parameter for correct weight of this unit.
	 * @param	weight
	 * 			The weight to check against.
	 * @param	param
	 * 			The parameter to check against
	 * @return 	The highest possible value for the parameter
	 */
	@Basic
	public static int getMaxValueAgainstWeight(int weight, int param) {
		return (2*weight-param);
	}
	
	/**
	 * Return the lowest possible value for weight of this unit.
	 * @param	strength
	 * 			The strength to check against.
	 * @param	agility
	 * 			The agility to check against.
	 * @return 	The lowest possible value for weight
	 * 			is not below MIN_WEIGHT for all units.
	 *       	| result >= MIN_WEIGHT
	 */
	@Basic
	public static int getMinWeight(int strength, int agility) {
		int minWeight = (strength + agility)/2;
		if (minWeight <= MIN_WEIGHT)
			return (MIN_WEIGHT);
		return minWeight;
	}

	//endregion

	//region Static checkers
	/**
	 * Check whether the given agility is a valid agility for
	 * any unit.
	 *  
	 * @param  agility
	 *         The agility to check.
	 * @return is true if strength is between MIN_AGILITY and MAX_AGILITY
	 *       | result == (MIN_AGILITY <= agility <= MAX_AGILITY)
	*/
	public static boolean isValidAgility(int agility) {
		return (MIN_AGILITY <= agility && agility <= MAX_AGILITY);
	}
	/**
	 * Check whether the given hitpoints are valid hitpoints for
	 * any unit.
	 *
	 * @param  	hitpoints
	 *         	The hitpoints to check.
	 * @param	weight
	 * 			The weight to check against.
	 * @param 	toughness
	 * 			The toughness to check against.
	 * @return
	 *       	| result == (MIN_HITPOINTS <= hitpoints <= getMaxHitpoints(weight, toughness))
	*/
	public static boolean isValidHitpoints(int hitpoints, int weight, int toughness) {
		return (MIN_HITPOINTS <= hitpoints && hitpoints <= getMaxHitpoints(weight, toughness));
	}
	/**
	 * Check whether the given initial agility is a valid initial agility for
	 * any unit.
	 *  
	 * @param  agility
	 *         The agility to check.
	 * @return is true if agility is between INITIAL_MIN_AGILITY and INITIAL_MAX_AGILITY
	 *       | result == (INITIAL_MIN_AGILITY <=strength<= INITIAL_MAX_AGILITY)
	*/
	public static boolean isValidInitialAgility(int agility) {
		return (INITIAL_MIN_AGILITY <= agility  && agility <= INITIAL_MAX_AGILITY);
	}
	/**
	 * Check whether the given initial hitpoints are valid initial hitpoints for
	 * any unit.
	 *
	 * @param  	hitpoints
	 *         	The initial hitpoints to check.
	 * @param	weight
	 * 			The weight to check against.
	 * @param 	toughness
	 * 			The toughness to check against.
	 * @return
	 *       	| result == (INITIAL_MIN_HITPOINTS <= hitpoints <= getMaxHitpoints(weight, toughness))
	*/
	public static boolean isValidInitialHitpoints(int hitpoints, int weight, int toughness) {
		return (INITIAL_MIN_HITPOINTS <= hitpoints && hitpoints <= getMaxHitpoints(weight, toughness));
	}

	/**
	 * Check whether the given initial stamina is a valid initial stamina for
	 * any unit.
	 *
	 * @param  	stamina
	 *         	The initial stamina to check.
	 * @param	weight
	 * 			The weight to check against.
	 * @param	toughness
	 * 			The toughness to check against.
	 * @return
	 *       	| result == (INITIAL_MIN_STAMINA <= stamina <= getMaxStamina(weight, toughness) )
	*/
	public static boolean isValidInitialStamina(int stamina, int weight, int toughness) {
		return (stamina <= getMaxStamina(weight, toughness) && stamina >= INITIAL_MIN_STAMINA);
	}

	/**
	 * Check whether the given initial strength is a valid initial strength for
	 * any unit.
	 *  
	 * @param  strength
	 *         The strength to check.
	 * @return is true if strength is between INITIAL_MIN_STRENGTH and INITIAL_MAX_STRENGTH
	 *       | result == (INITIAL_MIN_STRENGTH <=strength<= INITIAL_MAX_STRENGTH)
	*/
	public static boolean isValidInitialStrength(int strength) {
		return (INITIAL_MIN_STRENGTH <= strength  && strength <= INITIAL_MAX_STRENGTH);
	}
	/**
	 * Check whether the given initial toughness is a valid initial toughness for
	 * any unit.
	 *  
	 * @param  toughness
	 *         The initial toughness to check.
	 * @return is true if strength is between MIN_TOUGHNESS and MAX_TOUGHNESS
	 *       | result == (INITIAL_MIN_TOUGHNESS <= toughness <= INITIAL_MAX_TOUGHNESS)
	*/
	public static boolean isValidInitialToughness(int toughness) {
		return (INITIAL_MIN_TOUGHNESS <= toughness && toughness <= INITIAL_MAX_TOUGHNESS);
	}
    /**
	 * Check whether the given initial weight is a valid initial weight for
	 * any unit.
	 *  
	 * @param  	weight
	 *       	The initial weight to check.
	 * @param	strength
	 * 			The strength to check against.
	 * @param	agility
	 * 			The agility to check against.
	 * @return 	is true if weight is between getInitialMinWeight() and INITIAL_MAX_WEIGHT.
	 *       	| result == (getInitialMinWeight() <= weight <= INITIAL_MAX_WEIGHT)
	*/
	public static boolean isValidInitialWeight(int weight, int strength, int agility) {
		return (getInitialMinWeight(strength, agility) <= weight && weight <= INITIAL_MAX_WEIGHT);
	}
    /**
     * Check whether the given name is a valid name for
     * any Unit.
     *
     * @param name
     * The name to check.
     * @return True if the first character of name is in UpperCase AND name consists of at least 2 characters AND
     *          name contains only allowed characters (defined by the pattern ALLOWED_NAME_PATTERN)
     * | result == name.length()>=2 && Character.isUpperCase(name.charAt(0)) && name.matches(ALLOWED_NAME_PATTERN)
     */
    public static boolean isValidName(String name) {
        return name.length()>=2 && Character.isUpperCase(name.charAt(0)) && name.matches(ALLOWED_NAME_PATTERN);
    }
    /**
	 * Check whether the given orientation is a valid orientation for
	 * any Unit.
	 *
	 * @param orientation
	 * The orientation to check.
	 * @return
	 * | result == MIN_ORIENTATION <= orientation <= MAX_ORIENTATION
	 */
	public static boolean isValidOrientation(float orientation) {
		return (orientation >= MIN_ORIENTATION && orientation <= MAX_ORIENTATION);
	}
    /**
	 * Check whether the given stamina is a valid stamina for
	 * any unit.
	 *
	 * @param  	stamina
	 *         	The stamina to check.
	 * @param	weight
	 * 			The weight to check against.
	 * @param	toughness
	 * 			The toughness to check against.
	 * @return
	 *       	| result == (MIN_STAMINA <= stamina <= getMaxStamina(weight, toughness) )
	*/
	public static boolean isValidStamina(int stamina, int weight, int toughness) {
		return (stamina <= getMaxStamina(weight, toughness) && stamina >= MIN_STAMINA);
	}

	
	/**
	 * Check whether the given strength is a valid strength for
	 * any unit.
	 *  
	 * @param  strength
	 *         The strength to check.
	 * @return is true if strength is between MIN_STRENGTH and MAX_STRENGTH
	 *       | result == (MIN_STRENGTH <=strength<= MAX_STRENGTH)
	*/
	public static boolean isValidStrength(int strength) {
		return (MIN_STRENGTH <= strength  && strength <= MAX_STRENGTH);
	}
	
	/**
	 * Check whether the given toughness is a valid toughness for
	 * any unit.
	 *  
	 * @param  toughness
	 *         The toughness to check.
	 * @return is true if strength is between MIN_TOUGHNESS and MAX_TOUGHNESS
	 *       | result == (MIN_TOUGHNESS <= toughness <= MAX_TOUGHNESS)
	*/
	public static boolean isValidToughness(int toughness) {
		return (MIN_TOUGHNESS <= toughness && toughness <= MAX_TOUGHNESS);
	}
	
	/**
	 * Check whether the given weight is a valid weight for
	 * any unit.
	 *  
	 * @param  	weight
	 *       	The weight to check.
	 * @param	strength
	 * 			The strength to check against.
	 * @param	agility
	 * 			The agility to check against.
	 * @return 	is true if weight is between getMinWeight() and MAX_WEIGHT.
	 *       	| result == (this.getMinWeight() <= weight <= MAX_WEIGHT )
	*/
	public static boolean isValidWeight(int weight, int strength, int agility) {
		return (getMinWeight(strength, agility) <= weight && weight <= MAX_WEIGHT);
	}

	//endregion

	//region Getters

	/**
	 * Return the agility of this unit.
	 */
	@Basic @Raw
	public int getAgility() {
		return this.agility;
	}

	/**
	 * Return the current speed of this unit.
	 */
	public double getCurrentSpeed(){
		if(!this.isMoving() && !this.isFalling())
			return (0d);
		return ((Move)this./*activityController.*/getCurrentActivity()).getCurrentSpeed();
	}

	/**
	 * Return the hitpoints of this unit.
	 */
	@Basic @Raw
	public int getHitpoints() {
		return this.hitpoints;
	}

	/**
	 * Return the Id of this Unit.
	 */
	@Basic
	@Raw
	@Immutable
	public long getId() {
		return this.Id;
	}

	/**
	 * Return the name of this Unit.
	 */
	@Basic
	@Raw
	public String getName() {
		return this.name;
	}
	
	/**
	 * Return the orientation of this Unit.
	 */
	@Basic
	@Raw
	public float getOrientation() {
		return this.orientation;
	}

	/**
	 * Return the stamina of this unit.
	 */
	@Basic @Raw
	public int getStamina() {
		return this.stamina;
	}

	/**
	 * Return the strength of this unit.
	 */
	@Basic @Raw
	public int getStrength() {
		return this.strength;
	}

	/**
	 * Return the toughness of this unit.
	 */
	@Basic @Raw
	public int getToughness() {
		return this.toughness;
	}

	/**
	 * Return the weight of this unit.
	 */
	@Basic @Raw
	public int getWeight() {
		return this.weight + (this.isCarryingMaterial() ? this.getCarriedMaterial().getWeight() : 0);
	}

	//endregion

	//region Checkers
	/**
	 * Return a boolean indicating whether or not this person
	 * is attacking.
	 */
	/*@Raw
	public boolean isAttacking() {
		return this.activityController.isAttacking();
	}*/

	/**
	 * Return a boolean indicating whether or not the units default behaviour is activated.
	 */
	@Basic
	public boolean isDefaultActive(){
		return this./*activityController.*/getCurrentActivity().isDefault();
	}

	/**
	 * Return a boolean indicating whether or not this person
	 * is in its initial resting period.
	 */
	public boolean isInitialRestMode(){
		return this.isResting() && ((Rest)this./*activityController.*/getCurrentActivity()).isInitialRestMode();
	}
	/**
	 * Return a boolean indicating whether or not this person
	 * is moving.
	 */
	/*public boolean isMoving(){
		return this.activityController.isMoving();
	}
	public boolean isResting(){
		return this.activityController.isResting();
	}*/
	/**
	 * Return a boolean indicating whether or not this person
	 * is sprinting.
	 */
	public boolean isSprinting(){
		return this.isMoving() && !this.isFalling() && ((Move)this./*activityController.*/getCurrentActivity()).isSprinting();
	}

	/*public boolean isWorking(){
		return this.activityController.isWorking();
	}*/

	//endregion

	//region Setters
	/**
	 * Set the agility of this unit to the given agility.
	 *
	 * @param  agility
	 *         The new agility for this unit.
	 * @post   If the given agility is a valid agility for any unit,
	 *         the agility of this new unit is equal to the given
	 *         agility.
	 *       | if (isValidAgility(agility))
	 *       |   then new.getAgility() == agility
	 * @post   If the agility is to high for the weight, 
	 * 		   the agility will be set on the highest possible value.
	 * @post   If the weight of a unit is below the lowest possible value, 
	 * 		   it will be set to that value.
	 * 		 | if (this.getWeight() < getMinWeight(this.getStrength(), this.getAgility()))
			 |	  (new this).getWeight == getMinWeight(getStrength(), getAgility());
	 */
	@Raw
	public void setAgility(int agility) {
		if (isValidAgility(agility))
			this.agility = agility;
		int minWeight = getMinWeight(this.getStrength(), this.getAgility());
		if(minWeight > MAX_WEIGHT)
			this.setAgility(getMaxValueAgainstWeight(MAX_WEIGHT, this.getStrength()));
		if (this.getWeight() < minWeight)
			this.setWeight(minWeight);
	}



	/**
	 * Set the hitpoints of this unit to the given hitpoints.
	 *
	 * @param	hitpoints
	 *       	The new hitpoints for this unit.
	 * @pre    	The given hitpoints must be a valid hitpoints for any
	 *         	unit.
	 *       	| isValidHitpoints(hitpoints)
	 * @pre  	The units weight and toughness should already have been set.
	 *			| isValidWeight(this.getWeight()) && isValidToughness(this.getToughness())
	 * @post   	The hitpoints of this unit is equal to the given
	 *         	hitpoints.
	 *       	| new.getHitpoints() == hitpoints
	 */
	@Raw
	public void setHitpoints(int hitpoints) {
		assert isValidHitpoints(hitpoints, this.getWeight(), this.getToughness());
		this.hitpoints = hitpoints;
	}

	/**
	 * Substract the given hitpoints from the current hitpoints.
	 * @param hitpoints
	 * The hitpoints to substract.
	 * @pre		The units hitpoints should already have been set.
	 *			| isValidHitpoints(this.getHitPoints, this.getWeight(), this.getToughness())
	 * @effect	The hintpoints are set to this.getHitpoints()-hitpoints
	 * 			| new.getHitpoints()
	 * @effect	If the hitpoints reaches MIN_HITPOINTS, the unit will terminate.
	 * 			| this.terminate()
	 */
	@Raw
	public void removeHitpoints(int hitpoints){
		assert hitpoints>=0;
		int newHit = getHitpoints()-hitpoints;
		if (newHit <= MIN_HITPOINTS)
			this.terminate();
		else
			setHitpoints(newHit);
	}

	/**
	 * Set the initial hitpoints of this unit to the given hitpoints.
	 *
	 * @param	hitpoints
	 *       	The initial hitpoints for this unit.
	 * @pre    	The given initial hitpoints must be valid hitpoints for any
	 *         	unit.
	 *       	| isValidInitialHitpoints(hitpoints)
	 * @pre  	The units weight and toughness should already have been set.
	| isValidWeight(this.getWeight()) && isValidToughness(this.getToughness())
	 * @post   	The hitpoints of this unit is equal to the given
	 *         	hitpoints.
	 *       	| new.getHitpoints() == hitpoints
	 */
	@Raw
	public void setInitialHitpoints(int hitpoints) {
		assert isValidInitialHitpoints(hitpoints, this.getWeight(), this.getToughness());
		this.hitpoints = hitpoints;
	}

	/**
	 * Set the initial stamina of this unit to the given stamina.
	 *
	 * @param  	stamina
	 *         	The initial stamina for this unit.
	 * @pre    	The given stamina must be a valid initial stamina for any
	 *         	unit.
	 *       	| isValidInitialStamina(stamina)
	 * @pre		The units weight and toughness should already have been set.
	 *       	| isValidWeight(this.getWeight()) && isValidToughness(this.getToughness())
	 * @post   	The stamina of this unit is equal to the given initial
	 *         	stamina.
	 *       	| new.getStamina() == stamina
	 */
	@Raw
	public void setInitialStamina(int stamina) {
		assert isValidInitialStamina(stamina, this.getWeight(), this.getToughness());
		this.stamina = stamina;
	}

	/**
	 * Set the name of this Unit to the given name.
	 *
	 * @param name
	 * The new name for this Unit.
	 * @post The name of this new Unit is equal to
	 * the given name.
	 * | new.getName() == name
	 * @throws IllegalArgumentException * The given name is not a valid name for any
	 * Unit.
	 * | ! isValidName(getName())
	 */
	@Raw
	public void setName(String name) throws IllegalArgumentException {
		if (! isValidName(name))
			throw new IllegalArgumentException();
		this.name = name;
	}

	/**
	 * Set the orientation of this Unit to the given orientation.
	 * @param orientation
	 * The new orientation for this Unit.
	 * @post The orientation of this unit is equal to the rest of the division
	 * between the given orientation and MAX_ORIENTATION. If the rest is negative, 
	 * the rest is added with MAX_ORIENTATION.  
	 * | new.getOrientation() == orientation%MAX_ORIENTATION
	 * | if (orientation%MAX_ORIENTATION < 0)
	 * | 	new.getOrientation() == (orientation%MAX_ORIENTATION) + MAX_ORIENTATION
	 */
	@Raw
	public void setOrientation(float orientation) {
		this.orientation = orientation%MAX_ORIENTATION;// Deal with too large orientations
		if(this.orientation<0) this.orientation += MAX_ORIENTATION;// Deal with negative orientations
	}

	/**
	 * Set the stamina of this unit to the given stamina.
	 *
	 * @param  	stamina
	 *         	The new stamina for this unit.
	 * @pre    	The given stamina must be a valid stamina for any
	 *         	unit.
	 *       	| isValidStamina(stamina)
	 * @pre		The units weight and toughness should already have been set.
	 *       	| isValidWeight(this.getWeight()) && isValidToughness(this.getToughness())
	 * @post   	The stamina of this unit is equal to the given
	 *         	stamina.
	 *       	| new.getStamina() == stamina
	 */
	@Raw
	public void setStamina(int stamina) {
		assert isValidStamina(stamina, this.getWeight(), this.getToughness());
		this.stamina = stamina;
	}

	/**
	 * Set the strength of this unit to the given strength.
	 *
	 * @param  strength
	 *         The new strength for this unit.
	 * @post   If the given strength is a valid strength for any unit,
	 *         the strength of this new unit is equal to the given
	 *         strength.
	 *       | if (isValidStrength(strength))
	 *       |   then new.getStrength() == strength
	 * @post   If the strength is to high for the weight, 
	 * 		   the strength will be set on the highest possible value.
	 * @post   If the weight of a unit is below the lowest possible value, 
	 * 		   it will be set to that value.
	 * 		 | if (this.getWeight() < getMinWeight(this.getStrength(), this.getAgility()))
			 |	  (new this).getWeight == getMinWeight(getStrength(), getAgility());
	 */
	@Raw
	public void setStrength(int strength) {
		if (isValidStrength(strength))
			this.strength = strength;
		int minWeight = getMinWeight(this.getStrength(), this.getAgility());
		if(minWeight > MAX_WEIGHT)
			this.setStrength(getMaxValueAgainstWeight(MAX_WEIGHT, this.getAgility()));
		if (this.getWeight()< minWeight)
			this.setWeight(minWeight);
	}

	/**
	 * Set the toughness of this unit to the given toughness.
	 *
	 * @param  toughness
	 *         The new toughness for this unit.
	 * @post   If the given toughness is a valid toughness for any unit,
	 *         the toughness of this new unit is equal to the given
	 *         toughness.
	 *       | if (isValidToughness(toughness))
	 *       |   then new.getToughness() == toughness
	 */
	@Raw
	public void setToughness(int toughness) {
		if (isValidToughness(toughness))
			this.toughness = toughness;
	}

	/**
	 * Set the weight of this unit to the given weight.
	 *
	 * @param  weight
	 *         The new weight for this unit.
	 * @post   If the given weight is a valid weight for any unit,
	 *         the weight of this new unit is equal to the given
	 *         weight.
	 *       | if (isValidWeight(weight))
	 *       |   then new.getWeight() == weight
	 */
	@Raw
	public void setWeight(int weight) {
		if (isValidWeight(weight, this.getStrength(), this.getAgility()))
			this.weight = weight;
	}
	//endregion

	//region Constructors
	/**
	 * Initialize this new Unit in the given world. All other properties are set to random chosen
	 * initial values.
	 * @param world The world this new Unit belongs to
	 * @effect This Unit is initialized in the given world with random initial values for its other properties
	 * 			| this(world, random toughness, random weight)
	 */
	public Unit(IWorld world) throws IllegalArgumentException{
		this(world,randInt(INITIAL_MIN_TOUGHNESS, INITIAL_MAX_TOUGHNESS),
				randInt(getInitialMinWeight(INITIAL_MIN_STRENGTH,INITIAL_MIN_AGILITY), INITIAL_MAX_WEIGHT) );
	}
	/**
	 * Initialize this new Unit in the given world with the given toughness and weight. 
	 * All other properties are set to random chosen initial values.
	 * @param world The world this new Unit belongs to
	 * @param toughness The toughness of this new Unit
	 * @param weight The weight of this new Unit
	 * @effect This Unit is initialized in the given world with random initial values for its other properties
	 * 			| this(world, random Name, random position, random strength, random agility, toughness, weight, random stamina, random hitpoints)
	 */
	private Unit(IWorld world, int toughness, int weight) throws IllegalArgumentException{
		this(world, "Unnamed Unit", world.getSpawnPosition(), randInt(INITIAL_MIN_STRENGTH, INITIAL_MAX_STRENGTH),
				randInt(INITIAL_MIN_AGILITY, INITIAL_MAX_AGILITY), toughness, weight, randInt(INITIAL_MIN_STAMINA, getMaxStamina(weight, toughness)), randInt(INITIAL_MIN_HITPOINTS, getMaxHitpoints(weight, toughness)));
	}
	/**
	 * Initialize this new Unit with given name and position in the given world. All other properties are set to their
	 * default initial values.
	 * @param world The world this new Unit belongs to
	 * @param name The name of this new Unit
	 * @param position The position of this new Unit
	 * @effect This Unit is initialized with the given name and position and the default initial values for its other properties
	 * 			| this(world, name, position, INITIAL_MIN_STRENGTH, INITIAL_MIN_AGILITY, INITIAL_MIN_TOUGHNESS, INITIAL_MIN_WEIGHT, INITIAL_MIN_STAMINA, INITIAL_MIN_HITPOINTS)
	 */
	public Unit(IWorld world, String name, Vector position) throws IllegalArgumentException {
		this(world, name, position, INITIAL_MIN_STRENGTH, INITIAL_MIN_AGILITY, INITIAL_MIN_TOUGHNESS, INITIAL_MIN_WEIGHT, INITIAL_MIN_STAMINA, INITIAL_MIN_HITPOINTS);
	}

	/**
	 * Initialize this new Unit with given name, position, strength, agility, toughness and weight in the given world.
	 * @param world The world this new Unit belongs to
	 * @param name The name for this new Unit
	 * @param position The position of this new Unit in the given world.
	 * @param strength The strength of this new Unit
	 * @param agility The agility of this new Unit
	 * @param toughness The toughness of this new Unit
     * @param weight The weight of this new Unit
	 * @effect This Unit is initialized with the given properties and the maximum values for its remaining properties (stamina and hitpoints)
	 * 			| this(world, name, position, strength, agility, toughness, weight, getMaxStamina(weight, toughness), getMaxHitpoints(weight, toughness)
     */
	public Unit(IWorld world, String name, Vector position, int strength, int agility, int toughness, int weight){
		this(world, name, position, strength, agility, toughness, weight, getMaxStamina(weight, toughness), getMaxHitpoints(weight, toughness));
	}

	/**
	 * Initialize this new Unit with given name, position, strength, agility, toughness, weight, stamina and hitpoints.
	 * @param world The world this new Unit belongs to
	 * @param name
	 * The name for this new Unit.
	 * @param position
	 * The position for this new Unit.
	 * @param  strength
	 *         The strength for this new unit.
	 * @param  agility
	 *         The agility for this new unit.
	 * @param  toughness
	 *         The toughness for this new unit.
	 * @param  weight
	 *         The weight for this new unit.
	 * @param  stamina
	 *         The stamina for this new unit.
	 * @param  hitpoints
	 *         The hitpoints for this new unit.
	 * @pre    The given stamina must be a valid initial stamina for any unit.
	 *       | isValidInitialStamina(stamina)
	 * @pre    The given hitpoints must be valid initial hitpoints for any unit.
	 *       | isValidInitialHitpoints(hitpoints)
	 * @effect This new Unit is initialized as a new WorldObject with
	 *         given position in the given world.
	 *       | super(world, position)
	 * @effect This new Unit is added to the given world. This world will
	 * 			put the Unit in a Faction related to that world.
	 * 			| world.addUnit(this)
	 * @post	This new Unit belongs to a Faction related to the given world.
	 * 			| world.getFactions().contains(this.getFaction()) == true
	 * @effect The name of this new Unit is set to
	 * the given name.
	 * | this.setName(name)
	 * @effect The orientation of this new Unit is set to the default orientation.
	 * 			| this.setOrientation(INITIAL_ORIENTATION)
	 * @post The Id of this new Unit is equal to ID.
	 * 		| this.getId()==ID
	 * @post The static field ID is incremented by 1. This way uniqueness is almost certainly guaranteed.
	 * 		| new ID == ID + 1
	 * @post   If the given strength is a valid strength for any unit,
	 *         the strength of this new unit is equal to the given
	 *         strength. Otherwise, the strength of this new unit is equal
	 *         to INITIAL_MIN_STRENGTH.
	 *       | if (isValidStrength(strength))
	 *       |   then new.getStrength() == strength
	 *       |   else new.getStrength() == DEFAULT_STRENGTH
	 * @post   If the given agility is a valid agility for any unit,
	 *         the agility of this new unit is equal to the given
	 *         agility. Otherwise, the agility of this new unit is equal
	 *         to INITIAL_MIN_AGILITY.
	 *       | if (isValidAgility(agility))
	 *       |   then new.getAgility() == agility
	 *       |   else new.getAgility() == INITIAL_MIN_AGILITY
	 * @post   If the given toughness is a valid toughness for any unit,
	 *         the toughness of this new unit is equal to the given
	 *         toughness. Otherwise, the toughness of this new unit is equal
	 *         to INITIAL_MIN_TOUGHNESS.
	 *       | if (isValidToughness(toughness))
	 *       |   then new.getToughness() == toughness
	 *       |   else new.getToughness() == INITIAL_MIN_TOUGHNESS
	 * @post   If the given weight is a valid weight for any unit,
	 *         the weight of this new unit is equal to the given
	 *         weight. Otherwise, the weight of this new unit is equal
	 *         to INITIAL_MIN_WEIGHT.
	 *       | if (isValidWeight(weight))
	 *       |   then new.getWeight() == weight
	 *       |   else new.getWeight() == INITIAL_MIN_WEIGHT
	 * @post   The stamina of this new unit is equal to the given
	 *         stamina.
	 *       | new.getStamina() == stamina
	 * @post   The hitpoints of this new unit is equal to the given
	 *         hitpoints.
	 *       | new.getHitpoints() == hitpoints
	 */
	public Unit(IWorld world, String name, Vector position, int strength, int agility, int toughness, int weight, int stamina, int hitpoints) throws IllegalArgumentException {
		super(world, position.add(Cube.CUBE_SIDE_LENGTH/2));
		world.addUnit(this);

		this.Id = ID;
		ID++;
		// Defensive
		this.setName(name);
		
		// Total
		if (! isValidInitialStrength(strength))
			strength=INITIAL_MIN_STRENGTH;
		this.setStrength(strength);
		if (! isValidInitialAgility(agility))
			agility = INITIAL_MIN_AGILITY;
		this.setAgility(agility);
		setAgility(agility);
		if (! isValidInitialToughness(toughness))
			toughness = INITIAL_MIN_TOUGHNESS;
		this.setToughness(toughness);
		if (! isValidInitialWeight(weight, strength, agility))
			weight = getInitialMinWeight(strength, agility);
		this.setWeight(weight);
		// Nominal
		this.setInitialStamina(stamina);
		this.setInitialHitpoints(hitpoints);
		// Total
		this.setOrientation(INITIAL_ORIENTATION);

		/*this.activityController = new ActivityController();
		this.activityController.start();*/

		this.activityStack = new Stack<>();
		this.activityStack.push(NONE);
		this.getCurrentActivity().start();
	}

	//endregion

	//region AdvanceTime

	@Override
	public void advanceTime(double dt){
		if(dt<0 || dt>0.2)
			throw new IllegalArgumentException("The parameter dt must be in the range [0;0.2]");
		// Defensively without documentation
		if (!isFalling() && !validatePosition(getPosition())){// TODO: kheb hier && !isMoving() weggedaan, kdenk da daarmee den TODO hieronder inorde is
			this.requestNewActivity(new Fall(this)); //TODO: bij move telkens controleren of hij opeens moet vallen als er een blok veranderd naar passable
		}

		if(!this.isResting()) {
			restTimer += dt;
			if (restTimer >= Rest.REST_INTERVAL && REST.isAbleTo()) {
				rest();
			}
		}

		this.getCurrentActivity().advanceTime(dt);
	}

	@Override
	protected boolean validatePosition(Vector position) {
		IWorld world = this.getWorld();
		if(world instanceof LobbyWorld) return true;
		// world.isValidPosition(position) wordt al gecontroleerd in isValidPosition
		if(world.isCubePassable(position.getCubeCoordinates())){
			if(isAdjacentSolid(position))
				return true;
			if(isFalling())
				return true;
		}
		return false;
	}

	private boolean isAdjacentSolid(Vector position){// TODO: move this to World?
		if(position.cubeZ() == 0)
			return true;
		for(Cube cube : this.getWorld().getDirectlyAdjacentCubes(position.getCubeCoordinates()))
			if(!cube.isPassable())
				return true;
		return false;
	}

	public boolean isLowerSolid(Vector position){// TODO: move this to World?
		if(position.cubeZ() == 0)
			return true;
		if(!this.getWorld().getCube(position.getCubeCoordinates().add(new Vector(0,0,-1))).isPassable())
			return true;
		return false;
	}

	//endregion

	//region Default behaviour

	/**
	 * Activate the default behaviour of this unit.
	 *
	 * @post	Default behaviour of this unit is activated.
	 *       	| new.isDefaultActive() == true
	 */
	public void startDefaultBehaviour(){
		this./*activityController.*/getCurrentActivity().setDefault(true);
	}

	/**
	 * Deactivate the default behaviour of this unit.
	 *
	 * @post   	Default behaviour of this unit is deactivated.
	 *       	| new.isDefaultActive() == false
	 * @post   	The unit stops doing the default.
	 * 			| this.stopDoingDefault()
	 * @post   	The new current activity of this unit is equal to None.
	 * 			| new.getCurrentActivity() == NONE.
	 */
	public void stopDefaultBehaviour(){
		this./*activityController.*/getCurrentActivity().setDefault(false);
		this./*activityController.*/requestActivityFinish(this./*activityController.*/getCurrentActivity());
	}
	//endregion

	//region Fighting
	/**
	 * Let this unit attack the defender.
	 *
	 * @param  defender
	 *         The defender of this attack.
	 * @post   	The orientation of this unit and the defender is changed. 
	 * 			They are orientated to each other.
	 *			| (new defender).getOrientation() == -this.setOrientation((float)Math.atan2(defender.getPosition().Y()-this.getPosition().Y(), (defender.getPosition().X()-this.getPosition().X())))
	 * 			| new.getOrientation() == this.setOrientation((float)Math.atan2(defender.getPosition().Y()-this.getPosition().Y(), (defender.getPosition().X()-this.getPosition().X())))
	 * @post	the attacker is attacking.
	 * 			| new.isAttacking() == true
	 * @post	the current activity is attack
	 * 			| new.getCurrentActivity() == Activity.Attack
	 * @post	The attacker stops the default behaviour if it was doing this.
	 * 			| new.isDoingBehaviour
	 * @effect	The defender defends this attack
	 * 			| defender.defend(this)
	 * @effect	If the attack is successfully, the attacker gains FIGHT_POINTS, else, the defender gains them.
	 * @throws	IllegalArgumentException * the attacker is not able to attack this unit.
	 * 			| !this.isValidAttack(defender)	
	 */
	public void attack(Unit defender) throws IllegalArgumentException{
		/*activityController.*/requestNewActivity(new Attack(this, defender));
	}
	
	/**
	 * Let defend this unit against the given attacker.
	 *
	 * @param  attacker
	 *         The attacker of this attack.
	 * @effect The unit first tries to dodge the attack, then blocks it. 
	 * 			If it fails to dodge and block, this unit will lose hitpoins.
	 */
	public void defend(Unit attacker){// Code is moved to Activity Attack
		// empty method since attacking is controlled by the attacker
	}
	//endregion

	//region Moving
	/**
	 * Method to let the Unit move to an adjacent cube.
	 * @param direction The direction the Unit should move towards. Since this method can only be used to move to
	 *                  neighbouring cubes, each element of the array must have a value of (-)1 or 0.
	 * @post	This unit stops the default behaviour if it was doing this
	 * 			| new.isDoingBehaviour
	 * @throws IllegalStateException
	 * 			When this Unit is not able to move at this moment
	 * 			| this.isAbleToMove() == false
	 * @throws IllegalArgumentException
	 * 			When the given direction points to an invalid position
	 * 			| isValidPosition(this.getPosition().getCubeCenterCoordinates().add(direction)) == false
     */
	public void moveToAdjacent(Vector direction) throws IllegalStateException, IllegalArgumentException{
		/*activityController.*/requestNewActivity(new AdjacentMove(this, direction));
	}

	/**
	 * Method to let the Unit move to a target.
	 * @param targetPosition 
	 * 			The direction the Unit should move towards.
	 * @post	This unit stops the default behaviour if it was doing this
	 * 			| new.isDoingBehaviour
	 * @post	if this is the default behaviour, set the stateDefault to a new state.
	 * 			| new.isDoingBehaviour
	 * @throws IllegalStateException * If this unit isn't able to move.
	 * 		|!this.isAbleToMove()
	 */
	public void moveToTarget(Vector targetPosition) throws IllegalStateException, IllegalArgumentException{
		if(targetPosition==null || !isValidPosition(targetPosition))
			throw new IllegalArgumentException("The target is not a valid position.");
		/*activityController.*/requestNewActivity(new TargetMove(this, targetPosition));
	}

	public void sprint() throws IllegalStateException{
		if(!this.isMoving() || this.isFalling())
			throw new IllegalStateException("Unit is not able to sprint at this moment.");
		((Move)/*activityController.*/getCurrentActivity()).sprint();
	}

	public void stopSprint(){
		if(!this.isMoving() || this.isFalling())
			throw new IllegalStateException("Unit is not moving at this moment.");
		((Move)/*activityController.*/getCurrentActivity()).stopSprint();
	}
	
	public void follow(Unit unit){
		if(unit==null || unit == this)
			throw new IllegalArgumentException("The unit is not a valid unit.");
		Set<Unit> units = new HashSet<>();
		units.add(unit);
		/*activityController.*/requestNewActivity(new TargetMove(this, units));
	}
	
	//endregion
	/**
	 * Method to let the Unit rest.
	 * @post the unit is resting.
	 * 		| new.getCurrentActivity() == Activity.REST
	 * @post This unit stops the default behaviour if it was doing this.
	 * 		| new.isDoingBehaviour
	 * @post if this is the default behaviour, set the stateDefault to a new state.
	 * 		| new.isDoingBehaviour 
	 * @post reset the restTimer of this unit
	 * 		new.restTimer
	 * @post reset the restHitpoints of this unit
	 * 		new.restHitpoints
	 * @post reset the restStamina of this unit
	 * 		new.restStamina
	 * @throws IllegalStateException * if the unit isn't able to rest.
	 * | !this.isAbleToRest()
	 */
	public void rest() throws IllegalStateException{
		//activityController.
		requestNewActivity(new Rest(this));
	}

	//endregion

	//region Working
	/**
	 * Method to let the Unit work.
	 * @post the unit is working.
	 * 		| new.getCurrentActivity() == Activity.WORK
	 * @post This unit stops the default behaviour if it was doing this.
	 * 		| new.isDoingBehaviour
	 * @post if this is the default behaviour, set the stateDefault to a new state.
	 * 		| new.isDoingBehaviour 
	 * @post reset the work progress
	 * 		| new.getWorkprogress()
	 * @post reset the work duration
	 * 		| new.getWorkDuration()
	 * @throws IllegalStateException * if the unit isn't able to work.
	 * | !this.isAbleToWork()
	 * @throws IllegalArgumentException
	 * 			The given position is not a neighbouring position
	 * 			| !isValidWorkPosition(position)
	 */
	public void work(Vector position) throws IllegalStateException, IllegalArgumentException{
		//activityController.
		requestNewActivity(new Work(this, position));
	}

	@Override
	public int getMaxNbOwnedMaterials() {
		return 1;
	}

	/**
	 * Return the carriedMaterial of this Unit.
	 */
	@Basic
	@Raw
	public Material getCarriedMaterial() {
	    return (this.getNbOwnedMaterials()==0) ? null : this.getOwnedMaterialAt(1);
	}

	/**
	 * Set the carriedMaterial of this Unit to the given material.
	 *
	 * @param material
	 * The new carriedMaterial for this Unit.
	 * @post The carriedMaterial of this new Unit is equal to
	 * the given material.
	 * | new.getCarriedMaterial() == material
	 * @throws IllegalArgumentException * The given material is not a valid carriedMaterial for any
	 * Unit.
	 * | ! isValidMaterial(getMaterial())
	 */
	public void setCarriedMaterial(@Raw Material material) throws IllegalArgumentException {
	    if (! canHaveAsOwnedMaterial(material))
	        throw new IllegalArgumentException();
		material.setOwner(this);
		this.addOwnedMaterial(material);
	}

	public boolean isCarryingLog(){
		return this.getCarriedMaterial()!=null && this.getCarriedMaterial() instanceof Log;
	}

	public boolean isCarryingBoulder(){
		return this.getCarriedMaterial()!=null && this.getCarriedMaterial() instanceof Boulder;
	}

	public boolean isCarryingMaterial(){
		return this.getCarriedMaterial()!=null;
	}

	public void dropCarriedMaterial(Cube dropCube){
		Material m = this.getCarriedMaterial();
		if(m!=null) {
			m.setOwner(dropCube);
			this.removeOwnedMaterial(m);
			dropCube.addOwnedMaterial(m);
		}
	}
	//endregion

	//TODO: work this out
	/**
	 * Terminate this Unit.
	 *
	 * @post This Unit is terminated.
	 * | new.isTerminated()
	 * @post ...
	 * | ...
	 */
	@Override
	public void terminate() {
	    this.isTerminated = true;
		this.setHitpoints(0);
		this.getWorld().removeUnit(this);
		Faction f = this.getFaction();
		this.faction = null;
		f.removeUnit(this);
	}
	/**
	 * Return a boolean indicating whether or not this Unit
	 * is terminated.
	 */
	@Override
	@Basic
	@Raw
	public boolean isTerminated() {
	    return this.isTerminated;
	}
	/**
	 * Variable registering whether this person is terminated.
	 */
	private boolean isTerminated = false;


	/**
	 * Return the faction of this Unit.
	*/
	@Basic
	@Raw
	public Faction getFaction() {
	    return this.faction;
	}
	/**
	 * Check whether the given faction is a valid faction for
	 * any Unit.
	 *
	 * @param faction
	 * The faction to check.
	 * @return
	 * | result ==
	 */
	public boolean isValidFaction(Faction faction) {
		return this.getWorld().hasAsFaction(faction);
	}
	/**
	 * Set the faction of this Unit to the given faction.
	 *
	 * @param faction
	 * The new faction for this Unit.
	 * @pre The given faction must be a valid faction for any
	 * Unit.
	 * | isValidFaction(faction)
	 * @post The faction of this Unit is equal to the given
	 * faction.
	 * | new.getFaction() == faction
	 */
	@Raw
	public void setFaction(Faction faction) {
		 assert isValidFaction(faction);
		 this.faction = faction;
	 }
	/**
	 * Variable registering the faction of this Unit.
	 */
	private Faction faction;

	/**
	 * Return the task of this Unit.
	 */
	@Basic
	@Raw
	public Task getTask() {
	    return this.task;
	}
	/**
	 * Check whether the given task is a valid task for
	 * any Unit.
	 *
	 * @param task
	 * The task to check.
	 * @return
	 * | result == true
	 */
	public static boolean isValidTask(Task task) {
	    return true;
	}
	/**
	 * Set the task of this Unit to the given task.
	 *
	 * @param task
	 * The new task for this Unit.
	 * @post The task of this new Unit is equal to
	 * the given task.
	 * | new.getTask() == task
	 * @throws IllegalArgumentException * The given task is not a valid task for any
	 * Unit.
	 * | ! isValidTask(getTask())
	 */
	@Raw
	public void setTask(Task task) throws IllegalArgumentException {
	    if (! isValidTask(task))
	        throw new IllegalArgumentException();
	    this.task = task;
	}
	/**
	 * Variable registering the task of this Unit.
	 */
	private Task task;

//	private Vector getValidatePosition(IWorld world){
//		double x = randDouble(world.getMinPosition().X(), world.getMaxPosition().X());
//		double y = randDouble(world.getMinPosition().Y(), world.getMaxPosition().Y());
//		double z = randDouble(world.getMinPosition().Z(), world.getMaxPosition().Z());
//		Vector position = new Vector(x,y,z).getCubeCoordinates();
//		if(validatePosition(position))
//			return position;
//		else
//			return getValidatePosition(world);
//	}

	//region XP points
	/**
	 * Variable registering the experience points of this unit.
	 */
	private int experiencePoints = 0;

	/**
	 * Add XP by the units current XP.
	 * @param xp
	 * The to be added xp.
	 * @effect	...
	 * 			| this.getXP() += xp.
	 * @effect 	...
	 * 			|while (this.getXP() >= MAX_XP &&
	 * 			|	(this.getStrength() != MAX_STRENGTH || this.getAgility() != MAX_AGILITY || this.getToughness() != MAX_TOUGHNESS)
	 * 			|		( (new this).getStrength() || (new this).getAgility() || (new this).Toughness())
	 * 			| 		&& (new this).getXP()
	 */
	public void addXP(int xp){ //TODO: toevoegen bij methodes waar en hoeveel xp wordt verdient
		experiencePoints += xp;
		while (experiencePoints >= MAX_XP){
			final String Strength = "s", Agility = "a", Toughness = "t";
			List<String> attributes = new LinkedList<>(Arrays.asList(Strength,Agility,Toughness));
			if(getStrength() == MAX_STRENGTH)
				attributes.remove(Strength);
			if(getAgility() == MAX_AGILITY)
				attributes.remove(Agility);
			if(getToughness() == MAX_TOUGHNESS)
				attributes.remove(Toughness);
			int size = attributes.size();
			if(size == 0){
				experiencePoints = MAX_XP-1;
				return; //TODO: experiencePoints gelijk zetten aan MAX_XP-1 of laten opbouwen naar oneindig?
			}
			else{
				experiencePoints -= MAX_XP;
				switch(attributes.get(randInt(0, size-1))){
					case Strength:
						setStrength(getStrength()+1);
						break;
					case Agility:
						setAgility(getAgility()+1);
						break;
					case Toughness:
						setToughness(getToughness()+1);
						break;
				}
			}
		}
	}

	/**
	 * Return the XP of this unit.
	 */
	@Basic
	public int getXP(){
		return experiencePoints;
	}
	//endregion

	public void notifyTerrainChange(Terrain oldTerrain, Cube cube){
		if(!this.isFalling() && this.isMoving()){
			if(this.isExecuting(AdjacentMove.class))
				this.requestActivityFinish(this.getCurrentActivity());
			assert this.getCurrentActivity() instanceof TargetMove;
			((TargetMove)this.getCurrentActivity()).notifyTerrainChange(oldTerrain, cube);
		}
	}

	/**
	 * Variable registering the time passed since the unit's last rest.
	 */
	private double restTimer = 0d;

	public final None NONE = new None(Unit.this);
	public final Rest REST = new Rest(Unit.this);

	private Stack<Activity> activityStack;

	public void requestNewActivity(Activity activity) throws IllegalArgumentException,IllegalStateException{
		if(activity.getUnitId()!=Unit.this.getId())
			throw new IllegalArgumentException("This activity is not bound to this unit.");
		if(activity.isActive())
			throw new IllegalArgumentException("This activity is already active.");
		if(!this.getCurrentActivity().isDefault() && !activity.isAbleTo())
			throw new IllegalStateException("This unit cannot " + activity.toString() + " at this moment");

		boolean isDefault = this.getCurrentActivity().isDefault();
		try{
			this.getCurrentActivity().interrupt(activity);
		}catch(IllegalStateException interruptException){
			try{
				stopCurrentActivity(true);
			}catch(IllegalStateException stopException){
				throw new IllegalStateException("The current activity cannot be interrupted nor stopped by the given Activity.");
			}
		}
		this.activityStack.push(activity);
		this.getCurrentActivity().start(isDefault);
		if(this.getCurrentActivity() instanceof Rest)
			restTimer = 0d;// Reset rest timer
	}

	public void requestActivityFinish(Activity activity){
		requestActivityFinish(activity, false);
	}

	public void requestActivityFinish(Activity activity, boolean finishParent) throws IllegalArgumentException{
		// TODO: maybe change this to reportActivityFinish and check for inactiviy of current activity and then resume previous in stack
		if(activity == null)
			throw new IllegalArgumentException("Invalid activity.");
		if(activity.getUnitId()!=Unit.this.getId())
			throw new IllegalArgumentException("This activity is not bound to this unit.");
		if(activity!=this.getCurrentActivity() || !activity.isActive())
			throw new IllegalArgumentException("This activity is not currently active.");
		boolean isDefault = this.getCurrentActivity().isDefault();
		stopCurrentActivity(finishParent);
		if(this.activityStack.size()==0)
			this.activityStack.push(NONE);
		this.getCurrentActivity().start(isDefault);// Resume previous activity in stack
	}

	public void restartActivity(){
		restartActivity(false);
	}

	public void restartActivity(boolean restartParent){
		Activity activity = this.getCurrentActivity();
		boolean isDefault = activity.isDefault();
		if(restartParent && !activity.isParentActivity(null)){
			stopCurrentActivity(false);
			this.getCurrentActivity().setDefault(isDefault);
			restartActivity(true);
		}else {
			activity.stop();
			activity.start(isDefault);
		}
	}

	private void stopCurrentActivity(boolean finishParent) throws IllegalStateException{
		Activity oldActivity = this.getCurrentActivity();
		oldActivity.stop();
		this.activityStack.pop();
		if(oldActivity.wasSuccessful())
			Unit.this.addXP(oldActivity.getXp());
		if(oldActivity instanceof Rest)
			restTimer = 0d;// Reset rest timer
		if(finishParent && oldActivity.isParentActivity(this.getCurrentActivity()))
			stopCurrentActivity(true);
	}

	private Activity getCurrentActivity(){
		return this.activityStack.peek();
	}

	public boolean isCurrentActivity(Activity activity){
		return this.getCurrentActivity()==activity;
	}

	/**
	 * General method to check whether this Unit is executing an activity of given kind.
	 * @param activity The kind of activity to check for
	 * @return True if this Unit's current Activity is of the same kind as activity AND this Activity is active.
	 */
	public boolean isExecuting(Class<? extends Activity> activity){
		return this.getCurrentActivity().isActive() && activity.isInstance(this.getCurrentActivity());
	}

	public boolean isAttacking(){
		return this.isExecuting(Attack.class);
	}

	public boolean isMoving(){
		return this.isExecuting(Move.class);
	}

	public boolean isResting(){
		return this.isExecuting(Rest.class);
	}

	public boolean isWorking(){
		return this.isExecuting(Work.class);
	}

	public boolean isFalling(){
		return this.isExecuting(Fall.class);
	}
}

	

	

	

