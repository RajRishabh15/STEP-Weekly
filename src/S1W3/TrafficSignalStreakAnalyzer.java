package S1W3;

public class TrafficSignalStreakAnalyzer {

    static void findLongestStreak(String signalLog) {

        if (signalLog.isEmpty()) {
            System.out.println("Signal log is empty");
            return;
        }

        int currentStreak = 1;
        int longestStreak = 1;

        char currentColor = signalLog.charAt(0);
        char longestColor = signalLog.charAt(0);

        for (int i = 1; i < signalLog.length(); i++) {

            if (signalLog.charAt(i) == signalLog.charAt(i - 1)) {

                currentStreak++;

            } else {

                currentStreak = 1;
            }

            if (currentStreak > longestStreak) {

                longestStreak = currentStreak;
                longestColor = signalLog.charAt(i);
            }
        }

        System.out.println(
                "Longest Streak: '" + longestColor +
                        "' repeated " + longestStreak + " times"
        );
    }

    public static void main(String[] args) {

        findLongestStreak("RRGGGYRR");

        findLongestStreak("RRRRYYGG");
    }
}
