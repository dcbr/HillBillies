package hillbillies.model;

import java.util.concurrent.ThreadLocalRandom;

import be.kuleuven.cs.som.annotate.*;
import hillbillies.utils.Vector;

/**
 * Class representing a Hillbilly unit
 * @author Kenneth & Bram
 * @version 1.0
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
	private static long ID = 0;

    private static final String ALLOWED_NAME_PATTERN = "[a-zA-Z \"']+";
    
    public static final double CUBE_SIDE_LENGTH = 1;
    /*public static final double MIN_X = CUBE_SIDE_LENGTH * 0;
    public static final double MIN_Y = CUBE_SIDE_LENGTH * 0;
    public static final double MIN_Z = CUBE_SIDE_LENGTH * 0;
    public static final double MAX_X = CUBE_SIDE_LENGTH * 50;
    public static final double MAX_Y = CUBE_SIDE_LENGTH * 50;
    public static final double MAX_Z = CUBE_SIDE_LENGTH * 50;*/
    public static final Vector MIN_POSITION = new Vector(CUBE_SIDE_LENGTH * 0, CUBE_SIDE_LENGTH * 0, CUBE_SIDE_LENGTH * 0);
    public static final Vector MAX_POSITION = new Vector(CUBE_SIDE_LENGTH * 50, CUBE_SIDE_LENGTH * 50, CUBE_SIDE_LENGTH * 50);
    
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

	public static final int SPRINT_STAMINA_LOSS = 1;// Stamina loss per interval when sprinting
	public static final double SPRINT_STAMINA_LOSS_INTERVAL = 0.1d;// Unit will loose SPRINT_STAMINA_LOSS every SPRINT_STAMINA_LOSS_INTERVAL seconds when sprinting
	public static final double REST_INTERVAL = 3*60;// Unit will rest every REST_INTERVAL seconds
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
		this.setPosition(position);
		// Total
		if (! isValidInitialStrength(strength))
			strength = INITIAL_MIN_STRENGTH;
		if (! isValidInitialAgility(agility))
			agility = INITIAL_MIN_AGILITY;
		setAgility(agility);
		if (! isValidInitialToughness(toughness))
			toughness = INITIAL_MIN_TOUGHNESS;
		setToughness(toughness);
		if (! isValidInitialWeight(weight, strength, agility))
			weight = INITIAL_MIN_WEIGHT;
		setWeight(weight);
		// Nominal
		this.setInitialStamina(stamina);
		this.setInitialHitpoints(hitpoints);
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
	public Unit(String name, Vector position) throws IllegalArgumentException {
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
    public Vector getPosition() {
        return this.position.clone();
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
	 * @return 	is true if weight is between getMinWeight() and MAX_WEIGHT.
	 *       	| result == (this.getMinWeight() <= weight <= MAX_WEIGHT )
	*/
	public static boolean isValidWeight(int weight, int strength, int agility) {
		return (getMinWeight(strength, agility) <= weight && weight <= MAX_WEIGHT);
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
	 * Return the lowest possible value for weight of this unit.
	 * @param	strength
	 * 			The strength to check against.
	 * @param	agility
	 * 			The agility to check against.
	 * @return 	The lowest possible value for weight of all
	 *         	units is not below MIN_WEIGHT for all units.
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
	 * Return the hitpoints of this unit.
	 */
	@Basic @Raw
	public int getHitpoints() {
		return this.hitpoints;
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
		restTimer += dt;
		if(restTimer >= REST_INTERVAL && this.isAbleToRest()){
			rest();
		}

		switch(getCurrentActivity()){
			case MOVE:
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
					if(targetPosition.equals(cpos))
						setCurrentActivity(Activity.NONE); // TODO: enum doing nothing
					else{
						int dx = 0, dy = 0, dz = 0;
						if (targetPosition.X() < cpos.X())
							dx = 1;
						else if(targetPosition.X() > cpos.X())
							dx = -1;
						if (targetPosition.Y() < cpos.Y())
							dx = 1;
						else if(targetPosition.Y() > cpos.Y())
							dx = -1;
						if (targetPosition.Z() < cpos.Z())
							dx = 1;
						else if(targetPosition.Z() > cpos.Z())
							dx = -1;
						this.stateDefault +=1;
						moveToAdjacent(new Vector(dx, dy, dz));
					}
				}
				if(nextPosition!=null && !nextPosition.equals(cpos)){
					Vector difference = nextPosition.difference(cpos);
					double d = difference.length();
					double v = this.isSprinting ? getSprintSpeed(difference) : getWalkingSpeed(difference);
					Vector dPos = difference.multiply(v/d*dt);
					Vector velocity = difference.multiply(v/d);
					Vector newPos = cpos.add(dPos);
					if(newPos.equals(nextPosition) || nextPosition.isInBetween(cpos,newPos))
						newPos = nextPosition;// Set correct position if newPos would surpass next position
					setPosition(newPos);
					setOrientation((float)Math.atan2(velocity.Y(),velocity.X()));
				}
				if(this.getStateDefault()==2 && !this.isSprinting &&this.isAbleToSprint() &&randInt(0, 1) == 0)
					this.sprint();
				break;
			case WORK:

				break;
			case ATTACK:
				if (this.isDefending){
					this.stopDefending();
					if(!this.isAttacking())
						setCurrentActivity(Activity.NONE); 
				}
				if (activityProgress > 1){
						this.stopAttacking();
						setCurrentActivity(Activity.NONE);// TODO: enum doing nothing
				} 
				break;
			case REST:
				int maxHp = getMaxHitpoints(this.getWeight(), this.getToughness());
				int maxSt = getMaxStamina(this.getWeight(), this.getToughness());
				double extraTime = -1d;
				if(this.getHitpoints()<maxHp){
					double extraRestHitpoints = getIntervalTicks(activityProgress, dt, 0.2d)*this.getToughness()/1000d;
					int extraHitpoints = getIntervalTicks(restHitpoints, extraRestHitpoints, 1d);// TODO: make constants
					int newHitpoints = this.getHitpoints() + extraHitpoints;
					double newRestHitpoints = restHitpoints + extraRestHitpoints;
					if(newHitpoints>=maxHp) {
						newHitpoints = maxHp;
						newRestHitpoints = 0;
						double neededExtraRestHitpoints = maxHp - this.getHitpoints() - restHitpoints %1;
						int neededTicks = (int)Math.ceil(neededExtraRestHitpoints*1000/this.getToughness());
						double neededTime = 0.2d*neededTicks - activityProgress % 0.2;
						extraTime = dt - neededTime;
						assert extraTime >= 0;
					}
					this.setHitpoints(newHitpoints);
					restHitpoints = newRestHitpoints;
				}
				if((this.getHitpoints()==maxHp && extraTime==-1d || extraTime > 0d) && this.getStamina()<maxSt){
					double extraRestStamina = getIntervalTicks(activityProgress, dt, 0.2d)*this.getToughness()/500d;
					int extraStamina = getIntervalTicks(restStamina, extraRestStamina, 1d);
					int newStamina = this.getStamina() + extraStamina;
					double newRestStamina = restStamina + extraRestStamina;
					if(newStamina>=maxSt){
						newStamina = maxSt;
						newRestStamina = 0;
						setCurrentActivity(Activity.NONE);// TODO: verify this
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

	//region Movement

	/**
	 * Retrieve the Unit's base speed
	 * @return
     */
	private double getBaseSpeed(){
		return 1.5*(this.getStrength()+this.getAgility())/(200*this.getWeight()/100);
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
	 * Retrieve the Unit's sprinting speed
	 * @param direction The direction the Unit is sprinting in, this is a vector with norm 1
	 * @return
     */
	private double getSprintSpeed(Vector direction){
		return 2*this.getWalkingSpeed(direction);
	}

	/**
	 * Method to let the Unit move to an adjacent cube
	 * @param direction The direction the Unit should move towards. Since this method can only be used to move to
	 *                  neighbouring cubes, each element of the array must have a value of (-)1 or 0
     */
	public void moveToAdjacent(Vector direction){
		if(this.getStateDefault()!=2){
			if(!this.isAbleToMove() )
				throw new IllegalStateException("Unit is not able to move at this moment.");
		}
		if(this.getStateDefault() ==2) //TODO: controleren en verder aanpassen
			this.stopDoingDefault();
		if(this.getStateDefault() >=1)
			this.stateDefault -=1;
		setCurrentActivity(Activity.MOVE);
		nextPosition = this.getPosition().getCubeCoordinates().add(direction);// TODO: make setPosition to check nextPosition is between world boundaries
	}

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
		this.targetPosition = targetPosition;
	}
	private Vector nextPosition, targetPosition;
	private boolean isSprinting = false;

	public void sprint() throws IllegalStateException{
		if(!this.isAbleToSprint())
			throw new IllegalStateException("The Unit is not able to sprint!");
		this.isSprinting = true;
	}

	public void stopSprint(){
		this.isSprinting = false;
	}

	public boolean isAbleToSprint(){
		return this.isMoving() && getStamina()>MIN_STAMINA;
	}

	public boolean isAbleToMove(){
		return !this.isInitialRestMode() && this.getCurrentActivity()!=Activity.ATTACK && this.getCurrentActivity()!=Activity.WORK;
	}

	public boolean isMoving(){
		return this.getCurrentActivity()==Activity.MOVE;
	}

	public boolean isSprinting(){
		return this.isSprinting;
	}
	//endregion


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

// TODO: COMMENT!
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

	public boolean isAbleToWork(){
		return !this.isInitialRestMode() && this.getCurrentActivity() != Activity.ATTACK;
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
	 * @post	The activityProgress timer is reset
	 * 			| new.activityProgress == 0d
	 */
	@Raw
	private void setCurrentActivity(Activity activity) {
		this.activity = activity;
		this.activityProgress = 0d;
	}

	/**
	 * Variable registering the current Activity of this unit.
	 */
	private Activity activity;

	private double activityProgress = 0d;
	
	/**
	 * Returns a random integer between min and max, inclusive.
	 *
	 * @param 	min 
	 * 			Minimum value
	 * @param 	max 
	 * 			Maximum value.  
	 * @throws  IllegalArgumentException * min is greater than max
	 * 			| (max < min)
	 */
	public static int randInt(int min, int max) throws IllegalArgumentException {
		if (max < min)
			throw new IllegalArgumentException();
		return ThreadLocalRandom.current().nextInt(min, max + 1);
	}

//TODO: COMMENT
	public void defend(Unit attacker){
		this.startDefending();
		setCurrentActivity(Activity.ATTACK);
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
	

	//TODO: COMMENT
	/**
	 * Register an attack from this unit to the given defender.
	 *
	 * @param  defender
	 *         The defender of this attack.
	 * 
	 */
	public void attack(Unit defender) throws IllegalArgumentException{
		if (!this.isValidAttack(defender))
			throw new IllegalArgumentException("Cannot attack that unit");
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
	 * Check whether an attack is a valid for any unit.
	 *  
	 * @param  defender
	 *         The defender to check.
	 * @return 	is true if the position of the attackers cube lies next to the defenders cube 
	 * 			or is the same cube and the attacker do not attacks itself and
	 * 			the attacker is not attacking another unit at the same time and
	 * 			the attacker is not in the initial rest mode
	 *       | result == (this.getId()!=defender.getId() &&
	 *				!this.isAttacking &&
	 *				!this.isInitialRestMode() &&
	 * 				(Math.abs(defender.getPosition().cubeX()-this.getPosition().cubeX())<=1) &&
	 *				(Math.abs(defender.getPosition().cubeY()-this.getPosition().cubeY())<=1) &&
	 *				(Math.abs(defender.getPosition().cubeZ()-this.getPosition().cubeZ())<=1))
	 */
	public boolean isValidAttack(Unit defender) {
		return this.getId()!=defender.getId() &&
				!this.isAttacking() &&
				!this.isInitialRestMode() &&
				(Math.abs(defender.getPosition().cubeX()-this.getPosition().cubeX())<=1) &&
				(Math.abs(defender.getPosition().cubeY()-this.getPosition().cubeY())<=1) &&
				(Math.abs(defender.getPosition().cubeZ()-this.getPosition().cubeZ())<=1);
	
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
	 * Start attacking.
	 * @post   This unit is attacking.
	 *       | new.isAttacking() == true
	 */
	public void startAttacking(){
		this.isAttacking = true;
	}
	
	/**
	 * Stop attacking.
	 * @post   This unit is not attacking.
	 *       | new.isAttacking() == false
	 */
	
	public void stopAttacking(){
		this.isAttacking = false;
	}
	
	/**
	 * Variable registering whether this unit is attacking.
	 */
	private boolean isAttacking = false;
	
	/**
	 * Return a boolean indicating whether or not this person
	 * is defending.
	 */
	@Basic @Raw
	public boolean isDefending() {
		return this.isDefending;
	}
	
	/**
	 * Start defending.
	 * @post   This unit is defending.
	 *       | new.isDefending() == true
	 */
	public void startDefending(){
		this.isDefending = true;
	}
	
	/**
	 * Stop defending.
	 * @post   This unit is not defending.
	 *       | new.isDefending() == false
	 */
	
	public void stopDefending(){
		this.isDefending = false;
	}
	
	/**
	 * Variable registering whether this unit is defending.
	 */
	private boolean isDefending= false;

	/**
	 * Return the Id of this Unit.
	 */
	@Basic
	@Raw
	@Immutable
	public long getId() {
		return this.Id;
	}

	//region resting

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

	public boolean isAbleToRest(){
		return this.getCurrentActivity()!=Activity.ATTACK;
	}

	public boolean isInitialRestMode(){
		return this.getCurrentActivity()==Activity.REST && this.restHitpoints<1d;
	}

	private double restTimer = 0d;
	private double restHitpoints = 0d;
	private double restStamina = 0d;
	//endregion

	//region default
	/**
	 * Variable registering whether the default behaviour of this unit is activated.
	 */
	private boolean defaultActive = false;
	
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
	 * Return a boolean indicating whether or not the units default behaviour is activated
	 */
	@Basic
	public boolean isDefaultActive(){
		return this.defaultActive;
	}
	
	/**
	 * Variable registering the state of the default behaviour.
	 */
	private int stateDefault = 0
			// 0 && 1(in moveToAdjacent)= not doing default behaviour
			// 2= doing the default behaviour
			// 3= starting default behaviour
			;
	
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
	 * Stop the default behaviour of this unit.
	 *
	 * @post   Default behaviour of this unit is stopped.
	 *       | new.getStateDefault()
	 */
	public void stopDoingDefault(){
		this.stateDefault = 0;
	}
	
	/**
	 * Return a integer indicating in what state the default behaviour is.
	 */
	@Basic
	public int getStateDefault(){
		return this.stateDefault;
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
			this.moveToTarget(new Vector ((double)(Math.random()*(MAX_POSITION.X()-MIN_POSITION.X())+MIN_POSITION.X()),
					(double)(Math.random()*(MAX_POSITION.Y()-MIN_POSITION.Y())+MIN_POSITION.Y()),
					(double)(Math.random()*MAX_POSITION.Z()-MIN_POSITION.Z())+MIN_POSITION.Z()));
			if (this.isAbleToSprint() && randInt(0, 1) == 0){
				this.sprint();
			}
		}
		if (activity == 1)
			this.work();
		if (activity ==2)
			this.rest();
	}
	//endregion
	
}

	

	

	

