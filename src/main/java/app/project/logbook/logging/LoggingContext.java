package app.project.logbook.logging;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

@Slf4j
public class LoggingContext {

    public static final String TRACE_ID = "traceId";
    public static final String CORRELATION_ID = "correlationId";

    public static void setTraceId(String traceId) {
        MDC.put(TRACE_ID, traceId);
    }

    public static void setCorrelationId(String correlationId) {
        MDC.put(CORRELATION_ID, correlationId);
    }
    public static String getTraceId() {
        return MDC.get(TRACE_ID);
    }

    public static String getCorrelationId() {
        return MDC.get(CORRELATION_ID);
    }

    public static void clear() {
        MDC.clear();
    }
}
