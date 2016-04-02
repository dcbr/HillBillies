package hillbillies.model;


import java.util.HashMap;
import java.util.Map;

import be.kuleuven.cs.som.annotate.*;
import hillbillies.utils.Vector;
import hillbillies.model.Cube;

/**
 * Class representing a Hillbilly world
 * @author Kenneth & Bram
 * @version 1.0
 */
public class World implements IWorld {
/** TO BE ADDED TO CLASS HEADING
 * @invar  The Terrain Matrix of each World must be a valid Terrain Matrix for any
 *         World.
 *       | isValidTerrainMatrix(getTerrainMatrix())
 */
	
	private static final int MAX_UNITS = 100;
	private static final int MAX_FACTIONS = 5;



	/**
	 * Constant reflecting the minimum position in this world.
	 */
	public static final Vector MIN_POSITION = new Vector(Cube.CUBE_SIDE_LENGTH * 0, Cube.CUBE_SIDE_LENGTH * 0, Cube.CUBE_SIDE_LENGTH * 0);
	/**
	 * Constant reflecting the maximum position in the units world.
	 */// TODO: change this to getMaxPosition since the World's dimensions are variable
	public static final Vector MAX_POSITION = new Vector(Cube.CUBE_SIDE_LENGTH * 50, Cube.CUBE_SIDE_LENGTH * 50, Cube.CUBE_SIDE_LENGTH * 50);


	/**
	 * Initialize this new World with given Terrain Matrix.
	 *
	 * @param  terrainTypes
	 *         The Terrain Matrix for this new World.
	 * @effect The Terrain Matrix of this new World is set to
	 *         the given Terrain Matrix.
	 *       | this.setTerrainMatrix(terrainTypes)
	 */
	public World(int[][][] terrainTypes)
			throws IllegalArgumentException {
		this.setTerrainMatrix(terrainTypes);
	}
	

	
	/**
	 * Check whether the given Terrain Matrix is a valid Terrain Matrix for
	 * any World.
	 *  
	 * @param  terrainTypes
	 *         The Terrain Matrix to check.
	 * @return 
	 *       | result == 
	*/
	public static boolean isValidTerrainMatrix(int[][][] terrainTypes) {
		//TODO
		return false;
	}

	/**
	 * Check whether the given position is a valid position for
	 * any WorldObject.
	 *
	 * @param position The position to check.
	 * @return True when each coordinate of position is within the predefined bounds of MIN_POSITION and getMaxPosition()
	 * | result == position.isInBetween(MIN_POSITION, getMaxPosition())
	 */
	public boolean isValidPosition(Vector position){
		//TODO: probably move this to WorldObject?
		return position.isInBetween(MIN_POSITION, this.getMaxPosition());
	}
	
	/**
	 * Set the Terrain Matrix of this World to the given Terrain Matrix.
	 * 
	 * @param  terrainMatrix
	 *         The new Terrain Matrix for this World.
	 * @post   The Terrain Matrix of this new World is equal to
	 *         the given Terrain Matrix.
	 *       | new.getTerrainMatrix() == terrainTypes
	 * @throws IllegalArgumentException
	 *         The given Terrain Matrix is not a valid Terrain Matrix for any
	 *         World.
	 *       | ! isValidTerrainMatrix(getTerrainMatrix())
	 */
	@Raw
	public void setTerrainMatrix(int[][][] terrainMatrix) 
			throws IllegalArgumentException {
		Map<Vector, Cube> CubeMap = new HashMap<Vector , Cube>();
		setNbCubesX(terrainMatrix.length);
		setNbCubesY(terrainMatrix[0].length);
		setNbCubesZ(terrainMatrix[0][0].length);
		for(int x = 0; x< getNbCubesX(); x++){
			for(int y = 0; y< getNbCubesY(); y++){
				if (terrainMatrix[x].length != getNbCubesY()){
					throw new IllegalArgumentException();
				}
				for(int z = 0; z< getNbCubesZ(); z++){
					if (terrainMatrix[x][y].length != getNbCubesZ()){
						throw new IllegalArgumentException();
					}
					Vector position = new Vector(x,y,z);
					CubeMap.put(position, new Cube(this,position,Terrain.fromId(terrainMatrix[x][y][z])));
				}
			}
		}
		
		
	}
	

	private int NbCubesX;
	private int NbCubesY;
	private int NbCubesZ;
	
	private void setNbCubesX(int nbCubesX){
		this.NbCubesX = nbCubesX;
	}
	public int getNbCubesX(){
		return this.NbCubesX;
	}
	
	private void setNbCubesY(int nbCubesY){
		this.NbCubesY = nbCubesY;
	}
	public int getNbCubesY(){
		return this.NbCubesY;
	}
	
	private void setNbCubesZ(int nbCubesZ){
		this.NbCubesZ = nbCubesZ;
	}
	public int getNbCubesZ(){
		return this.NbCubesZ;
	}

	/**
	 * Get the maximum position in this world.
     */
	public Vector getMaxPosition(){
		return new Vector(Cube.CUBE_SIDE_LENGTH * getNbCubesX(), Cube.CUBE_SIDE_LENGTH * getNbCubesY(), Cube.CUBE_SIDE_LENGTH * getNbCubesZ());
	}
	
	public void spawnUnit(){
		
	}
}
