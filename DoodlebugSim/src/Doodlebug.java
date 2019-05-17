
import java.util.*;
/** Doodlebug.java
 *  This class defines a Doodlebug's attributes and behavior.
 */
public class Doodlebug extends Organism {
  /** Constant defining the number of moves required before a Doodlebug
   *   can breed. */
	public static final int DOODLEBREED = 4;	

  /** Constant defining the number of moves a Doodlebug can make without
   *   eating before it starves. */
	public static final int DOODLESTARVE = 8;	

  /** Number of moves since last eating */
	private int starveTicks;	
	
  /** Number of moves since last breeding */
	private int breedTicks;

	/** Empty Constructor
	 */
 	public Doodlebug() {
 		super();
 		starveTicks = 0;
 		breedTicks = 0;
	}

  /** Argument constructor
   * @param world - World grid on which this Doodlebug lives
   * @param x - Initial x coordinate position of this Doodlebug
   * @param y - Initial y coordinate position of this Doodlebug
   */
	public Doodlebug(World world, int x, int y) {
			super(world,x,y);
			starveTicks=0;
			breedTicks = 0;
	}
	
 
	/** Method defining what happens when a Doodlebug moves.
	 *  This method will randomly search all adjacent locations for a cell
	 *   occupied by an Ant (it wants to move where it can eat).
	 *  If a cell with an Ant is not found, this Doodlebug moves as an Ant,
	 *   picking a single random location and not moving if this chosen
	 *   location is not valid or is occupied.
	 */
	public void move() {	
		int direction; //direction doodlebug will move to
		
		//need a way to track if all directions were checked for food
		List<Integer> foodDirectionList = new ArrayList<Integer>(); //if size = 4, then all were checked
		List<Integer> moveDirectionList = new ArrayList<Integer>(); //if size = 4, then all were checked and doodlebug can't move		
		
		//current coordinates that need to be set to null after move
		int currentX = this.x;
		int currentY = this.y;
		
		// testing purposes
//		System.out.println(currentX+","+currentY+" - "+this.hashCode()+" - "+this.breedTicks+" - "+this.starveTicks);	
		
		while (foodDirectionList.size()<4) {
			direction = (int) (Math.random()*4); // 0 = up, 1 = right, 2 = down, 3 = left
			
				// if canFeed returns true, there's an Ant nearby			
				if (direction == 0 && canFeed(currentX-1,currentY)) {
					this.world.setAt(currentX-1, currentY, this); // move to new location
					this.world.setAt(currentX, currentY, null); //change original location to null
					this.x = currentX-1; // making sure to update x or y
					this.starveTicks = 0; // doodlebug has eaten an Ant
					this.breedTicks++; // doodlebug didn't breed
					break; // doodlebug moved, no need to continue looking for a spot
				}
				else if (direction == 1 && canFeed(currentX,currentY+1)) {
					this.world.setAt(currentX, currentY+1, this);
					this.world.setAt(currentX, currentY, null);
					this.y = currentY+1;
					this.starveTicks = 0;
					this.breedTicks++;
					break;
				}
				else if (direction == 2 && canFeed(currentX+1,currentY)) {
					this.world.setAt(currentX+1, currentY, this);
					this.world.setAt(currentX, currentY, null);
					this.x = currentX+1;
					this.starveTicks = 0;
					this.breedTicks++;
					break;
				}
				else if (direction == 3 && canFeed(currentX,currentY-1)){ // eating Ant to the left
					this.world.setAt(currentX, currentY-1, this);
					this.world.setAt(currentX, currentY, null);
					this.y = currentY-1;
					this.starveTicks = 0;
					this.breedTicks++;
					break;
				}			
				else { // if current direction doesn't contain Ant
					if (!foodDirectionList.contains(direction)) // if the direction isn't in the list, add it
						 foodDirectionList.add(direction); // once the size reaches 4, the loop breaks and doodlebug doesnt move					
				}			
		} // end of foodDirectionList while loop
		
		// all 4 directions were checked for food, but nothing was there
		if (foodDirectionList.size() == 4) { // if less than 4, doodlebug already moved and this isn't needed
			while (moveDirectionList.size() < 4) { //checking to see if there's an empty spot to move to				
				direction = (int) (Math.random()*4); // 0 = up, 1 = right, 2 = down, 3 = left
								
					if (direction == 0 && inWorldAndUnoccupied(currentX-1,currentY)) {
						this.world.setAt(currentX-1, currentY, this); // move to new location
						this.world.setAt(currentX, currentY, null); //change original location to null
						this.x = currentX-1; // making sure to update x or y
						this.starveTicks++; // doodlebug moved without eating
						this.breedTicks++; // doodlebug didn't breed
						break; // doodlebug moved, no need to continue looking for a spot
					}
					else if (direction == 1 && inWorldAndUnoccupied(currentX,currentY+1)) {
						this.world.setAt(currentX, currentY+1, this);
						this.world.setAt(currentX, currentY, null);
						this.y = currentY+1;
						this.starveTicks++;
						this.breedTicks++;
						break;
					}
					else if (direction == 2 && inWorldAndUnoccupied(currentX+1,currentY)) {
						this.world.setAt(currentX+1, currentY, this);
						this.world.setAt(currentX, currentY, null);
						this.x = currentX+1;
						this.starveTicks++;
						this.breedTicks++;
						break;
					}
					else if (direction == 3 && inWorldAndUnoccupied(currentX,currentY-1)) { //moving to the left
						this.world.setAt(currentX, currentY-1, this);
						this.world.setAt(currentX, currentY, null);
						this.y = currentY-1;
						this.starveTicks++;
						this.breedTicks++;
						break;
					}				
					else { //if current direction isn't available
						if (!moveDirectionList.contains(direction)) // if the direction isn't in the list, add it
							 moveDirectionList.add(direction); //once the size reaches 4, the loop breaks and doodlebug doesnt move
					}
			}
		}
		
		//if doodlebug failed to eat and if unable to move, increase ticks
		if (foodDirectionList.size() == 4 && moveDirectionList.size() == 4) {
			this.starveTicks++;
			this.breedTicks++;
		}
	}
	
	
	/**
	 * Will check to see if the new coordinates puts the organism outside of the world
	 * or if the new spot is occupied by another Organism
	 * @param newX - new X coordinates
	 * @param newY - new Y coordinates 
	 */
	public boolean inWorldAndUnoccupied(int newX, int newY) {
		if (newX < 0 || newY < 0 || newX >= World.WORLDSIZE || newY >= World.WORLDSIZE
		    || this.world.getAt(newX, newY) instanceof Doodlebug
		    || this.world.getAt(newX, newY) instanceof Ant) //move() already checks for ants and eats them. this is here for the breed method
			return false;
		else
			return true;
	}
	
