package com.health.meditrack.service;

import com.health.meditrack.entity.Appointment;
import com.health.meditrack.exception.EntityNotFoundException;
import com.health.meditrack.service.interfaces.Observer;
import com.health.meditrack.service.interfaces.Subject;
import static com.health.meditrack.util.Validator.isNull;
import java.util.ArrayList;
import java.util.List;

public class NotificationServiceImpl implements Subject {

        private final List<Observer> observers = new ArrayList<>();

        /**
         * Register an observer
         */
        @Override
        public void addObserver(Observer observer) {

                if (isNull(observer)) {
                        throw new EntityNotFoundException("Attempted to add null observer");
                }

                observers.add(observer);

                System.out.println("Observer registered: " + observer.getClass().getSimpleName());
        }

        /**
         * Remove observer
         */
        @Override
        public void removeObserver(Observer observer) {

                observers.remove(observer);

                System.out.println("Observer removed: " + observer.getClass().getSimpleName());
        }

        /**
         * Notify all observers
         */
        @Override
        public void notifyObservers(Appointment appointment) {

                System.out.println("Sending notifications to observers");

                for (Observer observer : observers) {

                        try {
                                observer.update(appointment);
                        } catch (Exception e) {

                                System.out.println("Failed notifying observer: "
                                                + observer.getClass().getSimpleName());
                        }
                }
        }

}
