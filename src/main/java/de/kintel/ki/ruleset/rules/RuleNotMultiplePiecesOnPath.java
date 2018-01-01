package de.kintel.ki.ruleset.rules;

import de.kintel.ki.algorithm.PathFinder;
import de.kintel.ki.model.Field;
import de.kintel.ki.model.Move;
import de.kintel.ki.ruleset.IRule;

import java.util.Deque;
import java.util.Optional;

/**
 * There must be none or one piece on the path to be a valid move.
 * (Mehrfachschlagen ist im Spiel ausgeschlossen TODO: Richtigkeit prüfen)
 */
public class RuleNotMultiplePiecesOnPath implements IRule {
    @Override
    public boolean isValidMove(Move move) {
        Deque<Field> path = PathFinder.find(move);
        path.removeFirst();
        path.removeLast();
        return path.stream()
                   .map(Field::getOwner)
                   .filter(Optional::isPresent)
                   .map(Optional::get)
                   .count() <= 1;
    }
}
