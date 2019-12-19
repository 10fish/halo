package run.halo.app.config;

import com.qcloud.cos.annotation.GuardedBy;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;

@Repository
public class HaloTraceRepository implements HttpTraceRepository {
    private static final int MAX_TRACE_QUEUE_SIZE = 19;

    private static final Object lock = new Object();

    @GuardedBy("lock")
    private final LinkedList<HttpTrace> traces = new LinkedList<>();

    @Override
    public List<HttpTrace> findAll() {
        return traces;
    }

    @Override
    public void add(HttpTrace trace) {
        synchronized (lock) {
            if (ObjectUtils.allNotNull(trace)) {
                if (traces.size() > MAX_TRACE_QUEUE_SIZE) {
                    traces.poll();
                }
                traces.offer(trace);
            }
        }
    }
}
