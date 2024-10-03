package com.arlania.engine.task.impl;

import com.arlania.engine.task.Task;
import com.arlania.engine.task.TaskManager;
import com.arlania.world.content.skill.impl.hunter.Hunter;
import com.arlania.world.content.skill.impl.hunter.Trap;
import com.arlania.world.content.skill.impl.hunter.TrapExecution;

import java.util.Iterator;

public class HunterTrapsTask extends Task {

    public HunterTrapsTask() {
        super(1);
    }

    @Override
    protected void execute() {
        final Iterator<Trap> iterator = Hunter.traps.iterator();
        while (iterator.hasNext()) {
            final Trap trap = iterator.next();
            if (trap == null)
                continue;
            if (trap.getOwner() == null || !trap.getOwner().isRegistered())
                Hunter.deregister(trap);
            TrapExecution.setTrapProcess(trap);
            TrapExecution.trapTimerManagement(trap);
        }
        if (Hunter.traps.isEmpty())
            stop();
    }

    @Override
    public void stop() {
        setEventRunning(false);
        running = false;
    }

    public static void fireTask() {
        if (running)
            return;
        running = true;
        TaskManager.submit(new HunterTrapsTask());
    }

    private static boolean running;
}
