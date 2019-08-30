package com.bytatech.ayoos.client.patient_dms.model;

import java.util.Objects;
import com.bytatech.ayoos.client.patient_dms.model.ContentInfo;
import com.bytatech.ayoos.client.patient_dms.model.Node;
import com.bytatech.ayoos.client.patient_dms.model.PathInfo;
import com.bytatech.ayoos.client.patient_dms.model.PermissionsInfo;
import com.bytatech.ayoos.client.patient_dms.model.UserInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * DeletedNode
 */
@Validated
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2019-05-10T09:58:46.053+05:30[Asia/Kolkata]")

public class DeletedNode extends Node  {
  @JsonProperty("archivedByUser")
  private UserInfo archivedByUser = null;

  @JsonProperty("archivedAt")
  private OffsetDateTime archivedAt = null;

  public DeletedNode archivedByUser(UserInfo archivedByUser) {
    this.archivedByUser = archivedByUser;
    return this;
  }

  /**
   * Get archivedByUser
   * @return archivedByUser
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public UserInfo getArchivedByUser() {
    return archivedByUser;
  }

  public void setArchivedByUser(UserInfo archivedByUser) {
    this.archivedByUser = archivedByUser;
  }

  public DeletedNode archivedAt(OffsetDateTime archivedAt) {
    this.archivedAt = archivedAt;
    return this;
  }

  /**
   * Get archivedAt
   * @return archivedAt
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public OffsetDateTime getArchivedAt() {
    return archivedAt;
  }

  public void setArchivedAt(OffsetDateTime archivedAt) {
    this.archivedAt = archivedAt;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DeletedNode deletedNode = (DeletedNode) o;
    return Objects.equals(this.archivedByUser, deletedNode.archivedByUser) &&
        Objects.equals(this.archivedAt, deletedNode.archivedAt) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(archivedByUser, archivedAt, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DeletedNode {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    archivedByUser: ").append(toIndentedString(archivedByUser)).append("\n");
    sb.append("    archivedAt: ").append(toIndentedString(archivedAt)).append("\n");
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

