package de.kintel.ki.ruleset.rules;

import de.kintel.ki.model.Move;
import de.kintel.ki.ruleset.IRule;

public class RuleDestinationIsEmpty implements IRule {
    @Override
    public boolean isValidMove(Move move) {
        return !move.getTargetField()
                    .peekHead().isPresent();
    }
}