package br.com.xunfos.jeeves;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class QueueEntry {

    private String username;
    private String mention;

}
