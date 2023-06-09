package com.maurilio.autenticacao.dto;

public class ErroDto {
    public ErroDto(int status, String message) {
        this.status = status;
        this.message = message;
    }

    private int status;

    private String message;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }



}
