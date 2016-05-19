package hillbillies.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.utils.Vector;

/**
 * Created by Bram on 2-4-2016.
 */
public interface IWorld {

    /**
     * Check whether the given position is a valid position
     * for any IWorldObject in this world.
     *
     * @param position The position to check
     * @return True when the given position is a valid position
     */
    public boolean isValidPosition(Vector position);

    /**
     * Get the minimum position in this world.
     * This is the position of the most bottom left back cube.
     */
    public Vector getMinPosition();

    /**
     * Get the maximum position in this world.
     * This is the position of the most up right front cube.
     */
    public Vector getMaxPosition();

    /**
     * Add the given unit to the set of units of this world.
     *
     * @param unit The unit to be added.
     * @pre The given unit is effective and already references
     * this world.
     * | (unit != null) && (unit.getWorld() == this)
     */
    public void addUnit(Unit unit);

    /**
     * @param vector vector in cubeCoordinates
     * @return
     */
    public boolean isCubePassable(Vector vector);

    public Vector getSpawnPosition();

    public Set<Cube> getDirectlyAdjacentCubes(Vector position);

    public <T> void getDirectlyAdjacentCubesSatisfying(Collection<T> collection, Vector cubeCoordinates, Predicate<Cube> condition, Function<Cube, T> mapper);

    public Set<Cube> getNeighbouringCubes(Vector position);

    public List<Vector> getDirectlyAdjacentCubesPositions(Vector cubeCoordinates);

    public List<Vector> getNeighbouringCubesPositions(Vector cubeCoordinates);

    public Set<Unit> getUnitsInCube(Cube cube);

    public Cube getCube(Vector position);

    public boolean hasAsFaction(@Raw Faction faction);

    public Set<Log> getLogs(boolean inCube);

    public Set<Boulder> getBoulders(boolean inCube);

    public Set<Unit> getUnits();

    public int getNbUnits();

    public Set<Cube> getWorkshops();

    public <T> void getNeighbouringCubesSatisfying(Collection<T> collection, Vector cubeCoordinates, Predicate<Cube> condition, Function<Cube, T> mapper);

    public boolean isAdjacentSolid(Vector position);

    public boolean isLowerSolid(Vector position);

}
