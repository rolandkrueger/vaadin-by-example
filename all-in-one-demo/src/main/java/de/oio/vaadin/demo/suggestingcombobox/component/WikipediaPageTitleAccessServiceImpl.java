package de.oio.vaadin.demo.suggestingcombobox.component;

import de.oio.vaadin.DemoUI;
import org.roklib.collections.TernarySearchTreeSet;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Component
public class WikipediaPageTitleAccessServiceImpl implements WikipediaPageTitleAccessService {

  private TernarySearchTreeSet searchMap;

  public WikipediaPageTitleAccessServiceImpl() {
    searchMap = new TernarySearchTreeSet(true);
  }

  @Override
  public List<WikipediaPage> filterCountryTableInDatabase(String filterPrefix) {
    if (filterPrefix == null || "".equals(filterPrefix.trim())) {
      return Collections.emptyList();
    }

    List<WikipediaPage> result = new ArrayList<WikipediaPage>();
    long id = 0;
    final Iterable<CharSequence> prefixMatch = searchMap.getPrefixMatch(filterPrefix);
    for (CharSequence entry : prefixMatch) {
      result.add(new WikipediaPage(++id, entry.toString()));
    }

    return result;
  }

  @PostConstruct
  public void populateSearchMap() throws Exception {
    long id = 0;
    ZipInputStream zis = new ZipInputStream(DemoUI.class.getResourceAsStream("/wikipedia-pages-extract-en.zip"));
    final ZipEntry entry = zis.getNextEntry();

    BufferedReader reader = new BufferedReader(new InputStreamReader(zis));
    String line;
    while ((line = reader.readLine()) != null) {
      searchMap.add(line);
    }
  }
}