	/**
	 * Checks to see if new location contains an Ant
	 * @param newX - possible x coordinate of ant
	 * @param newY - possible y coordinate of ant
	 */
	public boolean canFeed(int newX, int newY) {
		if (this.world.getAt(newX, newY) instanceof Ant)
			return true;
		else
			return false;
	}
	
	
	/**
	 * Checks to see if new location contains an Ant
	 * @param currentX - Current x coordinate position of this Doodlebug
	 * @param currentY - Current y coordinate position of this Doodlebug
	 * @param direction - Direction Doodlebug might move (up = 0, right = 1, down = 2, left = 3)	 
	 */
	public boolean canFeed(int currentX,int currentY,int direction) {
		if (direction == 0) { //if direction = up			
			//if the spot above is occupied by another Ant 
			if (this.world.getAt(currentX-1, currentY) != null && 
			    this.world.getAt(currentX-1, currentY) instanceof Ant)
				return true;		
			else
				return false;
		}
		else if(direction == 1) { // if direction = right
			if (this.world.getAt(currentX, currentY+1) != null &&
					this.world.getAt(currentX, currentY+1) instanceof Ant)
				return true;
			else
				return false;
		}
		else if(direction == 2) { //if direction = down
			if (this.world.getAt(currentX+1, currentY) != null && 
					this.world.getAt(currentX+1, currentY) instanceof Ant)
				return true;
			else 
				return false;		
		}
		else { //if direction = 3 or left
			if (this.world.getAt(currentX, currentY-1) != null && 
					this.world.getAt(currentX, currentY-1) instanceof Ant)
				return true;
			else 
				return false;
		}
	}//end of canFeed

	/** Method defining what happens when a Doodlebug breeds. 
	 *  - This method must check that the Doodlebug is able to breed and 
	 *     may reset it if the Doodlebug breeds successfully.
	 *  - The Doodlebug must search all locations around it randomly.
	 *  - Doodlebug may only breed if the space next to it is open
	 */
	public void breed() {
		if (breedTicks == DOODLEBREED) {
			//if size = 4, then all directions were checked and doodlebug can't breed
			List<Integer> moveDirectionList = new ArrayList<Integer>();
			int direction;
			int currentX = this.x;
			int currentY = this.y;
			while(moveDirectionList.size() < 4) {
				direction = (int) (Math.random() *4); // 0 = up, 1 = right, 2 = down, 3 = left
				
				//if the spot above is in the world and doesn't contain a Doodlebug or Ant
				if (direction == 0 && inWorldAndUnoccupied(currentX-1,currentY)) {
					//create new doodlebug and place it in the valid spot
					this.world.setAt(currentX-1, currentY, new Doodlebug(this.world,currentX-1,currentY)); // move to new location
					this.breedTicks = 0; //doodlebug successfully bred
					break; // doodlebug moved, no need to continue looking for a spot
				}
				else if (direction == 1 && inWorldAndUnoccupied(currentX,currentY+1)) {
					this.world.setAt(currentX, currentY+1, new Doodlebug(this.world,currentX,currentY+1));
					this.breedTicks = 0;
					break;
				}
				else if (direction == 2 && inWorldAndUnoccupied(currentX+1,currentY)) {
					this.world.setAt(currentX+1, currentY, new Doodlebug(this.world,currentX+1,currentY));
					this.breedTicks = 0;
					break;
				}
				else if (direction == 3 && inWorldAndUnoccupied(currentX,currentY-1)) { //moving to the left
					this.world.setAt(currentX, currentY-1, new Doodlebug(this.world,currentX,currentY-1));
					this.breedTicks = 0;
					break;
				}				
				else { //if current direction isn't available
					if (!moveDirectionList.contains(direction)) // if the direction isn't in the list, add it
						 moveDirectionList.add(direction); //once the size reaches 4, the loop breaks and doodlebug doesn't breed
				}
				
			} //end of breedwhile
		} //end of breed if			
	} // end of breed

	/** Method to determine whether the Doodlebug is currently starving.
	 * @return boolean - TRUE if the Doodlebug is starving
	 */
	public boolean starve()	{
		return starveTicks == DOODLESTARVE;			
	}

	/** Returns a displayable character for this Doodlebug (represented by "X")
	 */
	public String getPrintableChar()	{
		return "X";
	}
}
