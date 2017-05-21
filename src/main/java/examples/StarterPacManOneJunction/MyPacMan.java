package examples.StarterPacManOneJunction;

import pacman.controllers.MASController;
import pacman.controllers.PacmanController;
import pacman.controllers.examples.po.POCommGhosts;
import pacman.game.Constants;
import pacman.game.Game;
import pacman.game.info.GameInfo;
import pacman.game.internal.Ghost;

/**
 * Created by piers on 23/02/17.
 */
public class MyPacMan extends PacmanController {

    @Override
    public Constants.MOVE getMove(Game game, long timeDue) {
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
        System.out.print("_");

        // Make some ghosts
        MASController ghosts = new POCommGhosts(50);

        // Get the best one Junction lookahead move
        Constants.MOVE bestMove = null;
        int bestScore = -Integer.MAX_VALUE;
        //For loop from UP to Neutral 
        for (Constants.MOVE move : Constants.MOVE.values()) {
            Game forwardCopy = coGame.copy();
            // Have to forward once before the loop - so that we aren't on a junction
            forwardCopy.advanceGame(move, ghosts.getMove(forwardCopy.copy(), 40));
            while(!forwardCopy.isJunction(forwardCopy.getPacmanCurrentNodeIndex())){
                forwardCopy.advanceGame(move, ghosts.getMove(forwardCopy.copy(), 40));
            }
            int score = forwardCopy.getScore();
            if (score > bestScore) {
                bestMove = move;
                bestScore = score;
            }
            else{
            	
            }
            //show the move , advanced position and the score
            System.out.print("[mv:");
            System.out.print(move);
            System.out.print("-]");
            System.out.print("[ap:");
            System.out.print(forwardCopy.getPacmanCurrentNodeIndex());
            System.out.print("-]");
            System.out.print("[sc:");
            System.out.print(score);
            System.out.print("] ");
            
            
            
        }
        System.out.println(" ");
        return bestMove;
    }

}