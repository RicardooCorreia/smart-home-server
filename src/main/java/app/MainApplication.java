package app;

import domain.initializer.FirebaseAppInitializer;
import domain.sensor.SensorListener;
import domain.input.command.line.InputDelegate;
import domain.input.socket.SocketJob;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MainApplication {

    public static void main(String[] args) throws Exception {
        ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(3);
        FirebaseAppInitializer.init();

        scheduledExecutorService.schedule(new SensorListener(), 0, TimeUnit.SECONDS);
        scheduledExecutorService.schedule(new SocketJob(), 0, TimeUnit.SECONDS);
        scheduledExecutorService.schedule(new InputDelegate(), 0, TimeUnit.SECONDS);
    }
}
