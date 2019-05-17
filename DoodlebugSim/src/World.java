
/** World.java
 * The World class stores data about the world by creating a
 * WORLDSIZE by WORLDSIZE array of type Organism.
 * Null indicates an empty spot, otherwise a valid object
 * indicates an ant or doodlebug.  
 */
public class World {
	public static final int WORLDSIZE = 20;
	
	/** 2-D Array of Organisms that make up a WORLDSIZE x WORLDSIZE
	 *  grid.  Some x,y positions may be null, some may contain Organisms
	 *  of Ant or Doodlebug types.
	 */ 
	private Organism[][] grid;

	/** Empty constructor.  
	 *  Initialize the grid to contain no organisms
	 */
	public World()	{
	   grid = new Organism[WORLDSIZE][WORLDSIZE];
	}

	/** Returns the Organism stored in the grid array at x,y.
	 * @param x - X grid coordinate 
	 * @param y - Y grid coordinate 
	 * @return Organism - organism occupying this x,y coordinate of the
	 *  grid, or NULL if no organism is at that location.
	 */
	public Organism getAt(int x, int y)	{
		if ((x >= 0) && (x < World.WORLDSIZE) && 
		    (y >= 0) && (y < World.WORLDSIZE)) {
			return grid[x][y];
		}
		
		// return null for out of bounds checks
		return null;
	}

	/** Puts the Organism on the grid at x,y.
	 * @param x - X grid coordinate 
	 * @param y - Y grid coordinate 
	 * @param org (Organism) - to place on the grid
	 */
	public void setAt(int x, int y, Organism org) {
	   if ((x >= 0) && (x < World.WORLDSIZE) && 
         (y >= 0) && (y < World.WORLDSIZE))	{
	      grid[x][y] = org;
	   }
	}

	/** Displays the world grid in ASCII.text with Organisms denoted by
	 *  their display character, getPrintableChar()
	 */
	public void displayWorld() {
	   System.out.println("\n\n*****************************************\n");
	   for (int i = 0; i < World.WORLDSIZE; i++)	{
	      System.out.println();
	      for (int j = 0; j < World.WORLDSIZE; j++)	{
	         if (grid[i][j]==null) {
	            System.out.print(".");
	         } else {
	            // X for Doodle, o for Ant
	            System.out.print(grid[i][j].getPrintableChar());
	         }
	      }
	   }
	   System.out.println();
	}

	/** This is the main routine that simulates one turn in the world.
	 * First, a flag for each organism is used to indicate if it has moved.
	 * This is because we iterate through the grid starting from the top
	 * looking for an organism to move. If one moves down, we don't want
	 * to move it again when we reach it.
	 * First move doodlebugs, then ants, and if they are still alive then
	 * we breed them.  If any Organism is starving at the end of this turn,
	 * it will be killed off.
	 */
	public void SimulateOneStep()	{
		 // For this new turn, reset all organisms to not moved
	   for (int i = 0; i < World.WORLDSIZE; i++) {
	      for (int j = 0; j < World.WORLDSIZE; j++)	{
	         if (grid[i][j] != null) {
	            grid[i][j].setMoved(false);
	         }
	      }
	   }

		 // Move all Doodlebugs, checks that the Doodlebug found has not
		 //  moved yet this turn (since it can move right and down to a
		 //  new grid position 
	   for (int i = 0; i < World.WORLDSIZE; i++) {
	      for (int j = 0; j < World.WORLDSIZE; j++) {
	         if ((grid[i][j] != null) && 
	               (grid[i][j] instanceof Doodlebug) &&
	               (!grid[i][j].getMoved()) ) {
	            // NOTE: setMoved() is used to track whether an Organism
	            //  has "attempted" to move so it is not moved again,
	            //  there is no other purpose for this variable
	            grid[i][j].setMoved(true);
	            grid[i][j].move();
	         }
	      }
	   }
		
		 // Move all Ants - similar to above Doodlebug move loop
	   for (int i = 0; i < World.WORLDSIZE; i++) {
	      for (int j = 0; j < World.WORLDSIZE; j++) {
	    	  if ((grid[i][j] != null) && 
		               (grid[i][j] instanceof Ant) &&
		               (!grid[i][j].getMoved()) ) {		            
		            grid[i][j].setMoved(true);
		            grid[i][j].move();
		         }
	      }
	   }
		
		// Kill off any starving Organisms
		for (int i = 0; i < World.WORLDSIZE; i++) {
			for (int j = 0; j < World.WORLDSIZE; j++) {
				if (grid[i][j] !=null && grid[i][j].starve()) 
					grid[i][j] = null;
			}
		}
		
		// Breed any Organisms that have moved and have enough breedTicks
		//  to move.
		for (int i = 0; i < World.WORLDSIZE; i++) {
		   for (int j = 0; j < World.WORLDSIZE; j++) {
			   if (grid[i][j] !=null)
				   grid[i][j].breed();
		   }
		}
	}
}