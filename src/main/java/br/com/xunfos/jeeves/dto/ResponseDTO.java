package br.com.xunfos.jeeves.dto;

import lombok.Builder;
import lombok.Data;

/**
 * Created by rrc on 31/05/2017.
 */
@Builder
@Data
public class ResponseDTO {
    private String color = "green";
    private String message;
    private boolean notify = false;
    private String messageFormat = "text";

}
