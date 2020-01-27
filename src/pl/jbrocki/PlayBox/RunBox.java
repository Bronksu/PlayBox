package pl.jbrocki.PlayBox;

import javax.sound.sampled.LineUnavailableException;

public class RunBox {
    public static GpioManager gpioManager;
    public static void main(String[] args) throws InterruptedException, LineUnavailableException {
        gpioManager = new GpioManager(provider);
        gpioManager.RunGpio();
    }
}
