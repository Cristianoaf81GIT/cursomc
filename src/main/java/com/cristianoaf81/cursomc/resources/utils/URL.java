package com.cristianoaf81.cursomc.resources.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class URL {

  public static String decodeParam(String s) {
    try {
      return URLDecoder.decode(s, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
      return "";
    }
  }

  public static List<Integer> decodeIntList(String s) {
    Function<String, Integer> convertToInteger = Integer::parseInt;
    return Arrays.asList(s.split(",")).stream().map(convertToInteger).collect(Collectors.toList());
  }
}
