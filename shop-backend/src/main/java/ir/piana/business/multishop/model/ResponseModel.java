package ir.piana.business.multishop.model;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseModel<T> {
    private int code;
    private T data;
}
