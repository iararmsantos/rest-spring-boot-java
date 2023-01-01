package com.iarasantos.data.vo.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.dozermapper.core.Mapping;
import org.springframework.hateoas.RepresentationModel;
import java.io.Serializable;
import java.util.Objects;

@JsonPropertyOrder({"id", "firstname", "lastname", "email"})
public class PersonVO extends RepresentationModel<PersonVO> implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("id")
    @Mapping("id")
    private Long key;
    @JsonProperty("firstname")
    private String firstName;
    @JsonProperty("lastname")
    private String lastName;

    //to omit email in the JSON
    //@JSonIgnore
    private String email;

    public PersonVO() {
    }

    public Long getKey() {
        return key;
    }

    public void setKey(Long key) {
        this.key = key;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PersonVO personVO)) return false;
        if (!super.equals(o)) return false;

        if (!Objects.equals(key, personVO.key)) return false;
        if (!Objects.equals(firstName, personVO.firstName)) return false;
        if (!Objects.equals(lastName, personVO.lastName)) return false;
        return Objects.equals(email, personVO.email);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (key != null ? key.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }
}