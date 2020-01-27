package pl.jbrocki.PlayBox;

import com.pi4j.gpio.extension.pcf.PCF8574GpioProvider;
import com.pi4j.gpio.extension.pcf.PCF8574Pin;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.trigger.GpioCallbackTrigger;
import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CFactory.UnsupportedBusNumberException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import java.io.File;
import java.io.IOException;

public class GpioManager {

    File kogut_sound = new File("/home/pi/ZabawkaKrzysiaDzwieki/Kogut.wav");
    File krowa_sound = new File("/home/pi/ZabawkaKrzysiaDzwieki/Krowa.wav");
    File pies_sound = new File("/home/pi/ZabawkaKrzysiaDzwieki/Pies.wav");
    File kot_sound = new File("/home/pi/ZabawkaKrzysiaDzwieki/Kot.wav");
    File swinia_sound = new File("/home/pi/ZabawkaKrzysiaDzwieki/Swinia.wav");
    File owca_sound = new File("/home/pi/ZabawkaKrzysiaDzwieki/Owca.wav");

    private PlayMusic playMusic;
    private Clip clip;
    int isPlaying = 0;

    // create gpio controller
    final GpioController gpio = GpioFactory.getInstance();

    // provision gpio pin #02 as an input pin with its internal pull down resistor enabled
    final GpioPinDigitalInput myButton1 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_00,
            PinPullResistance.PULL_DOWN);
    final GpioPinDigitalInput myButton2 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_02,
            PinPullResistance.PULL_DOWN);
    final GpioPinDigitalInput myButton3 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_03,
            PinPullResistance.PULL_DOWN);
    final GpioPinDigitalInput myButton4 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_12,
            PinPullResistance.PULL_DOWN);
    final GpioPinDigitalInput myButton5 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_13,
            PinPullResistance.PULL_DOWN);
    final GpioPinDigitalInput myButton6 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_07,
            PinPullResistance.PULL_DOWN);


    @SuppressWarnings("InfiniteLoopStatement")
    public void RunGpio() throws LineUnavailableException, InterruptedException, IOException, UnsupportedBusNumberException {

        final PCF8574GpioProvider provider = new PCF8574GpioProvider(I2CBus.BUS_1, PCF8574GpioProvider.PCF8574A_0x3F);

        GpioPinDigitalOutput[] myLed = {
                gpio.provisionDigitalOutputPin(provider, PCF8574Pin.GPIO_00, PinState.LOW),
                gpio.provisionDigitalOutputPin(provider, PCF8574Pin.GPIO_01, PinState.LOW),
                gpio.provisionDigitalOutputPin(provider, PCF8574Pin.GPIO_02, PinState.LOW),
                gpio.provisionDigitalOutputPin(provider, PCF8574Pin.GPIO_03, PinState.LOW),
                gpio.provisionDigitalOutputPin(provider, PCF8574Pin.GPIO_04, PinState.LOW),
                gpio.provisionDigitalOutputPin(provider, PCF8574Pin.GPIO_05, PinState.LOW)
        };

        playMusic = new PlayMusic();
        clip = clip = AudioSystem.getClip();
        // create a gpio callback trigger on gpio pin#4; when #4 changes state, perform a callback
        // invocation on the user defined 'Callable' class instance
        myButton1.addTrigger(new GpioCallbackTrigger(() -> {
            clip.stop();
            isPlaying = 1;
            myLed[0].high();
            playMusic.playMusic(kogut_sound, clip);
            Thread.sleep(2000);
            myLed[0].low();
            isPlaying = 0;
            return null;
        }));

        myButton2.addTrigger(new GpioCallbackTrigger(() -> {
            clip.stop();
            isPlaying = 1;
            myLed[1].high();
            playMusic.playMusic(krowa_sound, clip);
            Thread.sleep(2000);
            myLed[1].low();
            isPlaying = 0;
            return null;
        }));

        myButton3.addTrigger(new GpioCallbackTrigger(() -> {
            clip.stop();
            isPlaying = 1;
            myLed[2].high();
            playMusic.playMusic(kot_sound, clip);
            Thread.sleep(2000);
            myLed[2].low();
            isPlaying = 0;

            return null;
        }));

        myButton4.addTrigger(new GpioCallbackTrigger(() -> {
            clip.stop();
            isPlaying = 1;
            myLed[3].high();
            playMusic.playMusic(owca_sound, clip);
            Thread.sleep(2000);
            myLed[3].low();
            isPlaying = 0;

            return null;
        }));

        myButton5.addTrigger(new GpioCallbackTrigger(() -> {
            clip.stop();
            isPlaying = 1;
            myLed[4].high();
            playMusic.playMusic(pies_sound, clip);
            Thread.sleep(2000);
            myLed[4].low();
            isPlaying = 0;

            return null;
        }));

        // keep program running until user aborts (CTRL-C)
        while (true) {
            Thread.sleep(500);
        }
    }
}

