package fission.threading;



public class Slave implements Runnable {

    private Workable 		 target;
    private Master 			 master;
    private SlaveListener 	 reportTo;
    private Thread 			 thread;
    private Object			 completedWork;

    private boolean successfullyCompletedWork;

    public Slave(Master master) {

        init();

        this.master = master;
    }

    public synchronized void work(Workable target, SlaveListener reportTo) {

        this.target     = target;
        this.reportTo   = reportTo;

        notify();

        Thread.yield();
    }

    public Object getWork(){

        return completedWork;
    }

    public void run() {

        synchronized (this) {

            while (true)  {

                try  {

                    successfullyCompletedWork = false;

                    wait();

                    completedWork = target.work();

                    successfullyCompletedWork = true;

                    prepareForNextAssigment();
                }
                catch (Exception e) {

                    System.out.println("Slave.run(): " + e);
                    e.printStackTrace();

                    prepareForNextAssigment();
                }
            }
        }
    }

    private void prepareForNextAssigment()  {

        if (reportTo != null){

            reportTo.report(this);
        }

        master.returnSlave(this);

        Thread.yield();
    }

    private final void init() {

        thread = new Thread(this);

        thread.start();
    }

    public Object getTarget()
    {
        return target;
    }

    public boolean successfullyCompletedWork(){

        return successfullyCompletedWork;
    }
}