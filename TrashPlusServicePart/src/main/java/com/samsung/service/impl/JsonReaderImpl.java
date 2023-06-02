package com.samsung.service.impl;

import com.samsung.service.JsonReader;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import java.io.*;
import java.net.URI;
import java.nio.charset.Charset;

public class JsonReaderImpl implements JsonReader {

    public static JSONObject readFromLink(String url) throws IOException {
        String json = IOUtils.toString(URI.create(url), Charset.forName("UTF-8"));
        return new JSONObject(json);
    }
}
