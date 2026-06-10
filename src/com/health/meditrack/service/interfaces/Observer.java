package com.health.meditrack.service.interfaces;

import com.health.meditrack.entity.Appointment;

public interface Observer {

    void update(Appointment message);

}
