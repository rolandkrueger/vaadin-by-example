package de.oio.vaadin.demo.suggestingcombobox.component;

import java.util.List;

/**
 * Some service that fetches country data from the database.
 */
public interface WikipediaPageTitleAccessService {

  /**
   * Fetches a list of country objects whose names begin with the given filter
   * prefix from the database. This could be implemented with a SQL statement
   * like the following:
   * <p>
   * select * from country where name like filterprefix + '%'
   */
  List<WikipediaPage> filterCountryTableInDatabase(String filterPrefix);
}
