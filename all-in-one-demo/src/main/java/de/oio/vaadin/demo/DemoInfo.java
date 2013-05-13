package de.oio.vaadin.demo;

import lombok.Data;

@Data
public class DemoInfo {
	private String codeHostingURI;
	private String blogPostURI;
	private String blogPostTitle;

	private String shortDescriptionKey;
	private String demoHeadlineKey;
}
