package com.tresbu.collab.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

import com.tresbu.collab.domain.enumeration.CallType;

import com.tresbu.collab.domain.enumeration.CallStatus;

/**
 * A CallHistory.
 */
@Entity
@Table(name = "collab_call_history")
public class CallHistory extends AbstractEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "call_type", nullable = false)
    private CallType callType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "call_status", nullable = false)
    private CallStatus callStatus;

    @Column(name = "call_duration")
    private Long callDuration;

    @ManyToOne
    @NotNull
    private User caller;

    @ManyToOne
    @NotNull
    private User callee;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CallType getCallType() {
        return callType;
    }

    public CallHistory callType(CallType callType) {
        this.callType = callType;
        return this;
    }

    public void setCallType(CallType callType) {
        this.callType = callType;
    }

    public CallStatus getCallStatus() {
        return callStatus;
    }

    public CallHistory callStatus(CallStatus callStatus) {
        this.callStatus = callStatus;
        return this;
    }

    public void setCallStatus(CallStatus callStatus) {
        this.callStatus = callStatus;
    }

    public Long getCallDuration() {
        return callDuration;
    }

    public CallHistory callDuration(Long callDuration) {
        this.callDuration = callDuration;
        return this;
    }

    public void setCallDuration(Long callDuration) {
        this.callDuration = callDuration;
    }

    public User getCaller() {
        return caller;
    }

    public CallHistory caller(User user) {
        this.caller = user;
        return this;
    }

    public void setCaller(User user) {
        this.caller = user;
    }

    public User getCallee() {
        return callee;
    }

    public CallHistory callee(User user) {
        this.callee = user;
        return this;
    }

    public void setCallee(User user) {
        this.callee = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CallHistory callHistory = (CallHistory) o;
        if(callHistory.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, callHistory.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CallHistory{" +
            "id=" + id +
            ", callType='" + callType + "'" +
            ", callStatus='" + callStatus + "'" +
            ", callDuration='" + callDuration + "'" +
            '}';
    }
}
