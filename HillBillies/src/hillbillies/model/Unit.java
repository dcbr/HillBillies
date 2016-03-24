package hillbillies.model;

import be.kuleuven.cs.som.annotate.*;
import hillbillies.utils.Vector;
import ogp.framework.util.ModelException;

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
	 * Variable registering the current speed of this unit.
	 */
	private double currentSpeed = 0.0;
	/**
	 * Variable registering whether this unit is sprinting.
	 */
	private boolean isSprinting = false;

	/**
	 * Variable registering the work duration of this unit.
	 */
	private float workDuration = 0;

	/**
	 * Variable registering the work Progress of this unit.
	 */
	private float workProgress = 0;



	/**
	 * Variable registering the current Activity of this unit.
	 */
	private Activity activity;

	/**
	 * Variable registering the duration of an activity progress of this unit.
	 */
	private double activityProgress = 0d;

	/**
	 * Variable registering whether this unit is attacking.
	 */
	private boolean isAttacking = false;

	/**
	 * Variable registering time each period a unit starts to is required time.
	 */
	private double restTimer = 0d;
	/**
	 * Variable registering the hitpoints a unit recovered during the current rest period.
	 */
	private double restHitpoints = 0d;
	/**
	 * Variable registering the stamina points a unit recovered during the current rest period.
	 */
	private double restStamina = 0d;
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
	/**
	 * Return the time this unit shall be working.
	 * @param	strength
	 * 			The strength to check against.
	 * @return 	The time a unit need to work is positive for all units.
	 *       	| result >= 0
	 */
	@Basic
	public static int getWorkingTime(int strength) {
		return (500/strength);
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
	 * Retrieve the Unit's base speed
	 * @return
	 */
	private double getBaseSpeed(){
		return 1.5*(this.getStrength()+this.getAgility())/(200*this.getWeight()/100);
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
	public Activity getCurrentActivity() {
		return this.activity;
	}
	/**
	 * Return the current speed of this unit.
	 */
	public double getCurrentSpeed(){
		if(this.getCurrentActivity() != Activity.MOVE)
			return (0d);
		return this.currentSpeed;
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
		return this.nextPosition;
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
	 
	/**
	 * Retrieve the Unit's sprinting speed
	 * @param direction The direction the Unit is sprinting in, this is a vector with norm 1
	 * @return
	 */
	private double getSprintSpeed(Vector direction){
		return 2*this.getWalkingSpeed(direction);
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
	 * Retrieve the Unit's walking speed
	 * @param direction The direction the Unit is walking in, this is a vector with norm 1
	 * @return
	 */
	private double getWalkingSpeed(Vector direction){
		if(direction.Z()>0) return 1.2*this.getBaseSpeed();
		else if(direction.Z()<0) return 0.5*this.getBaseSpeed();
		else return this.getBaseSpeed();
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
	 * is able to move.
	 */
	public boolean isAbleToMove(){
		return !this.isInitialRestMode() && this.getCurrentActivity()!=Activity.ATTACK && this.getCurrentActivity()!=Activity.WORK;
	}
	/**
	 * Return a boolean indicating whether or not this person
	 * is able to rest.
	 */
	public boolean isAbleToRest(){
		return this.getCurrentActivity()!=Activity.ATTACK;
	}
	/**
	 * Return a boolean indicating whether or not this person
	 * is able to sprint.
	 */
	public boolean isAbleToSprint(){
		return this.isMoving() && getStamina()>MIN_STAMINA;
	}

	/**
	 * Return a boolean indicating whether or not this person
	 * is able to work.
	 */
	public boolean isAbleToWork(){
		return !this.isInitialRestMode() && this.getCurrentActivity() != Activity.ATTACK;
	}

	/**
	 * Return a boolean indicating whether or not this person
	 * is attacking.
	 */
	@Basic @Raw
	public boolean isAttacking() {
		return this.isAttacking;
	}

	/**
	 * Return a boolean indicating whether or not the units default behaviour is activated.
	 */
	@Basic
	public boolean isDefaultActive(){
		return this.defaultActive;
	}

	/**
	 * Return a boolean indicating whether or not this person
	 * is in its initial resting period.
	 */
	public boolean isInitialRestMode(){
		return this.getCurrentActivity()==Activity.REST && (this.restHitpoints+this.restStamina<1d);
	}
	/**
	 * Return a boolean indicating whether or not this person
	 * is moving.
	 */
	public boolean isMoving(){
		return this.getCurrentActivity()==Activity.MOVE;
	}
	/**
	 * Return a boolean indicating whether or not this person
	 * is sprinting.
	 */
	public boolean isSprinting(){
		return this.isSprinting;
	}

	/**
	 * Check whether an attack is a valid for any unit.
	 *
	 * @param  defender
	 *         The defender to check.
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
	 *				(Math.abs(defender.getPosition().cubeZ()-this.getPosition().cubeZ())<=1))
	 */
	public boolean isValidAttack(Unit defender) {
		return this.getId()!=defender.getId() &&
				!this.isAttacking() &&
				!this.isInitialRestMode() &&
				(defender.getHitpoints()> MIN_HITPOINTS) &&
				(Math.abs(defender.getPosition().cubeX()-this.getPosition().cubeX())<=1) &&
				(Math.abs(defender.getPosition().cubeY()-this.getPosition().cubeY())<=1) &&
				(Math.abs(defender.getPosition().cubeZ()-this.getPosition().cubeZ())<=1);

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
		this.activity = activity;
		this.activityProgress = 0d;
	}

	@Raw private void setCurrentSpeed(double speed){
		this.currentSpeed = speed;
	}
	
	/**
	 * Set current activity of this Unit to a random activity.
	 * @post The new current activity of this new Unit is equal to
	 * a random activity.
	 * | new.getCurrentActivity()
	 * @post The new state of the default behaviour is equal to startDoingDefault()
	 * | new.isDoingDefault() == startDoingDefault()
	 * @throws IllegalStateException * The default behaviour is not activated for this unit.
	 * |   !this.isDefaultActive()
	 */
	public void setDefaultBehaviour() throws IllegalStateException{
		if(!this.isDefaultActive())
			throw new IllegalStateException("The default behaviour of unit is activated");
		this.startDoingDefault();
		int activity = randInt(0,2);
		if (activity ==0){
			this.moveToTarget(new Vector (Math.random()*(MAX_POSITION.X()-MIN_POSITION.X())+MIN_POSITION.X(),
					Math.random()*(MAX_POSITION.Y()-MIN_POSITION.Y())+MIN_POSITION.Y(),
					Math.random()*MAX_POSITION.Z()-MIN_POSITION.Z()+MIN_POSITION.Z()));
			if (this.isAbleToSprint() && randInt(0, 9) < 1){
				this.sprint();
			}
		}
		if (activity == 1)
			this.work();
		if (activity ==2)
			this.rest();
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

	/**
	 * Set the work duration of this unit to the given work duration.
	 *
	 * @param  workDuration
	 *         The new work duration for this unit.
	 * @post   The work duration of this new unit is equal to
	 *         the given work duration.
	 *       | new.getWorkDuration() == workDuration
	 */
	@Raw
	private void setWorkDuration(float workDuration) {

		this.workDuration = workDuration;
	}

	/**
	 * Set the work progress of this unit to the given work progress.
	 *
	 * @param  workProgress
	 *         The new work progress for this unit.
	 * @post   The work progress of this new unit is equal to
	 *         the given work progress.
	 *       | new.getWorkProgress() == workProgress
	 */
	@Raw
	private void setWorkProgress(float workProgress) {
		this.workProgress = workProgress;
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
			weight = INITIAL_MIN_WEIGHT;
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

		switch(getCurrentActivity()){
			case MOVE:
//				System.out.println(this.getPosition().X());
				if(this.isSprinting){
					int newStamina = this.getStamina()-SPRINT_STAMINA_LOSS*getIntervalTicks(activityProgress, dt, SPRINT_STAMINA_LOSS_INTERVAL);
					if(newStamina<=0){
						newStamina = 0;
						stopSprint();
					}
					this.setStamina(newStamina);
				}
				Vector cpos = getPosition();
				if(targetPosition!=null){
						System.out.println("target: "+targetPosition);
						int dx = 0, dy = 0, dz = 0;
						if (targetPosition.cubeX() - cpos.cubeX() < 0)
							dx = -1;
						else if(targetPosition.cubeX() - cpos.cubeX() > 0)
							dx = 1;
						if (targetPosition.cubeY() - cpos.cubeY() < 0)
							dy = -1;
						else if(targetPosition.cubeY() - cpos.cubeY() > 0)
							dy = 1;
						if (targetPosition.cubeZ() - cpos.cubeZ() < 0)
							dz = -1;
						else if(targetPosition.cubeZ() - cpos.cubeZ() > 0)
							dz = 1;
						this.stateDefault +=1;
							moveToAdjacent(new Vector(dx, dy, dz));
				}
				if(getNextPosition()!=null){
					if(getNextPosition().equals(cpos)){
						setNextPosition(null);
						setCurrentActivity(Activity.NONE);
					}					
					else{
						Vector difference = getNextPosition().difference(cpos);
						double d = difference.length();
						double v = this.isSprinting ? getSprintSpeed(difference) : getWalkingSpeed(difference);
						this.setCurrentSpeed(v);
						Vector dPos = difference.multiply(v/d*dt);
						Vector velocity = difference.multiply(v/d);
						Vector newPos = cpos.add(dPos);
						System.out.println("diff: " + difference);
						for(int i=0;i< 3;i++){
				            if(getNextPosition().isInBetween(i,cpos,newPos)){
				            		double[] a = newPos.asArray();
				            		a[i] = getNextPosition().get(i);
				            		newPos = new Vector(a);
				            }
						}
						setPosition(newPos);
						setOrientation((float)Math.atan2(velocity.Y(),velocity.X()));
					}
				}	
				
				if(this.getStateDefault()==2 && !this.isSprinting &&this.isAbleToSprint() &&randInt(0, 9) < 1)
					this.sprint();
				break;
			case WORK:
				if (activityProgress >= this.getWorkDuration())
					setCurrentActivity(Activity.NONE);
				else{
					this.setWorkProgress(((float) activityProgress)/ (this.getWorkDuration()));
				}
				break;
			case ATTACK:
				if (activityProgress > 1){
					this.stopAttacking();
					setCurrentActivity(Activity.NONE);// TODO: enum doing nothing
				}
				break;
			case REST:
				int maxHp = getMaxHitpoints(this.getWeight(), this.getToughness());
				int maxSt = getMaxStamina(this.getWeight(), this.getToughness());
				double extraTime = -1d;
				if(maxHp == this.getHitpoints() && maxSt==this.getStamina())
					setCurrentActivity(Activity.NONE);
				if(this.getHitpoints()<maxHp){
					double extraRestHitpoints = getIntervalTicks(activityProgress, dt, 0.02d)*this.getToughness()/1000d;
					int extraHitpoints = getIntervalTicks(restHitpoints, extraRestHitpoints, 1d);// TODO: make constants
					int newHitpoints = this.getHitpoints() + extraHitpoints;
					double newRestHitpoints = restHitpoints + extraRestHitpoints;
					if(newHitpoints>=maxHp) {
						newHitpoints = maxHp;
						double neededExtraRestHitpoints = maxHp - this.getHitpoints() - restHitpoints %1;
						int neededTicks = (int)Math.ceil(neededExtraRestHitpoints*1000/this.getToughness());
						double neededTime = 0.2d*neededTicks - activityProgress % 0.2;
						extraTime = dt - neededTime;
						assert extraTime >= 0;
					}
					this.setHitpoints(newHitpoints);
					restHitpoints = newRestHitpoints;
				}
				if((this.getHitpoints()==maxHp || extraTime > 0d) && this.getStamina()<maxSt){
					if(extraTime > 0d)
						dt = extraTime;
					double extraRestStamina = getIntervalTicks(activityProgress, dt, 0.02d)*this.getToughness()/500d;
					int extraStamina = getIntervalTicks(restStamina, extraRestStamina, 1d);
					int newStamina = this.getStamina() + extraStamina;
					double newRestStamina = restStamina + extraRestStamina;
					if(newStamina>=maxSt){
						newStamina = maxSt;
						newRestStamina = 0;
						restHitpoints = 0;
						setCurrentActivity(Activity.NONE);
					}
					this.setStamina(newStamina);
					restStamina = newRestStamina;
				}
				
				break;
			case NONE:
				if(this.isDefaultActive())
					this.setDefaultBehaviour();
				break;

		}
		activityProgress += dt;


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
	public int getIntervalTicks(double prevProgress, double dt, double delta){
		double newTime = (prevProgress % delta) + dt;
		return (int)((newTime - newTime % delta) / delta);
	}

	//endregion

	//region Default behaviour
	/**
	 * Variable registering whether the default behaviour of this unit is activated.
	 */
	private boolean defaultActive = false;
	
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
	public void startDefaultBehviour(){
		this.defaultActive= true;
	}
	/**
	 * Start the default behaviour of this unit.
	 *
	 * @post   Default behaviour of this unit is started.
	 *       | new.getStateDefault()
	 */
	public void startDoingDefault(){
		this.stateDefault = 3;
	}
	/**
	 * Deactivate the default behaviour of this unit.
	 *
	 * @post   	Default behaviour of this unit is deactivated.
	 *       	| new.isDefaultActive() == false
	 * @post   	The new state of the default behaviour is equal to stopDoingDefault().
	 * 			| new.isDoingDefault() ==  stopDoingDefault()
	 * @post   	The new current activity of this unit is equal to None.
	 * 			| new.getCurrentActivity() == None
	 */
	public void stopDefaultBehaviour(){
		this.stopDoingDefault();
		this.defaultActive = false;
		this.setCurrentActivity(Activity.NONE);
	}
	/**
	 * Stop the default behaviour of this unit.
	 *
	 * @post   Default behaviour of this unit is stopped.
	 *       | new.getStateDefault()
	 */
	public void stopDoingDefault(){
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
		if(this.getStateDefault()!=2){
			if (!this.isValidAttack(defender))
				throw new IllegalArgumentException("Cannot attack that unit");
		}
		if(this.getStateDefault() ==2)
			this.stopDoingDefault();
		this.startAttacking();
		//Orientation
		double dx = (defender.getPosition().X()-this.getPosition().X());
		double dy = (defender.getPosition().Y()-this.getPosition().Y());
		defender.setOrientation((float)Math.atan2(-dy, -dx));
		this.setOrientation((float)Math.atan2(dy, dx));

		defender.defend(this);
		setCurrentActivity(Activity.ATTACK);


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
		setCurrentActivity(this.getCurrentActivity());
		//dodging
		if ((randInt(0,99)/100.0) < this.getDodgingProbability(attacker)){
			Boolean validDodge = false;
			while(! validDodge) {
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
	/**
	 * Start attacking.
	 * @post   This unit is attacking.
	 *       | new.isAttacking() == true
	 */
	private void startAttacking(){
		this.isAttacking = true;
	}
	/**
	 * Stop attacking.
	 * @post   This unit is not attacking.
	 *       | new.isAttacking() == false
	 */

	private void stopAttacking(){
		this.isAttacking = false;
	}
		
	//endregion

	//region Moving
	/**
	 * Method to let the Unit move to an adjacent cube.
	 * @param direction The direction the Unit should move towards. Since this method can only be used to move to
	 *                  neighbouring cubes, each element of the array must have a value of (-)1 or 0.
	 * @post	This unit stops the default behaviour if it was doing this
	 * 			| new.isDoingBehaviour
     */
	//TODO: throws comment
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
		this.targetPosition = targetPosition.getCubeCenterCoordinates();
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
		if(this.getStateDefault()!=2){
			if(!isAbleToRest())
				throw new IllegalStateException("This unit cannot rest at this moment");
		}
		if(this.getStateDefault() ==2)
			this.stopDoingDefault();
		if(this.getStateDefault() == 3)
			this.stateDefault -=1;
		setCurrentActivity(Activity.REST);
		this.restTimer = 0d;// TODO: verify when the restTimer should be reset
		this.restHitpoints = 0d;
		this.restStamina = 0d;
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
		if(this.getStateDefault()!=2){
			if(!this.isAbleToWork())
				throw new IllegalStateException("Unit is not able to work at this moment");
		}
		if(this.getStateDefault() ==2)
			this.stopDoingDefault();
		if(this.getStateDefault() == 3)
			this.stateDefault -=1;
		setCurrentActivity(Activity.WORK);
		setWorkProgress(0);
		setWorkDuration(getWorkingTime(this.getStrength()));
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

	

	

	

