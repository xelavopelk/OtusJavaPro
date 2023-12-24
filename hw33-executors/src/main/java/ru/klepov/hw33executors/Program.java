package ru.klepov.hw33executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@SpringBootApplication
public class Program
        implements CommandLineRunner {

    public int curVal;
    private int MAX_VAL = 10;
    private Integer currentThread = 0;
    private Integer SLEEP_INTERVAL = 500; //это чтобы видно было вывод

    private Lock lock = new ReentrantLock();
    private final Condition possible0 = lock.newCondition();
    private final Condition possible1 = lock.newCondition();

    private static Logger LOG = LoggerFactory.getLogger(Program.class);

    private Runnable runnable1 = () -> {
        var currentChange = 0;
        while (true) {
            try {
                Thread.sleep(SLEEP_INTERVAL);
            } catch (InterruptedException e) {
                LOG.error(e.getStackTrace().toString());
            }
            lock.lock();
            try {
                if (currentThread == 0) {
                    curVal += currentChange;
                    LOG.info(String.valueOf(curVal));
                    currentThread = 1;
                    if (currentChange == 0) {
                        currentChange = 1;
                    } else if (curVal == MAX_VAL || curVal == 1) {
                        currentChange *= -1;
                    }
                }
                possible1.signal();
                possible0.await();
            } catch (InterruptedException e) {
                LOG.error(e.getStackTrace().toString());
            } finally {
                lock.unlock();
            }
        }
    };

    Runnable runnable2 = () -> {
        while (true) {
            try {
                Thread.sleep(SLEEP_INTERVAL);
            } catch (InterruptedException e) {
                LOG.error(e.getStackTrace().toString());
            }
            lock.lock();
            try {
                if (currentThread == 1) {
                    LOG.info(String.valueOf(curVal));
                    currentThread = 0;
                }
                possible0.signal();
                possible1.await();
            } catch (InterruptedException e) {
                LOG.error(e.getStackTrace().toString());
            } finally {
                lock.unlock();
            }
        }
    };


    public static void main(String[] args) {
        LOG.info("STARTING THE APPLICATION");
        SpringApplication.run(Program.class, args);
        LOG.info("APPLICATION FINISHED");
    }

    @Override
    public void run(String... args) throws InterruptedException {
        LOG.info("EXECUTING : command line runner");
        curVal = 1;

        Thread t0 = new Thread(runnable1);
        Thread t1 = new Thread(runnable2);

        t0.start();
        t1.start();

        t0.join();
        t1.join();
    }
}