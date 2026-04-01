package at.madeha.intelliinvoice.restapi;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;

/**
 * CORS (Cross-Origin Resource Sharing) Filter.
 * This class acts as a security handshake that allows the Frontend (Angular , port 4200)
 * to safely communicate with the Backend (8080) API. without it the browser will reject the request for security reasons.
 */
@Provider
public class CorsFilter implements ContainerResponseFilter {
    /**
     * Injects the necessary HTTP headers into every API response.
     */
    @Override
    public void filter(ContainerRequestContext requestContext,
                       ContainerResponseContext responseContext) throws IOException {

        MultivaluedMap<String, Object> headers = responseContext.getHeaders();
// Allows requests specifically from the local Angular development server
        headers.add("Access-Control-Allow-Origin", "http://localhost:4200");
        // Defines which HTTP actions (GET, POST, etc.) are permitted
        headers.add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        // Defines which headers the Frontend is allowed to send (JSON, Auth, etc.)
        headers.add("Access-Control-Allow-Headers", "accept, content-type, authorization, x-requested-with");
        // Permitting credentials (like Cookies or Auth tokens) if needed
        headers.add("Access-Control-Allow-Credentials", "true");
        // How long the browser should cache these permissions (1 hour) to save time
        headers.add("Access-Control-Max-Age", "3600");
    }
}