package hillbillies.model;

import java.util.HashSet;
import java.util.Set;

import be.kuleuven.cs.som.annotate.*;

public class Faction {
	
	private static final int MAX_UNITS_PER_FACTION = 50;


	/** TO BE ADDED TO CLASS HEADING
	 * @invar  The UnitSet of each Faction must be a valid UnitSet for any
	 *         Faction.
	 *       | isValidUnitSet(getUnitSet())
	 */


/**
 * Initialize this new Faction with given UnitSet.
 *
 * @param  unit
 *         The UnitSet for this new Faction.
 * @effect The UnitSet of this new Faction is set to
 *         the given UnitSet.
 *       | this.setUnitSet(unit)
 */
public Faction(Unit unit)
		throws Exception {
	this.addUnit(unit);
}


/**
 * Return the UnitSet of this Faction.
 */
@Basic @Raw
public Set<Unit> getUnits() {
	return new HashSet<Unit>(unitSet);
}

/**
 * Check whether the given UnitSet is a valid UnitSet for
 * any Faction.
 *  
 * @param  UnitSet
 *         The UnitSet to check.
 * @return 
 *       | result == 
*/
public boolean isValidUnitSet(Unit unit) {
	return (getNbUnits() <= MAX_UNITS_PER_FACTION);
}

public int getNbUnits(){
	return this.getUnits().size();
}

/**
 * Set the UnitSet of this Faction to the given UnitSet.
 * 
 * @param  unit
 *         The new UnitSet for this Faction.
 * @post   The UnitSet of this new Faction is equal to
 *         the given UnitSet.
 *       | new.getUnitSet() == unit
 * @throws Exception
 *         The given UnitSet is not a valid UnitSet for any
 *         Faction.
 *       | ! isValidUnitSet(getUnitSet())
 */
@Raw
public void addUnit(Unit unit) 
		throws Exception {
	if (! isValidUnitSet(unit))
		throw new Exception();
	this.unitSet.add(unit);
}

/**
 * Variable registering the UnitSet of this Faction.
 */
private final Set<Unit> unitSet = new HashSet<Unit>();
}
