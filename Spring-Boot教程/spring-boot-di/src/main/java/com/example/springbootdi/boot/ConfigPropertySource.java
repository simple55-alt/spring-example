package com.example.springbootdi.boot;

import com.example.springbootdi.model.Config;
import org.springframework.core.env.EnumerablePropertySource;

public class ConfigPropertySource extends EnumerablePropertySource<Config> {


    public ConfigPropertySource(String name, Config source) {
        super(name, source);
    }


    @Override
    public Object getProperty(String name) {
        return "";
    }

    @Override
    public String[] getPropertyNames() {
        return new String[]{"hello","world"};
    }



}

