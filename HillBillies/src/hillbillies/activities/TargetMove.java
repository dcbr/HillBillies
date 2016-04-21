package hillbillies.activities;

import hillbillies.model.Cube;
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

    public TargetMove(Unit unit, Vector target) throws IllegalArgumentException{
        super(unit);
        this.path = new PathCalculator(target.getCubeCoordinates()).computePath(unit.getPosition());
        if(this.path==null)
            throw new IllegalArgumentException("The given target position is not reachable from the Unit's current position.");
    }

    public TargetMove(Unit unit){// Find random target
        super(unit);
            Vector target = new Vector(randDouble(unit.getWorld().getMinPosition().X(), unit.getWorld().getMaxPosition().X()),
                    randDouble(unit.getWorld().getMinPosition().Y(), unit.getWorld().getMaxPosition().Y()),
                    randDouble(unit.getWorld().getMinPosition().Z(), unit.getWorld().getMaxPosition().Z()));
            PathCalculator pathCalculator = new PathCalculator(unit.getPosition());
            Path path = pathCalculator.computePath(target);

            if(path!=null && path.hasNext())
                this.path = path;
            else if (pathCalculator.controlledPos.size() != 0)
                this.path = new PathCalculator(pathCalculator.controlledPos.get(randInt(0, pathCalculator.controlledPos.size() - 1))).computePath(unit.getPosition());

            else
            	throw new IllegalStateException();
            
        
    }

    /**
     * Activity specific code which is called when the Activity is started.
     */
    @Override
    protected void startActivity() {
        // TODO: check if cubes along the path have collapsed
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
        if(!path.hasNext()){// TODO: check if cubes along the path have collapsed
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

    private final class PathCalculator {

        /**
         * Set registering the controlled positions.
         */
        private List<Vector> controlledPos = new ArrayList<>();

        private final ArrayDeque<Map.Entry<Vector, Integer>> remainingPositions = new ArrayDeque<>();
        private final HashMap<Vector, Integer> positionDistances = new HashMap<>();
        private final Vector targetPosition;

        private PathCalculator(Vector targetPosition) {
            this.targetPosition = targetPosition;
            this.add(targetPosition, 0);
        }

        public boolean contains(Vector position) {
            return positionDistances.containsKey(position);
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

        public Path computePath(Vector fromPosition) {
            controlledPos.clear();
            while (!this.contains(fromPosition.getCubeCoordinates()) && this.hasNext()) {
                searchPath(this, this.getNext());
            }
            //controlledPos.clear();
            if (this.contains(fromPosition.getCubeCoordinates())) {// Path found
                ArrayDeque<Vector> path = new ArrayDeque<>();
                HashSet<Vector> pathPositions = new HashSet<>();
                Vector pos = fromPosition.getCubeCoordinates();
                while (!pos.equals(targetPosition)) {
                    pos = this.getNextPositionWithLowestDistance(pos);
                    path.add(pos);
                    pathPositions.add(pos);
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
        private final HashSet<Vector> pathPositions;// TODO: path positions must also contain all directly adjacent cubes in order to check the path validity

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
    }
}
