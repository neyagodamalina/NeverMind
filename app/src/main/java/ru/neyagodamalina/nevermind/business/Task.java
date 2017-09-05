package ru.neyagodamalina.nevermind.business;

import java.util.ArrayList;

import ru.neyagodamalina.nevermind.business.exception.WrongTimeStopTimeStartException;

/**
 * Created by developer on 04.09.2017.
 */

public class Task extends Slot {
    private ArrayList<Slot> slots = new ArrayList<>();


    @Override
    public void start() {
        Slot slot = new Slot();
        slot.start();
        if (slots.size() == 0)
            this.setTimeStart(slot.getTimeStart());
        slots.add(slot);
    }

    @Override
    public void stop() {
        try{
            if (slots.size() == 0)
                throw new WrongTimeStopTimeStartException("Stop task before start.");
            Slot slot = slots.get(slots.size() - 1);
            slot.stop();
            this.setTimeStop(slot.getTimeStop());
        }
        catch (WrongTimeStopTimeStartException e){
            System.err.println(e);
        }
    }


}
