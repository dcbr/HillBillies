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
 */
public class Unit {

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

    /**
     * Initialize this new Unit with given name.
     *
     * @param name
     * The name for this new Unit.
     * @effect The name of this new Unit is set to
     * the given name.
     * | this.setName(name)
     */
    public Unit(String name) throws IllegalArgumentException {
        this.setName(name);
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
     * Variable registering the name of this Unit.
     */
    private String name;

    /** TO BE ADDED TO CLASS HEADING
     * @invar The position of each Unit must be a valid position for any
     * Unit.
     * | isValidPosition(getPosition())
     */
    /**
     * Initialize this new Unit with given position.
     *
     * @param position
     * The position for this new Unit.
     * @effect The position of this new Unit is set to
     * the given position.
     * | this.setPosition(position)
     */
    public Unit(double[] position) throws IllegalArgumentException {
        this.setPosition(position);
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
     * Variable registering the position of this Unit.
     */
    private double[] position;
}
