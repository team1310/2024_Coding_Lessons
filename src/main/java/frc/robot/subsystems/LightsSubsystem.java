package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.LightsConstants;

public class LightsSubsystem extends SubsystemBase {

    // Note: on Shifty, the lights are Y-ed, both of the strips run the same pattern.

    private final AddressableLED       ledStrip;
    private final AddressableLEDBuffer ledBuffer;
    private final AddressableLEDBuffer invertedLEDBuffer;

    private static final Color         TEST_COLOR    = new Color(30, 30, 30); // RGB

    // RSL tracker
    private boolean                    previousRSLOn = false;
    private int                        rslSyncCount  = 0;

    public LightsSubsystem() {

        ledStrip          = new AddressableLED(LightsConstants.LED_PWM_PORT);
        ledBuffer         = new AddressableLEDBuffer(LightsConstants.LED_STRING_LENGTH);
        invertedLEDBuffer = new AddressableLEDBuffer(LightsConstants.LED_STRING_LENGTH);

        ledStrip.setLength(ledBuffer.getLength());
        ledStrip.setData(ledBuffer);

        ledStrip.start();

        // Run the test pattern
        setColor(TEST_COLOR);
    }

    public void robotEnabled() {
        // Flash the inverted color buffer 5 times when starting up.
        rslSyncCount = 4;
    }

    private void setColor(Color color) {
        for (int pixel = 0; pixel < ledBuffer.getLength(); pixel++) {
            setPixel(pixel, color);
        }
    }

    private void setPixel(int pixel, Color color) {
        // Make sure the pixel is in the buffer to avoid an out of bounds exception
        if (pixel >= 0 && pixel < ledBuffer.getLength()) {

            ledBuffer.setLED(pixel, color);

            // Set up the inverted color array for RSL sync.
            Color invertedColor = new Color(1.0 - color.red, 1.0 - color.green, 1.0 - color.blue);
            invertedLEDBuffer.setLED(pixel, invertedColor);
        }
    }

    @Override
    public void periodic() {

        // Update the buffer every loop
        if (rslSyncCount < 0) {
            ledStrip.setData(ledBuffer);
        }
        else {

            // Sync with the first set of RSL flashes when the robot is first enabled
            boolean rslOn = RobotController.getRSLState();

            if (!rslOn && previousRSLOn) {
                rslSyncCount--;
            }
            previousRSLOn = rslOn;

            if (rslOn) {
                ledStrip.setData(ledBuffer);
            }
            else {
                ledStrip.setData(invertedLEDBuffer);
            }
        }
    }
}
