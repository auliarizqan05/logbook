package app.project.logbook.config;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Scope;
import io.opentelemetry.exporter.otlp.trace.OtlpGrpcSpanExporter;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import io.opentelemetry.sdk.trace.export.BatchSpanProcessor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class TelemetryConfig {

    @Bean
    public OpenTelemetry openTelemetry() {
        OtlpGrpcSpanExporter exporter = OtlpGrpcSpanExporter.builder()
                .setEndpoint("http://localhost:4317") // Ganti dengan endpoint collector-mu
                .build();

        SdkTracerProvider tracerProvider = SdkTracerProvider.builder()
                .addSpanProcessor(BatchSpanProcessor.builder(exporter).build())
                .build();

        return OpenTelemetrySdk.builder()
                .setTracerProvider(tracerProvider)
                .buildAndRegisterGlobal();
    }

    @Bean
    public Tracer tracer(OpenTelemetry openTelemetry) {
        return openTelemetry.getTracer("app.project.optimus", "0.0.1");
    }

    // OpenTelemetry akan mengatur tracing otomatis di Spring Boot
    public void autoTraceOperation() {
        // Tracing otomatis dari Spring Boot filter atau handler
    }

    @Bean
    CommandLineRunner demo(Tracer tracer) {
        return args -> {
            Span span = tracer.spanBuilder("manual.startup").startSpan();
            try (Scope scope = span.makeCurrent()) {
                System.out.println(">>> Tracing manually created span.");
            } finally {
                span.end();
            }
        };
    }
}
