package entrants.pacman.josh24311;

import pacman.controllers.MASController;
import pacman.controllers.PacmanController;
import pacman.controllers.examples.po.POCommGhosts;
import pacman.game.Constants.MOVE;
import pacman.game.info.GameInfo;
import pacman.game.internal.Ghost;
import pacman.game.Constants;
import pacman.game.Game;

/*
 * This is the class you need to modify for your entry. In particular, you need to
 * fill in the getMove() method. Any additional classes you write should either
 * be placed in this package or sub-packages (e.g., entrants.pacman.username).
 */
public class MyPacMan extends PacmanController {
    private MOVE myMove = MOVE.NEUTRAL;

    public MOVE getMove(Game game, long timeDue) {
        //Place your game logic here to play the game as Ms Pac-Man

    	Game coGame;
        GameInfo info = game.getPopulatedGameInfo();
        info.fixGhosts((ghost) -> new Ghost(
                ghost,
                game.getCurrentMaze().lairNodeIndex,
                -1,
                -1,
                Constants.MOVE.NEUTRAL
        ));
        coGame = game.getGameFromInfo(info);
        int tnow = coGame.getCurrentLevelTime();
        //show the time now
        System.out.print("t_now:");
        System.out.print(tnow);
        System.out.print(" ");
        System.out.print("pos_now:");
        System.out.print(coGame.getPacmanCurrentNodeIndex());
        System.out.print(" ");
        // Make some ghosts
        MASController ghosts = new POCommGhosts(50);

        // Get the best one Junction lookahead move
        Constants.MOVE bestMove = null;
        int bestScore = -Integer.MAX_VALUE;
        //For loop from UP to Neutral 
        for (Constants.MOVE move : Constants.MOVE.values()) {
        	int all5same = 0;
            Game forwardCopy = coGame.copy();
            // Have to forward once before the loop - so that we aren't on a junction
            forwardCopy.advanceGame(move, ghosts.getMove(forwardCopy.copy(), 40));
            while(!forwardCopy.isJunction(forwardCopy.getPacmanCurrentNodeIndex())){
                forwardCopy.advanceGame(move, ghosts.getMove(forwardCopy.copy(), 40));
            }
            int score = forwardCopy.getScore();
            System.out.print("[mv:");
            System.out.print(move);
            System.out.print("]");
            System.out.print("sc:");
            System.out.print(score);
            System.out.print(" ");
            System.out.print("bsc:");
            System.out.print(bestScore);
            System.out.print(" ");
            
            if (score > bestScore) {
                bestMove = move;
                bestScore = score;
            }
            else{
            	
            }
            //show the move , advanced position and the score
            
            //System.out.print("[ap:");
            //System.out.print(forwardCopy.getPacmanCurrentNodeIndex());
            //System.out.print("]");
            //System.out.print("[sc:");
            //System.out.print(score);
            //System.out.print("] ");
            System.out.print("bsm:");
            System.out.print(bestMove);
            System.out.print("_");
            
        }
        System.out.println(" ");
        return bestMove;
    }
}