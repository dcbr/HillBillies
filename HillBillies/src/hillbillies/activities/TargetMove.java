package hillbillies.activities;

import hillbillies.model.Cube;
import hillbillies.model.IWorldObject;
import hillbillies.model.Terrain;
import hillbillies.model.Unit;
import hillbillies.utils.Vector;

import java.util.*;

import static hillbillies.utils.Utils.randDouble;
import static hillbillies.utils.Utils.randInt;

/**
 * Created by Bram on 17-4-2016.
 */
public class TargetMove extends Move {

    private Path path;
	private Vector nearestPos;
	private IWorldObject leader;

//    public TargetMove(Unit unit, Set<Vector> targets)throws IllegalArgumentException, NullPointerException{
//    	super(unit);
//    	if (targets.isEmpty())
//    		throw new NullPointerException("The given set of targets is empty");
//    	//Search for the nearest target from the unit
//    	PathCalculator pathCalculator = new PathCalculator(targets);
//    	this.path = pathCalculator.computePath(unit.getPosition().getCubeCoordinates());
//    	if(this.path==null || this.nearestPos == null)
//            throw new IllegalArgumentException("The given target position is not reachable from the Unit's current position.");
//    }
    
    public TargetMove(Unit unit, Set<IWorldObject> worldObjects) {
    	super(unit);
    	if (worldObjects.isEmpty())
    		throw new NullPointerException("The given set of worldObjects is empty");
    	Map<Vector, IWorldObject> positions = new HashMap<>();
    	for (IWorldObject worldObject : worldObjects ){
    		positions.put(worldObject.getPosition().getCubeCoordinates(),worldObject);
    	}
    	this.path = new PathCalculator(positions.keySet()).computePath(unit.getPosition().getCubeCoordinates());
        if(this.path==null || this.nearestPos == null)
            throw new IllegalArgumentException("The given target position is not reachable from the Unit's current position.");
        this.leader = positions.get(nearestPos);
    }
    
    public TargetMove(Unit unit, Vector target) throws IllegalArgumentException{
        super(unit);
        Set<Vector> pos = new HashSet<>();
        pos.add(target.getCubeCoordinates());
        this.path = new PathCalculator(pos).computePath(unit.getPosition().getCubeCoordinates());
        if(this.path==null)
            throw new IllegalArgumentException("The given target position is not reachable from the Unit's current position.");
    }

    public TargetMove(Unit unit){// Find random target
        super(unit);
            Vector target = (new Vector(randDouble(unit.getWorld().getMinPosition().X(), unit.getWorld().getMaxPosition().X()),
                    randDouble(unit.getWorld().getMinPosition().Y(), unit.getWorld().getMaxPosition().Y()),
                    randDouble(unit.getWorld().getMinPosition().Z(), unit.getWorld().getMaxPosition().Z()))).getCubeCoordinates();
            Set<Vector> pos = new HashSet<>();
            pos.add(target);
            PathCalculator pathCalculator = new PathCalculator(pos);
            Path path = pathCalculator.computePath(unit.getPosition());

            if(path!=null && path.hasNext())
                this.path = path;
            else if (pathCalculator.controlledPos.size() != 0){
            	Set<Vector> pos2 = new HashSet<>();
                pos2.add(pathCalculator.controlledPos.get(randInt(0, pathCalculator.controlledPos.size() - 1)).getCubeCoordinates());
                this.path = new PathCalculator(pos2).computePath(unit.getPosition().getCubeCoordinates());
            }
            else
            	throw new IllegalStateException();
            
        
    }
    
    /**
     * Activity specific code which is called when the Activity is started.
     */
    @Override
    protected void startActivity() {
        // TODO: check if cubes along the path have collapsed
        // Recalculate path?
    }

    /**
     * Activity specific code which is called when the Activity is stopped.
     */
    @Override
    protected void stopActivity() {

    }

    /**
     * Activity specific code which is called when the Activity is interrupted.
     * This code is also called before stopActivity when the Activity is stopped.
     */
    @Override
    protected void interruptActivity() {

    }

    /**
     * Activity specific code which is called when advanceTime of this Activity is called.
     *
     * @param dt
     */
    @Override
    protected void advanceMove(double dt) {
        Vector cpos = unit.getPosition().getCubeCoordinates();
        if(!path.hasNext()){
            requestFinish();
        }else{
            AdjacentMove nextMove = new AdjacentMove(unit, path.getNext().difference(cpos), this.isSprinting(), this);
            /*controller*/unit.requestNewActivity(nextMove);
        }
    }

    /**
     * Return a boolean indicating whether or not this unit
     * is able to perform an extended movement. (When not in default mode!)
     */
    @Override
    public boolean isAbleTo() {
        return super.isAbleTo() || unit.isExecuting(TargetMove.class);// New target was set => stop this Activity and execute new one
    }

    /**
     * Activity specific code to check whether this Activity can be interrupted by nextActivity.
     *
     * @param nextActivity The Activity which will be started when this Activity is interrupted.
     * @return True if this Activity should be interrupted for nextActivity.
     */
    @Override
    protected boolean shouldInterruptFor(Activity nextActivity) {
        return super.shouldInterruptFor(nextActivity) || nextActivity instanceof AdjacentMove;// Interrupt for next position movement
    }

    /**
     * Returns the amount of XP the Unit will get when this Activity stops successfully.
     */
    @Override
    public int getXp() {
        return 0;// Unit already gets its xp from AdjacentMove
    }

