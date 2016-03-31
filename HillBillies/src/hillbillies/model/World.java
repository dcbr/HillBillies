package hillbillies.model;


import be.kuleuven.cs.som.annotate.*;

/**
 * Class representing a Hillbilly world
 * @author Kenneth & Bram
 * @version 1.0
 */
public class World {
/** TO BE ADDED TO CLASS HEADING
 * @invar  The Terrain Matrix of each World must be a valid Terrain Matrix for any
 *         World.
 *       | isValidTerrainMatrix(getTerrainMatrix())
 */
	
	private static final int MAX_UNITS = 100;
	private static final int MAX_FACTIONS = 5;
	private static final int MAX_UNITS_PER_FACTION = 50;
	
	
	
	

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
	 * Return the Terrain Matrix of this World.
	 */
	@Basic @Raw
	public int[][][] getTerrainMatrix() {
		return this.terrainMatrix;
	}
	
	/**
	 * Check whether the given Terrain Matrix is a valid Terrain Matrix for
	 * any World.
	 *  
	 * @param  Terrain Matrix
	 *         The Terrain Matrix to check.
	 * @return 
	 *       | result == 
	*/
	public static boolean isValidTerrainMatrix(int[][][] terrainTypes) {
		//TODO
		return false;
	}
	
	/**
	 * Set the Terrain Matrix of this World to the given Terrain Matrix.
	 * 
	 * @param  terrainMatrix
	 *         The new Terrain Matrix for this World.
	 * @post   The Terrain Matrix of this new World is equal to
	 *         the given Terrain Matrix.
	 *       | new.getTerrainMatrix() == terrainTypes
	 * @throws ExceptionName_Java
	 *         The given Terrain Matrix is not a valid Terrain Matrix for any
	 *         World.
	 *       | ! isValidTerrainMatrix(getTerrainMatrix())
	 */
	@Raw
	public void setTerrainMatrix(int[][][] terrainMatrix) 
			throws IllegalArgumentException {
		if (! isValidTerrainMatrix(terrainMatrix))
			throw new IllegalArgumentException();
		this.terrainMatrix = terrainMatrix;
		setNbCubesX(terrainMatrix.length);
		setNbCubesY(terrainMatrix[0].length);
		setNbCubesZ(terrainMatrix[0][0].length);
		
		
	}
	
	/**
	 * Variable registering the Terrain Matrix of this World.
	 */
	private int[][][] terrainMatrix;
	
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
	
	

}
