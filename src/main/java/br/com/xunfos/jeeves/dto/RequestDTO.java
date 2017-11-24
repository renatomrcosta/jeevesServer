package br.com.xunfos.jeeves.dto;

import lombok.Builder;
import lombok.Data;

/**
 * DTO for request data. (no shit Sherlock!)
 */
@Builder
@Data
public class RequestDTO {

    private String username;
    private String mention;
    private String room;
}
