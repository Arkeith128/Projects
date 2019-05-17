
/** Simulation.java
 *  Initializes the simulation so the world will have 20 ants and 10 
 *   Doodlebugs.  Each are placed in random locations.  Once initialized, 
 *   runs the simulation with each time lapse being initiated by a 
 *   key press.
 */
import java.util.Scanner;

public class Simulation {
   public static final int INITIAL_ANTS = 20;
   public static final int INITIAL_BUGS = 10;

   // ======================
   //     main method
   // ======================
   public static void main(String[] args)	{
      String s;
      World w = new World();
      Scanner scan = new Scanner(System.in);

      // Randomly place all ants on the grid
      int antcount = 0;
      while (antcount < INITIAL_ANTS) {
         int x = (int) (Math.random() * World.WORLDSIZE);
         int y = (int) (Math.random() * World.WORLDSIZE);
         if (w.getAt(x,y)==null)	{	// Only put ant in empty spot
            antcount++;
            Ant a1 = new Ant(w,x,y);
         }
      }
      
      // Randomly place all doodlebugs on the grid
      int doodlecount = 0;
      while (doodlecount < INITIAL_BUGS) {
         int x = (int) (Math.random() * World.WORLDSIZE);
         int y = (int) (Math.random() * World.WORLDSIZE);
         if (w.getAt(x,y)==null)	{	// Only put doodlebug in empty spot
            doodlecount++;
            Doodlebug d1 = new Doodlebug(w,x,y);
         }
      }            

      // Run simulation forever, until user cancels
      while (true)  {
         w.displayWorld();
         w.SimulateOneStep();
         System.out.println("\nPress enter to initiate next run:");
         s = scan.nextLine();
      }
   }
}
