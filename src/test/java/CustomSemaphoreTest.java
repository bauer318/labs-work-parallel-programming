import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import ru.rsreu.kibamba.labswork08.OwnSemaphore;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;


public class CustomSemaphoreTest {

    @Test
    public void testTryAcquire() {
        OwnSemaphore semaphore = new OwnSemaphore(1);
        AtomicBoolean firstTaskDone = new AtomicBoolean(false);
        AtomicBoolean secondTaskDone = new AtomicBoolean(false);
        Thread firstTask = new Thread(() -> {
            firstTaskDone.set(semaphore.tryAcquire());
        });
        Thread secondTask = new Thread(() -> {
            secondTaskDone.set(semaphore.tryAcquire());
        });
        firstTask.start();
        secondTask.start();
        try {
            firstTask.join();
            secondTask.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertTrue(firstTaskDone.get() != secondTaskDone.get());
    }
    @Test
    public void testTryAcquireSingleThread(){
        OwnSemaphore semaphore = new OwnSemaphore(1);
        assertTrue(semaphore.tryAcquire());
        semaphore.release();
        assertTrue(semaphore.tryAcquire());
        assertFalse(semaphore.tryAcquire());
    }

    @RepeatedTest(20)
    public void testInterruptedException() {
        OwnSemaphore semaphore = new OwnSemaphore(1);
        CountDownLatch latch = new CountDownLatch(1);
        Thread mustBeExecuteSecond = new Thread(() -> {
            try {
                latch.await();
                Assertions.assertThrows(InterruptedException.class, () -> semaphore.acquire());
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        });
        Thread mustBeExecuteFirst = new Thread(() -> {
            try {
                semaphore.acquire();
                latch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        mustBeExecuteSecond.start();
        mustBeExecuteFirst.start();
        mustBeExecuteSecond.interrupt();
        try {
            mustBeExecuteSecond.join();
            mustBeExecuteFirst.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @RepeatedTest(20)
    public void testMultithreading() {
        int permits = 4;
        OwnSemaphore semaphore = new OwnSemaphore(permits);
        CountDownLatch latch = new CountDownLatch(1);
        AtomicBoolean runningTaskWasExceededPermits = new AtomicBoolean(false);
        AtomicInteger runningTaskCounter = new AtomicInteger(0);
        List<Thread> threadsList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            threadsList.add(new Thread(() -> {
                try {
                    latch.await();
                    semaphore.acquire();
                    runningTaskWasExceededPermits.set(runningTaskCounter.incrementAndGet()>permits);
                    Thread.sleep(15);
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                } finally {
                    runningTaskCounter.decrementAndGet();
                    semaphore.release();
                }
            }));
        }
        threadsList.forEach(t -> t.start());
        latch.countDown();
        threadsList.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        });
        Assertions.assertFalse(runningTaskWasExceededPermits.get());
        Assertions.assertEquals(0, runningTaskCounter.get());
    }

}
