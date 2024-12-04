package com.nicolyott.cineTrail.service;

public interface IDataConverter {

    <T> T convertData(String json, Class<T> tClass);
}
