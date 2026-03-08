package com.airtribe.meditrack.service.interfaces;

import com.airtribe.meditrack.entity.Appointment;

public interface Observer {

    void update(Appointment message);

}
