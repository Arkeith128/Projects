

import java.util.ArrayList;
import java.util.List;

/** Ant.java
 *  This class defines an Ant's attributes and behavior.
 */
public class Ant extends Organism {
  /** Constant defining the number of moves required before an Ant can breed */
	public static final int ANTBREED = 3;	
	/** Number of moves since last breeding */
	private int breedTicks;

	/** Empty Constructor */
 	public Ant() {
 		super();
 		breedTicks = 0;
	}

  /** Argument constructor 
   * @param world - World grid on which this Ant lives
   * @param x - Initial x coordinate position of this Ant
   * @param y - Initial y coordinate position of this Ant
   */
	public Ant(World world, int x, int y) {
		super(world,x,y);
		breedTicks = 0;
	}

	/** Method defining what happens when an Ant moves.
	 *  This method will choose a single location randomly.  If it is 
	 *   occupied or off the grid, the Ant will not move anywhere.
	 */
	public void move() {
		
		int direction; // direction ant will move
		
		//need a way to track if all directions were checked
		List<Integer> moveDirectionList = new ArrayList<Integer>(); 
		
		//current coordinates that need to be set to null after move
		int currentX = this.x;
		int currentY = this.y;
		
		// testing purposes
//		System.out.println(currentX+","+currentY+" - "+this.hashCode()+" - "+this.breedTicks);
		
		while (moveDirectionList.size()<4) { //if size = 4, then all were checked and Ant can't move
			direction = (int) (Math.random()*4); // 0 = up, 1 = right, 2 = down, 3 = left
			
			//if the spot above is in the world and isn't occupied, move there
			if (direction == 0 && inWorldAndUnoccupied(currentX-1,currentY)) { 
				this.world.setAt(currentX-1, currentY, this); //move to new spot
				this.world.setAt(currentX, currentY, null); //set old spot to null
				this.x=currentX-1; //updating x or y
				breedTicks++; //completed a move, have to increase
				break; // move is done, everything is updated, no need to continue this loop
			}
			else if (direction == 1 && inWorldAndUnoccupied(currentX,currentY+1)) {
				this.world.setAt(currentX, currentY+1, this);
				this.world.setAt(currentX, currentY, null);
				this.y=currentY+1;
				breedTicks++;
				break;
			}
			else if (direction == 2 && inWorldAndUnoccupied(currentX+1,currentY)) {
				this.world.setAt(currentX+1, currentY, this);
				this.world.setAt(currentX, currentY, null);
				this.x=currentX+1;
				breedTicks++;
				break;
			}
			else if (direction == 3 && inWorldAndUnoccupied(currentX,currentY-1)) { // moving left
				this.world.setAt(currentX, currentY-1, this);
				this.world.setAt(currentX, currentY, null);
				this.y=currentY-1;
				breedTicks++;
				break;
			}
			else { //if direction is invalid, add it to the list
				if(!moveDirectionList.contains(direction)) // only add it to the list if it isn't in there
					moveDirectionList.add(direction);
			}
		}
		
		//if none of the directions worked and the Ant couldn't move 
		if(moveDirectionList.size() == 4)
			breedTicks++; //increase ticks even though movement failed
	}		
	
	/**
	 * Will check to see if the new coordinates puts the organism outside of the world
	 * or if the new spot is occupied
	 * @param newX - new X coordinates
	 * @param newY - new Y coordinates 
	 */
	public boolean inWorldAndUnoccupied(int newX, int newY) {
		if (newX < 0 || newY < 0 || newX >= World.WORLDSIZE || newY >= World.WORLDSIZE
		    || this.world.getAt(newX, newY) instanceof Doodlebug || this.world.getAt(newX, newY) instanceof Ant)
			return false;
		else
			return true;
	}

	/** Method defining what happens when an Ant breeds. 
	 *  - This method will check that the Ant is able to breed and 
	 *     may reset it if the Ant breeds successfully.
	 *  - The Ant must search all locations around it randomly.
	 */
	public void breed() {
		if (breedTicks == ANTBREED) {
			//if size = 4, then all directions were checked and the Ant can't breed
			List<Integer> moveDirectionList = new ArrayList<Integer>();
			int direction;
			int currentX = this.x;
			int currentY = this.y;
			while(moveDirectionList.size() < 4) {
				direction = (int) (Math.random() *4); // 0 = up, 1 = right, 2 = down, 3 = left
				
				//if the spot above is in the world and doesn't contain a Doodlebug or Ant
				if (direction == 0 && inWorldAndUnoccupied(currentX-1,currentY)) {
					//create new Ant and place it in the valid spot
					this.world.setAt(currentX-1, currentY, new Ant(this.world,currentX-1,currentY)); // move to new location
					this.breedTicks = 0; //Ant successfully bred
					break; // Ant moved, no need to continue looking for a spot
				}
				else if (direction == 1 && inWorldAndUnoccupied(currentX,currentY+1)) {
					this.world.setAt(currentX, currentY+1, new Ant(this.world,currentX,currentY+1));
					this.breedTicks = 0;
					break;
				}
				else if (direction == 2 && inWorldAndUnoccupied(currentX+1,currentY)) {
					this.world.setAt(currentX+1, currentY, new Ant(this.world,currentX+1,currentY));
					this.breedTicks = 0;
					break;
				}
				else if (direction == 3 && inWorldAndUnoccupied(currentX,currentY-1)) { //moving to the left
					this.world.setAt(currentX, currentY-1, new Ant(this.world,currentX,currentY-1));
					this.breedTicks = 0;
					break;
				}				
				else { //if current direction isn't available
					if (!moveDirectionList.contains(direction)) // if the direction isn't in the list, add it
						 moveDirectionList.add(direction); //once the size reaches 4, the loop breaks and Ant doesn't breed
				}				
			} //end of breedwhile
		} //end of breed if	
	}

	/** Method to determine whether the Ant is currently starving.
	 * @return boolean 
	 */
	public boolean starve()	{
		return false;
	}

	/** Returns a displayable character for this Ant (represented by "o")
	 */
	public String getPrintableChar()	{
		return "o";
	}
}

