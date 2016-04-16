package hillbillies.model;

/**
 * Created by Bram on 1-4-2016.
 */
public enum Terrain {
    AIR(0,true),
    ROCK(1,false),
    WOOD(2,false),
    WORKSHOP(3,true);

    private int id;
    private boolean passable;

    Terrain(int id, boolean passable){
        this.id = id;
        this.passable = passable;
    }

    public boolean isPassable(){
        return this.passable;
    }

    public int getId(){
        return this.id;
    }

    public static Terrain fromId(int id){
        return Terrain.values()[id];
    }
}
