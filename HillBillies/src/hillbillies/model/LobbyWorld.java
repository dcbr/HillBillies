package hillbillies.model;

import java.util.ArrayList;
import java.util.List;

import hillbillies.utils.Vector;

public class LobbyWorld {
	
	private final List<Faction> factions = new ArrayList<>();
	public final static LobbyWorld lobby = new LobbyWorld();
	public LobbyWorld() {

	}
	public boolean isVallidPosition(Vector position){
		return true;
	}
	
	private Faction getCurrentFaction(){
		return this.factions.get(this.factions.size()-1);
	}
	
	private void addNewFaction(Unit unit){
		this.factions.add(new Faction(unit));
	}
	
	private void addUnit(){
		
	}
}
