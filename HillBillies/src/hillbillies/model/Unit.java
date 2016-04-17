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
 */
public class Unit extends WorldObject {// TODO: extend WorldObject

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


	/**
	 * Variable registering the current Activity of this unit.
	 */
	private Activity activity;
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
		if(!this.isMoving() && !this.isFalling())
			return (0d);
		return ((Move)this.getCurrentActivity()).getCurrentSpeed();
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
		return (this.nextPosition==null) ? null : this.nextPosition.clone();// TODO: move this to AdjacentMove
	}
	
	/**
	 * Return the orientation of this Unit.
	 */
	@Basic
	@Raw
	public float getOrientation() {
		return this.orientation;
	}

	private double getRestHitpointsGain(){
		return this.getToughness()/200d;
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
		return this.weight;// TODO: add weight of carriedMaterial
	}

	//endregion

	//region Checkers
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
		if(activity!=Activity.MOVE)
			stopSprint();
		this.activity.start(isDefault);
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
		List<Cube> AdjCubes = new ArrayList<Cube>(this.getWorld().getDirectlyAdjacentCubes(this.getPosition().getCubeCoordinates()));
		List<Unit> units = new ArrayList<>();
		for (int i = 0; i < AdjCubes.size(); i++){
			units.addAll(this.getWorld().getUnitsInCube(AdjCubes.get(i)));
		}
		units.removeIf(unit -> this.getFaction() == unit.getFaction());
		int nb = 2;
		if (units.size() > 0)
			nb +=1;
		int activity = randInt(0,nb);
		if (activity ==0){
			if (getHitpoints() == getMaxHitpoints(getWeight(), getToughness()) && getStamina() == getMaxStamina(getWeight(), getToughness()))
				activity = randInt(1,nb);
			else this.rest();
		}
		if (activity ==1){
			/*PathCalculator p = new PathCalculator(this.getPosition());
			Vector target = new Vector(-1,-1,-1);
			this.path = p.computePath(this.getPosition());
			int size = controlledPos.size();
			if( size == 0)
				activity = 2;
			*/
			//TODO: manier zoeken om alle bereikbare posities op te lijsten
			Vector target = new Vector (randDouble(getWorld().getMinPosition().X(), getWorld().getMaxPosition().X()),
					randDouble(getWorld().getMinPosition().Y(), getWorld().getMaxPosition().Y()),
					randDouble(getWorld().getMinPosition().Z(), getWorld().getMaxPosition().Z()));
			PathCalculator pathCalculator= new PathCalculator(this.getPosition());
			Path path = pathCalculator.computePath(target);

			if (path.hasNext())
				this.moveToTarget(target);
			else if (controlledPos.size() ==0)
				activity = 2;
			else
				this.moveToTarget(controlledPos.get(randInt(0, controlledPos.size()-1)));

			if (this.isAbleToSprint() && randInt(0, 99) < 1){
				this.sprint();
			}
		}
		if (activity == 2) {
			List<Vector> workPositions = getWorld().getDirectlyAdjacentCubesPositions(this.getPosition());
			workPositions.add(this.getPosition());
			this.work(workPositions.get(randInt(0,workPositions.size()-1)));
		}
		if (activity == 3){
			this.attack(units.get(randInt(0,units.size()-1)));
		}
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
	public Unit(IWorld world) throws IllegalArgumentException{	//TODO: Random value for hitpoints and stamina
		this(world, "Unnamed Unit", world.getSpawnPosition(), randInt(INITIAL_MIN_STRENGTH, INITIAL_MAX_STRENGTH),
				randInt(INITIAL_MIN_AGILITY, INITIAL_MAX_AGILITY), randInt(INITIAL_MIN_TOUGHNESS, INITIAL_MAX_TOUGHNESS),
				randInt(getInitialMinWeight(INITIAL_MIN_STRENGTH,INITIAL_MIN_AGILITY), INITIAL_MAX_WEIGHT) );
	}

	/**
	 * Initialize this new Unit with given name and position in the given world. All other properties are set to their
	 * default initial values.
	 * @param world The world this new Unit belongs to
	 * @param name The name of this new Unit
	 * @param position The position of this new Unit
	 * @effect This Unit is initialized with the given name and position and the default initial values for its other properties
	 * 			| this(name, position, INITIAL_MIN_STRENGTH, INITIAL_MIN_AGILITY, INITIAL_MIN_TOUGHNESS, INITIAL_MIN_WEIGHT, INITIAL_MIN_STAMINA, INITIAL_MIN_HITPOINTS)
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
	 * 			| world.addUnit(this) // TODO
	 * @post	This new Unit belongs to a Faction related to the given world.
	 * 			| world.getFactions().contains(this.getFaction()) == true // TODO
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

		this.setCurrentActivity(Activity.NONE);
	}

	//endregion

	//region AdvanceTime

	@Override
	public void advanceTime(double dt){
		// Defensively without documentation
		if (!isFalling() && getCurrentActivity() != Activity.MOVE && !validatePosition(getPosition())){
			falling(); //TODO: bij move telkens controleren of hij opeens moet vallen als er een blok veranderd naar passable
		}

		switch(getCurrentActivity()) {

			case FALLING:
				Vector cPos = getPosition();
				Vector cPosCube = cPos.getCubeCenterCoordinates();
				if (cPos == cPosCube && isLowerSolid(cPos) && this.getWorld().getCube(cPos).isPassable()) {
					setCurrentSpeed(0);
					setCurrentActivity(Activity.NONE);
					removeHitpoints((int) (fallingLevel - cPos.Z()));
					//setHitpoints((int)(getHitpoints()-(fallingLevel-cPos.Z())));
					fallingLevel = 0;
				} else {
					double speed = getCurrentSpeed();
					Vector nextPos = cPos.add(new Vector(0, 0, -speed * dt));
					if (isLowerSolid(cPos) && this.getWorld().getCube(cPos.getCubeCoordinates()).isPassable() && (cPosCube.isInBetween(2, cPos, nextPos) || cPos.Z() <= cPosCube.Z()))
						setPosition(cPosCube);
					else if (nextPos.getCubeCenterCoordinates().isInBetween(2, cPos, nextPos) && isLowerSolid(nextPos) && this.getWorld().getCube(nextPos.getCubeCoordinates()).isPassable())
						setPosition(nextPos.getCubeCenterCoordinates());
					else
						setPosition(nextPos);
				}
				break;

			case NONE:
				if (this.isDefaultActive())
					this.setDefaultBehaviour();
				break;
		}
		this.getCurrentActivity().advanceTime(dt);
	}

	@Override
	protected boolean validatePosition(Vector position) {
		IWorld world = this.getWorld();
		// world.isValidPosition(position) wordt al gecontroleerd in isValidPosition
		if(world.isCubePassable(position.getCubeCoordinates())){
			if(isAdjacentSolid(position))
				return true;
			if(isFalling())
				return true;
		}
		return false;
	}

	private boolean isAdjacentSolid(Vector position){
		if(position.cubeZ() == 0)
			return true;
		for(Cube cube : this.getWorld().getDirectlyAdjacentCubes(position.getCubeCoordinates()))
			if(!cube.isPassable())
				return true;
		return false;
	}

	private boolean isLowerSolid(Vector position){
		if(position.cubeZ() == 0)
			return true;
		if(!this.getWorld().getCube(position.getCubeCoordinates().add(new Vector(0,0,-1))).isPassable())
			return true;
		return false;
	}
	/**
	 * Check whether a given position is a good dodging position.
	 * @param position
	 * The position to check against
	 * @return true if this.getWorld().isValidPosition(position)
	 * 				&& this.getWorld().isCubePassable(position)
	 */
	private boolean isValidDodgePos(Vector position){
		IWorld world = this.getWorld();
		if(world.isValidPosition(position) && world.isCubePassable(position))
			return true;
		return false;
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
	 * @post   	The unit stops doing the default.
	 * 			| this.stopDoingDefault()
	 * @post   	The new current activity of this unit is equal to None.
	 * 			| new.getCurrentActivity() == NONE.
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
	 * @effect	If the attack is successfully, the attacker gains FIGHT_POINTS, else, the defender gains them.
	 * @throws	IllegalArgumentException * the attacker is not able to attack this unit.
	 * 			| !this.isValidAttack(defender)	
	 */
	public void attack(Unit defender) throws IllegalArgumentException{
		/*if(this.getStateDefault()!=2){
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
		if(isSuccessFulAttack){
			this.addXP(FIGHT_POINTS);
			isSuccessFulAttack = false;
		}
		else
			defender.addXP(FIGHT_POINTS);
		setCurrentActivity(Activity.ATTACK);
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
	public void defend(Unit attacker){// Code is moved to Activity Attack
		/*setCurrentActivity(this.getCurrentActivity());// TODO: check if this is still correct
		//dodging
		if ((randInt(0,99)/100.0) < this.getDodgingProbability(attacker)){
			Boolean validDodge = false;
			while(! validDodge) {// TODO: in part2 replace this by a list of valid positions and choose a random element from that list
				int dodgeX = randInt(-1, 1);
				int dodgeY = randInt(-1, 1);
				Vector newPos = this.getPosition().add(new Vector(dodgeX, dodgeY, 0));
				if ((dodgeX != 0 || dodgeY != 0) &&
						(isValidDodgePos(newPos))) {
					validDodge = true;
					this.setPosition(getPosition().add(new Vector(dodgeX,dodgeY,0)));
				}
			}
		}// fails to block
		else if (!((randInt(0,99)/100.0) < this.getBlockingProbability(attacker))){
			removeHitpoints(this.getDamagingPoints(attacker));
			//this.setHitpoints(this.getHitpoints()- this.getDamagingPoints(attacker));
			isSuccessFulAttack = true;
		}*/
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
		/*if(this.getStateDefault()!=2){
			if(!this.isAbleToMove() )
				throw new IllegalStateException("Unit is not able to move at this moment.");
		}
		if(this.getStateDefault() ==2) 
			this.stopDoingDefault();
		if(this.getStateDefault() >=1)
			this.stateDefault -=1;
		// setNextPosition throws the exception
		this.setNextPosition(this.getPosition().getCubeCenterCoordinates().add(direction));
		this.lastPosition = this.getPosition();
		setCurrentActivity(Activity.MOVE);*/
		setCurrentActivity(new AdjacentMove(this, direction));
	}
	
	/**
	 * Method to let the Unit move to an adjacent cube.
	 * @param dx The x direction to move towards.
	 * @param dy The y direction to move towards.
	 * @param dz The z direction to move towards.
	 * @pre 	Since this method can only be used to move to neighbouring cubes,
	 * each element must have a value of (-)1 or 0.
	 * @post	The unit moves to the adjacent cube in direction (dx, dy, dz)
	 * 			| this.moveToAdjacent(Vector(dx,dy,dz))
	 * @post	targetPosition of this unit is set to its default value.
	 * 			| new.targetPosition
     */
	public void moveToAdjacent(int dx, int dy, int dz){
		this.targetPosition = null;// TODO move this to AdjacentMove
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
	public void moveToTarget(Vector targetPosition) throws IllegalStateException, IllegalArgumentException{
		/*if(this.getStateDefault()!=2){
			if(!this.isAbleToMove())
				throw new IllegalStateException("Unit is not able to move at this moment.");
		}
		if(this.getStateDefault() ==2)
			this.stopDoingDefault();
		if(this.getStateDefault() == 3)
			this.stateDefault -=1;

		this.lastPosition = this.getPosition();// TODO: lastPosition will be overriden with every call to moveToAdjacent
		setTargetPosition(targetPosition.getCubeCenterCoordinates());// TODO: make setter and getter for targetPosition and check for invalid positions
		PathCalculator p = new PathCalculator(targetPosition);
		this.path = p.computePath(this.getPosition());
		if(this.path != null)
			setCurrentActivity(Activity.MOVE);*/

		if(targetPosition==null || !isValidPosition(targetPosition))
			throw new IllegalArgumentException("The target is not a valid position.");
		setCurrentActivity(new TargetMove(this, targetPosition));
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
		/*if(this.getStateDefault()!=2){
			if(!this.isAbleToWork())
				throw new IllegalStateException("Unit is not able to work at this moment");
		}
		if(this.getStateDefault() ==2)
			this.stopDoingDefault();
		if(this.getStateDefault() == 3)
			this.stateDefault -=1;
		setWorkCube(this.getWorld().getCube(position.getCubeCoordinates()));
		setWorkProgress(0);// TODO: change workProgress to activityProgress
		setWorkDuration(getWorkingTime(this.getStrength()));
		Vector workDirection = position.getCubeCoordinates().difference(this.getPosition().getCubeCoordinates());
		if(workDirection.X()!=0 || workDirection.Y()!=0)
			setOrientation((float) Math.atan2(workDirection.Y(),workDirection.X()));
		setCurrentActivity(Activity.WORK);*/
		setCurrentActivity(new Work(this, position));
	}


	/**
	 * Return the carriedMaterial of this Unit.
	 */
	@Basic
	@Raw
	public Material getCarriedMaterial() {
	    return this.carriedMaterial;
	}
	/**
	 * Check whether the given material is a valid carriedMaterial for
	 * any Unit.
	 *
	 * @param material
	 * The carriedMaterial to check.
	 * @return
	 * | result == material.getWorld() == this.getWorld()
	 */
	public boolean isValidMaterial(Material material) {
	    return material==null || material.getWorld()==this.getWorld();
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
	    if (! isValidMaterial(material))
	        throw new IllegalArgumentException();
	    this.carriedMaterial = material;
		material.setOwner(this);
	}
	/**
	 * Variable registering the carriedMaterial of this Unit.
	 */
	private Material carriedMaterial;

	public boolean isCarryingLog(){
		return this.getCarriedMaterial()!=null && this.getCarriedMaterial() instanceof Log;
	}

	public boolean isCarryingBoulder(){
		return this.getCarriedMaterial()!=null && this.getCarriedMaterial() instanceof Boulder;
	}

	public boolean isCarryingMaterial(){
		return this.getCarriedMaterial()!=null;
	}

	public void dropCarriedMaterial(){
		Material m = this.getCarriedMaterial();
		this.carriedMaterial = null;
		m.setOwner(this.getWorkCube());
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
		return true;// TODO: check if the Unit's world contains this faction
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
	 * Constant reflecting XP gaining after a fight.
	 */
	private static final int FIGHT_POINTS = 20;
	/**
	 * Constant reflecting XP gaining after a move.
	 */
	private static final int MOVE_POINTS = 1;
	/**
	 * Constant reflecting XP gaining after a work.
	 */
	private static final int WORK_POINTS = 10;
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
	private void addXP(int xp){ //TODO: toevoegen bij methodes waar en hoeveel xp wordt verdient
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

	//region falling
	/**
	 * Method to let this unit fall.
	 * @post	THe units stops its default behaviour when he was doing it.
	 * 			|new.isDoingBehaviour
	 * @effect	The unit's speed is changed
	 * 			|new.getCurrentSpeed()
	 * @effect	The units position is set to the center of the cube.
	 * 			|new.getPosition()
	 * @effect	The units current activity is changed to FALLING
	 * 			|new.getCurrentActivity()
	 * @effect	THe falling level is set to the Units Z Cube coordinate.
	 * 			|this.fallingLevel = getPosition().cubeZ()
	 */
	public void falling(){
		if(this.getStateDefault() ==2)
			this.stopDoingDefault();
		setCurrentSpeed(3d);
		Vector pos = getPosition();
		this.fallingLevel = getPosition().cubeZ();
		setCurrentActivity(Activity.FALLING);
		setPosition(new Vector(pos.getCubeCenterCoordinates().X(),pos.getCubeCenterCoordinates().Y(), pos.Z() ));
	}

	public boolean isFalling(){
		return this.getCurrentActivity()==Activity.FALLING;
	}

	/**
	 * Variable registering the starting falling level of this unit.
	 */
	private int fallingLevel = 0;

	//endregion

	public final ActivityController activityController = new ActivityController();

	public final class ActivityController{

		/**
		 * Variable registering the time passed since the unit's last rest.
		 */
		private double restTimer = 0d;

		public final None NONE = new None(Unit.this);
		public final Rest REST = new Rest(Unit.this);

		private Stack<Activity> activityStack;

		private ActivityController(){
			this.activityStack = new Stack<>();
			this.activityStack.push(NONE);
			this.getCurrentActivity().start();
		}

		public void requestNewActivity(Activity activity) throws IllegalArgumentException,IllegalStateException{
			if(activity.getUnitId()!=Unit.this.getId())
				throw new IllegalArgumentException("This activity is not bound to this unit.");
			if(activity.isActive())
				throw new IllegalArgumentException("This activity is already active.");
			if(!this.getCurrentActivity().isDefault() && !activity.isAbleTo())
				throw new IllegalStateException("This unit cannot " + activity.toString() + " at this moment");

			try{
				this.getCurrentActivity().interrupt(activity);
			}catch(IllegalStateException interruptException){
				try{
					this.getCurrentActivity().stop(activity);
					this.activityStack.pop();
				}catch(IllegalStateException stopException){
					throw new IllegalStateException("The current activity cannot be interrupted nor stopped by the given Activity.");
				}
			}
			this.activityStack.push(activity);
			this.getCurrentActivity().start();
			if(this.getCurrentActivity() instanceof Rest)
				restTimer = 0d;// Reset rest timer
		}

		public void requestActivityFinish(Activity activity) throws IllegalArgumentException{
			// TODO: maybe change this to reportActivityFinish and check for inactiviy of current activity and then resume previous in stack
			if(activity == null)
				throw new IllegalArgumentException("Invalid activity.");
			if(activity.getUnitId()!=Unit.this.getId())
				throw new IllegalArgumentException("This activity is not bound to this unit.");
			if(activity!=this.getCurrentActivity() || !activity.isActive())
				throw new IllegalArgumentException("This activity is not currently active.");
			//if(this.activityStack.size()==1)
			//	throw new IllegalStateException("Since this is the last activity in stack, it can not be finished.");
			this.getCurrentActivity().stop(activity);
			this.activityStack.pop();
			if(this.activityStack.size()==0)
				this.activityStack.push(NONE);
			this.getCurrentActivity().start();// Resume previous activity in stack
		}

		private void stopActivity(Activity nextActivity) throws IllegalStateException{
			Activity oldActivity = this.getCurrentActivity();
			oldActivity.stop(nextActivity);
			this.activityStack.pop();
			if(oldActivity.wasSuccessful())
				Unit.this.addXP(oldActivity.getXp());
			if(oldActivity instanceof Rest)
				restTimer = 0d;// Reset rest timer
		}

		private Activity getCurrentActivity(){
			return this.activityStack.peek();
		}

		public boolean isCurrentActivity(Activity activity){
			return this.getCurrentActivity()==activity;
		}

		public void advanceTime(double dt){
			if(!this.isResting()) {
				restTimer += dt;
				if (restTimer >= Rest.REST_INTERVAL && REST.isAbleTo()) {
					rest();
				}
			}

			this.getCurrentActivity().advanceTime(dt);
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
	}
}

	

	

	

