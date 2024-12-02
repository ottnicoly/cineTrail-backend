package com.nicolyott.cineTrail.service.Api;

public interface IConverteDados {

    <T> T converteDados(String json, Class<T> tClass);
}
