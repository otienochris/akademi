package ke.or.explorersanddevelopers.lms.exception;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Friday, 07/10/2022
 */
public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -1950941421358603425L;

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
