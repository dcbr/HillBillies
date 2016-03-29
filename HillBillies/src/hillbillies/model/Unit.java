package hillbillies.model;

import be.kuleuven.cs.som.annotate.*;
import hillbillies.utils.Vector;
import hillbillies.activities.*;

import static hillbillies.utils.Utils.randInt;

/**
 * Class representing a Hillbilly unit
 * @author Kenneth & Bram
 * @version 1.5
 *
 * @invar Each Unit has a unique Id.
 * | for each Unit u, for each Unit o -> u.getId()!=o.getId() if u!=o
 * @invar The name of each Unit must be a valid name for any
 * Unit.
 * | isValidName(getName())
 * @invar The position of each Unit must be a valid position for any
 * Unit.
 * | isValidPosition(getPosition())
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
 */
public class Unit {

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
	 * Constant reflecting the length of a cube side.    
	 */
    public static final double CUBE_SIDE_LENGTH = 1;
	/**
	 * Constant reflecting the minimum position in the units world.    
	 */
    public static final Vector MIN_POSITION = new Vector(CUBE_SIDE_LENGTH * 0, CUBE_SIDE_LENGTH * 0, CUBE_SIDE_LENGTH * 0);
	/**
	 * Constant reflecting the maximum position in the units world.    
	 */
    public static final Vector MAX_POSITION = new Vector(CUBE_SIDE_LENGTH * 50, CUBE_SIDE_LENGTH * 50, CUBE_SIDE_LENGTH * 50);
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
	/**
	 * Constant reflecting the stamina points a unit loses while sprinting.    
	 */
	public static final int SPRINT_STAMINA_LOSS = 1;// Stamina loss per interval when sprinting
	/**
	 * Constant reflecting the interval a sprinting unit loses a SPRINT_STAMINA_LOSS.    
	 */
	public static final double SPRINT_STAMINA_LOSS_INTERVAL = 0.1d;// Unit will loose SPRINT_STAMINA_LOSS every SPRINT_STAMINA_LOSS_INTERVAL seconds when sprinting
	/**
	 * Constant reflecting the interval when a unit has to rest.    
	 */
	public static final double REST_INTERVAL = 3*60;// Unit will rest every REST_INTERVAL seconds
	/**
	 * Constant reflecting the interval wherein a resting unit recovers getRestHitpointsGain() hitpoints.
	 */
	public static final double REST_HITPOINTS_GAIN_INTERVAL = 0.2d;
	/**
	 * Constant reflecting the interval wherein a resting unit recovers getRestStaminaGain() stamina.
	 */
	public static final double REST_STAMINA_GAIN_INTERVAL = 0.2d;
	/**
	 * Constant reflecting the duration of an attack.    
	 */
	public static final double ATTACK_DURATION = 1d;
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
	 * Variable registering the position of this Unit.
	 */
	private Vector position;

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
	
	/**
	 * Variable registering the next position and target position of this unit.
	 */
	private Vector nextPosition, targetPosition;
	

	/**
	 * Variable registering whether this unit is sprinting.
	 */
	private boolean isSprinting = false;


	/**
	 * Variable registering the current Activity of this unit.
	 */
	private Activity activity;


