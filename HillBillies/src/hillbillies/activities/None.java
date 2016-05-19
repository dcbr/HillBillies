package hillbillies.activities;

import hillbillies.model.Cube;
import hillbillies.model.Task;
import hillbillies.model.Unit;
import hillbillies.utils.Vector;

import java.util.ArrayList;
import java.util.List;

import static hillbillies.utils.Utils.randDouble;
import static hillbillies.utils.Utils.randInt;

/**
 * Created by Bram on 29-3-2016.
 */
public class None extends Activity {

    public None(Unit unit){
        super(unit);
    }

    /**
     * Activity specific code which is called when the Activity is started.
     */
    @Override
    protected void startActivity() {

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
    protected void advanceActivity(double dt) {
        if(this.isDefault()) {
            if(this.unit.getTask()==null) {
                Task availableTask = this.unit.getFaction().getScheduler().getHighestPriorityAssignableTask();
                if (availableTask == null)
                    setDefaultBehaviour();// No task available => do something random
                else {
                    this.unit.getFaction().getScheduler().schedule(availableTask, unit);
                    availableTask.run();
                }
            }else{
                this.unit.getTask().getRunner().advanceTask(dt);
            }
        }
    }

    /**
     * Activity specific code to check whether this Activity can be started.
     *
     * @return True if this Activity can be started as the nextActivity of the currently active Activity.
     */
    @Override
    public boolean isAbleTo() {
        return !unit.isInitialRestMode() && !unit.isAttacking() && !unit.isWorking();
    }

    /**
     * Activity specific code to check whether this Activity can be interrupted by nextActivity.
     *
     * @param nextActivity The Activity which will be started when this Activity is interrupted.
     * @return True if this Activity should be interrupted for nextActivity.
     */
    @Override
    protected boolean shouldInterruptFor(Activity nextActivity) {
        return true;// None can be interrupted for any Activity
    }

    /**
     * Returns the amount of XP the Unit will get when this Activity stops successfully.
     */
    @Override
    public int getXp() {
        return 0;
    }

    @Override
    public String toString() {
        return "none";
    }

    /**
     * Set current activity of this Unit to a random activity.
     * @post The new current activity of this new Unit is equal to
     * a random activity.
     * | new.getCurrentActivity()
     * @post The new state of the default behaviour is equal to startDoingDefault()
     * | new.isDoingDefault() == startDoingDefault()
     * @throws IllegalStateException * The default behaviour is not activated for this unit.
     * |   !this.isDefault()
     */
    private void setDefaultBehaviour() throws IllegalStateException{
        if(!this.isDefault())
            throw new IllegalStateException("The default behaviour of unit is not activated");

        List<Cube> AdjCubes = new ArrayList<Cube>(unit.getWorld().getDirectlyAdjacentCubes(unit.getPosition().getCubeCoordinates()));
        List<Unit> units = new ArrayList<>();
        for (int i = 0; i < AdjCubes.size(); i++){
            units.addAll(unit.getWorld().getUnitsInCube(AdjCubes.get(i)));
        }
        units.removeIf(defender -> unit.getFaction() == defender.getFaction() || Attack.isAccessible(this.unit, defender.getPosition().getCubeCoordinates()));
        int nb = 2;
        if (units.size() > 0)
            nb +=1;
        int activity = randInt(0,nb);
        System.out.println(activity);
        if (activity ==0){
            if (unit.getHitpoints() == Unit.getMaxHitpoints(unit.getWeight(), unit.getToughness()) && unit.getStamina() == Unit.getMaxStamina(unit.getWeight(), unit.getToughness()))
                activity = randInt(1,nb);
            else unit.rest();
        }
        if (activity ==1){
            try{
            	unit.requestNewActivity(new TargetMove(unit));
            }catch (IllegalStateException e){
            	activity =2;
            }
        }
        if (activity == 2) {
            List<Vector> workPositions = unit.getWorld().getDirectlyAdjacentCubesPositions(unit.getPosition().getCubeCoordinates()); //TODO: geeft nullpointerException bij hillbillies.model.World.getDirectlyAdjacentCubesSatisfying(World.java:696)
            workPositions.add(unit.getPosition());																// daar wordt er een cube gemapt, terwijl vector gevraagd is?
            workPositions.removeIf(position -> !Work.isAccessible(unit, position));
            unit.work(workPositions.get(randInt(0,workPositions.size()-1)));
        }
        if (activity == 3){
            unit.attack(units.get(randInt(0,units.size()-1)));
        }
    }
}
