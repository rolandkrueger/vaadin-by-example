package de.oio.vaadin.services.application;

import de.oio.vaadin.demo.componenthighlighter.ComponentHighlighterDemo;
import de.oio.vaadin.demo.fieldgroupselectnestedjavabeans.FieldGroupSelectNestedJavaBeansDemo;
import de.oio.vaadin.demo.i18nforcustomlayoutsusingvelocity.I18nForCustomLayoutsUsingVelocityDemo;
import de.oio.vaadin.demo.suggestingcombobox.SuggestingComboBoxDemo;
import de.oio.vaadin.demo.uiscope.UsingSessionAndUIScopeDemo;
import de.oio.vaadin.places.AboutPlace;
import de.oio.vaadin.places.DemoPlace;
import de.oio.vaadin.places.DemoSelectionPlace;
import de.oio.vaadin.places.HomePlace;
import lombok.extern.slf4j.Slf4j;
import org.roklib.urifragmentrouting.UriActionMapperTree;
import org.roklib.urifragmentrouting.mapper.UriPathSegmentActionMapper;
import org.springframework.stereotype.Component;
import org.vaadin.appbase.uriactions.commands.PlaceRequestActionCommand;
import org.vaadin.appbase.uriactions.commands.RedirectActionCommand;

import javax.annotation.PostConstruct;
import java.util.*;

@Component
@Slf4j
public class UriActionMapperTreeService {

  public final static String HOME = "home";

  private UriActionMapperTree uriActionMapperTree;
  private Map<String, UriPathSegmentActionMapper> actionMappers;

  protected UriActionMapperTreeService() {
    actionMappers = new HashMap<>();
  }

  public UriActionMapperTree getUriActionMapperTree() {
    return uriActionMapperTree;
  }

  public UriPathSegmentActionMapper getActionMapperForName(String actionMapperName) {
    return actionMappers.get(actionMapperName);
  }

  @PostConstruct
  public void initialize() {
    Set<String> demos = new HashSet<>();
    demos.addAll(Arrays.asList(
        UsingSessionAndUIScopeDemo.DEMO_NAME,
        SuggestingComboBoxDemo.DEMO_NAME,
        ComponentHighlighterDemo.DEMO_NAME,
        I18nForCustomLayoutsUsingVelocityDemo.DEMO_NAME,
        FieldGroupSelectNestedJavaBeansDemo.DEMO_NAME
    ));

    UriActionMapperTree.MapperTreeBuilder mapperTreeBuilder = UriActionMapperTree.create()
        .setRootActionCommandFactory(() -> new RedirectActionCommand(actionMappers.get(HOME)))
        .buildMapperTree()
        .map(HOME).onActionFactory(() -> new PlaceRequestActionCommand(new HomePlace())).finishMapper(mapper -> actionMappers.put(HOME, mapper))
        .map("about").onActionFactory(() -> new PlaceRequestActionCommand(new AboutPlace())).finishMapper()
        .map("demos").onActionFactory(() -> new PlaceRequestActionCommand(new DemoSelectionPlace())).finishMapper()
        .mapSubtree("demo").onSubtree();

    for (String demoName : demos) {
      mapperTreeBuilder = mapperTreeBuilder.map(demoName)
          .onActionFactory(() -> new PlaceRequestActionCommand(new DemoPlace(demoName)))
          .finishMapper(mapper -> actionMappers.put(demoName, mapper));
    }

    uriActionMapperTree = mapperTreeBuilder.finishMapper().build();
    log.info("Initialized {} with following URI action mapper tree:", getClass().getName());
    uriActionMapperTree.getMapperOverview().forEach(log::info);
  }
}
