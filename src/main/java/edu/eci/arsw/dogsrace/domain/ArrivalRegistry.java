package edu.eci.arsw.dogsrace.domain;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public final class ArrivalRegistry {

    private final AtomicInteger nextPosition = new AtomicInteger(1);
    private final List<String> posiciones;
    private volatile String winner = null;

    public ArrivalRegistry(int totalDogs) {
        this.posiciones = new ArrayList<>(
                Collections.nCopies(totalDogs, null)
        );
    }

    public ArrivalSnapshot registerArrival(String dogName) {
        int position = nextPosition.getAndIncrement();
        posiciones.set(position - 1, dogName);
        if (position == 1) {
            winner = dogName;
        }

        return new ArrivalSnapshot(position, winner);
    }

    public String getWinner() {return winner;}

    public int getNextPosition() {return nextPosition.get();}

    public List<String> getPosiciones() {return List.copyOf(posiciones);}

    public record ArrivalSnapshot(int position, String winner) {}
}


