package ir.piana.business.multishop.common.queue;

import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;

import javax.servlet.http.HttpServletRequest;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeferredResultContext<ResponseEntity> {
    private DeferredResult<org.springframework.http.ResponseEntity> deferredResult;
    private HttpServletRequest request;
}
