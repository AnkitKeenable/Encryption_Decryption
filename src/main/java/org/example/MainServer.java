package org.example;


import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import java.util.Base64;




@Path("/message")
public class MainServer {

    private DESService desService;

    public MainServer() throws Exception {
        this.desService = new DESService();
    }

    // Endpoint to return the encrypted message
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response getEncryptedMessage() throws Exception {
        String message = "My Name is Ankit";
        String encryptedMessage = DESService.encode(desService.encrypt(message));
        return Response.ok(encryptedMessage).build();
    }

    // Endpoint to decrypt the message when the correct key is passed in the header
    @GET
    @Path("/decrypt")
    @Produces(MediaType.TEXT_PLAIN)
    public Response decryptMessage(@QueryParam("encrypted") String encryptedMessage, @Context HttpHeaders headers) throws Exception {
        String key = headers.getHeaderString("Key");

        if (key == null || !key.equals(DESService.KEY_STRING)) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid or missing key").build();
        }

        String decryptedMessage = desService.decrypt(DESService.decoder(encryptedMessage));
        return Response.ok(decryptedMessage).build();
    }
}
