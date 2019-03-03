package com.endava.batch.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JobResultDTO {

    private long jobId;

    private Object jobResult;
}
