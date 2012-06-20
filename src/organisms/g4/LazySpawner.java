package organisms.g4;

import java.util.*;
import java.io.*;
import java.awt.Color;

import organisms.*;

public final class LazySpawner implements Player {

	static final String _CNAME = "LazySpawner"; 
	static final Color _CColor = Color.MAGENTA;
	private int state;
	private Random rand;
	private OrganismsGame game;
	private int steps = 0;

	
	/*
	 * This method is called when the Organism is created.
	 * The key is the value that is passed to this organism by its parent (not used here)
	 */
	public void register(OrganismsGame game, int key) throws Exception
	{
		rand = new Random();
		if(key == -1) {
			state = 0;
		} else {
			state = key;
		}
		
		
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
		//System.out.println(state);
		Move m = null; // placeholder for return value
		
		
		if(state < 1) {
			m = reckLessSpawn(neighbors);
			
		}
		
		
		if (energyleft > 100 + state * 10 && m == null) { // if energy over 400 try to reproduce

			// System.out.println("MAKE NEW ONE");
			m = spawn(foodpresent, neighbors);
		}  else if(m == null) {
			m = moveToFood(foodpresent, neighbors, foodleft);
		} 
			
		//CHECK FOR OVER ABONDANT FOOD
		

		if(state * 10 + 100 >= 500 && state > 20) {
			state = state - 19;
		}
		
		/*if(foodleft > 75 && state == 30) {
			state = 29;
		}*/
		
		
		
		
		
		
		if(m == null ) {
			if(steps < 10) {
				m = moveRandom();
			} else {
				m = new Move(STAYPUT);
			}			
		} 
		


		
		
		
		
		steps++;
		return m;
	}
	
	private Move reckLessSpawn(int[] neighbors) throws Exception {
		
		Move m = null; // placeholder for return value
		
		// this player selects randomly
		int direction;
		
		int trys = 0;
		
		while(m == null && trys < 20) {
			direction = rand.nextInt(4);
			
			
			
			switch (direction) {		
			case 0:if (neighbors[WEST] == -1) {
				m = new Move(REPRODUCE, WEST, state + 1);
				}
				break;
			case 1:if (neighbors[NORTH] == -1) {
				m = new Move(REPRODUCE, NORTH, state + 1);
				}
				break;
			case 2:if (neighbors[EAST] == -1) {
				m = new Move(REPRODUCE, EAST, state + 1);
				}
				break;
			case 3:if (neighbors[SOUTH] == -1) {
				m = new Move(REPRODUCE, SOUTH, state + 1);
				}
				break;
			}
			trys++;
		}


		return m;
		
	}

	private Move abondantFoodBirth(boolean[] foodpresent) {
		Move m = null;
		
		
		return m;
	}

	private Move moveToFood(boolean[] foodpresent, int[] neighbors, int foodleft) throws Exception {
		Move m = null;
		
		if(foodleft > 0) {//EAT IT ALL 
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
