package ap.njit.chatapp.activities;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Roll {

    private final String message;
    private Integer maxNum = -1;
    public Roll(String message) {
        this.message = message;
    }

    private Boolean isRollable() {
        Boolean result = null;
        Pattern pattern = Pattern.compile("^/roll[\\ ](0|[1-9][0-9]?|100)$");
        Matcher matcher = pattern.matcher(this.message);
        if (matcher.find()) {
            result = true;
            String extractedNumString = matcher.group(1);
            this.maxNum = Integer.parseInt(extractedNumString);
        } else {
            result = false;
        }

        return result;
    }

    public String roll() {
        String res = null;
        int intResult = -1;
        if (this.isRollable()) {
            Random random = new Random();
            intResult = random.nextInt(this.maxNum);
        }

        if (intResult == -1) {
            res = "null";
        } else {
            res = String.valueOf(intResult);
        }

        return res;
    }

}
