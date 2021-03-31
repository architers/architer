package com.lz.core.excel.html.configuration;

import com.lz.core.excel.html.parser.DefaultHtmlParser;
import com.lz.core.excel.html.parser.DefaultHtmlStyleParser;
import com.lz.core.excel.html.parser.HtmlParser;
import com.lz.core.excel.html.parser.HtmlStyleParser;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author luyi
 * html解析配置类
 */
@Configuration
public class HtmlParserConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public DefaultHtmlStyleParser defaultHtmlStyleParser() {
        return new DefaultHtmlStyleParser();
    }


    @Bean
    @ConditionalOnMissingBean
    public HtmlParser htmlParser(List<HtmlStyleParser> htmlStyleParsers) {
        return new DefaultHtmlParser(htmlStyleParsers);
    }
}
