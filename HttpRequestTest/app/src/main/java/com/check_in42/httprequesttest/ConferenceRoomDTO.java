package com.check_in42.httprequesttest;

import java.time.LocalDate;

public class ConferenceRoomDTO {
    private Long formId;
    private Long userId;
    private String intraId;
    private LocalDate date;
    private Long reservationCount;
    private Long reservationInfo;
    private boolean checkInState;

    ConferenceRoomDTO() {
    }

    public Long getFormId() {
        return formId;
    }

    public void setFormId(Long formId) {
        this.formId = formId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getIntraId() {
        return intraId;
    }

    public void setIntraId(String intraId) {
        this.intraId = intraId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getReservationCount() {
        return reservationCount;
    }

    public void setReservationCount(Long reservationCount) {
        this.reservationCount = reservationCount;
    }

    public Long getReservationInfo() {
        return reservationInfo;
    }

    public void setReservationInfo(Long reservationInfo) {
        this.reservationInfo = reservationInfo;
    }

    public boolean isCheckInState() {
        return checkInState;
    }

    public void setCheckInState(boolean checkInState) {
        this.checkInState = checkInState;
    }
}
