package ke.or.explorersanddevelopers.lms.exception;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Friday, 07/10/2022
 */
public class DuplicateRecordException extends RuntimeException {
    private static final long serialVersionUID = 8742679042480866356L;

    public DuplicateRecordException(String message) {
        super(message);
    }
}
