package hillbillies.activities;

import hillbillies.model.*;
import hillbillies.utils.Vector;

import java.util.*;

import static hillbillies.utils.Utils.randDouble;
import static hillbillies.utils.Utils.randInt;

/**
 * Created by Bram on 17-4-2016.
 */
public class TargetMove extends Move {

    private Path path;
	private IWorldObject leader;
    private Set<? extends IWorldObject> targets = new HashSet<>();

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
    
    public TargetMove(Unit unit, Set<? extends IWorldObject> worldObjects) {
    	super(unit);
    	if (worldObjects.isEmpty())
    		throw new NullPointerException("The given set of worldObjects is empty");
        if(!calculatePath(unit.getPosition().getCubeCoordinates(), worldObjects))
            throw new IllegalArgumentException("The given target objects are not reachable from the Unit's current position.");
    }
    
    public TargetMove(Unit unit, Vector target) throws IllegalArgumentException{
        super(unit);
        if(!calculatePath(unit.getPosition().getCubeCoordinates(), target.getCubeCoordinates()))
            throw new IllegalArgumentException("The given target position is not reachable from the Unit's current position.");
    }

    public TargetMove(Unit unit){// Find random target
        super(unit);
            Vector target = (new Vector(randDouble(unit.getWorld().getMinPosition().X(), unit.getWorld().getMaxPosition().X()),
                    randDouble(unit.getWorld().getMinPosition().Y(), unit.getWorld().getMaxPosition().Y()),
                    randDouble(unit.getWorld().getMinPosition().Z(), unit.getWorld().getMaxPosition().Z()))).getCubeCoordinates();
            PathCalculator pathCalculator = new PathCalculator(target);
            Path path = pathCalculator.computePath(unit.getPosition());

            if(path!=null && path.hasNext())
                this.path = path;
            else if (pathCalculator.controlledPos.size() != 0)
                calculatePath(unit.getPosition().getCubeCoordinates(), pathCalculator.controlledPos.get(randInt(0, pathCalculator.controlledPos.size() - 1)).getCubeCoordinates());
            else
            	throw new IllegalStateException("The given unit cannot reach any other position.");
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
        if(this.leader!=null) {
            this.targets.removeIf(obj -> !isValidLeader(obj));// Remove invalid leaders

            if(!isValidLeader(this.leader)) {
                if (this.hasNextLeader())
                    calculatePath(cpos, this.targets);// Calculate path to new leader
                else {
                    requestFinish();// No valid targets over
                    return;
                }
            }else{
                if (this.path.getTarget() != this.leader.getPosition().getCubeCoordinates()){// Leader's position has changed
                    Vector newTarget = leader.getPosition().getCubeCoordinates();
                    if(this.path.contains(newTarget)){
                        this.path.removeFromPath(newTarget);
                    }else
                        this.calculatePath(cpos, newTarget);// If no path is available, this.path will be null and requestFinish will be called
                }
            }
        }
        if(this.path!=null && this.path.hasNext()){
            AdjacentMove nextMove = new AdjacentMove(unit, path.getNext().difference(cpos), this.isSprinting(), this);
            unit.requestNewActivity(nextMove);
        }else
            requestFinish();
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
        if(this.path.dependsOn(cube.getPosition())){
            if(!calculatePath(unit.getPosition().getCubeCenterCoordinates(), this.path.getTarget()))
                this.requestFinish();
        }
    }
    
	public Vector getNearestPos(){
		return this.path.getTarget();
	}

	public IWorldObject getNearestObject(){
		return leader;
	}

    private boolean hasNextLeader(){
        return !this.targets.isEmpty();
    }

    private static boolean isValidLeader(IWorldObject leader){
        if(leader==null || leader.isTerminated()) return false;
        if(leader instanceof Unit && ((Unit)leader).isFalling()) return false;
        return true;
    }

    private boolean calculatePath(Vector fromPosition, Vector targetPosition){
        this.path = new PathCalculator(targetPosition).computePath(fromPosition);
        return this.path!=null;
    }

    private boolean calculatePath(Vector fromPosition, Set<? extends IWorldObject> targets){
        this.targets = targets;
        Map<Vector, IWorldObject> positions = new HashMap<>();
        for (IWorldObject worldObject : targets){
            positions.put(worldObject.getPosition().getCubeCoordinates(),worldObject);
        }
        this.path = new PathCalculator(positions.keySet()).computePath(fromPosition);
        if(this.path==null) return false;
        this.leader = positions.get(this.path.getTarget());
        return true;
    }

    private final class PathCalculator {

        /**
         * Set registering the controlled positions.
         */
        private List<Vector> controlledPos = new ArrayList<>();

        private final ArrayDeque<Map.Entry<Vector, Integer>> remainingPositions = new ArrayDeque<>();
        private final HashMap<Vector, Integer> positionDistances = new HashMap<>();
        private final Set<Vector> targetPositions;
        private Vector target;
        