	/**
	 * Variable registering the time passed since the unit's last rest.
	 */
	private double restTimer = 0d;
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
     * Check whether the given position is a valid position for
     * any Unit.
     *
     * @param position
     * The position to check.
     * @return True when each coordinate of position is within the predefined bounds of MIN_POSITION and MAX_POSITION
     * | isValid == true
     * | for(int i = 0 ; i<position.length ; i++){
     * |    isValid == (isValid && position[i] >= MIN_POSITION[i] && position[i] <= MAX_POSITION[i])
     * | }
     * | result == isValid
     */
    public static boolean isValidPosition(Vector position) {
        return position.isInBetween(MIN_POSITION, MAX_POSITION);
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
	 * Return the probability a unit can block an attack.
	 * @param	attacker
	 * 			The attacker to check against.
	 * @return 	The probability to block an attack is positive for all units.
	 *       	| result >= 0.0
	 */
	@Basic
	public double getBlockingProbability(Unit attacker) {
		return (0.25*(this.getAgility()+this.getStrength())/
				(attacker.getAgility()+attacker.getStrength()));
	}
	/**
	 * Return the current Activity of this unit.
	 */
	@Raw
	private Activity getCurrentActivity() {
		return this.activity;
	}
	/**
	 * Return the current speed of this unit.
	 */
	public double getCurrentSpeed(){
		if(!this.isMoving())
			return (0d);
		return ((Move)this.getCurrentActivity()).getCurrentSpeed();
	}
	/**
	 * Return the hitpoints a unit lose when he is taking damage.
	 * @param	attacker
	 * 			The attacker to check against.
	 * @return 	The hitpoints a unit loses is positive for all units.
	 *       	| result >= 0
	 * @return	The hitpoints a unit loses is less than or equal the current hitpoints of
	 * 			that unit for all units.
	 * 			| result <= this.getHitpoints()
	 */
	@Basic
	public int getDamagingPoints(Unit attacker) {
		int damage = (int)Math.round(attacker.getStrength()/10.0);
		if (damage < this.getHitpoints())
			return damage;
		return this.getHitpoints();
	}
	/**
	 * Return the probability a unit can dodge an attack.
	 * @param	attacker
	 * 			The attacker to check against.
	 * @return 	The probability to dodge an attack is positive for all units.
	 *       	| result >= 0.0
	 */
	@Basic
	public double getDodgingProbability(Unit attacker) {
		return (0.20*(this.getAgility())/(attacker.getAgility()));
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
	 * Return the next position of this unit.
	 */
	@Basic
	@Raw
	public Vector getNextPosition(){
		return this.nextPosition.clone();
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
	 * Return the position of this Unit.
	 */
	@Basic
	@Raw
	public Vector getPosition() {
		return (this.position).clone();
	}

	private double getRestHitpointsGain(){
		return this.getToughness()/200d;
	}

	private double getRestStaminaGain(){
		return this.getToughness()/100d;
	}


	/**
	 * Return the stamina of this unit.
	 */
	@Basic @Raw
	public int getStamina() {
		return this.stamina;
	}
	/**
	 * Return a integer indicating in what state the default behaviour is.
	 */
	@Basic
	public int getStateDefault(){
		return this.stateDefault;
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
		return this.weight;
	}

	/**
	 * Return the work duration of this unit.
	 */
	@Raw
	public float getWorkDuration() {
		return this.workDuration;
	}

	/**
	 * Return the work progress of this unit.
	 */
	@Raw
	public float getWorkProgress() {
		return this.workProgress;
	}

	//endregion

	//region Checkers
	/**
	 * Return a boolean indicating whether or not this person
	 * is able to sprint.
	 */
	public boolean isAbleToSprint(){
		return this.isMoving() && getStamina()>MIN_STAMINA;
	}

	/**
	 * Return a boolean indicating whether or not this person
	 * is attacking.
	 */
	@Raw
	public boolean isAttacking() {
		return this.getCurrentActivity() instanceof Attack;
	}

	/**
	 * Return a boolean indicating whether or not the units default behaviour is activated.
	 */
	@Basic
	public boolean isDefaultActive(){
		return this.getCurrentActivity().isDefault();
	}

	/**
	 * Return a boolean indicating whether or not this person
	 * is in its initial resting period.
	 */
	public boolean isInitialRestMode(){
		return this.isResting() && ((Rest)this.getCurrentActivity()).isInitialRestMode();
	}
	/**
	 * Return a boolean indicating whether or not this person
	 * is moving.
	 */
	public boolean isMoving(){
		return this.getCurrentActivity() instanceof Move;
	}
	/**
	 * Return a boolean indicating whether or not this person
	 * is sprinting.
	 */
	public boolean isSprinting(){
		return this.isMoving() && ((Move)this.getCurrentActivity()).isSprinting();
	}

	public boolean isResting(){
		return this.getCurrentActivity() instanceof Rest;
	}

	public boolean isWorking(){
		return this.getCurrentActivity() instanceof Work;// TODO: check if currentActivity.isActive ?
	}

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
	 * Set the current Activity of this unit to the given current Activity.
	 *
	 * @param  activity
	 *         The new current Activity for this unit.
	 * @post   The current Activity of this new unit is equal to
	 *         the given current Activity.
	 *       | new.getCurrentActivity() == activity
	 * @post	The activityProgress timer is reset
	 * 			| new.activityProgress == 0d
	 */
	@Raw
	private void setCurrentActivity(Activity activity) {
		if(activity.getUnitId()!=this.getId())
			throw new IllegalArgumentException("This activity is not bound to this unit.");
		if(activity.isActive())
			throw new IllegalArgumentException("This activity is already active.");
		// TODO: check if current activity should be interrupted instead of being stopped
		boolean isDefault = this.activity.isDefault();
		this.activity.stop();
		this.activity = activity;
		this.activity.start(isDefault);
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
	| isValidWeight(this.getWeight()) && isValidToughness(this.getToughness())
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
	 * Set the next position of this Unit to the given position.
	 *
	 * @param position
	 * The next position for this Unit.
	 * @post The next position of this new Unit is equal to
	 * the given position.
	 * | new.getNextPosition() == position
	 * @throws IllegalArgumentException * The given position is not a valid position for any
	 * Unit.
	 * | ! isValidPosition(getPosition())
	 */
	@Raw
	public void setNextPosition(Vector position) throws IllegalArgumentException {
		if (position!=null && ! isValidPosition(position))
			throw new IllegalArgumentException("Invalid position");
		this.nextPosition = position;
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
	 * Set the position of this Unit to the given position.
	 *
	 * @param position
	 * The new position for this Unit.
	 * @post The position of this new Unit is equal to
	 * the given position.
	 * | new.getPosition() == position
	 * @throws IllegalArgumentException * The given position is not a valid position for any
	 * Unit.
	 * | ! isValidPosition(getPosition())
	 */
	@Raw
	public void setPosition(Vector position) throws IllegalArgumentException {
		if (! isValidPosition(position))
			throw new IllegalArgumentException();
		this.position = position;
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
	 * Initialize this new Unit with given name and position. All other properties are set to their default initial values.
	 * @param name The name of this new Unit
	 * @param position The position of this new Unit
	 * @effect This Unit is initialized with the given name and position and the default initial values for its other properties
	 * 			| this(name, position, INITIAL_MIN_STRENGTH, INITIAL_MIN_AGILITY, INITIAL_MIN_TOUGHNESS, INITIAL_MIN_WEIGHT, INITIAL_MIN_STAMINA, INITIAL_MIN_HITPOINTS)
	 */
	public Unit(String name, Vector position) throws IllegalArgumentException {
		this(name, position, INITIAL_MIN_STRENGTH, INITIAL_MIN_AGILITY, INITIAL_MIN_TOUGHNESS, INITIAL_MIN_WEIGHT, INITIAL_MIN_STAMINA, INITIAL_MIN_HITPOINTS);
	}

	/**
	 * Initialize this new Unit with given name, position, strength, agility, toughness, weight, stamina and hitpoints.
	 *
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
	 * @effect The name of this new Unit is set to
	 * the given name.
	 * | this.setName(name)
	 * @effect The position of this new Unit is set to
	 * the given position.
	 * | this.setPosition(position)
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
	public Unit(String name, Vector position, int strength, int agility, int toughness, int weight, int stamina, int hitpoints) throws IllegalArgumentException {
		this.Id = ID;
		ID++;
		// Defensive
		this.setName(name);
		this.setPosition((position.add(0.5)));
		
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

		this.setCurrentActivity(Activity.NONE);
	}

	//endregion

	//region AdvanceTime

	public void advanceTime(double dt){
		// Defensively without documentation
		restTimer += dt;
		if(restTimer >= REST_INTERVAL && this.isAbleToRest()){
			rest();
		}

		this.getCurrentActivity().advanceTime(dt);

	}

	/**
	 * This method returns how many times the specified time-interval (delta) is contained in newTime.
	 * Where newTime is calculated as
	 * | newTime = (prevProgress % delta) + dt
	 * This method is used to determine game-time progression in a correct way inside the advanceTime method,
	 * because otherwise certain intervals could be skipped. (For example when dt is always smaller than delta,
	 * using dt / delta won't give the desired result)
	 * @param prevProgress The previous progress of an activity
	 * @param dt The time the progress will be increased with
	 * @param delta The time-interval to check
	 * @return How many times delta is contained in newTime
	 * 			| newTime == (prevProgress % delta) + dt
	 * 			| result == (newTime - newTime % delta) / delta
	 */
	public static int getIntervalTicks(double prevProgress, double dt, double delta){// TODO: move to utils? + find better name
		double newTime = (prevProgress % delta) + dt;
		return (int)((newTime - newTime % delta) / delta);
	}

	//endregion

	//region Default behaviour

	/**
	 * Variable registering the state of the default behaviour.
	 */
	private int stateDefault = 0
			// 0 && 1(in moveToAdjacent)= not doing default behaviour
			// 2= doing the default behaviour
			// 3= starting default behaviour
			;

	/**
	 * Activate the default behaviour of this unit.
	 *
	 * @post	Default behaviour of this unit is activated.
	 *       	| new.isDefaultActive() == true
	 */
	public void startDefaultBehaviour(){
		this.getCurrentActivity().setDefault(true);
	}
	/**
	 * Start the default behaviour of this unit.
	 *
	 * @post   Default behaviour of this unit is started.
	 *       | new.getStateDefault()
	 */
	private void startDoingDefault(){
		this.stateDefault = 3;
	}
	/**
	 * Deactivate the default behaviour of this unit.
	 *
	 * @post   	Default behaviour of this unit is deactivated.
	 *       	| new.isDefaultActive() == false
	 * @post   	The new state of the default behaviour is equal to stopDoingDefault(). // TODO: dees klopt ni echt denk ik, want stopDoingDefault returned niks
	 * 			| new.isDoingDefault() ==  stopDoingDefault() // TODO: en isDoingDefault() bestaat niet
	 * @post   	The new current activity of this unit is equal to None.
	 * 			| new.getCurrentActivity() == None
	 */
	public void stopDefaultBehaviour(){
		this.stopDoingDefault();
		this.getCurrentActivity().setDefault(false);
		this.setCurrentActivity(new None(this));
	}
	/**
	 * Stop the default behaviour of this unit.
	 *
	 * @post   Default behaviour of this unit is stopped.
	 *       | new.getStateDefault()
	 */
	private void stopDoingDefault(){
		this.stopSprint();
		this.stateDefault = 0;
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
	 * @throws	IllegalArgumentException * the attacker is not able to attack this unit.
	 * 			| !this.isValidAttack(defender)	
	 */
	public void attack(Unit defender) throws IllegalArgumentException{
		/*if(this.getStateDefault()!=2){
			if (!this.isValidAttack(defender))
				throw new IllegalArgumentException("Cannot attack that unit");
		}
		if(this.getStateDefault() ==2)
			this.stopDoingDefault();*/
		setCurrentActivity(new Attack(this, defender));
	}
	
	/**
	 * Let defend this unit against the given attacker.
	 *
	 * @param  attacker
	 *         The attacker of this attack.
	 * @effect The unit first tries to dodge the attack, then blocks it. 
	 * 			If it fails to dodge and block, this unit will lose hitpoins.
	 */
	public void defend(Unit attacker){
		setCurrentActivity(this.getCurrentActivity());// TODO: check if this is still correct
		//dodging
		if ((randInt(0,99)/100.0) < this.getDodgingProbability(attacker)){
			Boolean validDodge = false;
			while(! validDodge) {// TODO: in part2 replace this by a list of valid positions and choose a random element from that list
				int dodgeX = randInt(-1, 1);
				int dodgeY = randInt(-1, 1);
				if ((dodgeX != 0 || dodgeY != 0) &&
						(isValidPosition(this.getPosition().add(new Vector(dodgeX, dodgeY, 0))))) {
					validDodge = true;
					this.setPosition(getPosition().add(new Vector(dodgeX,dodgeY,0)));
				}
			}
		}// fails to block
		else if (!((randInt(0,99)/100.0) < this.getBlockingProbability(attacker)))
			this.setHitpoints(this.getHitpoints()- this.getDamagingPoints(attacker));
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
		if(this.getStateDefault()!=2){
			if(!this.isAbleToMove() )
				throw new IllegalStateException("Unit is not able to move at this moment.");
		}
		if(this.getStateDefault() ==2) 
			this.stopDoingDefault();
		if(this.getStateDefault() >=1)
			this.stateDefault -=1;
		// setNextPosition throws the exception
		this.setNextPosition(this.getPosition().getCubeCenterCoordinates().add(direction));
		setCurrentActivity(Activity.MOVE);	
	}
	
	public void moveToAdjacent(int dx, int dy, int dz){
		this.targetPosition = null;
  		this.moveToAdjacent(new Vector(dx,dy,dz));
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
	public void moveToTarget(Vector targetPosition) throws IllegalStateException{
		if(this.getStateDefault()!=2){
			if(!this.isAbleToMove())
				throw new IllegalStateException("Unit is not able to move at this moment.");
		}
		if(this.getStateDefault() ==2)
			this.stopDoingDefault();
		if(this.getStateDefault() == 3)
			this.stateDefault -=1;
		
		setCurrentActivity(Activity.MOVE);
		this.targetPosition = targetPosition.getCubeCenterCoordinates();// TODO: make setter and getter for targetPosition and check for invalid positions
	}
	/**
	 * Method to let the Unit sprint.
	 * @post the unit is sprinting.
	 * 		| new.isSprinting() == true
	 * @throws IllegalStateException * if the unit isn't able to sprint.
	 * | !this.isAbleToSprint()
	 */
	public void sprint() throws IllegalStateException{
		if(!this.isAbleToSprint())
			throw new IllegalStateException("The Unit is not able to sprint!");
		this.isSprinting = true;
	}
	/**
	 * Method to let the Unit sprint.
	 * @post the unit stop sprinting.
	 * 		| new.isSprinting() == false
	 */
	public void stopSprint(){
		this.isSprinting = false;
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
		/*if(this.getStateDefault()!=2){
			if(!isAbleToRest())
				throw new IllegalStateException("This unit cannot rest at this moment");
		}
		if(this.getStateDefault() ==2)
			this.stopDoingDefault();
		if(this.getStateDefault() == 3)
			this.stateDefault -=1;*/
		setCurrentActivity(new Rest(this));
		this.restTimer = 0d;// TODO: this is done by the Rest class
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
	 */
	public void work() throws IllegalStateException{
		/*if(this.getStateDefault()!=2){
			if(!this.isAbleToWork())
				throw new IllegalStateException("Unit is not able to work at this moment");
		}
		if(this.getStateDefault() ==2)
			this.stopDoingDefault();
		if(this.getStateDefault() == 3)
			this.stateDefault -=1;*/
		setCurrentActivity(new Work(this));
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
	public void terminate() {
	    this.isTerminated = true;
		this.setHitpoints(0);
	}
	/**
	 * Return a boolean indicating whether or not this Unit
	 * is terminated.
	 */
	@Basic
	@Raw
	public boolean isTerminated() {
	    return this.isTerminated;
	}
	/**
	 * Variable registering whether this person is terminated.
	 */
	private boolean isTerminated = false;
}

	

	

	

