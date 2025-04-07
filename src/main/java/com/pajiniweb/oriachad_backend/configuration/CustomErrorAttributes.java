package com.pajiniweb.oriachad_backend.configuration;



import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@Component
public class CustomErrorAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
        // Remove stack trace details
        options = options.including(ErrorAttributeOptions.Include.MESSAGE)
                .excluding(ErrorAttributeOptions.Include.EXCEPTION)
                .excluding(ErrorAttributeOptions.Include.STACK_TRACE);

        return super.getErrorAttributes(webRequest, options);
    }
}
