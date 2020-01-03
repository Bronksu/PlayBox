package pl.jbrocki.PlayBox;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.trigger.GpioCallbackTrigger;

import java.io.File;

public class GpioManager {

    File kogut_sound = new File("/home/pi/ZabawkaKrzysiaDzwieki/Kogut.wav");
    File krowa_sound = new File("/home/pi/ZabawkaKrzysiaDzwieki/Krowa.wav");
    File pies_sound = new File("/home/pi/ZabawkaKrzysiaDzwieki/Pies.wav");
    File kot_sound = new File("/home/pi/ZabawkaKrzysiaDzwieki/Kot.wav");
    File swinia_sound = new File("/home/pi/ZabawkaKrzysiaDzwieki/Swinia.wav");
    File owca_sound = new File("/home/pi/ZabawkaKrzysiaDzwieki/Owca.wav");

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
    final GpioPinDigitalInput myButton6 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_14,
            PinPullResistance.PULL_DOWN);

    GpioPinDigitalOutput[] myLed = {
            gpio.provisionDigitalOutputPin(RaspiPin.GPIO_04, "LED #1", PinState.LOW),
            gpio.provisionDigitalOutputPin(RaspiPin.GPIO_05, "LED #2", PinState.LOW),
            gpio.provisionDigitalOutputPin(RaspiPin.GPIO_06, "LED #3", PinState.LOW),
            gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, "LED #3", PinState.LOW),
            gpio.provisionDigitalOutputPin(RaspiPin.GPIO_10, "LED #3", PinState.LOW),
            gpio.provisionDigitalOutputPin(RaspiPin.GPIO_11, "LED #3", PinState.LOW)
    };

    private PlayMusic playMusic;

    @SuppressWarnings("InfiniteLoopStatement")
    public void RunGpio() throws InterruptedException {
        playMusic = new PlayMusic();
        // create a gpio callback trigger on gpio pin#4; when #4 changes state, perform a callback
        // invocation on the user defined 'Callable' class instance
        myButton1.addTrigger(new GpioCallbackTrigger(() -> {
            myLed[0].high();
            playMusic.playMusic(kogut_sound);
            Thread.sleep(2000);
            myLed[0].low();
            return null;
        }));

        myButton2.addTrigger(new GpioCallbackTrigger(() -> {
            myLed[1].high();
            playMusic.playMusic(krowa_sound);
            Thread.sleep(2000);
            myLed[1].low();
            return null;
        }));
        myButton3.addTrigger(new GpioCallbackTrigger(() -> {
            myLed[2].high();
            playMusic.playMusic(pies_sound);
            Thread.sleep(2000);
            myLed[2].low();
            return null;
        }));
        myButton4.addTrigger(new GpioCallbackTrigger(() -> {
            myLed[3].high();
            playMusic.playMusic(kot_sound);
            Thread.sleep(2000);
            myLed[3].low();
            return null;
        }));
        myButton5.addTrigger(new GpioCallbackTrigger(() -> {
            myLed[4].high();
            playMusic.playMusic(swinia_sound);
            Thread.sleep(2000);
            myLed[4].low();
            return null;
        }));
        myButton6.addTrigger(new GpioCallbackTrigger(() -> {
            myLed[5].high();
            playMusic.playMusic(owca_sound);
            Thread.sleep(2000);
            myLed[5].low();
            return null;
        }));

        // keep program running until user aborts (CTRL-C)
        while (true) {
            Thread.sleep(500);
        }

        // stop all GPIO activity/threads by shutting down the GPIO controller
        // (this method will forcefully shutdown all GPIO monitoring threads and scheduled tasks)
        // gpio.shutdown();   <--- implement this method call if you wish to terminate the Pi4J GPIO controller
    }
}

