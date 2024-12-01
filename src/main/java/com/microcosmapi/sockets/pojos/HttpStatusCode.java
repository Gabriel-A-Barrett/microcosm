package sockets.pojos;

import java.util.Map;

/**
 * Map of status code values and meanings.
 */
public class HttpStatusCode {

    public static final Map<Integer, String> STATUS_CODES = Map.of(
        200, "OK",
        201, "CREATE_SUCCESSFULLY",
        400, "BAD_REQUEST",
        401, "USER_NOT_AUTHENTICATED",
        403, "PERMISSION_CHECK_FAILED",
        404, "RESOURCE_NOT_FOUND",
        405, "METHOD_NOT_SUPPORTED",
        415, "UNSUPPORTED_DATA_REQUEST_FORMAT",
        500, "INTERNAL_SERVER_ERROR"
    );
}