package vn.conguyetduong.hogwarts.app.component;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.api.trace.Span;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class TraceIdFilter implements Filter {
    private final OpenTelemetry openTelemetry;
    private Tracer tracer;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        tracer = openTelemetry.getTracer(this.getClass().getSimpleName());
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // Get the traceId from the current span
        Span currentSpan = tracer.spanBuilder("setTraceIdInResponse").startSpan();
        String traceId = currentSpan.getSpanContext().getTraceId();

        // Set traceId in the response
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setHeader("X-Trace-Id", traceId);

        currentSpan.end();

        // Continue the filter chain
        chain.doFilter(request, response);

    }

    @Override
    public void destroy() {
        // Cleanup logic (if any)
    }
}

