package com.bytatech.ayoos.client.patient_dms.model;

import java.util.Objects;
import com.bytatech.ayoos.client.patient_dms.model.UserInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * AuditEntry
 */
@Validated
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2019-05-10T09:58:46.053+05:30[Asia/Kolkata]")

public class AuditEntry   {
  @JsonProperty("id")
  private String id = null;

  @JsonProperty("auditApplicationId")
  private String auditApplicationId = null;

  @JsonProperty("createdByUser")
  private UserInfo createdByUser = null;

  @JsonProperty("createdAt")
  private OffsetDateTime createdAt = null;

  @JsonProperty("values")
  @Valid
  private Map<String, Object> values = null;

  public AuditEntry id(String id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public AuditEntry auditApplicationId(String auditApplicationId) {
    this.auditApplicationId = auditApplicationId;
    return this;
  }

  /**
   * Get auditApplicationId
   * @return auditApplicationId
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getAuditApplicationId() {
    return auditApplicationId;
  }

  public void setAuditApplicationId(String auditApplicationId) {
    this.auditApplicationId = auditApplicationId;
  }

  public AuditEntry createdByUser(UserInfo createdByUser) {
    this.createdByUser = createdByUser;
    return this;
  }

  /**
   * Get createdByUser
   * @return createdByUser
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public UserInfo getCreatedByUser() {
    return createdByUser;
  }

  public void setCreatedByUser(UserInfo createdByUser) {
    this.createdByUser = createdByUser;
  }

  public AuditEntry createdAt(OffsetDateTime createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  /**
   * Get createdAt
   * @return createdAt
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public OffsetDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(OffsetDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public AuditEntry values(Map<String, Object> values) {
    this.values = values;
    return this;
  }

  public AuditEntry putValuesItem(String key, Object valuesItem) {
    if (this.values == null) {
      this.values = new HashMap<String, Object>();
    }
    this.values.put(key, valuesItem);
    return this;
  }

  /**
   * Get values
   * @return values
  **/
  @ApiModelProperty(value = "")


  public Map<String, Object> getValues() {
    return values;
  }

  public void setValues(Map<String, Object> values) {
    this.values = values;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AuditEntry auditEntry = (AuditEntry) o;
    return Objects.equals(this.id, auditEntry.id) &&
        Objects.equals(this.auditApplicationId, auditEntry.auditApplicationId) &&
        Objects.equals(this.createdByUser, auditEntry.createdByUser) &&
        Objects.equals(this.createdAt, auditEntry.createdAt) &&
        Objects.equals(this.values, auditEntry.values);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, auditApplicationId, createdByUser, createdAt, values);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AuditEntry {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    auditApplicationId: ").append(toIndentedString(auditApplicationId)).append("\n");
    sb.append("    createdByUser: ").append(toIndentedString(createdByUser)).append("\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
    sb.append("    values: ").append(toIndentedString(values)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

