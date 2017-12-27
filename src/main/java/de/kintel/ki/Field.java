package de.kintel.ki;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Optional;

public class Field {

    private boolean isForbidden;
    private Deque<Stein> steine = new LinkedList<>();

    public Field(boolean isForbidden) {
        this.isForbidden = isForbidden;
    }

    void addStein(Stein s) {
        steine.push(s);
    }

    public Optional<Stein> peekHead() {
        if (steine.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(steine.peek());
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        if( isForbidden ) {
            sb.append( "X");
        } else {
            steine.forEach( s -> sb.append(s) );
        }
        return sb.toString();
    }

    public Optional<Player> getOwner() {
        if (steine.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(steine.peek().getOwner());
        }
    }

    public boolean isForbidden() {
        return isForbidden;
    }
}
