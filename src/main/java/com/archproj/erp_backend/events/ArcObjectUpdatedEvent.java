package com.archproj.erp_backend.events;

import com.archproj.erp_backend.models.ArcObject;
import org.springframework.context.ApplicationEvent;

public class ArcObjectUpdatedEvent extends ApplicationEvent {

    private final ArcObject arcObject;

    public ArcObjectUpdatedEvent(Object source, ArcObject arcObject) {
        super(source);
        this.arcObject = arcObject;
    }

    public ArcObject getArcObject() {
        return arcObject;
    }
}
