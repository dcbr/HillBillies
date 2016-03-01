package hillbillies.model;

import be.kuleuven.cs.som.annotate.*;

/**
 * Class representing a Hillbilly unit
 * @author Kenneth & Bram
 * @version 1.0
 *
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
    private static final String ALLOWED_NAME_PATTERN = "[a-zA-Z \"']+";

    public static final double CUBE_SIDE_LENGTH = 1;
    /*public static final double MIN_X = CUBE_SIDE_LENGTH * 0;
    public static final double MIN_Y = CUBE_SIDE_LENGTH * 0;
    public static final double MIN_Z = CUBE_SIDE_LENGTH * 0;
    public static final double MAX_X = CUBE_SIDE_LENGTH * 50;
    public static final double MAX_Y = CUBE_SIDE_LENGTH * 50;
    public static final double MAX_Z = CUBE_SIDE_LENGTH * 50;*/
    public static final double[] MIN_POSITION = new double[]{ CUBE_SIDE_LENGTH * 0, CUBE_SIDE_LENGTH * 0, CUBE_SIDE_LENGTH * 0};
    public static final double[] MAX_POSITION = new double[]{ CUBE_SIDE_LENGTH * 50, CUBE_SIDE_LENGTH * 50, CUBE_SIDE_LENGTH * 50};
    
    public static final int MIN_STRENGTH = 1;
    public static final int MAX_STRENGTH = 200;
    public static final int MIN_AGILITY = 1;
    public static final int MAX_AGILITY = 200;
    public static final int MIN_TOUGHNESS = 1;
    public static final int MAX_TOUGHNESS = 200;
    public static final int MIN_WEIGHT = 1;
    public static final int MAX_WEIGHT = 200;
    public static final int MIN_STAMINA = 0;
    public static final int MIN_HITPOINTS = 0;
	public static final float MIN_ORIENTATION = 0;
	public static final float MAX_ORIENTATION = 2*(float)Math.PI;

    public static final int INITIAL_MIN_STRENGTH = 25;
    public static final int INITIAL_MAX_STRENGTH = 100;
    public static final int INITIAL_MIN_AGILITY = 25;
    public static final int INITIAL_MAX_AGILITY = 100;
    public static final int INITIAL_MIN_TOUGHNESS = 25;
    public static final int INITIAL_MAX_TOUGHNESS = 100;
    public static final int INITIAL_MIN_WEIGHT = 25;
    public static final int INITIAL_MAX_WEIGHT = 100;
	public static final int INITIAL_MIN_STAMINA = 1;
	public static final int INITIAL_MIN_HITPOINTS = 1;
	public static final float INITIAL_ORIENTATION = (float)Math.PI/2;
	//endregion

	//region Private members

	/**
	 * Variable registering the name of this Unit.
	 */
	private String name;
	/**
	 * Variable registering the position of this Unit.
	 */
	private double[] position;
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

	//endregion
	//region Constructors

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
	 * @pre    The given stamina must be a valid stamina for any unit.
	 *       | isValidStamina(stamina)
	 * @pre    The given hitpoints must be a valid hitpoints for any unit.
	 *       | isValidHitpoints(hitpoints)
	 * @effect The name of this new Unit is set to
	 * the given name.
	 * | this.setName(name)
	 * @effect The position of this new Unit is set to
	 * the given position.
	 * | this.setPosition(position)
	 * @effect The orientation of this new Unit is set to the default orientation.
	 * 			| this.setOrientation(INITIAL_ORIENTATION)
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
	public Unit(String name, double[] position, int strength, int agility, int toughness, int weight, int stamina, int hitpoints) throws IllegalArgumentException {
		// Defensive
		this.setName(name);
		this.setPosition(position);
		// Total
		if (! isValidStrength(strength))
			strength = INITIAL_MIN_STRENGTH;
		setStrength(strength);
		if (! isValidAgility(agility))
			agility = INITIAL_MIN_AGILITY;
		setAgility(agility);
		if (! isValidToughness(toughness))
			toughness = INITIAL_MIN_TOUGHNESS;
		setToughness(toughness);
		if (! isValidWeight(weight, strength, agility))
			weight = INITIAL_MIN_WEIGHT;
		setWeight(weight);
		// Nominal
		this.setStamina(stamina);
		this.setHitpoints(hitpoints);
		// Total
		this.setOrientation(INITIAL_ORIENTATION);
		
		this.setCurrentActivity(Activity.REST);
	}

	/**
	 * Initialize this new Unit with given name and position. All other properties are set to their default initial values.
	 * @param name The name of this new Unit
	 * @param position The position of this new Unit
	 * @effect This Unit is initialized with the given name and position and the default initial values for its other properties
	 * 			| this(name, position, INITIAL_MIN_STRENGTH, INITIAL_MIN_AGILITY, INITIAL_MIN_TOUGHNESS, INITIAL_MIN_WEIGHT, INITIAL_MIN_STAMINA, INITIAL_MIN_HITPOINTS)
	 */
	public Unit(String name, double[] position) throws IllegalArgumentException {
		this(name, position, INITIAL_MIN_STRENGTH, INITIAL_MIN_AGILITY, INITIAL_MIN_TOUGHNESS, INITIAL_MIN_WEIGHT, INITIAL_MIN_STAMINA, INITIAL_MIN_HITPOINTS);
	}
	//endregion
    /**
     * Return the name of this Unit.
     */
    @Basic
    @Raw
    public String getName() {
        return this.name;
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
     * Return the position of this Unit.
     */
    @Basic
    @Raw
    public double[] getPosition() {
        return this.position;
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
    public static boolean isValidPosition(double[] position) {
        boolean isValid = true;
        for(int i = 0; i<position.length ; i++){
            isValid = isValid && position[i] >= MIN_POSITION[i] && position[i] <= MAX_POSITION[i];
        }
        return isValid;
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
    public void setPosition(double[] position) throws IllegalArgumentException {
        if (! isValidPosition(position))
            throw new IllegalArgumentException();
        this.position = position;
    }

	
	/**
	 * Return the strength of this unit.
	 */
	@Basic @Raw
	public int getStrength() {
		return this.strength;
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
	 * Set the strength of this unit to the given strength.
	 * 
	 * @param  strength
	 *         The new strength for this unit.
	 * @post   If the given strength is a valid strength for any unit,
	 *         the strength of this new unit is equal to the given
	 *         strength.
	 *       | if (isValidStrength(strength))
	 *       |   then new.getStrength() == strength
	 */
	@Raw
	public void setStrength(int strength) {
		if (isValidStrength(strength))
			this.strength = strength;
	}

	
	/**
	 * Return the agility of this unit.
	 */
	@Basic @Raw
	public int getAgility() {
		return this.agility;
	}
	
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
	 * Set the agility of this unit to the given agility.
	 * 
	 * @param  agility
	 *         The new agility for this unit.
	 * @post   If the given agility is a valid agility for any unit,
	 *         the agility of this new unit is equal to the given
	 *         agility.
	 *       | if (isValidAgility(agility))
	 *       |   then new.getAgility() == agility
	 */
	@Raw
	public void setAgility(int agility) {
		if (isValidAgility(agility))
			this.agility = agility;
	}


	
	/**
	 * Return the toughness of this unit.
	 */
	@Basic @Raw
	public int getToughness() {
		return this.toughness;
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
	 * Return the weight of this unit.
	 */
	@Basic @Raw
	public int getWeight() {
		return this.weight;
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
	 * @return 	is true if strength is between MIN_WEIGHT and MAX_WEIGHT 
	 * 			and weight is at least the average between strength and agility
	 *       	| result == (MIN_WEIGHT <= weight <= MAX_WEIGHT && weight >= (strength + agility)/2)
	*/
	public static boolean isValidWeight(int weight, int strength, int agility) {
		return (MIN_WEIGHT <= weight && weight <= MAX_WEIGHT && weight >= (strength + agility)/2);
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
	 * Return the stamina of this unit.
	 */
	@Basic @Raw
	public int getStamina() {
		return this.stamina;
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
	 * Return the hitpoints of this unit.
	 */
	@Basic @Raw
	public int getHitpoints() {
		return this.hitpoints;
	}

	/**
	 * Check whether the given hitpoints is a valid hitpoints for
	 * any unit.
	 *
	 * @param  	hitpoints
	 *         	The hitpoints to check.
	 * @return
	 *       	| result == (MIN_HITPOINTS <= hitpoints <= getMaxHitpoints(weight, toughness))
	*/
	public static boolean isValidHitpoints(int hitpoints, int weight, int toughness) {
		return (MIN_HITPOINTS <= hitpoints && hitpoints <= getMaxHitpoints(weight, toughness));
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
	 * Return the orientation of this Unit.
	 */
	@Basic
	@Raw
	public float getOrientation() {
		return this.orientation;
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
	 * Set the orientation of this Unit to the given orientation.
	 * * @param orientation
	 * The new orientation for this Unit.
	 * @post If the given orientation is a valid orientation for any Unit,
	 * the orientation of this new Unit is equal to the given
	 * orientation. Otherwise the orientation is set to its default
	 * | if (isValidOrientation(orientation))
	 * | then new.getOrientation() == orientation}
	 */
	@Raw
	public void setOrientation(float orientation) {
		this.orientation = orientation%MAX_ORIENTATION;// Deal with too large orientations
		if(this.orientation<0) this.orientation += MAX_ORIENTATION;// Deal with negative orientations
	}
	/**
	 * Variable registering the orientation of this Unit.
	 */
	private float orientation;


	public void advanceTime(double dt){
		// Defensively without documentation
	}
	
	
	/**
	 * Return the work duration of this unit.
	 */
	@Raw
	public float getWorkDuration() {
		return this.workDuration;
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
	 * Variable registering the work duration of this unit.
	 */
	private float workDuration = 0;
	
	
	/**
	 * Return the work progress of this unit.
	 */
	@Raw
	public float getWorkProgress() {
		return this.workProgress;
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
	
	/**
	 * Variable registering the work Progress of this unit.
	 */
	private float workProgress = 0;
	
// COMMENT!
	public void work(){
		setCurrentActivity(Activity.WORK);
		setWorkProgress(0);
		setWorkDuration(getWorkingTime(this.getStrength()));
	}
	
	/**
	 * Return the current Activity of this unit.
	 */
	 @Raw
	public Activity getCurrentActivity() {
		return this.activity;
	}
		
	/**
	 * Set the current Activity of this unit to the given current Activity.
	 * 
	 * @param  activity
	 *         The new current Activity for this unit.
	 * @post   The current Activity of this new unit is equal to
	 *         the given current Activity.
	 *       | new.getCurrentActivity() == activity
	 */
	@Raw
	private void setCurrentActivity(Activity activity) {
		this.activity = activity;
	}
	
	/**
	 * Variable registering the current Activity of this unit.
	 */
	private Activity activity;
	
	public void defend(Unit attacker){
		//dodging
		if (Math.random() < this.getDodgingProbability(attacker)){
			
		}
			
		
	}
}
