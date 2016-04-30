package hillbillies.model;

import java.util.HashSet;
import java.util.Set;

import be.kuleuven.cs.som.annotate.*;

/**
 * Class representing a Faction which contains a limited number of Units.
 * @author Kenneth & Bram
 * @version 1.0
 *
 * @invar  This Faction is a valid Faction.
 *       | isValidFaction()
 * @invar Each faction must have proper units.
 * | hasProperUnits()
 * @invar Each Faction can have its scheduler as scheduler.
 * | canHaveAsScheduler(this.getScheduler())
 */
public class Faction {
	
	private static final int MAX_UNITS_PER_FACTION = 50;


	/**
	 * Initialize this new Faction which will contain the given unit.
	 *
	 * @param  unit
	 *         The Unit for this new Faction.
	 * @post This new faction contains the given unit.
	 * | new.getNbUnits() == 1
	 * | new.hasAsUnit(unit) == true
	 * @post The scheduler of this new Faction is a valid Scheduler for
	 * this Faction.
	 * | canHaveAsScheduler(new.getScheduler())
	 */
	public Faction(Unit unit) {
		this();
		this.addUnit(unit);
	}

	/**
	 * Initialize this new Faction as a non-terminated Faction with
	 * no units yet.
	 *
	 * @post This new faction has no units yet.
	 * | new.getNbUnits() == 0
	 * @post The scheduler of this new Faction is a valid Scheduler for
	 * this Faction.
	 * | canHaveAsScheduler(new.getScheduler())
	 */
	@Raw
	public Faction() {
		this.scheduler = new Scheduler(this);
	}


	/**
	 * Return the units belonging to this Faction.
	 */
	@Basic
	@Raw
	public Set<Unit> getUnits() {
		return new HashSet<>(units);
	}

	/**
	 * Check whether this Faction is a valid Faction.
	 *
	 * @return
	 *       | result == (this.getNbUnits() <= MAX_UNITS_PER_FACTION)
	*/
	public boolean isValidFaction() {
		return (this.getNbUnits() <= MAX_UNITS_PER_FACTION);
	}

	/**
	 * Check whether this Faction can have a new Unit.
	 * @return True as long as the MAX_UNITS_PER_FACTION limit is not reached.
	 * 			| result == (this.getNbUnits()+1 <= MAX_UNITS_PER_FACTION)
     */
	public boolean canHaveNewUnit() { return this.getNbUnits()+1 <= MAX_UNITS_PER_FACTION; }



	/**
	 * Check whether this faction has the given unit as one of its
	 * units.
	 *
	 * @param unit
	 * The unit to check.
	 */
	@Basic
	@Raw
	public boolean hasAsUnit(@Raw Unit unit) {
		return units.contains(unit);
	}
	/**
	 * Check whether this faction can have the given unit
	 * as one of its units.
	 *
	 * @param unit
	 * The unit to check.
	 * @return True if and only if the given unit is effective
	 * and this faction doesn't contain the unit already
	 * and that unit is a valid unit for this faction.
	 * | result ==
	 * | (unit != null) && (!unit.isTerminated()) &&
	 * | (!hasAsUnit(unit)) && Unit.isValidFaction(this)
	 */
	@Raw
	public boolean canHaveAsUnit(Unit unit) {
		return (unit != null) && (!unit.isTerminated()) && (unit.isValidFaction(this));
	}
	/**
	 * Check whether this faction has proper units attached to it.
	 *
	 * @return True if and only if this faction can have each of the
	 * units attached to it as one of its units,
	 * and if each of these units references this faction as
	 * the faction to which they are attached.
	 * | for each unit in Unit:
	 * | if (hasAsUnit(unit))
	 * | then canHaveAsUnit(unit) &&
	 * | (unit.getFaction() == this)
	 */
	public boolean hasProperUnits() {
		for (Unit unit: units) {
			if (!canHaveAsUnit(unit))
			    return false;
			if (unit.getFaction() != this)
			    return false;
		}
		return true;
	}
	/**
	 * Return the number of units associated with this faction.
	 *
	 * @return The total number of units collected in this faction.
	 * | result ==
	 * | card({unit:Unit | hasAsUnit({unit)})
	 */
	public int getNbUnits() {
		return units.size();
	}
	/**
	 * Add the given unit to the set of units of this faction.
	 *
	 * @param unit
	 * The unit to be added.
	 * @pre The given unit is effective and already references
	 * this faction.
	 * | (unit != null) && (unit.getFaction() == this)
	 * @post This faction has the given unit as one of its units.
	 * | new.hasAsUnit(unit)
	 * @throws IndexOutOfBoundsException
	 *         This Faction has reached its maximum number of units
	 *       | !canHaveNewUnit()
	 */
	public void addUnit(@Raw Unit unit) throws IndexOutOfBoundsException{
		assert canHaveAsUnit(unit);
		if (!canHaveNewUnit())
			throw new IndexOutOfBoundsException("This Faction has reached its maximum number of units.");
		this.units.add(unit);
	}
	/**
	 * Remove the given unit from the set of units of this faction.
	 *
	 * @param unit
	 * The unit to be removed.
	 * @pre This faction has the given unit as one of
	 * its units, and the given unit does not
	 * reference any faction.
	 * | this.hasAsUnit(unit) &&
	 * | (unit.getFaction() == null)
	 * @post This faction no longer has the given unit as
	 * one of its units.
	 * | ! new.hasAsUnit(unit)
	 */
	@Raw
	public void removeUnit(Unit unit) {
		assert this.hasAsUnit(unit) && (unit.getFaction() == null);
		units.remove(unit);
	}
	/**
	 * Variable referencing a set collecting all the units
	 * of this faction.
	 *
	 * @invar The referenced set is effective.
	 * | units != null
	 * @invar Each unit registered in the referenced list is
	 * effective and not yet terminated.
	 * | for each unit in units:
	 * | ( (unit != null) &&
	 * | (! unit.isTerminated()) )
	 */
	private final Set<Unit> units = new HashSet<>();

	/**
	 * Return the scheduler of this Faction.
	 */
	@Basic
	@Raw
	@Immutable
	public Scheduler getScheduler() {
	    return this.scheduler;
	}
	/**
	 * Check whether this Faction can have the given scheduler as its scheduler.
	 *
	 * @param scheduler
	 * The scheduler to check.
	 * @return
	 * | result == (scheduler.getFaction()==this)
	 */
	@Raw
	public boolean canHaveAsScheduler(Scheduler scheduler) {
	    return scheduler.getFaction()==this;
	}
	/**
	 * Variable registering the scheduler of this Faction.
	 */
	private final Scheduler scheduler;
}
