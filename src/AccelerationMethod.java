public class AccelerationMethod {
    final public static double minimumWait = 0.25; // This is the minimum amount of seconds the piece waits until it drops 1 down again.
    final public static double accelerationTimeFrame = 10; // Everytime that the timeframe fits in the time, the pieces drop a bit faster
    final public static double acceleration = 0.05; // Every time frame the pieces will fall 0.05 seconds faster.
    final public static long millisecondsToSeconds = 1000; // Milliseconds times 1000 creates seconds. //// This will be used for the Thread.sleep(long milliseconds) to convert the milliseconds to seconds
    public static void main(String[] args) {
        double startingTime = System.currentTimeMillis(); // Isn't necessary, but is used to start the counting of the time at the same time the player starts the game. //// Needs to be implemented when player starts the game.

        double currentTime = System.currentTimeMillis(); // Stores the current time.
        double playingTime = (currentTime-startingTime)/1000; // The playing time is the time since the player has started the game.
        double wait = fallingAcceleration(playingTime);  //Use this variable in the main method where you hold the piece for a moment.
    }

    public static double fallingAcceleration(double time){
        double timeIndicate = 1;

        countingloop:
        for (double i = accelerationTimeFrame; i < time; i+=accelerationTimeFrame){
            timeIndicate -= acceleration;
            if(timeIndicate <= minimumWait){
                timeIndicate = minimumWait;
                break countingloop;
            }    
        }

        return timeIndicate;
    }
}
