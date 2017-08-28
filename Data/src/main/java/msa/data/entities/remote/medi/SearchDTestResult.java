package msa.data.entities.remote.medi;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Abhimuktheeswarar on 05-05-2017
 */


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "test_id",
        "pre_test_condition",
        "test_name",
        "threshold_value",
        "created_by",
        "updated_by",
        "created_at",
        "updated_at",
        "is_deleted"
})
public class SearchDTestResult {

    @JsonProperty("test_id")
    private String testId;
    @JsonProperty("pre_test_condition")
    private String preTestCondition;
    @JsonProperty("test_name")
    private String testName;
    @JsonProperty("threshold_value")
    private String thresholdValue;
    @JsonProperty("created_by")
    private String createdBy;
    @JsonProperty("updated_by")
    private String updatedBy;
    @JsonProperty("created_at")
    private String createdAt;
    @JsonProperty("updated_at")
    private String updatedAt;
    @JsonProperty("is_deleted")
    private Integer isDeleted;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("test_id")
    public String getTestId() {
        return testId;
    }

    @JsonProperty("test_id")
    public void setTestId(String testId) {
        this.testId = testId;
    }

    @JsonProperty("pre_test_condition")
    public String getPreTestCondition() {
        return preTestCondition;
    }

    @JsonProperty("pre_test_condition")
    public void setPreTestCondition(String preTestCondition) {
        this.preTestCondition = preTestCondition;
    }

    @JsonProperty("test_name")
    public String getTestName() {
        return testName;
    }

    @JsonProperty("test_name")
    public void setTestName(String testName) {
        this.testName = testName;
    }

    @JsonProperty("threshold_value")
    public String getThresholdValue() {
        return thresholdValue;
    }

    @JsonProperty("threshold_value")
    public void setThresholdValue(String thresholdValue) {
        this.thresholdValue = thresholdValue;
    }

    @JsonProperty("created_by")
    public String getCreatedBy() {
        return createdBy;
    }

    @JsonProperty("created_by")
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @JsonProperty("updated_by")
    public String getUpdatedBy() {
        return updatedBy;
    }

    @JsonProperty("updated_by")
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    @JsonProperty("created_at")
    public String getCreatedAt() {
        return createdAt;
    }

    @JsonProperty("created_at")
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @JsonProperty("updated_at")
    public String getUpdatedAt() {
        return updatedAt;
    }

    @JsonProperty("updated_at")
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    @JsonProperty("is_deleted")
    public Integer getIsDeleted() {
        return isDeleted;
    }

    @JsonProperty("is_deleted")
    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}