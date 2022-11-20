import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.rsreu.kibamba.labswork08.CustomSemaphoreRealizer;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;


public class CustomSemaphoreTest {

    @Test
    public void testTryAcquire(){
        CustomSemaphoreRealizer semaphore = new CustomSemaphoreRealizer(1);
        AtomicBoolean firstTaskDone = new AtomicBoolean(false);
        AtomicBoolean secondTaskNotDone = new AtomicBoolean(false);
        Thread firstTask = new Thread(()->{
            firstTaskDone.set(semaphore.tryAcquire());
        });
        Thread secondTask = new Thread(()->{
            secondTaskNotDone.set(semaphore.tryAcquire());
        });
        firstTask.start();
        secondTask.start();
        try{
            firstTask.join();
            secondTask.join();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        assertTrue(firstTaskDone.get() != secondTaskNotDone.get());
    }

    @Test
    public void testInterruptedException(){
        CustomSemaphoreRealizer semaphore = new CustomSemaphoreRealizer(1);
        CountDownLatch latch = new CountDownLatch(1);

        Thread thread1 = new Thread(() -> {
            try {
                semaphore.acquire();
                latch.countDown();
                System.out.println("1");
            } catch (InterruptedException e) {
                System.out.println("ici 1 "+e.getMessage());
            }
        });

        Thread thread2 = new Thread(() -> {
            //Assertions.assertThrows(InterruptedException.class, semaphore::acquire);
            try {
                latch.await();
                System.out.println("2");
                //Assertions.assertThrows(InterruptedException.class, semaphore::acquire);
            } catch (InterruptedException e) {
                System.out.println("ici 2 "+e.getMessage());
            }
        });

        thread1.start();
        thread2.start();
        //thread2.interrupt();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            System.out.println("ici 3 "+e.getMessage());
        }

    }

    @Test
    public void doe() {
        /*AtomicBoolean firstDone = new AtomicBoolean(false);
        AtomicBoolean secondDone = new AtomicBoolean(false);*/

        CustomSemaphoreRealizer semaphore = new CustomSemaphoreRealizer(1);
        Thread t = new Thread(()->{
            try {
                semaphore.acquire();
            }catch (InterruptedException e){
                e.printStackTrace();
            }finally {
                semaphore.release();
            }
        });
        t.start();
        try{
            t.join();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        assertFalse(semaphore.tryAcquire());
        /*Thread t2 = new Thread(()->{
            semaphore.tryAcquire();
        });
        Thread t3 = new Thread(()->{
            semaphore.release();
        });
        t2.start();
        t3.start();
        try{
            t2.join();
            t3.join();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        assertEquals(1,semaphore.getPermits());*/
        /*CountDownLatch latch = new CountDownLatch(1);

        Thread thread1 = new Thread(() -> {
            try {
                latch.await();
                semaphore.acquire();
                firstDone.set(true);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            } finally {
                semaphore.release();
            }
        });

        Thread thread2 = new Thread(() -> {
            try {
                latch.await();
                semaphore.acquire();
                secondDone.set(true);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            } finally {
                semaphore.release();
            }
        });

        thread1.start();
        thread2.start();

        latch.countDown();
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        Assertions.assertTrue(firstDone.get());
        Assertions.assertTrue(secondDone.get());*/
    }

}
