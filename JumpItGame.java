import java.util.*;

// file: JumpItGame.java
// author: Ken Meerdink - main method
// author: Brea Zeller - findLowestCost method

public class JumpItGame {
    public static void main( String[] args ) {
        Random rand = new Random();
        
        int[] board = new int[ 5 + rand.nextInt( 6 ) ];
        board[ 0 ] = 0;
        board[ board.length - 1 ] = 0;
        for ( int i = 1; i < board.length - 1; i++ ) {
            board[ i ] = 1 + rand.nextInt( 100 );
        }
        
        System.out.println( "For the board " + Arrays.toString( board ) );
        System.out.println( "Greedy algorithm says cost is: " 
                           + greedy( board ) );
        System.out.println( "Iterative algorithm says cost is: " 
                           + iterative( board ) );
        System.out.print( "and the actual lowest cost is: "
                           + findLowestCost( board ) );
    }
    
    public static int greedy( int[] board ) {
        int cost = 0;
        int position = 0;
        while ( position < board.length - 2 ) {
            if ( board[ position + 1 ] < board[ position + 2 ] ) {
                cost += board[ position + 1 ];
                position += 1;
            }
            else { // move two columns
                cost += board[ position + 2 ];
                position += 2;
            }
        }
        return cost;
    }
    
    public static int findLowestCost( int[] board ) {
        return findLowestCost( board, 0 );
    }
    private static int findLowestCost( int[] board, int index ) {
        if( board.length - 1 == index || board.length - 2 == index){
            return board[index];
        }
     
        else if(findLowestCost(board, index + 1) <findLowestCost(board, index + 2)){
            return board[index] + findLowestCost(board, index + 1);
        }
        else{
            return board[index] + findLowestCost(board, index + 2);
        }
        
    }
    
    /**
     * Iteratively generate all possible sequences
     * of moves, determine the cost of each one.
     *
     * @param board the board that we are looking at
     *        to find the lowest cost
     * @return the lowest cost way to move through
     *         the board
     */
    public static int iterative( int[] board ) {
        ArrayList<ArrayList<Integer>> costs = 
                          generatePathList( board.length );
        int cost = 0;
        // find upper limit for cost
        for ( int i = 0; i < board.length; i++ ) {
            cost += board[ i ];
        }
        for ( ArrayList<Integer> c : costs ) {
            // sum the costs
            int sum = 0;
            int index = 0;
            for ( int i = 0; i < c.size(); i++ ) {
                index += c.get( i );
                sum += board[ index ];
            }
            if ( sum < cost ) {
                cost = sum;
            }
        }
        return cost;
    }
    
    /**
     * generate all possible paths through a
     * board of size n.
     * @param n the size of board we are using.
     * @return ArrayList of all possible paths
     *         through the board.
     *         Each path is an ArrayList of ints
     *         (1 or 2) indicating how big of a
     *         step to take on next move. 
     */
    private static ArrayList<ArrayList<Integer>>
    generatePathList( int n ) {
        ArrayList<ArrayList<Integer>> paths = 
                      new ArrayList<ArrayList<Integer>>();
                      
        // first move is a 1 or a 2.
        paths.add( new ArrayList<Integer>() ); 
        paths.add( new ArrayList<Integer>() );
        paths.get( 0 ).add( 1 );
        paths.get( 1 ).add( 2 );
        
        // now, add 1 and/or 2 to the end of each path,
        // until all paths are complete.
        
        
        // we will know we are done when there is  
        // no path that we can add anything to.
        boolean done = false;
        while ( !done ) {
            // assume we are done, then if ever we are able
            // to add to a path, then we aren't necessarily done.
            done = true;
            
            for ( int i = 0; i < paths.size(); i++ ) {
                ArrayList<Integer> p = paths.get( i );
                
                // if possible, add 1 or 2 to the end of every path.
                if ( aTwoStep( p, n ) ) {
                    // if a 2 step is possible, so is a 1 step
                    // generate another path for the extra case.
                    ArrayList<Integer> copy = new ArrayList<Integer>();
                    copy.addAll( p );
                    copy.add( 1 );
                    paths.add( copy );
                    p.add( 2 );
                    done = false;
                }
                else if ( aOneStep( p, n ) ) {
                    p.add( 1 );
                    done = false;
                }
            }
        }
        
        return paths;    
    }
    
    /**
     * helper method for generatePathList
     * @param p sequence of moves so far
     * @param n how many steps are left
     * @return true if it is possible to do a one-step
     */
    private static boolean aOneStep( ArrayList<Integer> p, int n ) {
        int sum = 0;
        for ( int i : p ) {
            sum += i;
        }
        return sum < n - 1;
    }
    
    /**
     * helper method for generatePathList
     * @param p sequence of moves so far
     * @param n how many steps are left
     * @return true if it is possible to do a two-step
     */
    private static boolean aTwoStep( ArrayList<Integer> p, int n ) {
        int sum = 0;
        for ( int i : p ) {
            sum += i;
        }
        return sum < n - 2;
    }
}