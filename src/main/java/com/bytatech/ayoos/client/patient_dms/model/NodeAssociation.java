package com.bytatech.ayoos.client.patient_dms.model;

import java.util.Objects;
import com.bytatech.ayoos.client.patient_dms.model.AssociationInfo;
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
 * NodeAssociation
 */
@Validated
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2019-05-10T09:58:46.053+05:30[Asia/Kolkata]")

public class NodeAssociation extends Node  {
  @JsonProperty("association")
  private AssociationInfo association = null;

  public NodeAssociation association(AssociationInfo association) {
    this.association = association;
    return this;
  }

  /**
   * Get association
   * @return association
  **/
  @ApiModelProperty(value = "")

  @Valid

  public AssociationInfo getAssociation() {
    return association;
  }

  public void setAssociation(AssociationInfo association) {
    this.association = association;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NodeAssociation nodeAssociation = (NodeAssociation) o;
    return Objects.equals(this.association, nodeAssociation.association) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(association, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NodeAssociation {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    association: ").append(toIndentedString(association)).append("\n");
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

