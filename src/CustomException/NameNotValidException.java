package CustomException;
//Exception for name not valid 
public class NameNotValidException extends Exception {
    public String toString() {
        return "Name is not valid. Please re-enter the Name.";
    }
}


