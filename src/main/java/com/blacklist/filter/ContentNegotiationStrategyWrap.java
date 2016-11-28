package com.blacklist.filter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.accept.ContentNegotiationStrategy;
import org.springframework.web.context.request.NativeWebRequest;

/**
 * 对 ServletPathExtensionContentNegotiationStrategy 进行包装
 * */
public class ContentNegotiationStrategyWrap implements
		ContentNegotiationStrategy {
	private static Logger log = LoggerFactory.getLogger(ContentNegotiationStrategyWrap.class);
	private final ContentNegotiationStrategy strategy;

    private Set<String> jsonpParameterNames = new LinkedHashSet<String>(Arrays.asList("jsonp", "callback"));

    /**
     * Pattern for validating jsonp callback parameter values.
     */
    private static final Pattern CALLBACK_PARAM_PATTERN = Pattern.compile("[0-9A-Za-z_\\.]*");

    private String getJsonpParameterValue(NativeWebRequest request) {
        if (this.jsonpParameterNames != null) {
            for (String name : this.jsonpParameterNames) {
                String value = request.getParameter(name);
                if (StringUtils.isEmpty(value)) {
                    continue;
                }
                if (!isValidJsonpQueryParam(value)) {
                    if (log.isDebugEnabled()) {
                    	log.debug("Ignoring invalid jsonp parameter value: " + value);
                    }
                    continue;
                }
                return value;
            }
        }
        return null;
    }

    protected boolean isValidJsonpQueryParam(String value) {
        return CALLBACK_PARAM_PATTERN.matcher(value).matches();
    }

    public ContentNegotiationStrategyWrap(ContentNegotiationStrategy strategy) {
        super();
        this.strategy = strategy;
    }

    @Override
    public List<MediaType> resolveMediaTypes(NativeWebRequest request) throws HttpMediaTypeNotAcceptableException {

        // JSONP 响应类型处理 ---- BEGIN
        String jsonpParameterValue = getJsonpParameterValue(request);
        if (jsonpParameterValue != null) {
            List<MediaType> mediaTypes = new ArrayList<>(1);
            mediaTypes.add(MediaType.valueOf("application/javascript"));
            return mediaTypes;
        }
        // JSONP 响应类型处理 ---- END

        return this.strategy.resolveMediaTypes(request);
    }

}
