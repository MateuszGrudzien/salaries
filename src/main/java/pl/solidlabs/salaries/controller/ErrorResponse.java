package pl.solidlabs.salaries.controller;

import lombok.AllArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class ErrorResponse {

    private String error;

    public ErrorResponse(List<String> errors) {
        this.error = errors.stream().collect(Collectors.joining("; "));
    }

    @Override
    public String toString() {
        return "{\"error\":\"" + this.error + "\"}";
    }
}
