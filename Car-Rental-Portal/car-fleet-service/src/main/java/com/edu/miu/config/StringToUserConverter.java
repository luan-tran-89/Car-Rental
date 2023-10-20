package com.edu.miu.config;

import com.edu.miu.dto.CarDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * https://stackoverflow.com/questions/52818107/how-to-send-the-multipart-file-and-json-data-to-spring-boot
 *
 * @author gasieugru
 */
@Component
public class StringToUserConverter implements Converter<String, CarDto> {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    @SneakyThrows
    public CarDto convert(String source) {
        return objectMapper.readValue(source, CarDto.class);
    }

}
