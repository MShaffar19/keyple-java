package org.keyple.seproxy;

/**
 * The Class APDUResponse.
 * This class defines the elements of a single APDU command response:
 */
public class ApduResponse {

    /** an array of the bytes of an APDU response (none structured, including the dataOut field and the
status of the command).*/
    private byte[] bytes;

    /** the success result of the processed APDU command. */
    private boolean successful;

    /** The status code. */
    private byte[] statusCode;

    
    /**
     * the constructor called by a ProxyReader in order to build the APDU command response to push to a
     * ticketing application.
     *
     * @param bytes the bytes
     * @param successful the successful
     */
    public ApduResponse(byte[] bytes, boolean successful) {
        this.bytes = (bytes == null ? null : bytes.clone());
        this.successful = successful;
    }
    
    /**
     * the constructor called by a ProxyReader in order to build the APDU command response to push to a
ticketing application.
     *
     * @param bytes the bytes
     * @param successful the successful
     * @param statusCode the status code
     */
    public ApduResponse(byte[] bytes, boolean successful, byte[] statusCode) {
        this.bytes = (bytes == null ? null : bytes.clone());
        this.successful = successful;
        this.statusCode = (statusCode == null ? null : statusCode.clone());
    }

    /**
     * Gets the bytes.
     *
     * @return the data of the APDU response.
     */
    public byte[] getbytes() {
        return bytes.clone();
    }

    /**
     * Checks if is successful.
     *
     * @return the status of the command transmission.
     */
    public boolean isSuccessful() {
        return successful;
    }

    /**
     * Gets the status code.
     *
     * @return the status code
     */
    public byte[] getStatusCode() {
        return statusCode.clone();
    }

}