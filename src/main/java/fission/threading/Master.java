package fission.threading;

import java.util.Stack;

public class Master {

    private Stack slaves;
    private int slaveCount;
    private int noBorrowed;

    public Master(int slaveCount) {

        this.slaveCount = slaveCount;

        init();

        Thread.yield();
    }

    public synchronized Slave borrowSlave() {

        try {

            while (noBorrowed >= slaveCount) {

                wait();
            }
        }
        catch (InterruptedException e) {

            System.out.println(e);
        }

        noBorrowed++;

        return (Slave) slaves.pop();
    }

    public synchronized void returnSlave(Slave slave) {

        slaves.push(slave);

        noBorrowed--;

        notify();
    }

    private final void init() {

        slaves = new Stack();

        for (int i = 0; i < slaveCount; i++) {

            slaves.push(new Slave(this));
        }
    }
}

	