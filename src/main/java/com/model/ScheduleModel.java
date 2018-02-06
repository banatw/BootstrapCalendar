package com.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Set;

public class ScheduleModel {
    //private Integer success;

    @JsonProperty("monthly")
    private List<EventModel> eventModels;

    public List<EventModel> getEventModels() {
        return eventModels;
    }

    public void setEventModels(List<EventModel> eventModels) {
        this.eventModels = eventModels;
    }

}
