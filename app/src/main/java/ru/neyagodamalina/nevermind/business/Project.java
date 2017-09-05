package ru.neyagodamalina.nevermind.business;

import java.util.ArrayList;

/**
 * Created by developer on 05.09.2017.
 */

public class Project extends Slot {
    ArrayList<Task> tasks = new ArrayList();

    public void addTask(Task task){
        tasks.add(task);
        if (tasks.size() == 0){
            this.setTimeStart(task.getTimeStart());
        }
        this.setTimeStop(task.getTimeStop());
    }
}
