package com.nicolyott.cineTrail.service;

public interface IConverteDados {

    <T> T converteDados(String json, Class<T> tClass);
}
