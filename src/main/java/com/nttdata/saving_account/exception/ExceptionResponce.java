package com.nttdata.saving_account.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponce {
    @JsonFormat(pattern = "dd::MM::yyyy KK:mm a")
    private LocalDateTime fecha;
    private String mensaje;
    private String detalle;
}
