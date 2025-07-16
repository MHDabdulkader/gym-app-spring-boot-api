package com.example.gym_app.utility;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

@Component
public class ErrorResponseUtil {
    private static  final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(ErrorResponseUtil.class);
    private ErrorResponseUtil(){

    }
    public static void sendErrorResponse(HttpServletResponse response, int status, String message, String path) throws IOException {
        try{
            response.setContentType("application/json");
            response.setStatus(status);

            Map<String, Object> errorDetails = Map.of(
                    "timestamp", LocalDateTime.now().toString(),
                    "status", status,
                    "error", HttpStatus.valueOf(status).getReasonPhrase(),
                    "message", message,
                    "path", path
            );
            String json = objectMapper.writeValueAsString(errorDetails);
            response.getWriter().write(json);
            logger.error("=== Error response: {}", json);
        }
        catch(IOException exception){
            logger.error("=== Failure to write error response: {}", exception.toString());
        }
    }

    public static void logMsg(String tag, String msg){
        logger.info("=== [{}] {}", tag,msg);
    }
    public static void logError(String tag, String msg, Throwable throwable){
        logger.error("=== [{}] {}", tag, msg, throwable);
    }
}