        /**
         * targetPosition
         */
        private PathCalculator(Set<Vector> targets) {
            this.targetPositions = targets;
            this.target = null;
        }

        private PathCalculator(Vector target){
            this.targetPositions = new HashSet<>(1);
            this.targetPositions.add(target);
        }

        private boolean targetFound(){
            return this.target!=null;
        }
        
        private void add(Vector position, int n) {
            // n is hier n0+1 uit de opgave
            // => enkel toevoegen indien er een n' bestaat waarvoor geldt dat n'+1>n=n0+1
            // Dat is equivalent met NIET toevoegen als er een n' bestaat waarvoor geldt dat n'+1<=n=n0+1 of n'<=n0
            if (positionDistances.getOrDefault(position, n) + 1 > n) {
                positionDistances.put(position, n);
                remainingPositions.add(new AbstractMap.SimpleEntry<>(position, n));
            }
        }

        private boolean hasNext() {
            return !remainingPositions.isEmpty();
        }

        private Map.Entry<Vector, Integer> getNext() {
            return remainingPositions.remove();
        }

        private Vector getNextPositionWithLowestDistance(Vector fromPosition) {
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
        private Path computePath(Vector fromPosition){
        	this.add(fromPosition.getCubeCoordinates(), 0);
            controlledPos.clear();
            while (!this.targetFound() && this.hasNext()) {
                searchNextPositions(this.getNext());
            }
            if(this.targetFound()){// Path found
                Path path = new Path();
                Vector pos = this.target;
                while (!fromPosition.equals(pos)) {
                    path.add(pos);
                    pos = this.getNextPositionWithLowestDistance(pos);
                }
                return path;
            } else
                return null;// No path found
        }

        private void searchNextPositions(Map.Entry<Vector, Integer> start) {
            List<Vector> nextPositions = TargetMove.this.unit.getWorld().getNeighbouringCubesPositions(start.getKey());
            Iterator<Vector> it = nextPositions.iterator();
            while(it.hasNext() && !this.targetFound()){
                Vector position = it.next();
                if (TargetMove.this.isValidNextPosition(start.getKey(), position) && !controlledPos.contains(position)) {
                    this.add(position, start.getValue() + 1);
                    controlledPos.add(position);

                    if(this.targetPositions.contains(position))
                        this.target = position;
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

        private Path(){
            this.path = new ArrayDeque<>();
            this.pathPositions = new HashSet<>();
        }

        public void removeFromPath(Vector position) {
        	assert path.contains(position);
			while(path.size()>0 && path.getLast() != position){
				Vector oldTarget = path.removeLast();
                Vector newTarget = null;
                if(path.size()>0)
                    newTarget = path.getLast();
                this.removeRedundantPathPositions(oldTarget, newTarget);
			}	
		}

		public boolean hasNext(){
            return !path.isEmpty();
        }

        public Vector getNext(){
            Vector next = path.removeFirst();
            Vector newNext = null;
            if(path.size()>0)
                newNext = path.getFirst();
            this.removeRedundantPathPositions(next, newNext);
            return next;
        }

        public boolean contains(Vector pathPosition){
            return path.contains(pathPosition);
        }

        public boolean dependsOn(Vector position){
           return pathPositions.contains(position);
        }

        public Vector getTarget(){
            return this.path.getLast();
        }

        public void add(Vector position){
        	path.addFirst(position);
        	pathPositions.add(position);
            List<Vector> adjacentPositions = unit.getWorld().getDirectlyAdjacentCubesPositions(position);
            adjacentPositions.removeIf(adjPos -> unit.getWorld().isCubePassable(adjPos));
            pathPositions.addAll(adjacentPositions);
        }

        /**
         * Remove the pathPositions that became redundant by removing oldEndPosition.
         * @param oldEndPosition The position that is removed from the path. This
         *                       position must be an 'end' position -> be the first
         *                       or the last in the path.
         * @param newEndPosition The position that became the new 'end' position by
         *                       removing oldEndPosition. This position is now the
         *                       first or the last in the path.
         */
        private void removeRedundantPathPositions(Vector oldEndPosition, Vector newEndPosition){
            Collection<Vector> redundantPathPositions = new HashSet<>();
            Collection<Vector> newNextAdjacentPositions = (newEndPosition==null) ? new HashSet<>() : unit.getWorld().getDirectlyAdjacentCubesPositions(newEndPosition);
            unit.getWorld().getDirectlyAdjacentCubesSatisfying(
                    redundantPathPositions,
                    oldEndPosition,
                    cube -> !newNextAdjacentPositions.contains(cube.getPosition()),
                    WorldObject::getPosition
            );
            pathPositions.remove(oldEndPosition);
            pathPositions.removeAll(redundantPathPositions);
        }
    }
}
