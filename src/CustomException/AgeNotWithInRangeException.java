package CustomException;
//Exception for AgeNotWithinrange
public class AgeNotWithInRangeException extends Exception {
    public String toString() {
        return "Age should not be  below 18. Please re-enter the Age.";
    }

}



