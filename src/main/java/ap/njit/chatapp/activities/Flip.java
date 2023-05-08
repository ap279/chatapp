package ap.njit.chatapp.activities;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Flip {

    private final String message;
    public Flip(String message) {
        this.message = message;
    }

    // Parse message to see if it fits the flip pattern
    private Boolean isFlippable() {
        Boolean result = null;
        Pattern pattern = Pattern.compile("/flip");
        Matcher matcher = pattern.matcher(this.message);
        if (matcher.find()) {
            // If a match is found, return truthy
            result = true;
        } else {
            result = false;
        }
        return result;
    };

    public String flip() {
        String res = null;

        int result = 0;
        if (this.isFlippable()) {
            Random random = new Random();
            result = random.nextInt(2); // 0 for tails, 1 for heads
        } else {
            // Result of 2 means that the message isn't flippable
            result = 2;
        }

        if (result == 0) {
            res = "tails";
        } else if (result == 1) {
            res = "heads";
        } else {
            res = "null";
        }

        return res;
    };

}
