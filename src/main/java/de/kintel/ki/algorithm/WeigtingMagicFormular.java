package de.kintel.ki.algorithm;

import de.kintel.ki.model.Board;
import de.kintel.ki.model.Player;
import de.kintel.ki.util.BoardUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;

@Component
@Qualifier("magicFormular")
public class WeigtingMagicFormular implements Weighting {

    private MoveClassifier moveClassifier;

    @Autowired
    public WeigtingMagicFormular(MoveClassifier moveClassifier) {
        this.moveClassifier = moveClassifier;
    }

    /**
     * Evaluate the state of the game <strong>for the current player</strong> after a move.
     * The greatest the value is, the better the position of the current player is.
     *
     * @param board the board
     * @return The evaluation of the position for the current player
     * @see #maxEvaluateValue()
     */
    @Override
    public double evaluate(@Nonnull Board board, @Nonnull Player currentPlayer) {
        Player opponentPlayer = (currentPlayer == Player.WEISS) ? Player.SCHWARZ : Player.WEISS;

        //int ownMoves = BoardUtils.getPossibleMoves(board, currentPlayer, moveClassifier).size();
        int opponentMoves = BoardUtils.getPossibleMoves(board, opponentPlayer, moveClassifier).size();

        if (opponentMoves == 0) {
            return 10000;
        }

        return 100 - opponentMoves;
    }

    @Override
    public double maxEvaluateValue() {
        return 10000+1;
    }
}
