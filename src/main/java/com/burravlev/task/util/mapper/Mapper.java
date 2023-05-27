package com.burravlev.task.util.mapper;

public interface Mapper<From, To> {
    To map(From from);
}
