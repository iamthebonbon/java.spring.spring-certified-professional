package com.example.scp.extension;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class BeforeEachCallbackExtension implements BeforeEachCallback, AfterEachCallback {
    private static final Logger LOGGER = LoggerFactory.getLogger(BeforeEachCallbackExtension.class);

    private static final ExtensionContext.Namespace NAMESPACE =
            ExtensionContext.Namespace.create(BeforeEachCallbackExtension.class);

    @Override
    public void beforeEach(ExtensionContext context) {
        context.getStore(NAMESPACE).put("timer", System.currentTimeMillis());
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        long timer = System.currentTimeMillis() - (long) context.getStore(NAMESPACE).get("timer");
        LOGGER.info("Execution: {}ms", timer);
    }
}
