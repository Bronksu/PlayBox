package pl.jbrocki.PlayBox;

import com.pi4j.io.i2c.I2CFactory;

import javax.sound.sampled.LineUnavailableException;
import java.io.IOException;

public class RunBox {
    public static GpioManager gpioManager;
    public static void main(String[] args) throws InterruptedException, LineUnavailableException, IOException, I2CFactory.UnsupportedBusNumberException {
        gpioManager = new GpioManager();
        gpioManager.RunGpio();
    }
}
