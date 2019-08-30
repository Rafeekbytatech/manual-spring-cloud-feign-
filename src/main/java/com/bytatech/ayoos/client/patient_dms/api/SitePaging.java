package com.bytatech.ayoos.client.patient_dms.api;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;

import org.springframework.validation.annotation.Validated;


/**
 * SitePaging
 */

public class SitePaging   {
  @JsonProperty("list")
  private Object list = null;

  public SitePaging list(Object list) {
    this.list = list;
    return this;
  }

  /**
   * Get list
   * @return list
  **/
 

  public Object getList() {
    return list;
  }

  public void setList(Object list) {
    this.list = list;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SitePaging sitePaging = (SitePaging) o;
    return Objects.equals(this.list, sitePaging.list);
  }

  @Override
  public int hashCode() {
    return Objects.hash(list);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SitePaging {\n");
    
    sb.append("    list: ").append(toIndentedString(list)).append("\n");
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

