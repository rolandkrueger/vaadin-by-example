package de.oio.vaadin;

import com.vaadin.annotations.Theme;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import org.roklib.urifragmentrouting.UriActionCommand;
import org.roklib.urifragmentrouting.UriActionMapperTree;
import org.roklib.urifragmentrouting.annotation.AllCapturedParameters;
import org.roklib.urifragmentrouting.annotation.RoutingContext;
import org.roklib.urifragmentrouting.mapper.UriPathSegmentActionMapper;
import org.roklib.urifragmentrouting.parameter.value.CapturedParameterValues;
import org.roklib.urifragmentrouting.parameter.value.ParameterValue;
import org.vaadin.uriactions.UriFragmentActionNavigatorWrapper;

import java.util.HashMap;
import java.util.Map;

@Theme("valo")
public class URIFragmentActionsDemoUI extends UI {

    private static final String HOME = "home";
    private static final String PROFILE = "profile";
    private static final String USERNAME_PARAM = "username";
    private static final String USER = "user";

    private static UriActionMapperTree actionMapperTree;
    private static Map<String, UriPathSegmentActionMapper> mappers;

    static {
        // Registry for the action mappers. Will be needed later for assembling HTML links
        mappers = new HashMap<>();

        // @formatter:off
        // build a URI action mapper tree which can handle the following URI fragments
        // #!home
        // #!user/username/john.doe/profile
        // #!user/username/joe.average/profile
        // etc.
        actionMapperTree = UriActionMapperTree.create()
                // redirect to '#!home' by default when no URI fragment is present
                // use lambda in a lambda construct
                .setRootActionCommandFactory(() -> () -> UI.getCurrent().getNavigator().navigateTo(HOME))
                // start building the active URI fragment hierarchy
                .buildMapperTree()
                // build mapper for '#!home'
                .map(HOME).onActionFactory(() -> new LogToLabelCommand("Welcome to the home screen."))
                    // store mapper for later reference
                    .finishMapper(mapper -> mappers.put(HOME, mapper))
                // build mapper for the '#!user' hierarchy
                .mapSubtree(USER)
                    // add a single-valued parameter 'username' to the 'user' action mapper
                    .withSingleValuedParameter(USERNAME_PARAM).forType(String.class).noDefault().onSubtree()
                    // build mapper for '#!user/.../profile'
                    .map(PROFILE).onActionFactory(
                            () -> new LogToLabelCommand("This is a user profile page"))
                    // finish the 'profile' mapper and store it for later reference
                    .finishMapper(mapper -> mappers.put(PROFILE, mapper))
                // finish the 'user' mapper
                .finishMapper()
                // finish and build the action mapper tree
                .build();
        // @formatter:on
    }

    @Override
    protected void init(VaadinRequest request) {
        Label label = new Label();
        label.setStyleName(ValoTheme.LABEL_H1);

        // build and configure the UriFragmentActionNavigatorWrapper
        UriFragmentActionNavigatorWrapper navigatorWrapper = new UriFragmentActionNavigatorWrapper(this);

        // set the routing context
        navigatorWrapper.setRoutingContext(new MyRoutingContext(label));

        // set the application-scoped URI action mapper tree
        navigatorWrapper.setUriActionMapperTree(actionMapperTree);

        VerticalLayout layout = new VerticalLayout();
        layout.addComponent(label);

        // add some links
        // link to home screen
        layout.addComponent(new Link("Home Screen",
                new ExternalResource("#!" + actionMapperTree.assembleUriFragment(mappers.get(HOME)))));

        // link to some user profiles
        CapturedParameterValues parameters = new CapturedParameterValues();
        parameters.setValueFor(USER, USERNAME_PARAM, ParameterValue.forValue("john.doe"));
        layout.addComponent(new Link("Visit John Doe's Profile",
                new ExternalResource("#!" + actionMapperTree.assembleUriFragment(parameters, mappers.get(PROFILE)))));

        parameters.setValueFor(USER, USERNAME_PARAM, ParameterValue.forValue("joe.average"));
        layout.addComponent(new Link("Visit Joe Average's Profile",
                new ExternalResource("#!" + actionMapperTree.assembleUriFragment(parameters, mappers.get(PROFILE)))));

        parameters.setValueFor(USER, USERNAME_PARAM, ParameterValue.forValue("dan.developer"));
        layout.addComponent(new Link("Visit Dan Developer's Profile",
                new ExternalResource("#!" + actionMapperTree.assembleUriFragment(parameters, mappers.get(PROFILE)))));
        setContent(layout);
    }

    /**
     * Context object which is used to transport current context information to the currently executed URI action
     * command. In this case, the context contains a reference to the label object of the current UI.
     */
    public static class MyRoutingContext {
        private Label label;

        public MyRoutingContext(Label label) {
            this.label = label;
        }

        public Label getLabel() {
            return label;
        }
    }

    /**
     * Action command which will be executed for each new navigator state. The command will set a new text on the label
     * object found in the routing context object.
     */
    public static class LogToLabelCommand implements UriActionCommand {
        private String text;
        private MyRoutingContext context;
        private CapturedParameterValues parameters;

        public LogToLabelCommand(String text) {
            this.text = text;
        }

        @Override
        public void run() {
            if (!parameters.isEmpty()) {
                text = text + ": " + parameters.asQueryParameterMap();
            }
            context.getLabel().setValue(text);
        }

        @RoutingContext
        public void setContext(MyRoutingContext context) {
            this.context = context;
        }

        @AllCapturedParameters
        public void setParameters(CapturedParameterValues parameters) {
            this.parameters = parameters;
        }
    }
}
