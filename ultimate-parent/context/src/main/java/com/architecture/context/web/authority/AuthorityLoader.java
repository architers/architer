package com.architecture.context.web.authority;

import org.springframework.http.HttpRequest;

import java.util.Set;

public interface AuthorityLoader {

    Set<String> load(HttpRequest httpRequest);

}
