package frc.robot;

/**
 * This class is designed to improve tank-drive robot handling via the {@link #getAdjustedValues(double leftSpeed, double rightSpeed)} method.
 */
public class Handling {
    /**
     * All values should follow 0 <= x <= 1:
     * 
     * <p> {@link #INPUT_EXPONENTIAL_ADJUSTMENT}: The inputs are put to this power plus 1. More speed control at lower speeds, but less at higher speeds.
     * <p> {@link #MINIMUM_ADJUSTMENT_FACTOR}: The minimum percent of adjustment. More handling, particularly when turning, but reduced overall turning speeds.
     * <p> {@link #MAXIMUM_ADJUSTMENT_FACTOR}: The maximum percent of adjustment. More handling, but without much downside. 1 is recommended.
     * <p> {@link #FINAL_OUTPUT_REDUCTION_MULTIPLIER}: The final output is multiplied by 1 minus this value. More speed control, particularly at higher speeds, but reduced overall speeds.
     */
    public static class HandlingConstants {
        public static final double INPUT_EXPONENTIAL_ADJUSTMENT = 0.65;
        public static final double MINIMUM_ADJUSTMENT_FACTOR = 0.25;
        public static final double MAXIMUM_ADJUSTMENT_FACTOR = 1;
        public static final double FINAL_OUTPUT_REDUCTION_MULTIPLIER = 0.15;
    }

    private static double average;
    private static double adjustmentFactor;

    /**
     * Adjusts input values from a controller to improve tank-drive robot handling based on some parameters ({@link HandlingConstants}). 
     * This is accomplished through a few main steps:
     * <p> 1. The inputs are exponentiated to a value between 1 and 2, which can reduce small inputs for finer control. (INPUT_EXPONENTIAL_ADJUSTMENT, 
     * NOTE: 1 is added to this value before being used as an exponent) 
     * <p> 2. The inputs are adjusted closer to their average proportionally to said average, which can straighten out the inputs at high speeds (MINIMUM_ADJUSTMENT_FACTOR, MAXIMUM_ADJUSTMENT_FACTOR, 
     * NOTE: These values do not represent hard limits, but instead points of a linear function that describes the proportionality of the adjustment)
     * <p> 3. The inputs are multiplied by a value between 0 and 1, reducing speeds at all input values, which can improve speed control (FINAL_OUTPUT_REDUCTION_MULTIPLIER, 
     * NOTE: This value is subtracted from 1 before being multiplied)
     * <p> The pros and cons of each adjustment are elaborated upon in the {@link HandlingConstants} JavaDoc. Also, there is a Desmos demo here: https://www.desmos.com/calculator/wjain3k5oa 
     * @param leftSpeed left input value
     * @param rightSpeed right input value
     * @return a two-element {@code double} array of the input values after being adjusted [left, right].
     */
    public static double[] getAdjustedValues(double leftSpeed, double rightSpeed) {
        leftSpeed = applyInputExponent(leftSpeed);
        rightSpeed = applyInputExponent(rightSpeed);

        average = (leftSpeed + rightSpeed) / 2;
        adjustmentFactor = getAdjustmentFactor();

        leftSpeed += getValueAdjustment(leftSpeed);
        rightSpeed += getValueAdjustment(rightSpeed);

        return new double[]{
            applyFinalOutputReduction(leftSpeed),
            applyFinalOutputReduction(rightSpeed)
        };
    }

    /**
     * @param input the input value
     * @return |input| ^ (INPUT_EXPONENTIAL_ADJUSTMENT + 1) * signum(input)
     */
    private static double applyInputExponent(double input) {
        return Math.pow(Math.abs(input), HandlingConstants.INPUT_EXPONENTIAL_ADJUSTMENT + 1) * Math.signum(input);
    }

    /**
     * @return |average| * (MAXIMUM_ADJUSTMENT_FACTOR - MINIMUM_ADJUSTMENT_FACTOR) + MINIMUM_ADJUSTMENT_FACTOR
     */
    private static double getAdjustmentFactor() {
        return Math.abs(average) * (HandlingConstants.MAXIMUM_ADJUSTMENT_FACTOR - HandlingConstants.MINIMUM_ADJUSTMENT_FACTOR) + HandlingConstants.MINIMUM_ADJUSTMENT_FACTOR;
    }

    /**
     * @param value the value to adjust
     * @return adjustmentFactor * (average - value)
     */
    private static double getValueAdjustment(double value) {
        return adjustmentFactor * (average - value);
    }

    /**
     * @param value the output value to reduce
     * @return value * (1 - FINAL_OUTPUT_REDUCTION_MULTIPLIER)
     */
    private static double applyFinalOutputReduction(double value) {
        return value * (1 - HandlingConstants.FINAL_OUTPUT_REDUCTION_MULTIPLIER);
    }
}
