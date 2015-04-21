package de.oio.ui.vaadin;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.SingleComponentContainer;
import com.vaadin.ui.UI;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author rkrueger
 */
public class FixedNavigator extends Navigator {
    public FixedNavigator(UI ui, SingleComponentContainer container) {
        super(ui, container);
    }

    private List<ViewChangeListener> listeners = new LinkedList<ViewChangeListener>();

    @Override
    protected void fireAfterViewChange(ViewChangeListener.ViewChangeEvent event) {
        for (ViewChangeListener l : new ArrayList<>(listeners)) {
            l.afterViewChange(event);
        }
    }

    @Override
    protected boolean fireBeforeViewChange(ViewChangeListener.ViewChangeEvent event) {
        for (ViewChangeListener l : new ArrayList<>(listeners)) {
            if (!l.beforeViewChange(event)) {
                return false;
            }
        }
        return true;
    }

    public void addViewChangeListener(ViewChangeListener listener) {
        listeners.add(listener);
    }

    public void removeViewChangeListener(ViewChangeListener listener) {
        listeners.remove(listener);
    }
}
