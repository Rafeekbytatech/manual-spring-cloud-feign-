package com.bytatech.ayoos.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import org.springframework.data.elasticsearch.annotations.GeoPointField;
/**
 * A Patient.
 */
@Entity
@Table(name = "patient")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "patient")
public class Patient implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "image_content_type")
    private String imageContentType;

    @Column(name = "phone_number")
    private Long phoneNumber;

    @Column(name = "patient_code")
    private String patientCode;

    @Column(name = "dob")
    private LocalDate dob;

    @Column(name = "gender")
    private String gender;

    @Column(name = "location")
    @GeoPointField
    private String location;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Column(name = "dms_id")
    private String dmsId;

    @OneToMany(mappedBy = "patient")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AddressLine> addressLines = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getImage() {
        return image;
    }

    public Patient image(byte[] image) {
        this.image = image;
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public Patient imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public Patient phoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPatientCode() {
        return patientCode;
    }

    public Patient patientCode(String patientCode) {
        this.patientCode = patientCode;
        return this;
    }

    public void setPatientCode(String patientCode) {
        this.patientCode = patientCode;
    }

    public LocalDate getDob() {
        return dob;
    }

    public Patient dob(LocalDate dob) {
        this.dob = dob;
        return this;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public Patient gender(String gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLocation() {
        return location;
    }

    public Patient location(String location) {
        this.location = location;
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public Patient createdDate(LocalDate createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public String getDmsId() {
        return dmsId;
    }

    public Patient dmsId(String dmsId) {
        this.dmsId = dmsId;
        return this;
    }

    public void setDmsId(String dmsId) {
        this.dmsId = dmsId;
    }

    public Set<AddressLine> getAddressLines() {
        return addressLines;
    }

    public Patient addressLines(Set<AddressLine> addressLines) {
        this.addressLines = addressLines;
        return this;
    }

    public Patient addAddressLines(AddressLine addressLine) {
        this.addressLines.add(addressLine);
        addressLine.setPatient(this);
        return this;
    }

    public Patient removeAddressLines(AddressLine addressLine) {
        this.addressLines.remove(addressLine);
        addressLine.setPatient(null);
        return this;
    }

    public void setAddressLines(Set<AddressLine> addressLines) {
        this.addressLines = addressLines;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Patient patient = (Patient) o;
        if (patient.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), patient.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Patient{" +
            "id=" + getId() +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + getImageContentType() + "'" +
            ", phoneNumber=" + getPhoneNumber() +
            ", patientCode='" + getPatientCode() + "'" +
            ", dob='" + getDob() + "'" +
            ", gender='" + getGender() + "'" +
            ", location='" + getLocation() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", dmsId='" + getDmsId() + "'" +
            "}";
    }
}
