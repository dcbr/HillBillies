package hillbillies.utils;

import hillbillies.model.Unit;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class representing a Vector
 * @author Kenneth & Bram
 * @version 1.0
 */
public class Vector {

    private double[] vectorList;

    public Vector(double x, double y, double z){
        this(new double[]{x,y,z});
    }
    public Vector(double[] vectorList){
        this.vectorList = vectorList.clone();
    }
    public Vector(){
        this(0,0,0);
    }

    public Vector add(Vector other){
        Vector result = this.clone();
        for(int i=0;i<vectorList.length;i++){
            result.vectorList[i] += other.vectorList[i];
        }
        return result;
    }

    public Vector difference(Vector other){
        Vector result = this.clone();
        for(int i=0;i<vectorList.length;i++){
            result.vectorList[i] -= other.vectorList[i];
        }
        return result;
    }

    public Vector multiply(double factor){
        Vector result = this.clone();
        for(int i=0;i<vectorList.length;i++){
            result.vectorList[i] *= factor;
        }
        return result;
    }

    public double length(){
        double length = 0d;
        for(double coordinate : vectorList)
            length += Math.pow(coordinate,2);
        return Math.sqrt(length);
    }

    public boolean isInBetween(Vector minPosition, Vector maxPosition){
        for(int i=0;i<vectorList.length;i++){
            if(vectorList[i]<minPosition.vectorList[i] || vectorList[i]>maxPosition.vectorList[i])
                return false;
        }
        return true;
    }

    public Vector clone() {
        try {
            return (Vector)super.clone();
        }catch(CloneNotSupportedException e){
            return new Vector(this.vectorList);
        }
    }

    public boolean equals(Vector other){
        for(int i=0;i<vectorList.length;i++){
            if(Math.abs(vectorList[i]-other.vectorList[i])>1e-7)
                return false;
        }
        return true;
    }

    public double X(){
        return this.vectorList[0];
    }
    public double Y(){
        return this.vectorList[1];
    }
    public double Z(){
        return this.vectorList[2];
    }
    public double cubeX(){
        return Math.floor(this.vectorList[0]/Unit.CUBE_SIDE_LENGTH);
    }
    public double cubeY(){
        return Math.floor(this.vectorList[1]/Unit.CUBE_SIDE_LENGTH);
    }
    public double cubeZ(){
        return Math.floor(this.vectorList[2]/Unit.CUBE_SIDE_LENGTH);
    }

    /**
     * Returns the coordinates of the center of the current cube
     * @return
     */
    public Vector getCubeCoordinates(){
        return new Vector(this.cubeX()+0.5, this.cubeY()+0.5, this.cubeZ()+0.5).multiply(Unit.CUBE_SIDE_LENGTH);
    }
}
