package it.powercolle.dto;

import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto {
    private String msg;
    private Boolean error;
    private Response.Status code;

    public ResponseDto(String msg, Boolean error) {
        this.msg = msg;
        this.error = error;
    }
}