    public void notifyTerrainChange(Terrain oldTerrain, Cube cube){
        if(this.path.contains(cube.getPosition())){
        	Set<Vector> pos = new HashSet<>();
        	pos.add(this.path.getTarget());
            this.path = new PathCalculator(pos).computePath(unit.getPosition().getCubeCoordinates());
        }
        if(this.path==null)
            this.requestFinish();
    }
    
	public Vector getNearestPos(){
		return nearestPos;
	}

	public IWorldObject getNearestObject(){
		return leader;
	}
    private final class PathCalculator {

        /**
         * Set registering the controlled positions.
         */
        private List<Vector> controlledPos = new ArrayList<>();

        private final ArrayDeque<Map.Entry<Vector, Integer>> remainingPositions = new ArrayDeque<>();
        private final HashMap<Vector, Integer> positionDistances = new HashMap<>();
        private final Set<Vector> targetPositions;
        
        /**
         * targetPosition
         */
        private PathCalculator(Set<Vector> pos) {
            this.targetPositions = pos;
            //this.add(pos.getCubeCoordinates(), 0); Verplaatst naar computePath
        }

//        public boolean contains(Vector position) {
//            return positionDistances.containsKey(position);
//        }

        public Set<Vector> retrain(Set<Vector> positions){
        	positions.retainAll(positionDistances.keySet());
        	return positions;
        }
        
        public void add(Vector position, int n) {
            // n is hier n0+1 uit de opgave
            // => enkel toevoegen indien er een n' bestaat waarvoor geldt dat n'+1>n=n0+1
            // Dat is equivalent met NIET toevoegen als er een n' bestaat waarvoor geldt dat n'+1<=n=n0+1 of n'<=n0
            if (positionDistances.getOrDefault(position, n) + 1 > n) {
                positionDistances.put(position, n);
                remainingPositions.add(new AbstractMap.SimpleEntry<>(position, n));
            }
        }

        public boolean hasNext() {
            return !remainingPositions.isEmpty();
        }

        public Map.Entry<Vector, Integer> getNext() {
            return remainingPositions.remove();
        }

        public Vector getNextPositionWithLowestDistance(Vector fromPosition) {
            List<Vector> nextPositions = TargetMove.this.unit.getWorld().getNeighbouringCubesPositions(fromPosition);
            Vector next = null;
            int lowestDistance = -1;
            for (Vector nextPosition : nextPositions) {
                if (positionDistances.containsKey(nextPosition) && (lowestDistance == -1 || positionDistances.get(nextPosition) < lowestDistance) &&
                        TargetMove.this.isValidNextPosition(fromPosition, nextPosition)) {
                    lowestDistance = positionDistances.get(nextPosition);
                    next = nextPosition;
                }
            }
            return next;
        }
        

        /**
         * 
         * @param fromPosition = zoeken vanaf hier 
         * @return
         */
        public Path computePath(Vector fromPosition){
        	this.add(fromPosition.getCubeCoordinates(), 0);
            controlledPos.clear();
            Set<Vector> retrainedSet = this.retrain(this.targetPositions);
            while (retrainedSet.isEmpty() && this.hasNext()) {
                searchPath(this, this.getNext());
                this.retrain(this.targetPositions);
            }
            if(retrainedSet.iterator().hasNext()){
            	TargetMove.this.nearestPos = retrainedSet.iterator().next();
            //if (this.contains(nearestPos)) {// Path found
                ArrayDeque<Vector> path = new ArrayDeque<>();
                HashSet<Vector> pathPositions = new HashSet<>();
                while (!TargetMove.this.nearestPos.equals(fromPosition)) {//TODO: kan zijn dat frompos en nearestpos moet worden omgedraaid
                    fromPosition = this.getNextPositionWithLowestDistance(fromPosition);
                    path.add(fromPosition);
                    pathPositions.add(fromPosition);
                    List<Vector> adjacentPositions = unit.getWorld().getDirectlyAdjacentCubesPositions(fromPosition);
                    adjacentPositions.removeIf(adjPos -> unit.getWorld().isCubePassable(adjPos));
                    pathPositions.addAll(adjacentPositions);
                }
                return new Path(path, pathPositions);
            } else
                return null;// No path found
        }

        private void searchPath(PathCalculator path, Map.Entry<Vector, Integer> start) {
            Set<Cube> nextCubes = TargetMove.this.unit.getWorld().getNeighbouringCubes(start.getKey());
            for (Cube nextCube : nextCubes) {
                Vector position = nextCube.getPosition();
                if (TargetMove.this.isValidNextPosition(start.getKey(), position) && !controlledPos.contains(position)) {
                    path.add(position, start.getValue() + 1);
                    controlledPos.add(position);
                }
            }
        }
    }

    public class Path{

        private final ArrayDeque<Vector> path;
        private final HashSet<Vector> pathPositions;

        private Path(ArrayDeque<Vector> path, HashSet<Vector> pathPositions){
            this.path = path;
            this.pathPositions = pathPositions;
        }

        public boolean hasNext(){
            return !path.isEmpty();
        }

        public Vector getNext(){
            Vector next = path.remove();
            pathPositions.remove(next);
            return next;
        }
        
        public boolean contains(Vector pathPosition){
           return pathPositions.contains(pathPosition);
        }

        public Vector getTarget(){
            return this.path.getLast();
        }
    }
}
