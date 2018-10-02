package scnd.wave.flashlight;

import android.hardware.Camera;

public class Light implements Runnable {
    static Light getInstance()
    {
        return instance == null ? instance = new Light() : instance;
    }

    private static Light instance;


    volatile boolean requestStop = false;
    private volatile boolean isRunning = false;

    volatile Home controller;

    @Override
    public void run() {
        if(isRunning)
            return;

        requestStop = false;
        isRunning = true;

        Camera camera = Camera.open();

        Camera.Parameters CameraON = camera.getParameters(), CameraOFF = camera.getParameters();

        CameraON.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        CameraOFF.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);

        while(!requestStop) {
            try {
                camera.setParameters(CameraON);
            } catch (Exception ignored){}
        }
        camera.setParameters(CameraOFF);

        camera.release();

        isRunning = false;
        requestStop = false;

    }
}