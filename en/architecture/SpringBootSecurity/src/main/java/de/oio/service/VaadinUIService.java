package de.oio.service;

/**
 * @author Roland Krüger
 */
public interface VaadinUIService {
    void postNavigationEvent(Object source, String target);

    boolean isUserAnonymous();
}
