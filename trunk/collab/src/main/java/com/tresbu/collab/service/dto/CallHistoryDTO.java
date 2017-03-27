package com.tresbu.collab.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.tresbu.collab.domain.enumeration.CallType;
import com.tresbu.collab.domain.enumeration.CallStatus;

/**
 * A DTO for the CallHistory entity.
 */
public class CallHistoryDTO implements Serializable {

    private Long id;

    @NotNull
    private CallType callType;

    @NotNull
    private CallStatus callStatus;

    private Long callDuration;


    private Long callerId;
    

    private String callerLogin;

    private Long calleeId;
    

    private String calleeLogin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public CallType getCallType() {
        return callType;
    }

    public void setCallType(CallType callType) {
        this.callType = callType;
    }
    public CallStatus getCallStatus() {
        return callStatus;
    }

    public void setCallStatus(CallStatus callStatus) {
        this.callStatus = callStatus;
    }
    public Long getCallDuration() {
        return callDuration;
    }

    public void setCallDuration(Long callDuration) {
        this.callDuration = callDuration;
    }

    public Long getCallerId() {
        return callerId;
    }

    public void setCallerId(Long userId) {
        this.callerId = userId;
    }


    public String getCallerLogin() {
        return callerLogin;
    }

    public void setCallerLogin(String userLogin) {
        this.callerLogin = userLogin;
    }

    public Long getCalleeId() {
        return calleeId;
    }

    public void setCalleeId(Long userId) {
        this.calleeId = userId;
    }


    public String getCalleeLogin() {
        return calleeLogin;
    }

    public void setCalleeLogin(String userLogin) {
        this.calleeLogin = userLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CallHistoryDTO callHistoryDTO = (CallHistoryDTO) o;

        if ( ! Objects.equals(id, callHistoryDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CallHistoryDTO{" +
            "id=" + id +
            ", callType='" + callType + "'" +
            ", callStatus='" + callStatus + "'" +
            ", callDuration='" + callDuration + "'" +
            '}';
    }
}
