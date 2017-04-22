package de.oio.vaadin.services.application;

import de.oio.vaadin.demo.componenthighlighter.ComponentHighlighterDemo;
import de.oio.vaadin.demo.fieldgroupselectnestedjavabeans.FieldGroupSelectNestedJavaBeansDemo;
import de.oio.vaadin.demo.i18nforcustomlayoutsusingthymeleaf.I18nForCustomLayoutsUsingThymeleafDemo;
import de.oio.vaadin.demo.suggestingcombobox.SuggestingComboBoxDemo;
import de.oio.vaadin.demo.uiscope.UsingSessionAndUIScopeDemo;
import de.oio.vaadin.demo.urifragmentactions.UriFragmentActionsDemo;
import de.oio.vaadin.uriactions.command.*;
import lombok.extern.slf4j.Slf4j;
import org.roklib.urifragmentrouting.UriActionMapperTree;
import org.roklib.urifragmentrouting.mapper.UriPathSegmentActionMapper;
import org.roklib.urifragmentrouting.parameter.SingleStringUriParameter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

@Component
@Slf4j
public class UriActionMapperTreeService {

  public final static String HOME = "home";
  public final static String DEMOS = "demos";
  public final static String ABOUT = "about";
  public final static String LANG_PARAMETER = "lang";

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
        I18nForCustomLayoutsUsingThymeleafDemo.DEMO_NAME,
        FieldGroupSelectNestedJavaBeansDemo.DEMO_NAME,
        UriFragmentActionsDemo.DEMO_NAME
    ));

    SingleStringUriParameter languageParameter = new SingleStringUriParameter(LANG_PARAMETER);
    languageParameter.setOptional("en");

    UriActionMapperTree.MapperTreeBuilder mapperTreeBuilder = UriActionMapperTree.create()
        .useDefaultActionCommandFactory(ShowErrorActionCommand::new)
        .setRootActionCommandFactory(() -> new RedirectActionCommand(actionMappers.get(HOME)))
        .registerRootActionMapperParameter(languageParameter)
        .buildMapperTree()
        .map(HOME).onActionFactory(ShowHomeUriActionCommand::new).finishMapper(mapper -> actionMappers.put(HOME, mapper))
        .map(ABOUT).onActionFactory(ShowAboutViewUriActionCommand::new).finishMapper(mapper -> actionMappers.put(ABOUT, mapper))
        .map(DEMOS).onActionFactory(ShowDemoSelectionViewUriActionCommand::new).finishMapper(mapper -> actionMappers.put(DEMOS, mapper))
        .mapSubtree("demo").onSubtree();

    for (String demoName : demos) {
      mapperTreeBuilder = mapperTreeBuilder.map(demoName)
          .onActionFactory(() -> new ShowDemoUriActionCommand(demoName))
          .finishMapper(mapper -> actionMappers.put(demoName, mapper));
    }

    uriActionMapperTree = mapperTreeBuilder.finishMapper().build();
    log.info("Initialized {} with following URI action mapper tree:", getClass().getName());
    uriActionMapperTree.getMapperOverview().forEach(log::info);
  }
}
