package com.miller.bdm.app.privateShop.templates.provider;

import com.miller.bdm.app.privateShop.templates.request.GetTemplatesRequestDTO;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;
@SuppressWarnings(value = "unused")
public class GetTemplatesDataProvider {
    static Stream<Arguments> GetTemplatesList() {
        GetTemplatesRequestDTO GetTemplatesRequestDTO = new GetTemplatesRequestDTO();
        GetTemplatesRequestDTO.setCategoryId(1L);
        return Stream.of(
                arguments(GetTemplatesRequestDTO)
        );
    }
}
