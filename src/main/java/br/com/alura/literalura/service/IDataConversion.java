package br.com.alura.literalura.service;

import java.util.List;

public interface IDataConversion {
    <T> T getData(String json, Class<T> classes);
    <T> List<T> getList(String json, Class<T> classes);
}