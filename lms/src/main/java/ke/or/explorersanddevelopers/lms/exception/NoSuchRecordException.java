package ke.or.explorersanddevelopers.lms.exception;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Wednesday, 05/10/2022
 */
public class NoSuchRecordException extends RuntimeException {
    private static final long serialVersionUID = 284055250060514454L;
    public NoSuchRecordException(String message) {
        super(message);
    }
}
