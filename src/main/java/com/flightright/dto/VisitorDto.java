package com.flightright.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VisitorDto {
    private String email;
    private String phone;
    private String source;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VisitorDto that = (VisitorDto) o;
        return email.equals(that.email) && phone.equals(that.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, phone);
    }
}
