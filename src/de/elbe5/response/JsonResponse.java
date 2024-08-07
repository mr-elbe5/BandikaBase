package de.elbe5.response;

import de.elbe5.base.JsonObject;
import de.elbe5.base.Log;
import de.elbe5.application.Configuration;
import de.elbe5.request.RequestData;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class JsonResponse implements IResponse {

    private final String json;

    public JsonResponse(JsonObject json) {
        this.json = json.toString();
    }

    public JsonResponse(String json) {
        //Log.log("json: " + json);
        this.json = json;
    }

    @Override
    public void processResponse(ServletContext context, RequestData rdata, HttpServletResponse response) {
        response.setContentType("application/json");
        if (!sendJsonResponse(response))
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }

    protected boolean sendJsonResponse(HttpServletResponse response) {
        try {
            ServletOutputStream out = response.getOutputStream();
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Expires", "Tues, 01 Jan 1980 00:00:00 GMT");
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("Pragma", "no-cache");
            if (json == null || json.length() == 0) {
                response.setHeader("Content-Length", "0");
            } else {
                byte[] bytes = json.getBytes(Configuration.ENCODING);
                response.setHeader("Content-Length", Integer.toString(bytes.length));
                out.write(bytes);
            }
            out.flush();
            Log.info("json has been sent");
        } catch (IOException ioe) {
            Log.error("response error", ioe);
            return false;
        }
        return true;
    }
}
