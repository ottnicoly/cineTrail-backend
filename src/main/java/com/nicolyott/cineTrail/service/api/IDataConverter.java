package com.nicolyott.cineTrail.service.api;

public interface IDataConverter {

    <T> T convertData(String json, Class<T> tClass);
}
