
/** Organism.java
 * Definition for the Organism base class.
 * Each organism has a reference back to the World object so it can move 
 *  itself about in the world.
 */
public abstract class Organism {
	 protected int x, y;		// Position in the world
	 protected boolean moved;	// boolean to indicate if moved this turn
	 protected int breedTicks;	// Number of moves since last breeding

   /** Reference to the world object so we can update its
	  * grid when we move around in the world 
	  */
	 protected World world = null;

   /** Abstract method to breed this organism. */
   public abstract void breed();

   /**  Abstract method to move this organism. */
   public abstract void move();

   /** Abstract method to determine if this organism is starving this turn. */
   public abstract boolean starve();

   /** Abstract method to return a displayable symbol for this organism */
   public abstract String getPrintableChar();
   
   /**
	 * Will check to see if the new coordinates puts the organism outside of the world
	 * or if the new spot is occupied by another Organism
	 * @param newX - new X coordinates
	 * @param newY - new Y coordinates 
	 */
   public abstract boolean inWorldAndUnoccupied(int newX,int newY);
	
	 /** Empty Constructor */
	 public Organism()	{
	 }

   /** Argument constructor
    * @param world - World in which this organism lives, moves
    * @param x - Initial x coordinate location for this organism
    * @param y - Initial y coordinate location for this organism
    */
	 public Organism(World world, int x, int y) {
      this.world = world;
  		this.x=x;
	  	this.y=y;
		  world.setAt(x, y, this);
	 }

   /** Accessor for the Moved variable
	  * @return boolean - TRUE if this organism has moved this turn.
	  */
	 public boolean getMoved() {
      return moved;
	 }

	 public void setMoved(boolean moved)	{
      this.moved = moved;
	 }
}

