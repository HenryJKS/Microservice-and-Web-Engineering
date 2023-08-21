package br.com.fiap.mspagamentos.dto;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ValidationCustomErrorDTO {

    private Instant timestamp;
    private Integer status;
    private String error;
    private String path;

    // lista de erros de campos
    private List<FieldMessageDTO> listFieldErrors = new ArrayList<>();

    public List<FieldMessageDTO> getFieldMessageDTOS() {
        return listFieldErrors;
    }

    public void addError(String fieldName, String message) {
        listFieldErrors.add(new FieldMessageDTO(fieldName, message));
    }




    public ValidationCustomErrorDTO(Instant timestamp, Integer status, String error, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.path = path;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public Integer getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getPath() {
        return path;
    }
}
