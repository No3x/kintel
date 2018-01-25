package de.kintel.ki.player;

import com.google.common.base.MoreObjects;
import de.kintel.ki.algorithm.KI;
import de.kintel.ki.algorithm.MoveMaker;
import de.kintel.ki.model.Board;
import de.kintel.ki.model.Move;
import de.kintel.ki.model.Player;
import de.kintel.ki.util.UMLCoordToCoord2D;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class KiPlayer extends Participant {

    private static final Logger logger = LoggerFactory.getLogger(KiPlayer.class);

    private final KI ki;
    private long timeConsumed;
    private Map<String,Move> moveMap;
    private MoveMaker moveMaker;

    public KiPlayer(Board board, UMLCoordToCoord2D converter,  Player player, KI ki, MoveMaker moveMaker) {
        super( board, player, converter );
        this.ki = ki;
        this.moveMaker = moveMaker;
        this.ki.setCurrentPlayer(player.name());
        moveMap = new HashMap<>();
    }
    
    @Override
    public Move getNextMove(int depth) {
        // erstelle Kopie des boards, auf dem die KI machen kann, was sie will
        ki.setBoard(getBoard().deepCopy());
        //Diese zeile ist wichtig, damit der Negamax mit dem richtigen Player anfängt!
        ki.setCurrentPlayer(getPlayer().name());
        long timeBefore = System.currentTimeMillis();

        Move bestMove = moveMap.get(getBoard().toStringWithRanks());
        if(bestMove != null){

            try {
                Thread.sleep(100,0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }else {
            bestMove = ki.getBestMove(depth);
            moveMap.put(getBoard().toStringWithRanks(), bestMove);
        }

        timeConsumed += System.currentTimeMillis() - timeBefore;
        logger.debug("Time consumed: {}s", getTimeConsumedInSeconds() );
        return bestMove;
    }

    @Override
    public void makeMove(Move move) {
        logger.debug("Found best move to execute now: {}", move);
        logger.debug("Found best move to execute now: {}{}", getConverter().convertCoordToUML(move.getSourceCoordinate()), getConverter().convertCoordToUML(move.getTargetCoordinate()) );
        moveMaker.makeMove(move, getBoard());
    }

    public long getTimeConsumedInSeconds() {
        return TimeUnit.MILLISECONDS.toSeconds(timeConsumed);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("player", getPlayer()).add("ki", ki.getWeighting().getClass().getSimpleName()).toString();
    }
}
