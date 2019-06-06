package com.octoperf.maven.plugin.context;

import com.octoperf.Application;
import com.octoperf.maven.api.Workspaces;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import static java.util.Objects.requireNonNull;
import static lombok.AccessLevel.PRIVATE;

@FieldDefaults(level = PRIVATE, makeFinal = true)
public final class SpringContexts implements SpringContextService {
  GenericApplicationContext context;

  public SpringContexts(final String apiKey, final String serverUrl) {
    System.setProperty("apiKey", apiKey);
    System.setProperty("serverUrl", serverUrl);
    final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
    context.register(Application.class);
    context.refresh();
    this.context = requireNonNull(context);
  }

  @Override
  public Workspaces getWorkspaces() {
    return context.getBean(Workspaces.class);
  }
}
