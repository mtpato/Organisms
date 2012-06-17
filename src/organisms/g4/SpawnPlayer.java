package organisms.g4;

import java.util.*;
import java.io.*;
import java.awt.Color;

import organisms.*;

public final class SpawnPlayer implements Player {

	static final String _CNAME = "SpawnPlayer";
	static final Color _CColor = new Color(1.0f, 0.67f, 0.67f);
	private int state;
	private Random rand;
	private OrganismsGame game;


	/*
	 * This method is called when the Organism is created.
	 * The key is the value that is passed to this organism by its parent (not used here)
	 */
	public void register(OrganismsGame game, int key) throws Exception
	{
		rand = new Random();
		state = key;
		//state = rand.nextInt(256);
		this.game = game;
	}

	/*
	 * Return the name to be displayed in the simulator.
	 */
	public String name() throws Exception {
		return _CNAME;
	}

	/*
	 * Return the color to be displayed in the simulator.
	 */
	public Color color() throws Exception {
		return _CColor;
	}

	/*
	 * Not, uh, really sure what this is...
	 */
	public boolean interactive() throws Exception {
		return false;
	}

	/*
	 * This is the state to be displayed to other nearby organisms
	 */
	public int externalState() throws Exception {
		return state;
	}

	/*
	 * This is called by the simulator to determine how this Organism should move.
	 * foodpresent is a four-element array that indicates whether any food is in adjacent squares
	 * neighbors is a four-element array that holds the externalState for any organism in an adjacent square
	 * foodleft is how much food is left on the current square
	 * energyleft is this organism's remaining energy
	 */
	public Move move(boolean[] foodpresent, int[] neighbors, int foodleft, int energyleft) throws Exception {

		Move m = null; // placeholder for return value
		
		
		if (energyleft > 200 + state * 10) { // if energy over 400 try to reproduce

			// System.out.println("MAKE NEW ONE");
			m = spawn(foodpresent, neighbors);
		}  else {
			m = moveToFood(foodpresent, neighbors, foodleft);
		} 
			
		//CHECK FOR OVER ABONDANT FOOD
		
		if(foodleft > 75) {
			state = 29;
		}
		
		
		
		
		
		if(m == null) {
			m = moveRandom();
		}
		


		
		
		
		

		return m;
	}
	
	private Move abondantFoodBirth(boolean[] foodpresent) {
		Move m = null;
		
		
		return m;
	}

	private Move moveToFood(boolean[] foodpresent, int[] neighbors, int foodleft) throws Exception {
		Move m = null;
		
		if(foodleft > 1) {
			m = new Move(STAYPUT);
		} else if (foodpresent[WEST] && neighbors[WEST] == -1) {
			m = new Move(WEST);
		} else if (foodpresent[NORTH] && neighbors[NORTH] == -1) {
			m = new Move(NORTH);
		} else if (foodpresent[EAST] && neighbors[EAST] == -1) {// maybe change these 2 to save food
			m = new Move(EAST);
		} else if (foodpresent[SOUTH] && neighbors[SOUTH] == -1) {// maybe change these 2 to save food
			m = new Move(SOUTH);
		}
		
		return m;
	}

	private Move spawn(boolean[] foodpresent, int[] neighbors) throws Exception {
		Move m = null;

		if (foodpresent[WEST] && neighbors[WEST] == -1) {
			return new Move(REPRODUCE, WEST, state + 1);
		} else if (foodpresent[NORTH] && neighbors[NORTH] == -1) {
			return new Move(REPRODUCE, NORTH, state + 1);
		} else if (foodpresent[EAST] && neighbors[EAST] == -1) {// maybe change these 2 to save food
			return new Move(REPRODUCE, EAST, state + 1);
		} else if (foodpresent[SOUTH] && neighbors[SOUTH] == -1) {// maybe change these 2 to save food
			return new Move(REPRODUCE, SOUTH, state + 1);
		}

		return m;
	}
	
	private Move moveRandom() throws Exception {
		Move m = null; // placeholder for return value
		
		// this player selects randomly
		int direction = rand.nextInt(4);
		
		
		
		switch (direction) {		
		case 0: m = new Move(WEST); break;
		case 1: m = new Move(EAST); break;
		case 2: m = new Move(NORTH); break;
		case 3: m = new Move(SOUTH); break;
		}
		
		return m;
	}
		
	

}
